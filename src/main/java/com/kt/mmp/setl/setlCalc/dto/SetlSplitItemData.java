package com.kt.mmp.setl.setlCalc.dto;

import com.google.common.collect.Lists;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SetlSplitItemData {

  private SetlItemInfo setlItemInfo;

  private List<SetlItemTarifInfo> setlItemTarifInfos;

  private List<SetlItemRuleInfo> setlItemRuleInfos;

  @Builder
  public SetlSplitItemData(SetlItemInfo setlItemInfo) {
    this.setlItemInfo = setlItemInfo;
    this.setlItemTarifInfos = Lists.newArrayList();
    this.setlItemRuleInfos = Lists.newArrayList();
  }

}
