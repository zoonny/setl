package com.kt.mmp.comn.code.domain;

import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.cache.domain.CacheEntity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

@Entity
@Table(name = "cd_group_info")
@ToString(exclude = "cdDtlInfos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"cdGroupId"})
public class CdGroupInfo extends BaseEntity implements CacheEntity<String> {

  @Id
  private String cdGroupId;

  @Column
  private String cdGroupNm;

  @Column
  private String cdGroupSbst;

  @Column
  private int cdLen;

  @Column
  private String useYn;

  @Column
  private String upCdGroupId;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  FetchType.LAZY : @Transactional 설정 필요
//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cdGroupId")
  private List<CdDtlInfo> cdDtlInfos;

  @Builder
  public CdGroupInfo(String cdGroupId, String cdGroupNm, String cdGroupSbst, int cdLen,
      String useYn, String upCdGroupId,
      @Singular List<CdDtlInfo> cdDtlInfos) {
    this.cdGroupId = cdGroupId;
    this.cdGroupNm = cdGroupNm;
    this.cdGroupSbst = cdGroupSbst;
    this.cdLen = cdLen;
    this.useYn = useYn;
    this.upCdGroupId = upCdGroupId;
    this.cdDtlInfos = cdDtlInfos;
  }

  @Override
  public String getPk() {
    return cdGroupId;
  }

  @Override
  public String getDefinedPk(int index) {
    return null;
  }

}
