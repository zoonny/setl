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
public class SetlItemDstrbDtl extends BaseEntity {

  @EmbeddedId
  private SetlItemDstrbDtlPk pk;

  @Setter(AccessLevel.PUBLIC)
  @Column(nullable = false)
  private long dstrbAmt;

  @Builder
  public SetlItemDstrbDtl(SetlItemDstrbDtlPk pk, long dstrbAmt) {
    this.pk = pk;
    this.dstrbAmt = dstrbAmt;
  }

}
