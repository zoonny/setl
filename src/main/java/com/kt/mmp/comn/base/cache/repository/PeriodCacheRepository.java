package com.kt.mmp.comn.base.cache.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PeriodCacheRepository<Entity, Id> extends JpaRepository<Entity, Id> {

  // 현재시간 기준 앞으로 유효한 데이터 조회
  public List<Entity> findByPkEfctFnsDtAfterOrderByPk(
      LocalDateTime curDt);

  // From ~ To 기준 유효한 데이터 조회
  public List<Entity> findByPkEfctFnsDtGreaterThanAndEfctStDtLessThanEqualOrderByPk(
      LocalDateTime basStDt, LocalDateTime basFnsDt);

}
