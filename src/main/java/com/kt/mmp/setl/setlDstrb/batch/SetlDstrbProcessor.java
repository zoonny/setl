package com.kt.mmp.setl.setlDstrb.batch;

import com.kt.mmp.comn.base.batch.SetlItemProcessor;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbDto;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbSumary;
import com.kt.mmp.setl.setlDstrb.service.SetlDstrbService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class SetlDstrbProcessor extends SetlItemProcessor<SetlItemMedatDtl, SetlDstrbSumary> {

  private final SetlDstrbService setlDstrbService;

  private final String setlTgtYm;

  private SetlDstrbDto setlDstrbDto;

  @Builder
  public SetlDstrbProcessor(SetlDstrbService setlDstrbService, String setlTgtYm) {
    this.setlDstrbService = setlDstrbService;
    this.setlTgtYm = setlTgtYm;
  }

  @Override
  public void doInitialize(StepExecution stepExecution) {
    setlDstrbDto = setlDstrbService.prepare(setlTgtYm);
  }

  @Override
  public SetlDstrbSumary process(SetlItemMedatDtl item) throws Exception {
    return setlDstrbService.process(setlDstrbDto, item);
  }

  @Override
  public void doFinalize(StepExecution stepExecution) {
    super.doFinalize(stepExecution);
    setlDstrbService.wrapup(setlDstrbDto);
  }

}
