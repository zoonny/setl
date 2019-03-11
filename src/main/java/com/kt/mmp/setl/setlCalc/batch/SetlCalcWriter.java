package com.kt.mmp.setl.setlCalc.batch;

import com.kt.mmp.comn.base.batch.SetlItemWriter;
import com.kt.mmp.setl.setlCalc.domain.SetlItemSumary;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcDto;
import com.kt.mmp.setl.setlCalc.dto.SetlCalcSumary;
import com.kt.mmp.setl.setlCalc.repository.SetlItemMedatDtlRepository;
import com.kt.mmp.setl.setlDstrb.repository.SetlItemDstrbDtlRepository;
import com.kt.mmp.setl.setlDstrb.repository.SetlItemDstrbSumaryRepository;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.batch.item.database.JpaItemWriter;

@Slf4j
public class SetlCalcWriter extends SetlItemWriter<SetlCalcDto> {

  private SetlItemMedatDtlRepository setlItemMedatDtlRepository;
  private SetlItemDstrbSumaryRepository setlItemDstrbSumaryRepository;
  private SetlItemDstrbDtlRepository setlItemDstrbDtlRepository;

  @Builder
  public SetlCalcWriter(EntityManagerFactory entityManagerFactory,
      SetlItemMedatDtlRepository setlItemMedatDtlRepository,
      SetlItemDstrbSumaryRepository setlItemDstrbSumaryRepository,
      SetlItemDstrbDtlRepository setlItemDstrbDtlRepository) {
    super(entityManagerFactory);
    this.setlItemMedatDtlRepository = setlItemMedatDtlRepository;
    this.setlItemDstrbSumaryRepository = setlItemDstrbSumaryRepository;
    this.setlItemDstrbDtlRepository = setlItemDstrbDtlRepository;
  }

  @Override
  public void write(List<? extends SetlCalcDto> items) {
    if (CollectionUtils.isNotEmpty(items)) {
      JpaItemWriter<SetlItemSumary> jpaItemWriter = new JpaItemWriter<>();
      jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
      items.stream()
          .forEach(e -> {
            SetlCalcDto setlCalcDto = (SetlCalcDto) e;

            setlCalcDto.getSetlCalcSumary().getPtnrSetlCalcSumaryMap().entrySet()
                .stream()
                .forEach(ptnrSetlCalcSumaryEntry -> {
                  jpaItemWriter
                      .write(ptnrSetlCalcSumaryEntry.getValue()
                          .getSetlItemSumaryMap().entrySet().stream()
                          .map(Entry::getValue)
                          .collect(Collectors.toList()));

                  setlItemMedatDtlRepository.saveAll(ptnrSetlCalcSumaryEntry.getValue()
                      .getSetlItemMedatDtlMap().values().stream()
                      .collect(Collectors.toList()));
                });

            setlCalcDto.getSetlDstrbSumary().getPtnrSetlDstrbSumaryMap().entrySet()
                .stream()
                .forEach(ptnrSetlDstrbSumaryEntry -> {
                  setlItemDstrbSumaryRepository.saveAll(ptnrSetlDstrbSumaryEntry.getValue()
                      .getSetlItemDstrbSumaryMap().values().stream()
                      .collect(Collectors.toList()));

                  setlItemDstrbDtlRepository.saveAll(ptnrSetlDstrbSumaryEntry.getValue()
                      .getSetlItemDstrbDtlMap().values().stream()
                      .collect(Collectors.toList()));
                });
          });
    }
  }

}

