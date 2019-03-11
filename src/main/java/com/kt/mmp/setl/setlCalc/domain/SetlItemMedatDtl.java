package com.kt.mmp.setl.setlCalc.domain;

import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"pk"}, callSuper = false)
public class SetlItemMedatDtl extends BaseEntity {

  @EmbeddedId
  private SetlItemMedatDtlPk pk;

  @Setter
  @Column(nullable = false)
  private long setlAmt;

  @Setter
  @Column
  private long setlVat;

  @Setter
  @Column(nullable = false)
  private long trtCascnt;

  @Setter
  @Column
  private long wholeAmt;

  @Builder
  public SetlItemMedatDtl(SetlItemMedatDtlPk pk, long setlAmt, long setlVat, long trtCascnt, long wholeAmt) {
    this.pk = pk;
    this.setlAmt = setlAmt;
    this.setlVat = setlVat;
    this.trtCascnt = trtCascnt;
    this.wholeAmt = wholeAmt;
  }

}
