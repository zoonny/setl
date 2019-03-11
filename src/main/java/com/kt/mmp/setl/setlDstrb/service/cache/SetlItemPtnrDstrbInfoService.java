package com.kt.mmp.setl.setlDstrb.service.cache;

import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.cache.service.PeriodCacheService;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfoPk;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SetlItemPtnrDstrbInfoService extends
    PeriodCacheService<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> {

  public SetlItemPtnrDstrbInfoService(
      JpaRepository<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> repository) {
    super(repository);
  }

  public Map<String, List<SetlItemPtnrDstrbInfo>> findGroupBySetlItemCdAndPtnrId() {
    return entities.stream()
        .collect(Collectors.groupingBy(i -> i.getPk().getSetlItemCd() + i.getPk().getPtnrId()));
  }

}
