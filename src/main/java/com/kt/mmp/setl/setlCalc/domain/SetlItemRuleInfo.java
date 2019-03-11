package com.kt.mmp.setl.setlCalc.domain;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.code.domain.CdDtlInfo;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"pk"}, callSuper = false)
public class SetlItemRuleInfo extends BaseEntity implements PeriodCacheEntity<SetlItemRuleInfoPk> {

  @EmbeddedId
  private SetlItemRuleInfoPk pk;

  @Column(nullable = false)
  private LocalDateTime efctStDt;

  @Column(nullable = false)
  private int odrg;

  @ManyToOne(targetEntity = RuleInfo.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "ruleCd", insertable = false, updatable = false)
  private RuleInfo ruleInfo;

  @Builder
  public SetlItemRuleInfo(SetlItemRuleInfoPk setlItemRuleInfoPk, LocalDateTime efctStDt, int odrg,
      RuleInfo ruleInfo) {
    this.pk = setlItemRuleInfoPk;
    this.efctStDt = efctStDt;
    this.odrg = odrg;
    this.ruleInfo = ruleInfo;
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
  public int compareTo(PeriodCacheEntity<SetlItemRuleInfoPk> entity) {
    return getOdrg() - ((SetlItemRuleInfo) entity).getOdrg();
  }

}
