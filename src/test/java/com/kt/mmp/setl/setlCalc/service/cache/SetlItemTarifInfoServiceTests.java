package com.kt.mmp.setl.setlCalc.service.cache;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.mvc.service.PeriodCacheServiceTests;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.CalcTypeCd;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfoPk;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemTarifInfoServiceTests extends
    PeriodCacheServiceTests<SetlItemTarifInfo, SetlItemTarifInfoPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemTarifInfo, SetlItemTarifInfoPk> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<SetlItemTarifInfo, SetlItemTarifInfoPk> service) {
    this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected SetlItemTarifInfo build(int index) {
    return SetlItemTarifInfo.builder()
        .pk(SetlItemTarifInfoPk.builder()
            .setlItemCd(TestConstants.SETL_ITEM_CD + index)
            .odrg(index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .calcTypeCd(CalcTypeCd.TRF.name())
        .tarifVal(133)
        .build();
  }

  @Override
  protected SetlItemTarifInfoPk getId(SetlItemTarifInfo entity) {
    return entity.getPk();
  }

}
