package com.kt.mmp.comn;

import java.time.LocalDateTime;

public class TestConstants {

  public static final LocalDateTime[] MEDICAL_TRANSAC_HST_OCC_DT = {
      LocalDateTime.of(2019, 1, 1, 0, 0, 0),
      LocalDateTime.of(2019, 1, 2, 0, 0, 0),
      LocalDateTime.of(2019, 1, 3, 0, 0, 0),
      LocalDateTime.of(2019, 2, 1, 0, 0, 0),
      LocalDateTime.of(2019, 2, 2, 0, 0, 0),
      LocalDateTime.of(2019, 2, 3, 0, 0, 0),
      LocalDateTime.of(2019, 2, 11, 0, 0, 0),
      LocalDateTime.of(2019, 2, 12, 0, 0, 0),
      LocalDateTime.of(2019, 2, 13, 0, 0, 0),
      LocalDateTime.of(2019, 2, 21, 0, 0, 0),
      LocalDateTime.of(2019, 2, 22, 0, 0, 0),
      LocalDateTime.of(2019, 2, 23, 0, 0, 0),
      LocalDateTime.of(2019, 3, 1, 0, 0, 0),
      LocalDateTime.of(2019, 3, 2, 0, 0, 0),
      LocalDateTime.of(2019, 3, 3, 0, 0, 0),
  };

  public static final int[] MEDICAL_TRANSAC_HST_OCC_AMTS = {
      1000,
      2000,
      3000
  };

  public static final String SETL_ITEM_CD = "ITEM_";
  public static final String SETL_ITEM_NM = "정산항목_";

  public static final String RULE_CD = "RULE_";
  public static final String RULE_NM = "정산규칙_";
  public static final String RULE_PARAM = "CD1;CD2;CD3";

  public static final String PTNR_ID = "PTNR_";
  public static final String PTNR_NM = "파트너_";

  public static Object getValue(int index, Object[] arr) {
    return arr[index % arr.length];
  }

  public static String getEnum(int index, Object[] arr) {
    return ((Enum)arr[index % arr.length]).name();
  }

  public static final LocalDateTime[] SPLIT_SET_ST_DT = {
      LocalDateTime.of(2019, 1, 1, 0, 0, 0),
      LocalDateTime.of(2019, 1, 10, 12, 0, 0),
      LocalDateTime.of(2019, 1, 20, 0, 0, 0),
      LocalDateTime.of(2019, 2, 10, 12, 0, 0),
      LocalDateTime.of(2019, 2, 20, 0, 0, 0),
      LocalDateTime.of(2019, 3, 1, 12, 0, 0),
      LocalDateTime.of(2019, 3, 20, 0, 0, 0),
  };

  public static final LocalDateTime[] SPLIT_SET_FNS_DT = {
      LocalDateTime.of(2019, 1, 10, 12, 0, 0),
      LocalDateTime.of(2019, 1, 20, 0, 0, 0),
      LocalDateTime.of(2019, 2, 10, 12, 0, 0),
      LocalDateTime.of(2019, 2, 20, 0, 0, 0),
      LocalDateTime.of(2019, 3, 1, 0, 0, 0),
      LocalDateTime.of(2019, 3, 20, 12, 0, 0),
      LocalDateTime.of(9999, 12, 31, 0, 0, 0),
  };

  public static final LocalDateTime[] UNSORTED_SPLIT_SET_ST_DT = {
      LocalDateTime.of(2019, 3, 20, 0, 0, 0),
      LocalDateTime.of(2019, 1, 20, 0, 0, 0),
      LocalDateTime.of(2019, 2, 10, 12, 0, 0),
      LocalDateTime.of(2019, 2, 20, 0, 0, 0),
      LocalDateTime.of(2019, 3, 1, 12, 0, 0),
      LocalDateTime.of(2019, 1, 10, 12, 0, 0),
      LocalDateTime.of(2019, 1, 1, 0, 0, 0),
  };

  public static final LocalDateTime[] UNSORTED_SPLIT_SET_FNS_DT = {
      LocalDateTime.of(9999, 12, 31, 0, 0, 0),
      LocalDateTime.of(2019, 2, 10, 12, 0, 0),
      LocalDateTime.of(2019, 2, 20, 0, 0, 0),
      LocalDateTime.of(2019, 3, 1, 0, 0, 0),
      LocalDateTime.of(2019, 3, 20, 12, 0, 0),
      LocalDateTime.of(2019, 1, 20, 0, 0, 0),
      LocalDateTime.of(2019, 1, 10, 12, 0, 0),
  };

  public static final LocalDateTime PERIOD_ST_DT = LocalDateTime.of(2019, 2, 1, 0, 0, 0);
  public static final LocalDateTime PERIOD_FNS_DT = LocalDateTime.of(2019, 2, 28, 23, 59, 59);

}
