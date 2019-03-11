package com.kt.mmp.comn.code.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SetlTypeCd {
  otgoInstnCd("발신기관코드"),
  otgoInstnChCd("발신기관채널코드"),
  rcvInstnCd("수신기관코드"),
  billWhyCd("청구사유코드"),
  motDivCd("전문구분코드"),
  clnLsnCd("진료과목코드"),
  SETL_TYPE_CD("");

  private String name;
}
