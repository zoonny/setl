package com.kt.mmp.comn.base.batch;

import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;

@Deprecated
@Slf4j
public abstract class SetlConfiguration {

  protected final JobBuilderFactory jobBuilderFactory;
  protected final StepBuilderFactory stepBuilderFactory;
  protected final EntityManagerFactory entityManagerFactory;

  protected SetlConfiguration(
      JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory,
      EntityManagerFactory entityManagerFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.entityManagerFactory = entityManagerFactory;
  }

  public abstract Step step();

  public abstract String jobName();

  @Bean
  public Job job() {
    return jobBuilderFactory.get(jobName())
        .start(step())
        .build();
  }

}
