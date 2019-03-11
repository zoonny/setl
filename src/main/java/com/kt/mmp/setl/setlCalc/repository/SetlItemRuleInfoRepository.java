package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.base.cache.repository.PeriodCacheRepository;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfoPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetlItemRuleInfoRepository extends
    PeriodCacheRepository<SetlItemRuleInfo, SetlItemRuleInfoPk> {

}
