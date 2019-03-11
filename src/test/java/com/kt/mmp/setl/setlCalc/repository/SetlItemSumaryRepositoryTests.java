package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.setl.setlCalc.domain.SetlItemSumary;
import com.kt.mmp.setl.setlCalc.domain.SetlItemSumaryPk;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemSumaryRepositoryTests extends
    CrudRepositoryTests<SetlItemSumary, SetlItemSumaryPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemSumary, SetlItemSumaryPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemSumary build(int index) {
    return SetlItemSumary.builder()
        .pk(SetlItemSumaryPk.builder()
            .setlTgtYm("201902")
            .setlItemCd("TEST" + index)
            .stepNo(1)
            .ptnrId("PTNR" + index)
            .build())
        .setlAmt(100)
        .setlVat(10)
        .adjAmt(0)
        .adjVat(0)
        .build();
  }

  @Override
  protected SetlItemSumaryPk getId(SetlItemSumary setlItemSumary) {
    return setlItemSumary.getPk();
  }
}
