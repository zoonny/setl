package com.kt.mmp.setl.setlCalc.service.cache;

import com.kt.mmp.comn.base.cache.service.PeriodCacheService;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfoPk;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SetlItemInfoService extends PeriodCacheService<SetlItemInfo, SetlItemInfoPk> {

  public SetlItemInfoService(
      JpaRepository<SetlItemInfo, SetlItemInfoPk> repository) {
    super(repository);
  }

  public Map<String, List<SetlItemInfo>> findGroupBySetlItemCd() {
    return entities.stream()
        .collect(Collectors.groupingBy(i -> i.getPk().getSetlItemCd()));
  }

}
