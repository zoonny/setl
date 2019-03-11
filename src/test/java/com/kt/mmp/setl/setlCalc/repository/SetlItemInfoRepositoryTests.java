package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.code.code.SetlTypeCd;
import com.kt.mmp.comn.code.code.Yn;
import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.comn.base.util.ComnUtils;
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
public class SetlItemInfoRepositoryTests extends CrudRepositoryTests<SetlItemInfo, SetlItemInfoPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemInfo, SetlItemInfoPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemInfo build(int index) {
    return SetlItemInfo.builder()
        .pk(SetlItemInfoPk.builder()
            .setlItemCd("TEST" + index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .setlItemNm("정산항목" + index)
        .setlTypeCd(SetlTypeCd.motDivCd.name())
        .setlSperd(9999)
        .vatYn(Yn.N.name())
        .dtlCretYn(Yn.N.name())
        .build();
  }

  @Override
  protected SetlItemInfoPk getId(SetlItemInfo setlItemInfo) {
    return setlItemInfo.getPk();
  }

}
