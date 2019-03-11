package com.kt.mmp.setl.setlCalc.domain;

import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"transacId"})
public class MedicalTransacHst extends BaseEntity {

  @Id
  @Column(nullable = false)
  private String transacId;

  @Column(nullable = false)
  private LocalDateTime occDt;

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

  private String medicalInstnCd;

  private String clnLsnCd;

  private long amt;

  private String trtResltCd;

  @Builder
  public MedicalTransacHst(String transacId, LocalDateTime occDt, String otgoInstnCd,
      String otgoInstnChCd, String rcvInstnCd, String billWhyCd, String motDivCd,
      String medicalInstnCd, String clnLsnCd, long amt, String trtResltCd) {
    this.transacId = transacId;
    this.occDt = occDt;
    this.otgoInstnCd = otgoInstnCd;
    this.otgoInstnChCd = otgoInstnChCd;
    this.rcvInstnCd = rcvInstnCd;
    this.billWhyCd = billWhyCd;
    this.motDivCd = motDivCd;
    this.medicalInstnCd = medicalInstnCd;
    this.clnLsnCd = clnLsnCd;
    this.amt = amt;
    this.trtResltCd = trtResltCd;
  }

}
