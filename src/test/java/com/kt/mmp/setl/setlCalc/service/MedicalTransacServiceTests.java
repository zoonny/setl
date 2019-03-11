package com.kt.mmp.setl.setlCalc.service;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.code.code.BillWhyCd;
import com.kt.mmp.comn.code.code.MotDivCd;
import com.kt.mmp.comn.code.code.OtgoInstnCd;
import com.kt.mmp.comn.code.code.OtgoInstnChCd;
import com.kt.mmp.comn.code.code.RcvInstnCd;
import com.kt.mmp.comn.code.code.TrtResltCd;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicalTransacServiceTests {

  @Autowired
  private MedicalTransacService medicalTransacService;
  private List<MedicalTransacHst> medicalTransacHsts = Lists.newArrayList();

  @Before
  public void initialize() {
    for (int index = 0; index < 100; index++) {
      MedicalTransacHst medicalTransacHst = MedicalTransacHst.builder()
          .transacId(String.valueOf(index))
          .occDt(TestConstants.MEDICAL_TRANSAC_HST_OCC_DT[index
              % TestConstants.MEDICAL_TRANSAC_HST_OCC_DT.length])
          .otgoInstnCd(OtgoInstnCd.values()[index % OtgoInstnCd.values().length].name())
          .otgoInstnChCd(OtgoInstnChCd.values()[index % OtgoInstnChCd.values().length].name())
          .rcvInstnCd(RcvInstnCd.values()[index % RcvInstnCd.values().length].name())
          .billWhyCd(BillWhyCd.values()[index % BillWhyCd.values().length].name())
          .motDivCd(MotDivCd.values()[index % MotDivCd.values().length].name())
          .amt(TestConstants.MEDICAL_TRANSAC_HST_OCC_AMTS[index
              % TestConstants.MEDICAL_TRANSAC_HST_OCC_AMTS.length])
          .trtResltCd(TrtResltCd.values()[index % TrtResltCd.values().length].name())
          .build();
      ComnUtils.setCretCtrlField(this, medicalTransacHst);
      this.medicalTransacHsts.add(medicalTransacHst);
    }
  }

  @Test
  public void 테스트_데이터_생성() {
    if (!medicalTransacService.findAll().isEmpty()) {
      medicalTransacService.deleteAll();
    }

    List<MedicalTransacHst> medicalTransacHsts = medicalTransacService
        .saveAll(this.medicalTransacHsts);

    Assertions.assertThat(medicalTransacHsts.size()).isGreaterThan(0);

    medicalTransacHsts = medicalTransacService.findByOccDtBetweenOrderByOccDt(
        LocalDateTime.of(2019, 2, 1, 0, 0, 0),
        LocalDateTime.of(2019, 2, 28, 23, 59, 59));

    medicalTransacHsts.stream()
        .forEach(System.out::println);

    Assertions.assertThat(medicalTransacHsts.size()).isEqualTo(61);
  }

}
