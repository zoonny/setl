package com.kt.mmp.setl.setlCalc.dto;

import com.kt.mmp.comn.base.split.SplitSetMap;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class SetlItemTarifSplitSetMap extends SplitSetMap<SetlItemTarifInfo> {

  @Builder
  public SetlItemTarifSplitSetMap(LocalDateTime basStDt, LocalDateTime basFnsDt) {
    super(basStDt, basFnsDt);
  }

  @Override
  public String getKey(SetlItemTarifInfo setlItemTarifInfo) {
    return setlItemTarifInfo.getPk().getSetlItemCd() + setlItemTarifInfo.getPk().getOdrg();
  }

}
