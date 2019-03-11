package com.kt.mmp.setl.setlCalc.batch;

import com.kt.mmp.comn.base.batch.SetlItemProcessor;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcDto;
import com.kt.mmp.setl.setlCalc.service.SetlCalcService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class SetlCalcProcessor extends SetlItemProcessor<MedicalTransacHst, SetlCalcDto> {

  private final SetlCalcService setlCalcService;

  private final String setlTgtYm;

  private SetlCalcDto setlCalcDto;

  @Builder
  public SetlCalcProcessor(SetlCalcService setlCalcService, String setlTgtYm) {
    this.setlCalcService = setlCalcService;
    this.setlTgtYm = setlTgtYm;
  }

  @Override
  public void doInitialize(StepExecution stepExecution) {
    setlCalcDto = setlCalcService.prepare(setlTgtYm);
  }

  @Override
  public SetlCalcDto process(MedicalTransacHst item) throws Exception {
    return setlCalcService.process(setlCalcDto, item);
  }

  @Override
  public void doFinalize(StepExecution stepExecution) {
    super.doFinalize(stepExecution);
    setlCalcService.wrapup(setlCalcDto);
  }

}
