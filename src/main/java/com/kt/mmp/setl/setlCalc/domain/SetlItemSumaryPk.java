package com.kt.mmp.setl.setlCalc.domain;

import java.io.Serializable;
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
public class SetlItemSumaryPk implements Serializable {

  @Column(nullable = false)
  private String setlTgtYm;

  @Column(nullable = false)
  private String setlItemCd;

  @Column(nullable = false)
  private int stepNo;

  @Column(nullable = false)
  private String ptnrId;

  @Builder
  public SetlItemSumaryPk(String setlTgtYm, String setlItemCd, int stepNo, String ptnrId) {
    this.setlTgtYm = setlTgtYm;
    this.setlItemCd = setlItemCd;
    this.stepNo = stepNo;
    this.ptnrId = ptnrId;
  }

}
