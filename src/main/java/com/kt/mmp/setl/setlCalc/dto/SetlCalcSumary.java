package com.kt.mmp.setl.setlCalc.dto;

import com.google.common.collect.Maps;
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
public class SetlCalcSumary {

  private String setlTgtYm;
  private int stepNo;
  private Map<String, PtnrSetlCalcSumary> ptnrSetlCalcSumaryMap;

  @Builder
  public SetlCalcSumary(String setlTgtYm, int stepNo) {
    this.setlTgtYm = setlTgtYm;
    this.stepNo = stepNo;
    this.ptnrSetlCalcSumaryMap = Maps.newHashMap();
  }

  public SetlItemSumary get(String ptnrId, String setlItemCd) {
    return get(ptnrId, SetlItemSumaryPk.builder()
        .setlTgtYm(setlTgtYm)
        .setlItemCd(setlItemCd)
        .stepNo(stepNo)
        .ptnrId(ptnrId)
        .build());
  }

  public SetlItemSumary get(String ptnrId, SetlItemSumaryPk key) {
    PtnrSetlCalcSumary ptnrSetlCalcSumry = ptnrSetlCalcSumaryMap.get(ptnrId);
    if (ptnrSetlCalcSumry == null) {
      ptnrSetlCalcSumry = PtnrSetlCalcSumary.builder()
          .setlTgtYm(setlTgtYm)
          .stepNo(stepNo)
          .ptnrId(ptnrId)
          .build();
      ptnrSetlCalcSumaryMap.put(ptnrId, ptnrSetlCalcSumry);
    }
    return ptnrSetlCalcSumry.get(key);
  }

  public SetlItemMedatDtl get(String ptnrId, String setlItemCd, String otgoInstnCd, String otgoInstnChCd,
      String billWhyCd, String motDivCd) {
    return get(ptnrId, SetlItemMedatDtlPk.builder()
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

  public SetlItemMedatDtl get(String ptnrId, SetlItemMedatDtlPk key) {
    PtnrSetlCalcSumary ptnrSetlCalcSumry = ptnrSetlCalcSumaryMap.get(ptnrId);
    if (ptnrSetlCalcSumry == null) {
      ptnrSetlCalcSumry = PtnrSetlCalcSumary.builder()
          .setlTgtYm(setlTgtYm)
          .stepNo(stepNo)
          .ptnrId(ptnrId)
          .build();
      ptnrSetlCalcSumaryMap.put(ptnrId, ptnrSetlCalcSumry);
    }
    return ptnrSetlCalcSumry.get(key);
  }

}
