package com.kt.mmp.setl.setlDstrb.dto;

import com.kt.mmp.comn.base.split.SplitSetMap;
import com.kt.mmp.setl.setlDstrb.domain.SetlItemPtnrDstrbInfo;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class SetlItemPtnrDstrbSplitSetMap extends SplitSetMap<SetlItemPtnrDstrbInfo> {

  @Builder
  public SetlItemPtnrDstrbSplitSetMap(LocalDateTime basStDt, LocalDateTime basFnsDt) {
    super(basStDt, basFnsDt);
  }

  @Override
  public String getKey(SetlItemPtnrDstrbInfo setlItemPtnrDstrbInfo) {
    return setlItemPtnrDstrbInfo.getPk().getSetlItemCd() + setlItemPtnrDstrbInfo.getPk().getPtnrId();
  }

}
