package com.kt.mmp.setl.setlDstrb.repository;

import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumary;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumaryPk;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemDstrbSumaryRepositoryTests extends
    CrudRepositoryTests<SetlItemDstrbSumary, SetlItemDstrbSumaryPk> {

  @Autowired
  @Override
  protected void setRepository(
      JpaRepository<SetlItemDstrbSumary, SetlItemDstrbSumaryPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemDstrbSumary build(int index) {
    return null;
  }

  @Override
  protected SetlItemDstrbSumaryPk getId(SetlItemDstrbSumary setlItemDstrbSumary) {
    return setlItemDstrbSumary.getPk();
  }
}
