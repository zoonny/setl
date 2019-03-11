package com.kt.mmp.comn.base.batch;

import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public abstract class SetlItemWriter<Item> implements ItemWriter<Item> {

  protected final EntityManagerFactory entityManagerFactory;

  public SetlItemWriter(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @BeforeStep
  private void intialize(StepExecution stepExecution) {
    doInitialize(stepExecution);
  }

  public void doInitialize(StepExecution stepExecution) {
    // TODO implement
  }

  @AfterStep
  private ExitStatus finalize(StepExecution stepExecution) {
    doFinalize(stepExecution);
    return stepExecution.getExitStatus();
  }

  public void doFinalize(StepExecution stepExecution) {
    // TODO implement
  }

}
