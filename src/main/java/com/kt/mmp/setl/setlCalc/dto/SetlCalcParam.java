package com.kt.mmp.setl.setlCalc.dto;

import com.kt.mmp.comn.base.util.ComnUtils;
import java.time.LocalDateTime;
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
public class SetlCalcParam {

  private String setlTgtYm;

  private LocalDateTime stDt;

  private LocalDateTime fnsDt;

  private LocalDateTime wrkDt;

  private int stepNo;

  @Builder
  public SetlCalcParam(String setlTgtYm, LocalDateTime wrkDt, int stepNo) {
    this.setlTgtYm = setlTgtYm;
    this.stDt = ComnUtils.stDtOfMonth(setlTgtYm);
    this.fnsDt = ComnUtils.fnsDtOfMonth(setlTgtYm);
    this.wrkDt = wrkDt;
    this.stepNo = stepNo;
  }

}
