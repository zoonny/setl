package com.kt.mmp.comn.base.split;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"stDt", "fnsDt"})
public class Split<Entity extends PeriodCacheEntity> implements Comparable<Split> {

  public enum Type {
    TEST___,
    BAS____,
    SPLIT__,
    ADD_INC,
    ADD_TAIL,
  }

  private int index;
  private Type type;
  private LocalDateTime stDt;
  private LocalDateTime fnsDt;
  private Entity entity;

  @Builder(toBuilder = true)
  public Split(int index, Type type, Entity entity, LocalDateTime stDt, LocalDateTime fnsDt) {
    this.index = index;
    this.type = type;
    this.entity = entity;
    this.stDt = stDt;
    this.fnsDt = fnsDt;
  }

  @Override
  public int compareTo(Split o) {
    return getEntity().compareTo(o.getEntity());
  }

  @Deprecated
  public Duration getDuration() {
    return Duration.between(stDt, fnsDt);
  }

}
