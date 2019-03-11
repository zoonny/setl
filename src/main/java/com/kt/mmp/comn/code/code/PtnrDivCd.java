package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PtnrDivCd {
  EMR("의료중개업체"),
  INS("보험회사"),
  PTNR_DIV_CD("");

  private String name;
}
