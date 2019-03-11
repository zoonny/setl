package com.kt.mmp.comn.code.domain;

import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cd_dtl_info")
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"pk"})
public class CdDtlInfo extends BaseEntity {

  @EmbeddedId
  private CdDtlInfoPk pk;

  @Column
  private String cdDtlNm;

  @Column
  private String cdDtlSbst;

  @Column
  private String useYn;

  @Column
  private int indcOdrg;

  @Column(name="expnsn_str_var_1")
  private String expnsnStrVar1;

  @Column(name="expnsn_str_var_2")
  private String expnsnStrVar2;

  @Column(name="expnsn_str_var_3")
  private String expnsnStrVar3;

  @Column
  private String upCdDtlId;

  @Builder
  public CdDtlInfo(CdDtlInfoPk pk, String cdDtlNm, String cdDtlSbst,
      String useYn, int indcOdrg, String expnsnStrVar1, String expnsnStrVar2,
      String expnsnStrVar3, String upCdDtlId) {
    this.pk = pk;
    this.cdDtlNm = cdDtlNm;
    this.cdDtlSbst = cdDtlSbst;
    this.useYn = useYn;
    this.indcOdrg = indcOdrg;
    this.expnsnStrVar1 = expnsnStrVar1;
    this.expnsnStrVar2 = expnsnStrVar2;
    this.expnsnStrVar3 = expnsnStrVar3;
    this.upCdDtlId = upCdDtlId;
  }

}
