package com.kt.mmp.setl.setlDstrb.domain;

import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.cache.domain.CacheEntity;
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
@EqualsAndHashCode(of = {"ptnrId"})
public class PtnrInfo extends BaseEntity implements CacheEntity<String> {

  @Id
  @Column(nullable = false)
  private String ptnrId;

  @Column(nullable = false)
  private String ptnrDivCd;

  @Column(nullable = false)
  private String ptnrNm;

  @Column(nullable = false)
  private String tkcgr;

  @Column(nullable = false)
  private String tkcgDept;

  @Column(nullable = false)
  private String telNo;

  @Column
  private String adr;

  @Column
  private String bizrNo;

  @Column
  private String email;

  @Column
  private String bankCd;

  @Column
  private String bnkacnNo;

  @Column
  private String dposrNm;

  @Builder
  public PtnrInfo(String ptnrId, String ptnrDivCd, String ptnrNm, String tkcgr,
      String tkcgDept, String telNo, String adr, String bizrNo, String email, String bankCd,
      String bnkacnNo, String dposrNm) {
    this.ptnrId = ptnrId;
    this.ptnrDivCd = ptnrDivCd;
    this.ptnrNm = ptnrNm;
    this.tkcgr = tkcgr;
    this.tkcgDept = tkcgDept;
    this.telNo = telNo;
    this.adr = adr;
    this.bizrNo = bizrNo;
    this.email = email;
    this.bankCd = bankCd;
    this.bnkacnNo = bnkacnNo;
    this.dposrNm = dposrNm;
  }

  @Override
  public String getPk() {
    return getPtnrId();
  }

  @Override
  public String getDefinedPk(int index) {
    return null;
  }

}
