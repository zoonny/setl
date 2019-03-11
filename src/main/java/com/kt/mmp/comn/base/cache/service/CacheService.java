package com.kt.mmp.comn.base.cache.service;

import com.kt.mmp.comn.base.cache.domain.CacheEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class CacheService<Entity, Id> {

  protected final JpaRepository<Entity, Id> repository;

  protected List<Entity> entities;

  public CacheService(
      JpaRepository<Entity, Id> repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public void initialize(LocalDateTime stDt, LocalDateTime fnsDt) {
    doInitialize(stDt, fnsDt);
  }

  protected void doInitialize(LocalDateTime stDt, LocalDateTime fnsDt) {
    entities = repository.findAll();
  }

  public List<Entity> findAll() {
    return entities;
  }

  public Optional<Entity> findById(Id id) {
    return entities.stream()
        .filter(entity -> ((CacheEntity<Id>)entity).getPk().equals(id))
        .findFirst();
  }

}
