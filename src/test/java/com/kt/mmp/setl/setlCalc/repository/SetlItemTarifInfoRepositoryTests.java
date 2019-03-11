package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.code.code.CalcTypeCd;
import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.comn.base.util.ComnUtils;
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
public class SetlItemTarifInfoRepositoryTests extends
    CrudRepositoryTests<SetlItemTarifInfo, SetlItemTarifInfoPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemTarifInfo, SetlItemTarifInfoPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemTarifInfo build(int index) {
    return SetlItemTarifInfo.builder()
        .pk(SetlItemTarifInfoPk.builder()
            .setlItemCd("TEST" + index)
            .odrg(index)
            .efctFnsDt(ComnUtils.getFnsDt())
            .build())
        .efctStDt(LocalDateTime.now())
        .calcTypeCd(CalcTypeCd.TRF.name())
        .tarifVal(133)
        .build();
  }

  @Override
  protected SetlItemTarifInfoPk getId(SetlItemTarifInfo setlItemTarifInfo) {
    return setlItemTarifInfo.getPk();
  }

}
