package com.kt.mmp.setl.setlCalc.repository;

import com.kt.mmp.comn.code.code.BillWhyCd;
import com.kt.mmp.comn.code.code.MotDivCd;
import com.kt.mmp.comn.code.code.OtgoInstnCd;
import com.kt.mmp.comn.code.code.OtgoInstnChCd;
import com.kt.mmp.comn.code.code.RcvInstnCd;
import com.kt.mmp.comn.code.code.TrtResltCd;
import com.kt.mmp.comn.base.mvc.repository.CrudRepositoryTests;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicalTransacHstRepositoryTests extends
    CrudRepositoryTests<MedicalTransacHst, String> {

  @Autowired
  @Override
  protected void setRepository(JpaRepository<MedicalTransacHst, String> repository) {
    this.repository = repository;
  }

  @Override
  protected int getDataCount() {
    return 5;
  }

  @Override
  protected MedicalTransacHst build(int index) {
    return MedicalTransacHst.builder()
        .transacId(String.valueOf(index))
        .occDt(LocalDateTime.now())
        .otgoInstnCd(OtgoInstnCd.NSS.name())
        .otgoInstnChCd(OtgoInstnChCd.K.name())
        .rcvInstnCd(RcvInstnCd.KB.name())
        .billWhyCd(BillWhyCd.D.name())
        .motDivCd(MotDivCd.MP.name())
        .amt(1000)
        .trtResltCd(TrtResltCd.S.name())
        .build();
  }

  @Override
  protected String getId(MedicalTransacHst medicalTransacHst) {
    return medicalTransacHst.getTransacId();
  }

}
