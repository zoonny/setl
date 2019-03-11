package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.base.cache.repository.PeriodCacheRepository;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfoPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetlItemTarifInfoRepository extends
    PeriodCacheRepository<SetlItemTarifInfo, SetlItemTarifInfoPk> {

}
