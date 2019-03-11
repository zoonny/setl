package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrtResltCd {
  S("성공"),
  F("실패"),
  TRT_RESLT_CD("");

  private String name;
}
