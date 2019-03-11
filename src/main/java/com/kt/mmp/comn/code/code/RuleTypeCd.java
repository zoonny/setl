package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuleTypeCd {
  EXC("제외"),
  INC("포함"),
  RULE_TYPE_CD("");

  private String name;
}
