package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OtgoInstnChCd {
  K("키오스크"),
  W("웹"),
  D("데스크"),
  OTGO_INSTN_CH_CD("");

  private String name;
}
