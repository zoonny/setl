package com.kt.mmp.setl.setlCalc.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SetlItemInfoPk implements Serializable {

  @Column(nullable = false)
  private String setlItemCd;

  @Column(nullable = false)
  private LocalDateTime efctFnsDt;

  @Builder
  public SetlItemInfoPk(String setlItemCd, LocalDateTime efctFnsDt) {
    this.setlItemCd = setlItemCd;
    this.efctFnsDt = efctFnsDt;
  }

}
