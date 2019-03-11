package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.comn.code.code.RuleTypeCd;
import com.kt.mmp.setl.setlCalc.domain.RuleInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleInfoRepositoryTests extends
    CrudRepositoryTests<RuleInfo, String> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<RuleInfo, String> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected RuleInfo build(int index) {
    return RuleInfo.builder()
        .ruleCd("RULE" + index)
        .ruleNm("RULE" + index)
        .ruleTypeCd(RuleTypeCd.EXC.name())
        .ruleParam("1:20;2:30,3:40")
        .build();
  }

  @Override
  protected String getId(RuleInfo ruleInfo) {
    return ruleInfo.getRuleCd();
  }

}
