package com.kt.mmp.setl.setlCalc.service.cache;

import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.setl.setlCalc.domain.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleInfoService extends CacheService<RuleInfo, String> {

  public RuleInfoService(JpaRepository<RuleInfo, String> repository) {
    super(repository);
  }

}
