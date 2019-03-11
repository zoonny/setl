package com.kt.mmp.setl.setlDstrb.repository;

import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtl;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbDtlPk;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemDstrbDtlRepositoryTests extends
    CrudRepositoryTests<SetlItemDstrbDtl, SetlItemDstrbDtlPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemDstrbDtl, SetlItemDstrbDtlPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemDstrbDtl build(int index) {
    return null;
  }

  @Override
  protected SetlItemDstrbDtlPk getId(SetlItemDstrbDtl setlItemDstrbDtl) {
    return setlItemDstrbDtl.getPk();
  }
}
