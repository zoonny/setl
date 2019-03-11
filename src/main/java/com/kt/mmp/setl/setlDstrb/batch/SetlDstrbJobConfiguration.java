package com.kt.mmp.setl.setlDstrb.batch;

import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbSumary;
import com.kt.mmp.setl.setlDstrb.repository.SetlItemDstrbDtlRepository;
import com.kt.mmp.setl.setlDstrb.service.SetlDstrbService;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

@Deprecated
@Slf4j
@RequiredArgsConstructor
//@Configuration
public class SetlDstrbJobConfiguration {

  private static final String JOB_NAME = "setlDstrbJob";
  private static final String STEP_NAME = "setlDstrbStep";
  private static final int CHUNK_SIZE = 10;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final SetlDstrbService setlDstrbService;
  private final SetlItemDstrbDtlRepository setlItemDstrbDtlRepository;

  @Bean
  public Job setlDstrbJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(setlDstrbStep())
        .build();
  }

  @Bean
  @JobScope
  public Step setlDstrbStep() {
    return stepBuilderFactory.get(STEP_NAME)
        .<SetlItemMedatDtl, SetlDstrbSumary>chunk(CHUNK_SIZE)
        .reader(setlDstrbReader(null))
        .processor(setlDstrbProcessor(null, null))
        .writer(setlDstrbWriter())
        .build();
  }

  @Bean
  @StepScope
  public SetlDstrbReader setlDstrbReader(@Value("#{jobParameters[setlTgtYm]}") String setlTgtYm) {
    return SetlDstrbReader.builder()
        .entityManagerFactory(entityManagerFactory)
        .pageSize(CHUNK_SIZE)
        .setlTgtYm(setlTgtYm)
        .build();
  }

  @Bean
  @StepScope
  public SetlDstrbProcessor setlDstrbProcessor(@Value("#{jobParameters[setlTgtYm]}") String setlTgtYm,
      @Value("#{jobParameters[baseDt]}") String baseDt) {
    return SetlDstrbProcessor.builder()
        .setlDstrbService(setlDstrbService)
        .setlTgtYm(setlTgtYm)
        .build();
  }

  @Bean
  @StepScope
  public SetlDstrbWriter setlDstrbWriter() {
    return SetlDstrbWriter.builder()
        .entityManagerFactory(entityManagerFactory)
        .setlItemDstrbDtlRepository(setlItemDstrbDtlRepository)
        .build();
  }

}
