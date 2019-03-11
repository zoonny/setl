package com.kt.mmp.comn.base.cache.domain;

import java.time.LocalDateTime;

public interface PeriodCacheEntity<Id> extends CacheEntity<Id> {

  public LocalDateTime getEfctStDt();

  public LocalDateTime getEfctFnsDt();

  public int compareTo(PeriodCacheEntity<Id> entity);

}
