package com.kt.mmp.setl.setlCalc.service;

import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.exception.BaseException;
import com.kt.mmp.comn.base.exception.ErrorCode;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class SetlService<Dto, ReadItem, WriteItem> implements ApplicationContextAware {

  protected ApplicationContext applicationContext;

  protected abstract Dto buildDto(String setlTgtYm);

  protected abstract WriteItem doProcess(final Dto dto, final ReadItem item);

  public Dto prepare(String setlTgtYm) {
    loadingCache(setlTgtYm);

    return buildDto(setlTgtYm);
  }

  @Transactional(readOnly = true)
  protected void loadingCache(String setlTgtYm) {
    Map<String, CacheService> cacheServiceMap = applicationContext
        .getBeansOfType(CacheService.class);

    Optional.ofNullable(cacheServiceMap).orElseThrow(() -> new BaseException(ErrorCode.NULL));

    for (Entry<String, CacheService> iter : cacheServiceMap.entrySet()) {
      iter.getValue()
          .initialize(ComnUtils.stDtOfMonth(setlTgtYm), ComnUtils.fnsDtOfMonth(setlTgtYm));
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public WriteItem process(final Dto dto, final ReadItem item) {
    return doProcess(dto, item);
  }

  public void wrapup(final Dto dto) {
    return;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
