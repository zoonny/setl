package com.kt.mmp.setl.setlCalc.service;

import com.google.common.base.Preconditions;
import com.kt.mmp.comn.base.mvc.dto.RuleParamSet;
import com.kt.mmp.comn.base.split.Split;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.BillWhyCd;
import com.kt.mmp.comn.code.code.CalcTypeCd;
import com.kt.mmp.comn.code.code.MotDivCd;
import com.kt.mmp.comn.code.code.OtgoInstnChCd;
import com.kt.mmp.comn.code.code.PtnrDivCd;
import com.kt.mmp.comn.code.code.RuleTypeCd;
import com.kt.mmp.comn.code.service.CdGroupInfoService;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemSumary;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcDto;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcParam;
import com.kt.mmp.setl.setlCalc.service.cache.SetlItemInfoService;
import com.kt.mmp.setl.setlCalc.service.cache.SetlItemRuleInfoService;
import com.kt.mmp.setl.setlCalc.service.cache.SetlItemTarifInfoService;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtl;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumary;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import com.kt.mmp.setl.setlDstrb.service.cache.PtnrInfoService;
import com.kt.mmp.setl.setlDstrb.service.cache.SetlItemPtnrDstrbInfoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SetlCalcService extends SetlService<SetlCalcDto, MedicalTransacHst, SetlCalcDto> {

  private final SetlItemInfoService setlItemInfoService;
  private final SetlItemTarifInfoService setlItemTarifInfoService;
  private final SetlItemRuleInfoService setlItemRuleInfoService;
  private final SetlItemPtnrDstrbInfoService setlItemPtnrDstrbInfoService;
  private final CdGroupInfoService cdGroupInfoService;
  private final PtnrInfoService ptnrInfoService;

  @Override
  protected SetlCalcDto buildDto(String setlTgtYm) {
    SetlCalcDto setlCalcDto = SetlCalcDto.builder()
        .setlCalcParam(SetlCalcParam.builder()
            .setlTgtYm(setlTgtYm)
            .wrkDt(LocalDateTime.now())
            .build())
        .build();

    buildSetlCalcSplitSet(setlCalcDto);

    return setlCalcDto;
  }

  protected void buildSetlCalcSplitSet(SetlCalcDto setlCalcDto) {
    Map<String, List<SetlItemInfo>> setlItemInfoMap = setlItemInfoService.findGroupBySetlItemCd();
    Preconditions.checkArgument(MapUtils.isNotEmpty(setlItemInfoMap));

    setlCalcDto.getSetlItemSplitSetMap().initSplit(setlItemInfoMap);
    setlCalcDto.getSetlItemSplitSetMap().sort();
    setlCalcDto.getSetlItemSplitSetMap().printSplits();

    Map<String, List<SetlItemTarifInfo>> setlItemTarifInfoMap = setlItemTarifInfoService
        .findGroupBySetlItemCd();
    Preconditions.checkArgument(MapUtils.isNotEmpty(setlItemTarifInfoMap));

    setlCalcDto.getSetlItemTarifSplitSetMap().initSplit(setlItemTarifInfoMap);
    setlCalcDto.getSetlItemTarifSplitSetMap().sort();
    setlCalcDto.getSetlItemTarifSplitSetMap().printSplits();

    Map<String, List<SetlItemRuleInfo>> setlItemRuleInfoMap = setlItemRuleInfoService
        .findGroupBySetlItemCd();
    Preconditions.checkArgument(MapUtils.isNotEmpty(setlItemRuleInfoMap));

    setlCalcDto.getSetlItemRuleSplitSetMap().initSplit(setlItemRuleInfoMap);
    setlCalcDto.getSetlItemRuleSplitSetMap().sort();
    setlCalcDto.getSetlItemRuleSplitSetMap().printSplits();

    Map<String, List<SetlItemPtnrDstrbInfo>> setlItemPtnrDstrbInfoMap = setlItemPtnrDstrbInfoService
        .findGroupBySetlItemCdAndPtnrId();
    Preconditions.checkArgument(MapUtils.isNotEmpty(setlItemPtnrDstrbInfoMap));

    setlCalcDto.getSetlItemPtnrDstrbSplitSetMap().initSplit(setlItemPtnrDstrbInfoMap);
    setlCalcDto.getSetlItemPtnrDstrbSplitSetMap().sort();
    setlCalcDto.getSetlItemPtnrDstrbSplitSetMap().printSplits();
  }

  @Override
  protected SetlCalcDto doProcess(SetlCalcDto setlCalcDto,
      MedicalTransacHst medicalTransacHst) {

    if (!validate(medicalTransacHst)) {
      setlCalcDto.getSetlErrorItems().addErrorItem(medicalTransacHst);
      return null;
//      throw new BaseException(ErrorCode.INVALID_DATA, medicalTransacHst);
    }

    setlCalcDto.getSetlItemSplitSetMap().getSplitSets().stream()
        .forEach(splitSet -> {
          Split<SetlItemInfo> setlItemInfoSplit = splitSet.find(medicalTransacHst.getOccDt());

          validateSplit(setlItemInfoSplit, medicalTransacHst);

          if (!isCalcSetlItem(setlCalcDto, setlItemInfoSplit.getEntity(), medicalTransacHst)) {
            return;
          }

          if (!applySetlItemRule(setlCalcDto, setlItemInfoSplit.getEntity(), medicalTransacHst)) {
            return;
          }

          // SetlAmt
          Pair<Long, Long> setlAmtAndVat = applySetlAmt(setlCalcDto, setlItemInfoSplit.getEntity(),
              medicalTransacHst);

          applySetlItemDtl(setlCalcDto, setlItemInfoSplit.getEntity(), medicalTransacHst,
              setlAmtAndVat);

          // SetlDstrb
          SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo = setlCalcDto
              .getSetlItemPtnrDstrbSplitSetMap()
              .findEfctEntityByKey(setlItemInfoSplit.getEntity().getPk().getSetlItemCd() +
                  medicalTransacHst.getOtgoInstnCd(), medicalTransacHst.getOccDt())
              .getEntity();

          Pair<Long, Long> dstrbAmtAndVat = applySetlDstrb(setlCalcDto,
              setlItemInfoSplit.getEntity(), setlItemPtnrDstrbInfo, medicalTransacHst,
              setlAmtAndVat);

          applySetlDstrbDtl(setlCalcDto, setlItemPtnrDstrbInfo, medicalTransacHst, dstrbAmtAndVat);
        });

    return setlCalcDto;
  }

  protected boolean validate(MedicalTransacHst medicalTransacHst) {
    if (!ptnrInfoService
        .isExistPtnr(medicalTransacHst.getOtgoInstnCd(), PtnrDivCd.EMR.name())) {
      return false;
    }
//    if (!cdGroupInfoService
//        .isExistCdDtl(OtgoInstnCd.OTGO_INSTN_CD.name(), medicalTransacHst.getOtgoInstnCd())) {
//      return false;
//    }

    if (!cdGroupInfoService
        .isExistCdDtl(OtgoInstnChCd.OTGO_INSTN_CH_CD.name(),
            medicalTransacHst.getOtgoInstnChCd())) {
      return false;
    }

    if (!ptnrInfoService
        .isExistPtnr(medicalTransacHst.getRcvInstnCd(), PtnrDivCd.INS.name())) {
      return false;
    }
//    if (!cdGroupInfoService
//        .isExistCdDtl(RcvInstnCd.RCV_INSTN_CD.name(), medicalTransacHst.getRcvInstnCd())) {
//      return false;
//    }

    if (!cdGroupInfoService
        .isExistCdDtl(BillWhyCd.BILL_WHY_CD.name(), medicalTransacHst.getBillWhyCd())) {
      return false;
    }

    if (!cdGroupInfoService
        .isExistCdDtl(MotDivCd.MOT_DIV_CD.name(), medicalTransacHst.getMotDivCd())) {
      return false;
    }

    return true;
  }

  private void validateSplit(Split<SetlItemInfo> split, MedicalTransacHst medicalTransacHst) {
    Preconditions.checkArgument(
        ComnUtils.isStDtGreaterThanEqualAndFnsDtLessThan(medicalTransacHst.getOccDt(), split),
        medicalTransacHst.getOccDt());

    Preconditions.checkArgument(
        ComnUtils.isStDtGreaterThanEqualAndFnsDtLessThan(medicalTransacHst.getOccDt(),
            split.getEntity().getEfctStDt(),
            split.getEntity().getPk().getEfctFnsDt()),
        medicalTransacHst.getOccDt());
  }

  protected boolean isCalcSetlItem(SetlCalcDto setlCalcDto, SetlItemInfo setlItemInfo,
      MedicalTransacHst medicalTransacHst) {
    boolean ret = false;

    Object property = ComnUtils
        .getProperty(medicalTransacHst, setlItemInfo.getSetlTypeCd());

    if (property != null && property instanceof String) {
      if (setlItemInfo.getSetlDivCd()
          .equals(medicalTransacHst.getMotDivCd())) {
        ret = true;
      }
    }

    return ret;
  }

  protected boolean applySetlItemRule(SetlCalcDto setlCalcDto, SetlItemInfo setlItemInfo,
      MedicalTransacHst medicalTransacHst) {

    List<SetlItemRuleInfo> setlItemRuleInfos = setlCalcDto.getSetlItemRuleSplitSetMap()
        .findEfctEntitiesByKey(setlItemInfo.getPk().getSetlItemCd(), medicalTransacHst.getOccDt())
        .stream()
        .sorted(Split::compareTo)
        .map(Split::getEntity)
        .collect(Collectors.toList());

    boolean ret = true;

    for (SetlItemRuleInfo iter : setlItemRuleInfos) {
      RuleParamSet ruleParamSet = RuleParamSet.builder()
          .ruleParam(iter.getRuleInfo().getRuleParam())
          .build();

      switch (RuleTypeCd.valueOf(iter.getRuleInfo().getRuleTypeCd())) {
        case INC:
          ret = ruleParamSet.check(medicalTransacHst);
          break;
        case EXC:
          ret = !ruleParamSet.check(medicalTransacHst);
          break;
      }

      if (!ret) {
        break;
      }
    }

    return ret;
  }

  protected Pair<Long, Long> applySetlAmt(SetlCalcDto setlCalcDto, SetlItemInfo setlItemInfo,
      MedicalTransacHst medicalTransacHst) {

    List<SetlItemTarifInfo> setlItemTarifs = setlCalcDto.getSetlItemTarifSplitSetMap()
        .findEfctEntitiesByKey(setlItemInfo.getPk().getSetlItemCd(), medicalTransacHst.getOccDt())
        .stream()
        .sorted(Split::compareTo)
        .map(Split::getEntity)
        .collect(Collectors.toList());

    long setlAmt = 0;
    long setlVat = 0;

    for (SetlItemTarifInfo iter : setlItemTarifs) {
      switch (CalcTypeCd.valueOf(iter.getCalcTypeCd())) {
        case TRF:
          setlAmt += iter.getTarifVal();
          break;
        case RAT:
          setlAmt *= iter.getTarifVal();
          break;
      }
    }

    SetlItemSumary sumary = setlCalcDto.getSetlCalcSumary()
        .get(medicalTransacHst.getRcvInstnCd(), setlItemInfo.getPk().getSetlItemCd());

    sumary.setSetlAmt(sumary.getSetlAmt() + setlAmt);

    if (ComnUtils.isYes(setlItemInfo.getVatYn())) {
      setlVat = (long) (setlAmt * 0.1);
      sumary.setSetlVat(sumary.getSetlVat() + setlVat);
    }

    return Pair.of(setlAmt, setlVat);
  }

  protected void applySetlItemDtl(SetlCalcDto setlCalcDto, SetlItemInfo setlItemInfo,
      MedicalTransacHst medicalTransacHst, Pair<Long, Long> setlAmtAndVat) {
    if (ComnUtils.isNo(setlItemInfo.getDtlCretYn())) {
      return;
    }

    SetlItemMedatDtl dtl = setlCalcDto.getSetlCalcSumary()
        .get(medicalTransacHst.getRcvInstnCd(), // ptnrId
            setlItemInfo.getPk().getSetlItemCd(),
            medicalTransacHst.getOtgoInstnCd(),
            medicalTransacHst.getOtgoInstnChCd(),
            medicalTransacHst.getBillWhyCd(),
            medicalTransacHst.getMotDivCd());

    dtl.setSetlAmt(dtl.getSetlAmt() + setlAmtAndVat.getLeft());
    dtl.setSetlVat(dtl.getSetlVat() + setlAmtAndVat.getRight());
    dtl.setTrtCascnt(dtl.getTrtCascnt() + 1);
    dtl.setWholeAmt(dtl.getWholeAmt() + medicalTransacHst.getAmt());
  }

  protected Pair<Long, Long> applySetlDstrb(SetlCalcDto setlCalcDto, SetlItemInfo setlItemInfo,
      SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo, MedicalTransacHst medicalTransacHst,
      Pair<Long, Long> setlAmtAndVat) {

    long dstrbAmt = 0;
    long dstrbVat = 0;

    if (ComnUtils.isYes(setlItemPtnrDstrbInfo.getDstrbYn())) {

      SetlItemDstrbSumary sumary = setlCalcDto.getSetlDstrbSumary()
          .get(medicalTransacHst.getOtgoInstnCd(), setlItemInfo.getPk().getSetlItemCd());

      dstrbAmt = (long) (setlAmtAndVat.getLeft() * setlItemPtnrDstrbInfo.getDstrbRate());

      sumary.setDstrbAmt(sumary.getDstrbAmt() + dstrbAmt);

      if (ComnUtils.isYes(setlItemInfo.getVatYn())) {
        dstrbVat = (long) (dstrbAmt * 0.1);
        sumary.setDstrbVat(sumary.getDstrbVat() + dstrbVat);
      }

    }

    return Pair.of(dstrbAmt, dstrbVat);
  }

  protected void applySetlDstrbDtl(SetlCalcDto setlCalcDto,
      SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo,
      MedicalTransacHst medicalTransacHst, Pair<Long, Long> setlDstrbAmtAndVat) {
    if (ComnUtils.isNo(setlItemPtnrDstrbInfo.getDstrbYn())) {
      return;
    }

    SetlItemDstrbDtl dtl = setlCalcDto.getSetlDstrbSumary()
        .get(medicalTransacHst.getOtgoInstnCd(),
            setlItemPtnrDstrbInfo.getPk().getSetlItemCd(),
            medicalTransacHst.getOtgoInstnCd(),
            medicalTransacHst.getOtgoInstnChCd(),
            medicalTransacHst.getBillWhyCd(),
            medicalTransacHst.getMotDivCd());

    dtl.setDstrbAmt(dtl.getDstrbAmt() + setlDstrbAmtAndVat.getLeft());
  }

  @Override
  public void wrapup(SetlCalcDto setlCalcDto) {
    log.info("+++++++++++++++++++++++++++++++++++++++++++");
    log.info("ERROR_COUNT: {}", setlCalcDto.getSetlErrorItems().getErrorCount());
    log.info("+++++++++++++++++++++++++++++++++++++++++++");
  }

}
