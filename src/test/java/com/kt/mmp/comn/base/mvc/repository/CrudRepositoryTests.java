package com.kt.mmp.comn.base.mvc.repository;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.base.exception.ErrorCode;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class CrudRepositoryTests<Entity extends BaseEntity, Id> {

  protected JpaRepository<Entity, Id> repository;

  protected List<Entity> testDatas = Lists.newArrayList();

  abstract protected void setRepository(JpaRepository<Entity, Id> repository);

  abstract protected int getDataCount();

  abstract protected Entity build(int index);

  abstract protected Id getId(Entity entity);

  @Before
  public void initialize() {
    makeTestData(testDatas);
  }

  @After
  public void finalize() {

  }

  public void makeTestData(List<Entity> entities) {
    for (int index = 0; index < getDataCount(); index++) {
      Entity entity = build(index);
      ComnUtils.setCretCtrlField(this, entity);
      entities.add(entity);
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Test
  public void 목록조회() {
    System.out.println(">>>>> FIND_ALL");

    repository.saveAll(this.testDatas);

    List<Entity> entities = repository.findAll();

    entities.stream()
        .forEach(entity -> {
          System.out.println(entity.hashCode() + " : " + entity);
        });

    Assertions.assertThat(entities.size()).isGreaterThanOrEqualTo(getDataCount());
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Test
  public void 생성_조회() {
    System.out.println(">>>>> FIND_BY_ID");

    repository.save(this.testDatas.get(0));

    Optional<Entity> entity = repository.findById(getId(this.testDatas.get(0)));

    System.out.println(entity.map(Entity::toString).orElse(ErrorCode.NO_DATA.name()));

    Assertions.assertThat(entity).isNotNull();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Test
  public void 수정() {
    System.out.println(">>>>> UPDATE");

    repository.save(this.testDatas.get(0));

    Optional<Entity> entity = repository.findById(getId(this.testDatas.get(0)));
    ComnUtils.setAmdCtrlField(this, entity.get());

    repository.save(entity.get());

    Assertions.assertThat(entity.get().getAmdrId()).isNotEmpty();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Test
  public void 삭제() {
    System.out.println(">>>>> DELETE");

    repository.saveAll(this.testDatas);

    repository.delete(this.testDatas.get(0));

    Optional<Entity> entity = repository.findById(getId(this.testDatas.get(0)));

    Assertions.assertThat(entity).isEmpty();
  }

}
