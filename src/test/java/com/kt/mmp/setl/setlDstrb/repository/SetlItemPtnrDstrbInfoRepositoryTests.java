package com.kt.mmp.setl.setlDstrb.repository;

import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfoPk;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemPtnrDstrbInfoRepositoryTests extends
    CrudRepositoryTests<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> {

  @Override
  protected void setRepository(
      JpaRepository<SetlItemPtnrDstrbInfo, SetlItemPtnrDstrbInfoPk> repository) {
  }

  @Override
  protected int getDataCount() {
    return 0;
  }

  @Override
  protected SetlItemPtnrDstrbInfo build(int index) {
    return null;
  }

  @Override
  protected SetlItemPtnrDstrbInfoPk getId(SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo) {
    return null;
  }

}
