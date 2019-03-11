package com.kt.mmp.comn.base.split;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.split.Split.Type;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfoPk;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfoPk;
import com.kt.mmp.setl.setlCalc.dto.SetlSplitItem;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitTests {

  private static final Logger log = LoggerFactory.getLogger(SplitTests.class);

  @Test
  public void 날짜_스플릿_셋() {
    // condition
    List<Split> splits = Lists.newArrayList();

    final AtomicInteger index = new AtomicInteger(0);

    LocalDateTime basStDt = LocalDateTime.of(2019, 2, 1, 0, 0, 0);
    LocalDateTime basFnsDt = LocalDateTime.of(2019, 2, 28, 23, 59, 59);

    // when
    SplitSet splitSet = SplitSet.builder()
        .basSplit(Split.builder()
            .index(0)
            .type(Type.BAS____)
            .entity(null)
            .stDt(basStDt)
            .fnsDt(basFnsDt)
            .build())
        .build();

    splits.stream()
        .forEach(i -> splitSet.add(i));

    printSplits(splitSet.getSplits(), "범위 지정 데이터");

    int _index = index.incrementAndGet();

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 1, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 10, 0, 0, 0)
        , "SplitCase 1) 0201 - 0210");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 10, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 20, 0, 0, 0)
        , "SplitCase 2) 0210 - 0220");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 20, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 28, 23, 59, 59)
        , "SplitCase 3) 0220 - 0228");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 5, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 15, 0, 0, 0)
        , "SplitCase 4) 0205 - 0215");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 12, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 25, 0, 0, 0)
        , "SplitCase 5) 0212 - 0225");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 3, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 23, 0, 0, 0)
        , "SplitCase 6) 0203 - 0223");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 1, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 2, 0, 0, 0)
        , "SplitCase 7) 0201 - 0202");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 20, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 21, 0, 0, 0)
        , "SplitCase 8) 0220 - 0221");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 4, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 28, 23, 59, 59)
        , "SplitCase 9) 0204 - 0228");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 18, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 20, 0, 0, 0)
        , "SplitCase 10) 0218 - 0220");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 1, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 28, 23, 59, 59)
        , "SplitCase 11) 0201 - 0228");

    // then
    Assertions.assertThat(splitSet.validate()).isEqualTo(true);
  }

  private int addSplit(SplitSet splitSet, int index, LocalDateTime stDt, LocalDateTime fnsDt,
      String message) {
    int _index = splitSet.add(Split.builder()
        .index(++index)
        .type(Type.SPLIT__)
        .entity(SetlItemInfo.builder()
            .pk(SetlItemInfoPk.builder()
                .setlItemCd("ITEM" + index)
                .efctFnsDt(fnsDt)
                .build())
            .efctStDt(stDt)
            .setlItemNm("NAME" + index)
            .build())
        .stDt(stDt)
        .fnsDt(fnsDt)
        .build());

    splitSet.sort();

    printSplits(splitSet.getSplits(), message);

    return _index;
  }

  private void printSplits(List<Split> splits, String message) {
    splits.stream()
        .forEach(System.out::println);
    System.out.println("===== " + message);
  }

  @Test
  public void 날짜_스플릿_셋_멀티타입() {
    // condition
    List<Split> splits = Lists.newArrayList();

    final AtomicInteger index = new AtomicInteger(0);

    LocalDateTime basStDt = LocalDateTime.of(2019, 2, 1, 0, 0, 0);
    LocalDateTime basFnsDt = LocalDateTime.of(2019, 2, 28, 23, 59, 59);

    // when
    SplitSet splitSet = SplitSet.builder()
        .basSplit(Split.builder()
            .index(0)
            .type(Type.BAS____)
            .entity(null)
            .stDt(basStDt)
            .fnsDt(basFnsDt)
            .build())
        .build();

    splits.stream()
        .forEach(i -> splitSet.add(i));

    printSplits(splitSet.getSplits(), "범위 지정 데이터");

    int _index = index.incrementAndGet();

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 1, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 10, 0, 0, 0)
        // 2/15일인 경우 2/15 ~ 2/20 스플릿이 생기지 않음
//        ,LocalDateTime.of(2019, 2, 15, 0, 0, 0)
        , "SplitCase 1) 0201 - 0210");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 10, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 20, 0, 0, 0)
        , "SplitCase 2) 0210 - 0220");

    _index = addSplit(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 20, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 28, 23, 59, 59)
        , "SplitCase 3) 0220 - 0228");

    _index = addSplitOther(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 1, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 15, 0, 0, 0)
        , "SplitCase 4) Another Type 0205 - 0215");

    _index = addSplitOther(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 15, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 28, 23, 59, 59)
        , "SplitCase 5) Another Type 0215 - 0228");

    _index = addSplitOther(splitSet
        , _index
        , LocalDateTime.of(2019, 2, 25, 0, 0, 0)
        , LocalDateTime.of(2019, 2, 28, 23, 59, 59)
        , "SplitCase 6) Another Type 0225 - 0228");

    // then
    Assertions.assertThat(splitSet.validate()).isEqualTo(true);
  }

  private int addSplitOther(SplitSet splitSet, int index, LocalDateTime stDt, LocalDateTime fnsDt,
      String message) {
    int _index = splitSet.add(Split.builder()
        .index(++index)
        .type(Type.SPLIT__)
        .entity(SetlItemTarifInfo.builder()
            .pk(SetlItemTarifInfoPk.builder()
                .setlItemCd("ITEM" + index)
                .odrg(index)
                .efctFnsDt(fnsDt)
                .build())
            .efctStDt(stDt)
            .calcTypeCd("TYPE" + index)
            .tarifVal(133)
            .build())
        .stDt(stDt)
        .fnsDt(fnsDt)
        .build());

    splitSet.sort();

    printSplits(splitSet.getSplits(), message);

    return _index;
  }

  @Test
  public void 스플릿_셋_비교() {
    Split split1 = Split.builder()
        .stDt(TestConstants.SPLIT_SET_ST_DT[0])
        .fnsDt(TestConstants.SPLIT_SET_FNS_DT[0])
        .build();

    Split split2 = Split.builder()
        .stDt(TestConstants.SPLIT_SET_ST_DT[0])
        .fnsDt(TestConstants.SPLIT_SET_FNS_DT[0])
        .build();

    System.out
        .println(split1 + "/" + split1.hashCode() + " == " + split2 + "/" + split2.hashCode());

    Assertions.assertThat(split1).isEqualTo(split2);
  }

  @Test
  public void 정렬_후_병렬_처리() {
    Arrays.asList(TestConstants.SPLIT_SET_ST_DT).stream()
        .sorted(Comparator.naturalOrder())  // = (a,b) -> a.compareTo(b)
        .parallel()
        .forEachOrdered(s -> System.out.println(s));
  }

}
