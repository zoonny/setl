package com.kt.mmp.setl.setlDstrb.domain;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@EqualsAndHashCode(of = {"pk"}, callSuper = false)
public class SetlItemPtnrDstrbInfo extends BaseEntity implements
    PeriodCacheEntity<SetlItemPtnrDstrbInfoPk> {

  @EmbeddedId
  private SetlItemPtnrDstrbInfoPk pk;

  @Column(nullable = false)
  private LocalDateTime efctStDt;

  @Column(nullable = false)
  private double dstrbRate;

  @Column
  private String dstrbYn;

  @Builder
  public SetlItemPtnrDstrbInfo(
      SetlItemPtnrDstrbInfoPk pk, LocalDateTime efctStDt, double dstrbRate,
      String dstrbYn) {
    this.pk = pk;
    this.efctStDt = efctStDt;
    this.dstrbRate = dstrbRate;
    this.dstrbYn = dstrbYn;
  }

  @Override
  public String getDefinedPk(int index) {
    return null;
  }

  @Override
  public LocalDateTime getEfctFnsDt() {
    return pk.getEfctFnsDt();
  }

  @Override
  public int compareTo(PeriodCacheEntity<SetlItemPtnrDstrbInfoPk> entity) {
    return getEfctStDt().compareTo(entity.getEfctStDt());
  }

}
