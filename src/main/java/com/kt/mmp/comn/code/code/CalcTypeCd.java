package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalcTypeCd {
  TRF("정액"),
  RAT("요율"),
  CALC_TYPE_CD("");

  private String name;
}
