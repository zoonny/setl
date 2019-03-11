package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.code.code.RuleTypeCd;
import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.comn.base.util.ComnUtils;
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
public class SetlItemRuleInfoRepositoryTests extends
    CrudRepositoryTests<SetlItemRuleInfo, SetlItemRuleInfoPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemRuleInfo, SetlItemRuleInfoPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemRuleInfo build(int index) {
    return SetlItemRuleInfo.builder()
        .setlItemRuleInfoPk(SetlItemRuleInfoPk.builder()
            .setlItemCd("TEST" + index)
            .ruleCd("TEST" + index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .odrg(1)
        .build();
  }

  @Override
  protected SetlItemRuleInfoPk getId(SetlItemRuleInfo setlItemRuleInfo) {
    return setlItemRuleInfo.getPk();
  }

}
