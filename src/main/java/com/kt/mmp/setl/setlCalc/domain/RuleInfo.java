package com.kt.mmp.setl.setlCalc.domain;

import com.kt.mmp.comn.base.cache.domain.CacheEntity;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
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
@EqualsAndHashCode(of = {"ruleCd"}, callSuper = false)
public class RuleInfo extends BaseEntity implements CacheEntity<String> {

  @Id
  @Column(nullable = false)
  private String ruleCd;

  @Column(nullable = false)
  private String ruleNm;

  @Column(nullable = false)
  private String ruleTypeCd;

  @Column
  private String ruleParam;

  @Builder
  public RuleInfo(String ruleCd, String ruleNm, String ruleTypeCd, String ruleParam) {
    this.ruleCd = ruleCd;
    this.ruleNm = ruleNm;
    this.ruleTypeCd = ruleTypeCd;
    this.ruleParam = ruleParam;
  }

  @Override
  public String getPk() {
    return getRuleCd();
  }

  @Override
  public String getDefinedPk(int index) {
    return null;
  }

}
