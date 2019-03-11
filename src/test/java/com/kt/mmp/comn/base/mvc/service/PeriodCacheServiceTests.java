package com.kt.mmp.comn.base.mvc.service;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import java.time.LocalDateTime;

public abstract class PeriodCacheServiceTests<Entity extends BaseEntity, Id> extends CacheServiceTests<Entity, Id> {

  @Override
  public void doInitialize() {
    makeTestData(this.testDatas);
    repository.saveAll(this.testDatas);
    service.initialize(getStDt(), getFnsDt());
  }

  protected LocalDateTime getStDt() {
    return TestConstants.PERIOD_ST_DT;
  }

  protected LocalDateTime getFnsDt() {
    return TestConstants.PERIOD_FNS_DT;
  }

}
