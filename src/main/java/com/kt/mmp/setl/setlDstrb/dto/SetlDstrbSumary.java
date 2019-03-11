package com.kt.mmp.setl.setlDstrb.dto;

import com.google.common.collect.Maps;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtl;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtlPk;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumary;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumaryPk;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SetlDstrbSumary {

  private String setlTgtYm;
  private int stepNo;
  private Map<String, PtnrSetlDstrbSumary> ptnrSetlDstrbSumaryMap;

  @Builder
  public SetlDstrbSumary(String setlTgtYm, int stepNo) {
    this.setlTgtYm = setlTgtYm;
    this.stepNo = stepNo;
    this.ptnrSetlDstrbSumaryMap = Maps.newHashMap();
  }

  public SetlItemDstrbSumary get(String ptnrId, String setlItemCd) {
    return get(ptnrId, SetlItemDstrbSumaryPk.builder()
        .ptnrId(ptnrId)
        .setlTgtYm(setlTgtYm)
        .stepNo(stepNo)
        .setlItemCd(setlItemCd)
        .build());
  }

  public SetlItemDstrbSumary get(String ptnrId, SetlItemDstrbSumaryPk key) {
    PtnrSetlDstrbSumary ptnrSetlDstrbSumary = ptnrSetlDstrbSumaryMap.get(ptnrId);
    if (ptnrSetlDstrbSumary == null) {
      ptnrSetlDstrbSumary = PtnrSetlDstrbSumary.builder()
          .ptnrId(ptnrId)
          .setlTgtYm(setlTgtYm)
          .stepNo(stepNo)
          .build();
      ptnrSetlDstrbSumaryMap.put(ptnrId, ptnrSetlDstrbSumary);
    }
    return ptnrSetlDstrbSumary.get(key);
  }

  public SetlItemDstrbDtl get(String ptnrId, String setlItemCd, String otgoInstnCd, String otgoInstnChCd,
      String billWhyCd, String motDivCd) {
    return get(ptnrId, SetlItemDstrbDtlPk.builder()
        .setlTgtYm(setlTgtYm)
        .setlItemCd(setlItemCd)
        .stepNo(stepNo)
        .ptnrId(ptnrId)
        .otgoInstnCd(otgoInstnCd)
        .otgoInstnChCd(otgoInstnChCd)
        .rcvInstnCd(ptnrId)
        .billWhyCd(billWhyCd)
        .motDivCd(motDivCd)
        .build());
  }

  public SetlItemDstrbDtl get(String ptnrId, SetlItemDstrbDtlPk key) {
    PtnrSetlDstrbSumary ptnrSetlDstrbSumry = ptnrSetlDstrbSumaryMap.get(ptnrId);
    if (ptnrSetlDstrbSumry == null) {
      ptnrSetlDstrbSumry = PtnrSetlDstrbSumary.builder()
          .setlTgtYm(setlTgtYm)
          .stepNo(stepNo)
          .ptnrId(ptnrId)
          .build();
      ptnrSetlDstrbSumaryMap.put(ptnrId, ptnrSetlDstrbSumry);
    }
    return ptnrSetlDstrbSumry.get(key);
  }

}
