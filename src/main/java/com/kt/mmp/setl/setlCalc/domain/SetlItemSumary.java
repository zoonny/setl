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
public class SetlItemSumary extends BaseEntity {

  @EmbeddedId
  private SetlItemSumaryPk pk;

  @Setter(AccessLevel.PUBLIC)
  @Column(nullable = false)
  private long setlAmt;

  @Setter(AccessLevel.PUBLIC)
  @Column
  private long setlVat;

  @Setter(AccessLevel.PUBLIC)
  @Column
  private long adjAmt;

  @Setter(AccessLevel.PUBLIC)
  @Column
  private long adjVat;

  @Builder
  public SetlItemSumary(SetlItemSumaryPk pk, long setlAmt, long setlVat, long adjAmt,
      long adjVat) {
    this.pk = pk;
    this.setlAmt = setlAmt;
    this.setlVat = setlVat;
    this.adjAmt = adjAmt;
    this.adjVat = adjVat;
  }

}
