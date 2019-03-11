package com.kt.mmp.setl.setlDstrb.domain;

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
public class SetlItemDstrbDtlPk implements Serializable {

  @Column(nullable = false)
  private String ptnrId;

  @Column(nullable = false)
  private String setlTgtYm;

  @Column(nullable = false)
  private String setlItemCd;

  @Column(nullable = false)
  private int stepNo;

  @Column(nullable = false)
  private String otgoInstnCd;

  @Column(nullable = false)
  private String otgoInstnChCd;

  @Column(nullable = false)
  private String rcvInstnCd;

  @Column(nullable = false)
  private String billWhyCd;

  @Column(nullable = false)
  private String motDivCd;

  @Builder
  public SetlItemDstrbDtlPk(String ptnrId, String setlTgtYm, String setlItemCd, int stepNo,
      String otgoInstnCd, String otgoInstnChCd, String rcvInstnCd, String billWhyCd,
      String motDivCd) {
    this.ptnrId = ptnrId;
    this.setlTgtYm = setlTgtYm;
    this.setlItemCd = setlItemCd;
    this.stepNo = stepNo;
    this.otgoInstnCd = otgoInstnCd;
    this.otgoInstnChCd = otgoInstnChCd;
    this.rcvInstnCd = rcvInstnCd;
    this.billWhyCd = billWhyCd;
    this.motDivCd = motDivCd;
  }

}
