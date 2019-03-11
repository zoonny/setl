package com.kt.mmp.setl.setlDstrb.service.cache;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.mvc.service.CacheServiceTests;
import com.kt.mmp.comn.code.code.PtnrDivCd;
import com.kt.mmp.setl.setlDstrb.domain.PtnrInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PtnrInfoServiceTests extends CacheServiceTests<PtnrInfo, String> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<PtnrInfo, String> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<PtnrInfo, String> service) {
    this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected PtnrInfo build(int index) {
    return PtnrInfo.builder()
        .ptnrId(TestConstants.PTNR_ID + index)
        .ptnrDivCd(PtnrDivCd.EMR.name())
        .ptnrNm(TestConstants.PTNR_NM + index)
        .tkcgr("홍길동")
        .tkcgDept("사업부")
        .telNo("01099998888")
        .adr("경기도 성남시 분당구 불정로 KT")
        .bizrNo("888999000")
        .email("hong@kt.com")
        .bankCd("KB")
        .bnkacnNo("3334445555")
        .dposrNm("KB손해보험")
        .build();
  }

  @Override
  protected String getId(PtnrInfo entity) {
    return entity.getPtnrId();
  }

}
