package com.kt.mmp.setl.setlCalc.service.cache;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.mvc.service.PeriodCacheServiceTests;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.RuleTypeCd;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfoPk;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemRuleInfoServiceTests extends
    PeriodCacheServiceTests<SetlItemRuleInfo, SetlItemRuleInfoPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemRuleInfo, SetlItemRuleInfoPk> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<SetlItemRuleInfo, SetlItemRuleInfoPk> service) {
    this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected SetlItemRuleInfo build(int index) {
    return SetlItemRuleInfo.builder()
        .setlItemRuleInfoPk(SetlItemRuleInfoPk.builder()
            .setlItemCd(TestConstants.SETL_ITEM_CD + index)
            .ruleCd(TestConstants.RULE_CD + index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .odrg(1)
        .build();
  }

  @Override
  protected SetlItemRuleInfoPk getId(SetlItemRuleInfo entity) {
    return entity.getPk();
  }

}
