package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.base.cache.repository.PeriodCacheRepository;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfoPk;
import java.util.List;
import javax.persistence.Entity;
import org.springframework.data.jpa.repository.Query;

public interface SetlItemInfoRepository extends
    PeriodCacheRepository<SetlItemInfo, SetlItemInfoPk> {

  // @ => m.setlItemInfoPk.efctFnsDt
  @Query(value = "select s from SetlItemInfo s where s.setlItemNm = ?1", nativeQuery = false)
  public List<Entity> findBySetlItemNm(String setlItemNm);

}
