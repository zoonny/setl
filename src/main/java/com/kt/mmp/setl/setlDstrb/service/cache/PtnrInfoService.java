package com.kt.mmp.setl.setlDstrb.service.cache;

import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.setl.setlDstrb.domain.PtnrInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PtnrInfoService extends CacheService<PtnrInfo, String> {

  public PtnrInfoService(
      JpaRepository<PtnrInfo, String> repository) {
    super(repository);
  }

  public boolean isExistPtnr(String ptnrId, String ptnrDivCd) {
    PtnrInfo ptnrInfo = findById(ptnrId).get();
    if (ptnrInfo != null && ptnrDivCd.equals(ptnrInfo.getPtnrDivCd())) {
      return true;
    }
    return false;
  }

}
