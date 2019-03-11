package com.kt.mmp.setl.setlCalc.dto;

import com.google.common.collect.Lists;
import com.kt.mmp.setl.setlCalc.domain.MedicalTransacHst;
import com.kt.mmp.setl.setlCalc.domain.SetlItemMedatDtl;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class SetlErrorItems {

  private List<Object> errorItems;

  @Builder
  public SetlErrorItems() {
    this.errorItems = Lists.newArrayList();
  }

  public void addErrorItem(Object errorItem) {
    errorItems.add(errorItem);
  }

  public int getErrorCount() {
    return errorItems.size();
  }

}
