package com.kt.mmp.comn.base.split;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.split.Split.Type;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@EqualsAndHashCode
public abstract class SplitSetMap<Entity extends PeriodCacheEntity> {

  protected Map<String, SplitSet<Entity>> splitSetMap;

  private LocalDateTime basStDt;

  private LocalDateTime basFnsDt;

  protected SplitSetMap(LocalDateTime basStDt, LocalDateTime basFnsDt) {
    this.basStDt = basStDt;
    this.basFnsDt = basFnsDt;
    this.splitSetMap = Maps.newHashMap();
  }

  public abstract String getKey(Entity entity);

  public void initSplit(Map<String, List<Entity>> entityMap) {
    entityMap.entrySet().stream()
        .forEach(i -> addSplits(i.getValue()));
  }

  public void addSplits(List<Entity> entities) {
    entities.stream()
        .forEach(i -> addSplit(i));
  }

  public void addSplit(Entity entity) {
    SplitSet<Entity> splitSet = splitSetMap.get(getKey(entity));

    if (splitSet == null) {
      splitSet = SplitSet.<Entity>builder()
          .basSplit(Split.<Entity>builder()
              .index(0)
              .type(Type.BAS____)
              .entity(null)
              .stDt(basStDt)
              .fnsDt(basFnsDt)
              .build())
          .build();
      splitSetMap.put(getKey(entity), splitSet);
    }

    splitSet.add(Split.<Entity>builder()
        .index(0)
        .type(Type.SPLIT__)
        .entity(entity)
        .stDt(entity.getEfctStDt())
        .fnsDt(entity.getEfctFnsDt())
        .build());
  }

  public Split<Entity> findEfctEntityByKey(String key, LocalDateTime basDt) {
    return splitSetMap.get(key).find(basDt);
  }

  public List<Split<Entity>> findEfctEntitiesByKey(String key, LocalDateTime basDt) {
    List<Split<Entity>> splits = Lists.newArrayList();

    splitSetMap.entrySet().stream()
        .filter(splitSet -> splitSet.getKey().startsWith(key))
        .collect(Collectors.toList()).forEach(splitSet -> {
          splitSet.getValue().getSplits().forEach(split -> {
            if (ComnUtils.isStDtGreaterThanEqualAndFnsDtLessThan(basDt, split)) {
              splits.add(split);
            }
          });
        });

    splits.stream().sorted(Split::compareTo).forEach(System.out::println);

    return splits;
  }

  public void sort() {
    splitSetMap.entrySet().stream()
        .forEach(i -> i.getValue().sort());
  }

  public List<SplitSet<Entity>> getSplitSets() {
    return splitSetMap.entrySet().stream()
        .map(Entry::getValue)
        .collect(Collectors.toList());
  }

  public void printSplits() {
    splitSetMap.entrySet().stream()
        .forEach(i -> {
          log.info(">>>>> " + i.getKey() + " ->");
          i.getValue().printSplits();
        });
  }

}
