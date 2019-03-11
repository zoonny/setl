package com.kt.mmp.setl.setlDstrb.dto;

import com.kt.mmp.setl.setlCalc.dto.SetlCalcParam;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Deprecated
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SetlDstrbParam extends SetlCalcParam {

  public SetlDstrbParam(String setlTgtYm, LocalDateTime wrkDt, int stepNo) {
    super(setlTgtYm, wrkDt, stepNo);
  }

}
