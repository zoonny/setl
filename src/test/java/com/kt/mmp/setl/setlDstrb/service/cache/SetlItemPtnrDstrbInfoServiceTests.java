package com.kt.mmp.setl.setlDstrb.service.cache;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.mvc.service.CacheServiceTests;
import com.kt.mmp.comn.base.mvc.service.PeriodCacheServiceTests;
import com.kt.mmp.comn.code.code.Yn;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfoPk;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemPtnrDstrbInfoServiceTests extends
    PeriodCacheServiceTests<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> {

  @Autowired
  @Override
  protected void setRepository(
      JpaRepository<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> service) {
      this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected SetlItemPtnrDstrbInfo build(int index) {
    return SetlItemPtnrDstrbInfo.builder()
        .pk(SetlItemPtnrDstrbInfoPk.builder()
            .setlItemCd(TestConstants.SETL_ITEM_CD + index)
            .ptnrId(TestConstants.PTNR_ID + index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .dstrbRate(0.4)
        .dstrbYn(Yn.Y.name())
        .build();
  }

  @Override
  protected SetlItemPtnrDstrbInfoPk getId(SetlItemPtnrDstrbInfo entity) {
    return entity.getPk();
  }

}
