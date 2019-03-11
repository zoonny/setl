package com.kt.mmp.setl.setlCalc.dto;

import com.kt.mmp.comn.base.cache.domain.PeriodCacheEntity;
import com.kt.mmp.comn.base.split.SplitItem;
import com.kt.mmp.setl.setlCalc.domain.SetlItemInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemRuleInfo;
import com.kt.mmp.setl.setlCalc.domain.SetlItemTarifInfo;
import java.lang.reflect.InvocationTargetException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

@Deprecated
@Slf4j
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SetlSplitItem extends SplitItem<SetlSplitItemData> {

  @Builder
  public SetlSplitItem(SetlSplitItemData data,
      PeriodCacheEntity<?> entity) {
    super(data, entity);
  }

  @Deprecated
  @Override
  public SplitItem<SetlSplitItemData> newInstance() {
    SetlSplitItem setlSplitItem = SetlSplitItem.builder().build();
    try {
      PropertyUtils.copyProperties(setlSplitItem, this);
      return setlSplitItem;
    } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String getKey() {
    if (entity instanceof SetlItemInfo) {
      return entity.getPk().toString();
    } else if (entity instanceof SetlItemTarifInfo) {
      return entity.getPk().toString();
    } else if (entity instanceof SetlItemRuleInfo) {
      return entity.getPk().toString();
    } else {
      return null;
    }
  }

  @Override
  public void handleData(Type type, PeriodCacheEntity<?> entity) {
    if (data == null) {
      data = SetlSplitItemData.builder().build();
    }

    if (entity instanceof SetlItemInfo) {
      log.info(">>>>> {}:{}", type, entity);
      data.setSetlItemInfo((SetlItemInfo) entity);
    } else if (entity instanceof SetlItemTarifInfo) {
      log.info(">>>>> {}:{}", type, entity);
      data.getSetlItemTarifInfos().add((SetlItemTarifInfo) entity);
    } else if (entity instanceof SetlItemRuleInfo) {
      log.info(">>>>> {}:{}", type, entity);
      data.getSetlItemRuleInfos().add((SetlItemRuleInfo) entity);
    }
  }

}
