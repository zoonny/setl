package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Yn {
  Y("Yes"),
  N("No"),
  YN("");

  private String name;
}
