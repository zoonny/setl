package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MotDivCd {
  MP("의료처방전"),
  RC("의료영수증"),
  CT("진료내역"),
  MOT_DIV_CD("");

  private String name;
}
