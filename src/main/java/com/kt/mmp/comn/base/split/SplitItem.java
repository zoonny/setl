package com.kt.mmp.comn.base.split;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Deprecated
@ToString(exclude = {"item"})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class SplitItem<Data> implements Comparable<SplitItem> {

  public enum Type {
    INCLUDE,
    EQUAL_BOTH,
    EQUAL_ST,
    EQUAL_FNS,
    TAIL,
    NEXT,
    SPLIT
  }

  protected Data data;

  protected PeriodCacheEntity<?> entity;

  public SplitItem(Data data, PeriodCacheEntity<?> entity) {
    this.data = data;
    this.entity = entity;
  }

  public abstract String getKey();

  public abstract void handleData(Type type, PeriodCacheEntity<?> entity);

  public abstract SplitItem<Data> newInstance();

  public LocalDateTime getStDt() {
    return entity.getEfctStDt();
  }

  public LocalDateTime getFnsDt() {
    return entity.getEfctFnsDt();
  }

  public Data getData() {
    return data;
  }

  @Override
  public int compareTo(SplitItem o) {
    return getFnsDt().compareTo(o.getFnsDt());
  }

}
