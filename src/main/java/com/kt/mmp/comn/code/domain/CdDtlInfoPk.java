package com.kt.mmp.comn.code.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CdDtlInfoPk implements Serializable {

  @Column
  private String cdGroupId;

  @Column
  private String cdDtlId;

  @Builder
  public CdDtlInfoPk(String cdGroupId, String cdDtlId) {
    this.cdGroupId = cdGroupId;
    this.cdDtlId = cdDtlId;
  }

}
