package com.kt.mmp.comn.base.mvc.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@ToString
@Getter
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  @Column(name = "cretr_id")
  private String cretrId;

  @Column(name = "cret_pgm_id")
  private String cretPgmId;

  @Column(name = "cret_dt")
  @CreatedDate
  private LocalDateTime cretDt;

  @Column(name = "amdr_id")
  private String amdrId;

  @Column(name = "amd_pgm_id")
  private String amdPgmId;

  @Column(name = "amd_dt")
  @LastModifiedDate
  public LocalDateTime amdDt;

}
