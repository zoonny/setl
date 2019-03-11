package com.kt.mmp.setl.setlCalc.domain;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.cache.domain.CacheEntity;
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
//@ToString(exclude = {"tarifVal", "odrg"})
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"pk"}, callSuper = false)
public class SetlItemTarifInfo extends BaseEntity implements
    PeriodCacheEntity<SetlItemTarifInfoPk> {

  @EmbeddedId
  private SetlItemTarifInfoPk pk;

  @Column(nullable = false)
  private LocalDateTime efctStDt;

  @Column(nullable = false)
  private double tarifVal;

  @Column(nullable = false)
  private String calcTypeCd;

  @Builder
  public SetlItemTarifInfo(SetlItemTarifInfoPk pk, LocalDateTime efctStDt,
      double tarifVal, String calcTypeCd) {
    this.pk = pk;
    this.efctStDt = efctStDt;
    this.tarifVal = tarifVal;
    this.calcTypeCd = calcTypeCd;
  }

  @Override
  public String getDefinedPk(int index) {
    return null;
  }

  @Override
  public LocalDateTime getEfctFnsDt() {
    return getPk().getEfctFnsDt();
  }

  @Override
  public int compareTo(PeriodCacheEntity<SetlItemTarifInfoPk> entity) {
    return getPk().getOdrg() - entity.getPk().getOdrg();
  }

}
