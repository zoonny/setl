package com.kt.mmp.setl.setlDstrb.repository;

import com.kt.mmp.comn.base.cache.repository.PeriodCacheRepository;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfoPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetlItemPtnrDstrbInfoRepository extends
    PeriodCacheRepository<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> {

}
