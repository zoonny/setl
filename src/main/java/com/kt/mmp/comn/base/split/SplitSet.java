package com.kt.mmp.comn.base.split;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
@ToString
@Getter
@EqualsAndHashCode
public class SplitSet<Entity extends PeriodCacheEntity> {

  private Split<Entity> basSplit;
  private List<Split<Entity>> splits = Lists.newArrayList();

  @Builder
  public SplitSet(Split<Entity> basSplit) {
    this.basSplit = basSplit;
  }

  public int add(Split<Entity> split) {
    Preconditions.checkArgument(split.getFnsDt().compareTo(split.getStDt()) > 0);

    if (ComnUtils.isGreaterThanEqual(split.getStDt(), basSplit.getFnsDt())) {
      return split.getIndex();
    }

    if (ComnUtils.isLessThan(split.getFnsDt(), basSplit.getStDt())) {
      return split.getIndex();
    }

    return SplitEngine.<Entity>builder()
        .basSplit(basSplit)
        .splits(splits)
        .split(split)
        .build()
        .split();
  }

  public Split<Entity> find(LocalDateTime basDt) {
    return splits.stream()
        .filter(split -> ComnUtils.isStDtGreaterThanEqualAndFnsDtLessThan(basDt, split))
        .findFirst().orElse(null);
  }

  public void sort() {
    splits.sort(Comparator.comparing(Split::getFnsDt));
  }

  @Deprecated
  public void sortDuration() {
    splits.sort(Comparator.comparing(Split::getDuration));
  }

  public boolean validate() {
    if (CollectionUtils.isNotEmpty(splits)) {
      LocalDateTime prevFnsDt = null;
      for (Split<Entity> iter : splits) {
        if (prevFnsDt != null && ComnUtils.isNotEqual(prevFnsDt, iter.getStDt())) {
          return false;
        }
        prevFnsDt = iter.getFnsDt();
      }
    }

    return true;
  }

  public void printSplits(String... message) {
    splits.stream()
        .forEach(entitySplit -> {
          log.info("{}", entitySplit);
        });
  }

}
