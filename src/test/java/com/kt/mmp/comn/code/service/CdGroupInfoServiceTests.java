package com.kt.mmp.comn.code.service;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.code.domain.CdDtlInfo;
import com.kt.mmp.comn.code.domain.CdDtlInfoPk;
import com.kt.mmp.comn.code.domain.CdGroupInfo;
import com.kt.mmp.comn.base.mvc.service.CacheServiceTests;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.exception.ErrorCode;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CdGroupInfoServiceTests extends CacheServiceTests<CdGroupInfo, String> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<CdGroupInfo, String> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<CdGroupInfo, String> service) {
    this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected CdGroupInfo build(int index) {
    List<CdDtlInfo> cdDtlInfos = Lists.newArrayList();

    for (int i = 0; i < 10; i++) {
      CdDtlInfo cdDtlInfo = CdDtlInfo.builder()
          .pk(CdDtlInfoPk.builder()
              .cdGroupId("GRPCD" + index)
              .cdDtlId("DTLCD" + i)
              .build())
          .cdDtlNm("상세코드" + i)
          .build();
      ComnUtils.setCretCtrlField(this, cdDtlInfo);
      cdDtlInfos.add(cdDtlInfo);
    }

    return CdGroupInfo.builder()
        .cdGroupId("GRPCD" + index)
        .cdGroupNm("그룹코드명" + index)
        .cdGroupSbst("그룹코드설명" + index)
        .cdLen(10)
        .useYn("Y")
        .cdDtlInfos(cdDtlInfos)
        .build();
  }

  @Override
  protected String getId(CdGroupInfo entity) {
    return entity.getCdGroupId();
  }

  @Test
  public void 상세코드_조회() {
    System.out.println(">>>>> FIND_CD_DTL_INFO");

    Optional<CdDtlInfo> cdDtlInfo = ((CdGroupInfoService) service)
        .findCdDtlInfo("GRPCD0", "DTLCD0");

    System.out.println(cdDtlInfo.map(CdDtlInfo::toString).orElse(ErrorCode.NO_DATA.name()));

    Assertions.assertThat(cdDtlInfo.orElse(null)).isNotNull();
    Assertions.assertThat(cdDtlInfo.get().getPk().getCdDtlId()).isEqualTo("DTLCD0");
  }

  @Test
  public void 상세코드_명_조회() {
    System.out.println(">>>>> FIND_CD_DTL_INFO_NM");

    String cdDtlNm = ((CdGroupInfoService) service)
        .findCdDtlInfoNm("GRPCD0", "DTLCD0");

    System.out.println(cdDtlNm);

    Assertions.assertThat(cdDtlNm).isNotEmpty();
  }

}
