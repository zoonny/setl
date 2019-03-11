package com.kt.mmp.setl.setlDstrb.batch;

import com.kt.mmp.comn.base.batch.SetlItemWriter;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemDstrbSumary;
import com.kt.mmp.setl.setlDstrb.dto.SetlDstrbSumary;
import com.kt.mmp.setl.setlDstrb.repository.SetlItemDstrbDtlRepository;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.batch.item.database.JpaItemWriter;

@Slf4j
public class SetlDstrbWriter extends SetlItemWriter<SetlDstrbSumary> {

  private SetlItemDstrbDtlRepository setlItemDstrbDtlRepository;

  @Builder
  public SetlDstrbWriter(EntityManagerFactory entityManagerFactory,
      SetlItemDstrbDtlRepository setlItemDstrbDtlRepository) {
    super(entityManagerFactory);
    this.setlItemDstrbDtlRepository = setlItemDstrbDtlRepository;
  }

  @Override
  public void write(List<? extends SetlDstrbSumary> items) {
    if (CollectionUtils.isNotEmpty(items)) {
      JpaItemWriter<SetlItemDstrbSumary> jpaItemWriter = new JpaItemWriter<>();
      jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
      items.stream()
          .forEach(setlDstrbSumary -> {
            ((SetlDstrbSumary) setlDstrbSumary).getPtnrSetlDstrbSumaryMap().entrySet().stream()
                .forEach(ptnrSetlDstrbSumaryEntry -> {
                  jpaItemWriter
                      .write(ptnrSetlDstrbSumaryEntry.getValue()
                          .getSetlItemDstrbSumaryMap().entrySet().stream()
                          .map(Entry::getValue)
                          .collect(Collectors.toList()));

                  setlItemDstrbDtlRepository.saveAll(ptnrSetlDstrbSumaryEntry.getValue()
                      .getSetlItemDstrbDtlMap().values().stream()
                      .collect(Collectors.toList()));
                });
          });
    }
  }

}

