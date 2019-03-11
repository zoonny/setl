package com.kt.mmp.comn.code.repository;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.code.domain.CdDtlInfo;
import com.kt.mmp.comn.code.domain.CdDtlInfoPk;
import com.kt.mmp.comn.code.domain.CdGroupInfo;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Deprecated
@RunWith(SpringRunner.class)
@SpringBootTest
//@DataJpaTest
public class CdGroupInfoRepositoryTests {

  @Autowired
  private CdGroupInfoRepository cdGroupInfoRepository;

  private CdGroupInfo cdGroupInfo;

  @Before
  public void initialize() {
    List<CdDtlInfo> cdDtlInfos = Lists.newArrayList();

    for (int idx = 0; idx < 10; idx++) {
      CdDtlInfo cdDtlInfo = CdDtlInfo.builder()
          .pk(CdDtlInfoPk.builder()
              .cdGroupId("GRPCD")
              .cdDtlId("DTLCD" + idx)
              .build())
          .cdDtlNm("상세코드" + idx)
          .build();
      ComnUtils.setCretCtrlField(this, cdDtlInfo);
      cdDtlInfos.add(cdDtlInfo);
    }

    this.cdGroupInfo = CdGroupInfo.builder()
        .cdGroupId("GRPCD")
        .cdGroupNm("그룹코드")
        .cdGroupSbst("그룹코드명")
        .cdDtlInfos(cdDtlInfos)
        .build();
    ComnUtils.setCretCtrlField(this, this.cdGroupInfo);
  }

  @After
  public void finalize() {

  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Test
  public void crudWithTransactional() {
    // given
// CdGroupInfo : @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    // when
    cdGroupInfoRepository.save(this.cdGroupInfo);
    CdGroupInfo cdGroupInfo = cdGroupInfoRepository.findAll().get(0);

    // then
    Assertions.assertThat(cdGroupInfo.getCdGroupId()).isEqualTo("GRPCD");
    Assertions.assertThat(cdGroupInfo.getCdDtlInfos().size()).isEqualTo(10);

    // when
    ComnUtils.setAmdCtrlField(this, cdGroupInfo);
    ComnUtils.setAmdCtrlField(this, cdGroupInfo.getCdDtlInfos().get(0));
    cdGroupInfoRepository.save(cdGroupInfo);

    // then
    Assertions.assertThat(cdGroupInfo.getAmdrId()).isNotEmpty();
    Assertions.assertThat(cdGroupInfo.getCdDtlInfos().get(0).getAmdrId()).isNotEmpty();

    // when
//    cdGroupInfoRepository.deleteAll();

    // 테스트는 성공, deleteAll() 호출과 무관하게, DB에 데이터가 남지 않음
    // FetchType.EAGER 도 성공, DB에 데이터는 남지 않음
    // 모델 직접 변경 시, DB 변경 발생

    // then
//    List<CdGroupInfo> cdGroupInfoList = cdGroupInfoRepository.findAll();
//    Assertions.assertThat(cdGroupInfoList).isEmpty();
  }

  @Test
  public void crudWithNonTransactional() {

// CdGroupInfo : @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    // when
    cdGroupInfoRepository.save(this.cdGroupInfo);
    CdGroupInfo cdGroupInfo = cdGroupInfoRepository.findAll().get(0);

    // then
    Assertions.assertThat(cdGroupInfo.getCdGroupId()).isEqualTo("GRPCD");
    Assertions.assertThat(cdGroupInfo.getCdDtlInfos().size()).isEqualTo(10);

    // when
    ComnUtils.setAmdCtrlField(this, cdGroupInfo);
    ComnUtils.setAmdCtrlField(this, cdGroupInfo.getCdDtlInfos().get(0));
    cdGroupInfoRepository.save(cdGroupInfo);

    // then
    Assertions.assertThat(cdGroupInfo.getAmdrId()).isNotEmpty();
    Assertions.assertThat(cdGroupInfo.getCdDtlInfos().get(0).getAmdrId()).isNotEmpty();

    // when
    cdGroupInfoRepository.deleteAll();

    // 테스트는 성공, 데이터도 저장됨

    // then
//    List<CdGroupInfo> cdGroupInfoList = cdGroupInfoRepository.findAll();
//    Assertions.assertThat(cdGroupInfoList).isEmpty();
  }

}
