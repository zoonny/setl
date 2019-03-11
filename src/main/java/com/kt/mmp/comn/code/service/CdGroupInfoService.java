package com.kt.mmp.comn.code.service;

import com.kt.mmp.comn.code.domain.CdDtlInfo;
import com.kt.mmp.comn.code.domain.CdDtlInfoPk;
import com.kt.mmp.comn.code.domain.CdGroupInfo;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.exception.BaseException;
import com.kt.mmp.comn.base.exception.ErrorCode;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CdGroupInfoService extends CacheService<CdGroupInfo, String> {

  public CdGroupInfoService(
      JpaRepository<CdGroupInfo, String> repository) {
    super(repository);
  }

  public Optional<CdDtlInfo> findCdDtlInfo(String cdGroupId, String cdDtlId) {
    CdDtlInfoPk cdDtlInfoPk = CdDtlInfoPk.builder()
        .cdGroupId(cdGroupId)
        .cdDtlId(cdDtlId)
        .build();

    return findById(cdGroupId)
        .map(CdGroupInfo::getCdDtlInfos)
//        .orElseThrow(() -> new BaseException(ErrorCode.NO_DATA.name(), cdGroupId, cdDtlId))
        .orElseThrow(() -> new BaseException(cdGroupId))
        .stream()
        .filter(cdDtlInfo -> {
          return cdDtlInfo.getPk().equals(cdDtlInfoPk);
        })
        .findFirst();
  }

  public String findCdDtlInfoNm(String cdGroupId, String cdDtlId) {
    return findCdDtlInfo(cdGroupId, cdDtlId)
        .map(CdDtlInfo::getCdDtlNm)
        .orElse("");
  }

  public boolean isExistCdDtl(String cdGroupId, String cdDtlId) {
    boolean ret = true;
    try {
      if (findCdDtlInfo(cdGroupId, cdDtlId).get() != null) {
        ret = true;
      }
    } catch (Exception e) {
      ret = false;
    }
    return ret;
  }

}
