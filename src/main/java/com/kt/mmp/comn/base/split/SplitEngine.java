package com.kt.mmp.comn.base.split;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.split.Split.Type;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

public class SplitEngine<Entity extends PeriodCacheEntity> {

  private Split<Entity> basSplit;
  private List<Split<Entity>> splits;
  private Split<Entity> split;

  @Builder
  public SplitEngine(Split basSplit, List<Split<Entity>> splits, Split<Entity> split) {
    this.basSplit = basSplit;
    this.splits = splits;
    this.split = split;
  }

  private void limitPeriod(Split<Entity> split) {
    if (ComnUtils.isLessThan(split.getStDt(), basSplit.getStDt())) {
      split.setStDt(basSplit.getStDt());
    }

    if (ComnUtils.isGreaterThan(split.getFnsDt(), basSplit.getFnsDt())) {
      split.setFnsDt(basSplit.getFnsDt());
    }
  }

  public int split() {
    limitPeriod(split);

    final SplitIndex index = new SplitIndex(split.getIndex());
    final List<Split<Entity>> addSplits = new ArrayList<>();
    final Split addSplit = split.toBuilder().build();
    final SplitRfrn splitRfrn = new SplitRfrn();

    if (CollectionUtils.isNotEmpty(splits)) {

      for (Split<Entity> iter : splits) {

        if (ComnUtils.isEqual(split.getStDt(), iter.getStDt())
            && ComnUtils.isEqual(split.getFnsDt(), iter.getFnsDt())) {
          iter.setEntity(split.getEntity());
          splitRfrn.setAddSplit(false);
          break;
        }

        // TODO re-test
        if (ComnUtils.isInclude(split, iter)) {
          if (ComnUtils.isEqual(split.getStDt(), iter.getStDt())) {
            iter.setStDt(split.getFnsDt());
          } else if (ComnUtils.isEqual(split.getFnsDt(), iter.getFnsDt())) {
            iter.setFnsDt(split.getStDt());
          } else {
            addSplits.add(Split.<Entity>builder()
                .index(index.incrementAndGet())
                .type(Type.ADD_INC)
                .entity(split.getEntity())
                .stDt(split.getFnsDt())
                .fnsDt(iter.getFnsDt())
                .build());
            iter.setFnsDt(split.getStDt());
          }
          break;
        }

        if (ComnUtils.isStDtGreaterThanEqualAndFnsDtLessThan(split.getStDt(), iter)) {

          splitRfrn.setNextFnsDt(iter.getFnsDt());
          splitRfrn.setSettedNext(true);
          splitRfrn.setSettedNextFirst(true);

          if (ComnUtils.isEqual(split.getStDt(), iter.getStDt())) {
            iter.setEntity(split.getEntity());
            splitRfrn.setAddSplit(false);
          } else {
            iter.setFnsDt(split.getStDt());
          }

        }

        if (ComnUtils.isStDtGreaterThanAndFnsDtLessThanEqual(split.getFnsDt(), iter)) {

          if (ComnUtils.isEqual(split.getFnsDt(), iter.getFnsDt())) {
            iter.setEntity(split.getEntity());
          } else {
            addSplits.add(Split.<Entity>builder()
                .index(index.incrementAndGet())
                .type(Type.ADD_TAIL)
                .entity(iter.getEntity())
                .stDt(iter.getStDt())
                .fnsDt(split.getFnsDt())
                .build());
            iter.setStDt(split.getFnsDt());
          }

          splitRfrn.setSplitComplete(true);

        }

        if (splitRfrn.isSplitComplete()) {
          break;
        }

        if (splitRfrn.isSettedNext()) {
          addSplit.setFnsDt(splitRfrn.getNextFnsDt());

          if(!splitRfrn.isSettedNextFirst()) {
            iter.setEntity(split.getEntity());
          } else {
            splitRfrn.setSettedNextFirst(false);
          }
        }

      }
    }

    if (splitRfrn.isAddSplit()) {
      splits.add(addSplit);
    }

    if (CollectionUtils.isNotEmpty(addSplits)) {
      splits.addAll(addSplits);
    }

    return index.get();
  }

  @ToString
  @EqualsAndHashCode
  private class SplitIndex {
    private int index;

    public SplitIndex(int index) {
      this.index = index;
    }
    public int get() {
      return index;
    }
    public int incrementAndGet() {
      return ++index;
    }
  }

  @ToString
  @Getter
  @Setter
  @EqualsAndHashCode
  private class SplitRfrn {
    private boolean addSplit;
    private LocalDateTime nextFnsDt;
    private boolean settedNext;
    private boolean settedNextFirst;
    private boolean splitComplete;

    public SplitRfrn() {
      this.addSplit = true;
      this.nextFnsDt = null;
      this.settedNext = false;
      this.settedNextFirst = false;
      this.splitComplete = false;
    }
  }
}
