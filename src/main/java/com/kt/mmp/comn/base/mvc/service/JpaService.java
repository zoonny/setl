package com.kt.mmp.comn.base.mvc.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public abstract class JpaService<Entity, Id> extends BaseService {

  protected JpaRepository<Entity, Id> repository;

  abstract protected void setRepository(JpaRepository<Entity, Id> repository);

  @Transactional(readOnly = true)
  public List<Entity> findAll() {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public List<Entity> findAllByIds(List<Id> ids) {
    return repository.findAllById(ids);
  }

  @Transactional(readOnly = true)
  public Optional<Entity> findById(Id id) {
    return repository.findById(id);
  }

  @Transactional(readOnly = true)
  public Optional<Entity> findOne(Entity entity) {
    return repository.findOne(Example.of(entity));
  }

  @Transactional(readOnly = true)
  public Entity getOne(Id id) {
    return repository.getOne(id);
  }

  public List<Entity> saveAll(List<Entity> entities) {
    return repository.saveAll(entities);
  }

  public Entity save(Entity entity) {
    return repository.save(entity);
  }

  public Entity saveAndFlush(Entity entity) {
    return repository.saveAndFlush(entity);
  }

  public void deleteAll() {
    repository.deleteAll();
  }

  public void deleteAll(List<Entity> entities) {
    repository.deleteAll(entities);
  }

  public void deleteById(Id id) {
    repository.deleteById(id);
  }

  public void delete(Entity entity) {
    repository.delete(entity);
  }

  public void deleteInBatch(List<Entity> entities) {
    repository.deleteInBatch(entities);
  }

}
