package com.kt.mmp.setl.setlCalc.dto;

import com.kt.mmp.comn.base.split.SplitSetMap;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfo;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class SetlItemRuleSplitSetMap extends SplitSetMap<SetlItemRuleInfo> {

  @Builder
  public SetlItemRuleSplitSetMap(LocalDateTime basStDt, LocalDateTime basFnsDt) {
    super(basStDt, basFnsDt);
  }

  @Override
  public String getKey(SetlItemRuleInfo setlItemRuleInfo) {
    return setlItemRuleInfo.getPk().getSetlItemCd();
  }

}
