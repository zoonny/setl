package com.kt.mmp.setl.setlCalc.dto;

import com.kt.mmp.comn.base.split.Split;
import com.kt.mmp.comn.base.split.Split.Type;
import com.kt.mmp.comn.base.split.SplitSetMap;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class SetlItemSplitSetMap extends SplitSetMap<SetlItemInfo> {

  @Builder
  public SetlItemSplitSetMap(LocalDateTime basStDt, LocalDateTime basFnsDt) {
    super(basStDt, basFnsDt);
  }

  @Override
  public String getKey(SetlItemInfo setlItemInfo) {
    return setlItemInfo.getPk().getSetlItemCd();
  }

}
