package com.kt.mmp.setl.setlDstrb.dto;

import com.google.common.collect.Maps;
import com.kt.mmp.comn.base.util.ComnUtils;
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
public class PtnrSetlDstrbSumary {

  private String setlTgtYm;
  private int stepNo;
  private String ptnrId;
  private Map<SetlItemDstrbSumaryPk, SetlItemDstrbSumary> setlItemDstrbSumaryMap;
  private Map<SetlItemDstrbDtlPk, SetlItemDstrbDtl> setlItemDstrbDtlMap;

  @Builder
  public PtnrSetlDstrbSumary(String setlTgtYm, int stepNo, String ptnrId) {
    this.setlTgtYm = setlTgtYm;
    this.stepNo = stepNo;
    this.ptnrId = ptnrId;
    this.setlItemDstrbSumaryMap = Maps.newHashMap();
    this.setlItemDstrbDtlMap = Maps.newHashMap();
  }

  @Deprecated
  public SetlItemDstrbSumary get(String setlItemCd, String ptnrId) {
    return get(SetlItemDstrbSumaryPk.builder()
        .setlTgtYm(setlTgtYm)
        .setlItemCd(setlItemCd)
        .stepNo(stepNo)
        .ptnrId(ptnrId)
        .build());
  }

  public SetlItemDstrbSumary get(SetlItemDstrbSumaryPk key) {
    SetlItemDstrbSumary setlItemDstrbSumary = setlItemDstrbSumaryMap.get(key);
    if (setlItemDstrbSumary == null) {
      setlItemDstrbSumary = SetlItemDstrbSumary.builder()
          .pk(key)
          .dstrbAmt(0)
          .dstrbVat(0)
          .build();
      ComnUtils.setCretCtrlField(this, setlItemDstrbSumary);
      setlItemDstrbSumaryMap.put(key, setlItemDstrbSumary);
    }
    return setlItemDstrbSumary;
  }

  @Deprecated
  public SetlItemDstrbDtl get(String ptnrId, String setlItemCd, String otgoInstnCd, String otgoInstnChCd,
      String billWhyCd, String motDivCd) {
    return get(SetlItemDstrbDtlPk.builder()
        .ptnrId(ptnrId)
        .setlTgtYm(setlTgtYm)
        .setlItemCd(setlItemCd)
        .stepNo(stepNo)
        .otgoInstnCd(otgoInstnCd)
        .otgoInstnChCd(otgoInstnChCd)
        .rcvInstnCd(ptnrId)
        .billWhyCd(billWhyCd)
        .motDivCd(motDivCd)
        .build());
  }

  public SetlItemDstrbDtl get(SetlItemDstrbDtlPk key) {
    SetlItemDstrbDtl setlItemDstrbDtl = setlItemDstrbDtlMap.get(key);
    if (setlItemDstrbDtl == null) {
      setlItemDstrbDtl = SetlItemDstrbDtl.builder()
          .pk(key)
          .dstrbAmt(0)
          .build();
      ComnUtils.setCretCtrlField(this, setlItemDstrbDtl);
      setlItemDstrbDtlMap.put(key, setlItemDstrbDtl);
    }
    return setlItemDstrbDtl;
  }

}
