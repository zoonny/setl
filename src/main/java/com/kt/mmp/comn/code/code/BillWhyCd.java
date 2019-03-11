package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillWhyCd {
  A("상해"),
  D("질병"),
  BILL_WHY_CD("");

  private String name;
}
