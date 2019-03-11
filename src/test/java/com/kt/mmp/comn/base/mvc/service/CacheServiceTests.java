package com.kt.mmp.comn.base.mvc.service;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.exception.ErrorCode;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CacheServiceTests<Entity extends BaseEntity, Id> {

  protected JpaRepository<Entity, Id> repository;

  protected CacheService<Entity, Id> service;

  protected List<Entity> testDatas = Lists.newArrayList();

  abstract protected void setRepository(JpaRepository<Entity, Id> repository);

  abstract protected void setService(CacheService<Entity, Id> service);

  abstract protected int getDataCount();

  abstract protected Entity build(int index);

  abstract protected Id getId(Entity entity);

  @Before
  public void initialize() {
    doInitialize();
  }

  public void doInitialize() {
    makeTestData(this.testDatas);
    repository.saveAll(this.testDatas);
    service.initialize(null, null);
  }

  @After
  public void finalize() {
    doFinalize();
  }

  public void doFinalize() {

  }

  public void makeTestData(List<Entity> entities) {
    for (int index = 0; index < getDataCount(); index++) {
      Entity entity = build(index);
      ComnUtils.setCretCtrlField(this, entity);
      entities.add(entity);
    }
  }

  @Test
  public void 목록조회() {
    System.out.println(">>>>> FIND_ALL");

    List<Entity> entities = service.findAll();

    entities.stream()
        .forEach(entity -> {
          System.out.println(entity.hashCode() + " : " + entity);
        });

    Assertions.assertThat(entities.size()).isGreaterThanOrEqualTo(getDataCount());
  }

  @Test
  public void 조회() {
    System.out.println(">>>>> FIND_BY_ID");

    System.out.println(getId(this.testDatas.get(0)) + "/" + getId(this.testDatas.get(0)).hashCode());

    Optional<Entity> entity = service.findById(getId(this.testDatas.get(0)));

    System.out.println(entity.map(Entity::toString).orElse(ErrorCode.NO_DATA.name()));

    Assertions.assertThat(entity.orElse(null)).isNotNull();
  }

}
