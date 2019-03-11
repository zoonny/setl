package com.kt.mmp.setl.setlCalc.dto;

import com.google.common.collect.Maps;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtlPk;
import com.kt.mmp.setl.setlCalc.domain.SetlItemSumary;
import com.kt.mmp.setl.setlCalc.domain.SetlItemSumaryPk;
import java.util.Map;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class PtnrSetlCalcSumary {

  private String setlTgtYm;
  private int stepNo;
  private String ptnrId;
  private Map<SetlItemSumaryPk, SetlItemSumary> setlItemSumaryMap;
  private Map<SetlItemMedatDtlPk, SetlItemMedatDtl> setlItemMedatDtlMap;

  @Builder
  public PtnrSetlCalcSumary(String setlTgtYm, int stepNo, String ptnrId) {
    this.setlTgtYm = setlTgtYm;
    this.stepNo = stepNo;
    this.ptnrId = ptnrId;
    this.setlItemSumaryMap = Maps.newHashMap();
    this.setlItemMedatDtlMap = Maps.newHashMap();
  }

  @Deprecated
  public SetlItemSumary get(String setlItemCd) {
    return get(SetlItemSumaryPk.builder()
        .setlTgtYm(setlTgtYm)
        .setlItemCd(setlItemCd)
        .stepNo(stepNo)
        .build());
  }

  public SetlItemSumary get(SetlItemSumaryPk key) {
    SetlItemSumary setlItemSumary = setlItemSumaryMap.get(key);
    if (setlItemSumary == null) {
      setlItemSumary = SetlItemSumary.builder()
          .pk(key)
          .setlAmt(0)
          .setlVat(0)
          .build();
      ComnUtils.setCretCtrlField(this, setlItemSumary);
      setlItemSumaryMap.put(key, setlItemSumary);
    }
    return setlItemSumary;
  }

  @Deprecated
  public SetlItemMedatDtl get(String setlItemCd, String ptnrId, String otgoInstnCd, String otgoInstnChCd,
      String billWhyCd, String motDivCd) {
    return get(SetlItemMedatDtlPk.builder()
        .setlTgtYm(setlTgtYm)
        .setlItemCd(setlItemCd)
        .stepNo(stepNo)
        .ptnrId(ptnrId)
        .otgoInstnCd(otgoInstnCd)
        .otgoInstnChCd(otgoInstnChCd)
        .billWhyCd(billWhyCd)
        .motDivCd(motDivCd)
        .build());
  }

  public SetlItemMedatDtl get(SetlItemMedatDtlPk key) {
    SetlItemMedatDtl setlItemMedatDtl = setlItemMedatDtlMap.get(key);
    if (setlItemMedatDtl == null) {
      setlItemMedatDtl = SetlItemMedatDtl.builder()
          .pk(key)
          .trtCascnt(0)
          .wholeAmt(0)
          .build();
      ComnUtils.setCretCtrlField(this, setlItemMedatDtl);
      setlItemMedatDtlMap.put(key, setlItemMedatDtl);
    }
    return setlItemMedatDtl;
  }

}
