package com.kt.mmp.comn.base.cache.service;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.cache.repository.PeriodCacheRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public abstract class PeriodCacheService<Entity, Id> extends CacheService<Entity, Id> {

  public PeriodCacheService(
      JpaRepository<Entity, Id> repository) {
    super(repository);
  }

  @Override
  protected void doInitialize(LocalDateTime stDt, LocalDateTime fnsDt) {
    if (stDt != null && fnsDt != null) {
      this.entities = ((PeriodCacheRepository) repository)
          .findByPkEfctFnsDtGreaterThanAndEfctStDtLessThanEqualOrderByPk(stDt, fnsDt);
    } else {
      super.doInitialize(null, null);
    }
  }

  public Optional<Entity> findByEfctDt(LocalDateTime basDt) {
    return entities.stream()
        .filter(entity -> {
          PeriodCacheEntity<Id> _entity = (PeriodCacheEntity<Id>) entity;
          if (basDt.compareTo(_entity.getEfctStDt()) >= 0 &&
              basDt.compareTo(_entity.getEfctFnsDt()) < 0) {
            return true;
          }
          return false;
        })
        .findFirst();
//        .collect(Collectors.toList());
  }

}
