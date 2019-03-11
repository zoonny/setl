package com.kt.mmp.setl.setlCalc.batch;

import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcDto;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcSumary;
import com.kt.mmp.setl.setlCalc.repository.SetlItemMedatDtlRepository;
import com.kt.mmp.setl.setlCalc.service.SetlCalcService;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumary;
import com.kt.mmp.setl.setlDstrb.repository.SetlItemDstrbDtlRepository;
import com.kt.mmp.setl.setlDstrb.repository.SetlItemDstrbSumaryRepository;
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
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SetlCalcJobConfiguration {

  private static final String JOB_NAME = "setlCalcJob";
  private static final String STEP_NAME = "setlCalcStep";
  private static final int CHUNK_SIZE = 10;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final SetlCalcService setlCalcService;
  private final SetlItemMedatDtlRepository setlItemMedatDtlRepository;
  private final SetlItemDstrbSumaryRepository setlItemDstrbSumaryRepository;
  private final SetlItemDstrbDtlRepository setlItemDstrbDtlRepository;

  @Bean
  public Job setlCalcJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(setlCalcStep())
        .build();
  }

  @Bean
  @JobScope
  public Step setlCalcStep() {
    return stepBuilderFactory.get(STEP_NAME)
        .<MedicalTransacHst, SetlCalcDto>chunk(CHUNK_SIZE)
        .reader(setlCalcReader(null))
        .processor(setlCalcProcessor(null, null))
        .writer(setlCalcWriter())
        .build();
  }

  @Bean
  @StepScope
  public SetlCalcReader setlCalcReader(@Value("#{jobParameters[setlTgtYm]}") String setlTgtYm) {
    return SetlCalcReader.builder()
        .entityManagerFactory(entityManagerFactory)
        .pageSize(CHUNK_SIZE)
        .setlTgtYm(setlTgtYm)
        .build();
  }

  @Bean
  @StepScope
  public SetlCalcProcessor setlCalcProcessor(@Value("#{jobParameters[setlTgtYm]}") String setlTgtYm,
      @Value("#{jobParameters[baseDt]}") String baseDt) {
    return SetlCalcProcessor.builder()
        .setlCalcService(setlCalcService)
        .setlTgtYm(setlTgtYm)
        .build();
  }

  @Bean
  @StepScope
  public SetlCalcWriter setlCalcWriter() {
    return SetlCalcWriter.builder()
        .entityManagerFactory(entityManagerFactory)
        .setlItemMedatDtlRepository(setlItemMedatDtlRepository)
        .setlItemDstrbSumaryRepository(setlItemDstrbSumaryRepository)
        .setlItemDstrbDtlRepository(setlItemDstrbDtlRepository)
        .build();
  }

}
