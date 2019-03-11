package com.kt.mmp.setl.setlDstrb.dto;

import com.kt.mmp.setl.setlCalc.dto.SetlErrorItems;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Deprecated
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SetlDstrbDto {

  private SetlDstrbParam setlDstrbParam;
  private SetlItemPtnrDstrbSplitSetMap setlItemPtnrDstrbSplitSetMap;
  private SetlDstrbSumary setlDstrbSumary;
  private SetlErrorItems setlErrorItems;

  @Builder
  public SetlDstrbDto(SetlDstrbParam setlDstrbParam) {
    this.setlDstrbParam = setlDstrbParam;
    this.setlItemPtnrDstrbSplitSetMap = SetlItemPtnrDstrbSplitSetMap.builder()
        .basStDt(setlDstrbParam.getStDt())
        .basFnsDt(setlDstrbParam.getFnsDt())
        .build();
    this.setlDstrbSumary = SetlDstrbSumary.builder()
        .setlTgtYm(setlDstrbParam.getSetlTgtYm())
        .stepNo(setlDstrbParam.getStepNo())
        .build();
    this.setlErrorItems = SetlErrorItems.builder().build();
  }

}
