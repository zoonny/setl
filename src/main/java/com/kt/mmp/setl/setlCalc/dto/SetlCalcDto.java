package com.kt.mmp.setl.setlCalc.dto;

import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbSumary;
import com.kt.mmp.setl.setlDstrb.dto.SetlItemPtnrDstrbSplitSetMap;
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
public class SetlCalcDto {

  private SetlCalcParam setlCalcParam;
  private SetlItemSplitSetMap setlItemSplitSetMap;
  private SetlItemTarifSplitSetMap setlItemTarifSplitSetMap;
  private SetlItemRuleSplitSetMap setlItemRuleSplitSetMap;
  private SetlItemPtnrDstrbSplitSetMap setlItemPtnrDstrbSplitSetMap;
  private SetlCalcSumary setlCalcSumary;
  private SetlDstrbSumary setlDstrbSumary;
  private SetlErrorItems setlErrorItems;

  @Builder
  public SetlCalcDto(SetlCalcParam setlCalcParam) {
    this.setlCalcParam = setlCalcParam;
    this.setlItemSplitSetMap = SetlItemSplitSetMap.builder()
        .basStDt(setlCalcParam.getStDt())
        .basFnsDt(setlCalcParam.getFnsDt())
        .build();
    this.setlItemTarifSplitSetMap = SetlItemTarifSplitSetMap.builder()
        .basStDt(setlCalcParam.getStDt())
        .basFnsDt(setlCalcParam.getFnsDt())
        .build();
    this.setlItemRuleSplitSetMap = SetlItemRuleSplitSetMap.builder()
        .basStDt(setlCalcParam.getStDt())
        .basFnsDt(setlCalcParam.getFnsDt())
        .build();
    this.setlItemPtnrDstrbSplitSetMap = SetlItemPtnrDstrbSplitSetMap.builder()
        .basStDt(setlCalcParam.getStDt())
        .basFnsDt(setlCalcParam.getFnsDt())
        .build();
    this.setlCalcSumary = SetlCalcSumary.builder()
        .setlTgtYm(setlCalcParam.getSetlTgtYm())
        .build();
    this.setlDstrbSumary = SetlDstrbSumary.builder()
        .setlTgtYm(setlCalcParam.getSetlTgtYm())
        .build();
    this.setlErrorItems = SetlErrorItems.builder()
        .build();
  }

}
