package com.kt.mmp.setl.setlDstrb.domain;

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
public class SetlItemDstrbSumary extends BaseEntity {

  @EmbeddedId
  private SetlItemDstrbSumaryPk pk;

  @Setter(AccessLevel.PUBLIC)
  @Column(nullable = false)
  private long dstrbAmt;

  @Setter(AccessLevel.PUBLIC)
  @Column
  private long dstrbVat;

  @Setter(AccessLevel.PUBLIC)
  @Column
  private long adjAmt;

  @Setter(AccessLevel.PUBLIC)
  @Column
  private long adjVat;

  @Builder
  public SetlItemDstrbSumary(
      SetlItemDstrbSumaryPk pk, long dstrbAmt, long dstrbVat, long adjAmt,
      long adjVat) {
    this.pk = pk;
    this.dstrbAmt = dstrbAmt;
    this.dstrbVat = dstrbVat;
    this.adjAmt = adjAmt;
    this.adjVat = adjVat;
  }

}
