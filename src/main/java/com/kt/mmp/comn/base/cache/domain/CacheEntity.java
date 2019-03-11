package com.kt.mmp.comn.base.cache.domain;

public interface CacheEntity<Id> {

  public Id getPk();

  public String getDefinedPk(int index);

}
