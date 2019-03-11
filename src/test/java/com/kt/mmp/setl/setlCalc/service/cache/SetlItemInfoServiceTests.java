package com.kt.mmp.setl.setlCalc.service.cache;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.mvc.service.PeriodCacheServiceTests;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.SetlTypeCd;
import com.kt.mmp.comn.code.code.Yn;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfoPk;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemInfoServiceTests extends
    PeriodCacheServiceTests<SetlItemInfo, SetlItemInfoPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemInfo, SetlItemInfoPk> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<SetlItemInfo, SetlItemInfoPk> service) {
    this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected SetlItemInfo build(int index) {
    return SetlItemInfo.builder()
        .pk(SetlItemInfoPk.builder()
            .setlItemCd(TestConstants.SETL_ITEM_CD + index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .setlItemNm(TestConstants.SETL_ITEM_NM + index)
        .setlTypeCd(SetlTypeCd.motDivCd.name())
        .setlSperd(999)
        .vatYn(Yn.Y.name())
        .dtlCretYn(Yn.Y.name())
        .build();
  }

  @Override
  protected SetlItemInfoPk getId(SetlItemInfo entity) {
    return entity.getPk();
  }

}
