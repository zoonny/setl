package com.kt.mmp.setl.setlDstrb.batch;

import com.google.common.collect.Maps;
import com.kt.mmp.comn.base.batch.SetlItemReader;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.comn.code.code.TrtResltCd;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class SetlDstrbReader extends SetlItemReader<SetlItemMedatDtl> {

  private static final String NAME = "setlDstrbReader";
  private static final StringBuilder SQL = new StringBuilder()
      .append(" SELECT s ")
      .append(" FROM SetlItemMedatDtl s")
      .append(" WHERE s.pk.setlTgtYm = :setlTgtYm")
      .append(" ORDER BY s.pk.setlItemCd");

  @Builder
  public SetlDstrbReader(EntityManagerFactory entityManagerFactory, int pageSize, String setlTgtYm) {
    setName(NAME);
    setEntityManagerFactory(entityManagerFactory);
    setPageSize(pageSize);
    setQueryString(SQL.toString());
    Map<String, Object> queryParam = Maps.newHashMap();
    queryParam.put("setlTgtYm", setlTgtYm);
    setParameterValues(queryParam);
  }

  @Override
  public void doInitialize(StepExecution stepExecution) {
    super.doInitialize(stepExecution);
    log.debug(">>>>> baseDt={}", ComnUtils.getJobParameter(stepExecution, "baseDt"));
  }

}
