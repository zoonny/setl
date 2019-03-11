package com.kt.mmp.setl.setlDstrb.service;

import com.google.common.base.Preconditions;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.BillWhyCd;
import com.kt.mmp.comn.code.code.MotDivCd;
import com.kt.mmp.comn.code.code.OtgoInstnChCd;
import com.kt.mmp.comn.code.code.PtnrDivCd;
import com.kt.mmp.comn.code.service.CdGroupInfoService;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcDto;
import com.kt.mmp.setl.setlCalc.service.SetlService;
import com.kt.mmp.setl.setlDstrb.domain.PtnrInfo;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtl;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumary;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbDto;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbParam;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbSumary;
import com.kt.mmp.setl.setlDstrb.service.cache.PtnrInfoService;
import com.kt.mmp.setl.setlDstrb.service.cache.SetlItemPtnrDstrbInfoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Deprecated
@Slf4j
@RequiredArgsConstructor
@Service
public class SetlDstrbService extends
    SetlService<SetlDstrbDto, SetlItemMedatDtl, SetlDstrbSumary> {

  private final PtnrInfoService ptnrInfoService;
  private final SetlItemPtnrDstrbInfoService setlItemPtnrDstrbInfoService;
  private final CdGroupInfoService cdGroupInfoService;

  @Override
  protected SetlDstrbDto buildDto(String setlTgtYm) {
    SetlDstrbDto setlDstrbDto = SetlDstrbDto.builder()
        .setlDstrbParam(new SetlDstrbParam(setlTgtYm, LocalDateTime.now(), 0))
        .build();

    buildSetlDstrbSplitSet(setlDstrbDto);

    return setlDstrbDto;
  }

  protected void buildSetlDstrbSplitSet(SetlDstrbDto setlDstrbDto) {
    Map<String, List<SetlItemPtnrDstrbInfo>> setlItemPtnrDstrbInfoMap = setlItemPtnrDstrbInfoService
        .findGroupBySetlItemCdAndPtnrId();
    Preconditions.checkArgument(MapUtils.isNotEmpty(setlItemPtnrDstrbInfoMap));

    setlDstrbDto.getSetlItemPtnrDstrbSplitSetMap().initSplit(setlItemPtnrDstrbInfoMap);
    setlDstrbDto.getSetlItemPtnrDstrbSplitSetMap().sort();
    setlDstrbDto.getSetlItemPtnrDstrbSplitSetMap().printSplits();
  }

  @Override
  protected SetlDstrbSumary doProcess(SetlDstrbDto setlDstrbDto,
      SetlItemMedatDtl setlItemMedatDtl) {

    if (!validate(setlItemMedatDtl)) {
      setlDstrbDto.getSetlErrorItems().addErrorItem(setlItemMedatDtl);
      return null;
    }

    PtnrInfo ptnrInfo = ptnrInfoService.findById(setlItemMedatDtl.getPk().getPtnrId()).get();
    log.info("{}", ptnrInfo);

    SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo = setlDstrbDto.getSetlItemPtnrDstrbSplitSetMap()
        .findEfctEntityByKey(setlItemMedatDtl.getPk().getSetlItemCd() +
            setlItemMedatDtl.getPk().getOtgoInstnCd(), setlDstrbDto.getSetlDstrbParam().getStDt())
        .getEntity();

    Pair<Long, Long> dstrbAmtAndVat = applySetlDstrb(setlDstrbDto, setlItemPtnrDstrbInfo,
        setlItemMedatDtl);

    applySetlDstrbDtl(setlDstrbDto, setlItemPtnrDstrbInfo, setlItemMedatDtl, dstrbAmtAndVat);

    return setlDstrbDto.getSetlDstrbSumary();
  }

  protected boolean validate(SetlItemMedatDtl setlItemMedatDtl) {
    if (!ptnrInfoService
        .isExistPtnr(setlItemMedatDtl.getPk().getOtgoInstnCd(), PtnrDivCd.EMR.name())) {
      return false;
    }

    if (!cdGroupInfoService
        .isExistCdDtl(OtgoInstnChCd.OTGO_INSTN_CH_CD.name(),
            setlItemMedatDtl.getPk().getOtgoInstnChCd())) {
      return false;
    }

    if (!ptnrInfoService
        .isExistPtnr(setlItemMedatDtl.getPk().getPtnrId(), PtnrDivCd.INS.name())) {
      return false;
    }

    if (!cdGroupInfoService
        .isExistCdDtl(BillWhyCd.BILL_WHY_CD.name(), setlItemMedatDtl.getPk().getBillWhyCd())) {
      return false;
    }

    if (!cdGroupInfoService
        .isExistCdDtl(MotDivCd.MOT_DIV_CD.name(), setlItemMedatDtl.getPk().getMotDivCd())) {
      return false;
    }

    return true;
  }

  protected Pair<Long, Long> applySetlDstrb(SetlDstrbDto setlDstrbDto,
      SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo, SetlItemMedatDtl setlItemMedatDtl) {

    long dstrbAmt = 0;
    long dstrbVat = 0;

    if (ComnUtils.isYes(setlItemPtnrDstrbInfo.getDstrbYn())) {

      SetlItemDstrbSumary sumary = setlDstrbDto.getSetlDstrbSumary()
          .get(setlItemMedatDtl.getPk().getOtgoInstnCd(), setlItemMedatDtl.getPk().getSetlItemCd());

      dstrbAmt = (long) (setlItemMedatDtl.getSetlAmt() * setlItemPtnrDstrbInfo.getDstrbRate());

      sumary.setDstrbAmt(sumary.getDstrbAmt() + dstrbAmt);

//      if (ComnUtils.isYes(setlItemInfo.getVatYn())) {
      dstrbVat = (long) (dstrbAmt * 0.1);
      sumary.setDstrbVat(sumary.getDstrbVat() + dstrbVat);
//      }

    }

    return Pair.of(dstrbAmt, dstrbVat);
  }

  protected void applySetlDstrbDtl(SetlDstrbDto setlDstrbDto,
      SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo,
      SetlItemMedatDtl setlItemMedatDtl, Pair<Long, Long> setlDstrbAmtAndVat) {
    if (ComnUtils.isNo(setlItemPtnrDstrbInfo.getDstrbYn())) {
      return;
    }

    SetlItemDstrbDtl dtl = setlDstrbDto.getSetlDstrbSumary()
        .get(setlItemMedatDtl.getPk().getOtgoInstnCd(),
            setlItemMedatDtl.getPk().getSetlItemCd(),
            setlItemMedatDtl.getPk().getOtgoInstnCd(),
            setlItemMedatDtl.getPk().getOtgoInstnChCd(),
            setlItemMedatDtl.getPk().getBillWhyCd(),
            setlItemMedatDtl.getPk().getMotDivCd());

    dtl.setDstrbAmt(dtl.getDstrbAmt() + setlDstrbAmtAndVat.getLeft());
  }

  @Override
  public void wrapup(SetlDstrbDto setlDstrbDto) {
    log.info("+++++++++++++++++++++++++++++++++++++++++++");
    log.info("ERROR_COUNT: {}", setlDstrbDto.getSetlErrorItems().getErrorCount());
    log.info("+++++++++++++++++++++++++++++++++++++++++++");
  }

}
