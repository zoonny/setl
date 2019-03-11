package com.kt.mmp.setl.setlCalc.service.cache;

import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.cache.service.CacheService;
import com.kt.mmp.comn.base.mvc.service.PeriodCacheServiceTests;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.RuleTypeCd;
import com.kt.mmp.setl.setlCalc.domain.RuleInfo;
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
public class RuleInfoServiceTests extends PeriodCacheServiceTests<RuleInfo, String> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<RuleInfo, String> repository) {
    this.repository = repository;
  }

  @Autowired
  @Override
  protected void setService(CacheService<RuleInfo, String> service) {
    this.service = service;
  }

  @Override
  protected int getDataCount() {
    return 10;
  }

  @Override
  protected RuleInfo build(int index) {
    return RuleInfo.builder()
        .ruleCd(TestConstants.RULE_CD + index)
        .ruleNm(TestConstants.RULE_NM + index)
        .ruleTypeCd(RuleTypeCd.INC.name())
        .ruleParam(TestConstants.RULE_PARAM)
        .build();
  }

  @Override
  protected String getId(RuleInfo entity) {
    return entity.getRuleCd();
  }

}
