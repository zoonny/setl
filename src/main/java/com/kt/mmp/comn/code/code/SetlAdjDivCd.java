package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SetlAdjDivCd {
  A("관리자조정"),
  R("요청에의한조정"),
  SETL_ADJ_DIV_CD("");

  private String name;
}
