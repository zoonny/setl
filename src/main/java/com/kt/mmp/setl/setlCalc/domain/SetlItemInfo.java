package com.kt.mmp.setl.setlCalc.domain;

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
//@ToString(exclude = {"setlItemNm", "setlTypeCd", "setlDivCd", "setlSpread", "vatYn", "dtlCretYn"})
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"pk"}, callSuper = false)
//@IdClass(SetlItemInfo.class)  // for declare multiple id
//@Alias("setlItemInfo")  // for mybatis
public class SetlItemInfo extends BaseEntity implements PeriodCacheEntity<SetlItemInfoPk> {

  @EmbeddedId
  private SetlItemInfoPk pk;

  @Column(nullable = false)
  private LocalDateTime efctStDt;

  @Column(nullable = false)
  private String setlItemNm;

  @Column(nullable = false)
  private String setlTypeCd;

  @Column(nullable = false)
  private String setlDivCd;

  @Column
  private int setlSperd;

  @Column
  private String vatYn;

  @Column
  private String dtlCretYn;

  @Builder
  public SetlItemInfo(SetlItemInfoPk pk, LocalDateTime efctStDt,
      String setlItemNm, String setlTypeCd, String setlDivCd, int setlSperd, String vatYn, String dtlCretYn) {
    this.pk = pk;
    this.efctStDt = efctStDt;
    this.setlItemNm = setlItemNm;
    this.setlTypeCd = setlTypeCd;
    this.setlDivCd = setlDivCd;
    this.setlSperd = setlSperd;
    this.vatYn = vatYn;
    this.dtlCretYn = dtlCretYn;
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
  public int compareTo(PeriodCacheEntity<SetlItemInfoPk> entity) {
    return getEfctStDt().compareTo(entity.getEfctStDt());
  }

}
