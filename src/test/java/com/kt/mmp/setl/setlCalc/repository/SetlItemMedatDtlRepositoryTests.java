package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.code.code.BillWhyCd;
import com.kt.mmp.comn.code.code.MotDivCd;
import com.kt.mmp.comn.code.code.OtgoInstnCd;
import com.kt.mmp.comn.code.code.OtgoInstnChCd;
import com.kt.mmp.comn.code.code.RcvInstnCd;
import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtlPk;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetlItemMedatDtlRepositoryTests extends
    CrudRepositoryTests<SetlItemMedatDtl, SetlItemMedatDtlPk> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<SetlItemMedatDtl, SetlItemMedatDtlPk> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected SetlItemMedatDtl build(int index) {
    return SetlItemMedatDtl.builder()
        .pk(SetlItemMedatDtlPk.builder()
            .setlTgtYm("201902")
            .setlItemCd("TEST" + index)
            .stepNo(1)
            .ptnrId(RcvInstnCd.KB.name())
            .otgoInstnCd(OtgoInstnCd.NSS.name())
            .otgoInstnChCd(OtgoInstnChCd.K.name())
            .billWhyCd(BillWhyCd.D.name())
            .motDivCd(MotDivCd.MP.name())
            .build())
        .trtCascnt(10)
        .wholeAmt(1000)
        .build();
  }

  @Override
  protected SetlItemMedatDtlPk getId(SetlItemMedatDtl setlItemMedatDtl) {
    return setlItemMedatDtl.getPk();
  }
}
