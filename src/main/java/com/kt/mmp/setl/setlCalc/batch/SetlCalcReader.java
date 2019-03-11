package com.kt.mmp.setl.setlCalc.batch;

import com.google.common.collect.Maps;
import com.kt.mmp.comn.base.batch.SetlItemReader;
import com.kt.mmp.comn.code.code.TrtResltCd;
import com.kt.mmp.comn.base.util.ComnUtils;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class SetlCalcReader extends SetlItemReader<MedicalTransacHst> {

  private static final String NAME = "setlCalcReader";
  private static final StringBuilder SQL = new StringBuilder()
      .append(" SELECT m ")
      .append(" FROM MedicalTransacHst m")
      .append(" WHERE m.occDt BETWEEN :stDt AND :fnsDt")
      .append(" AND trtResltCd = :trtResltCd")
      .append(" ORDER BY m.occDt");

  @Builder
  public SetlCalcReader(EntityManagerFactory entityManagerFactory, int pageSize, String setlTgtYm) {
    setName(NAME);
    setEntityManagerFactory(entityManagerFactory);
    setPageSize(pageSize);
    setQueryString(SQL.toString());
    Map<String, Object> queryParam = Maps.newHashMap();
    queryParam.put("stDt", ComnUtils.stDtOfMonth(setlTgtYm));
    queryParam.put("fnsDt", ComnUtils.fnsDtOfMonth(setlTgtYm));
    queryParam.put("trtResltCd", TrtResltCd.S.name());
    setParameterValues(queryParam);
  }

  @Override
  public void doInitialize(StepExecution stepExecution) {
    super.doInitialize(stepExecution);
    log.debug(">>>>> baseDt={}", ComnUtils.getJobParameter(stepExecution, "baseDt"));
  }

}
