package com.kt.mmp.comn.base.mvc.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kt.mmp.comn.base.util.ComnUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class RuleParamSet {

  private static final String SPLITTER = ";";
  private static final String EQUAL = "=";
  private static final String COMMA = ",";

  private String ruleParam;
  private Set<RuleParam> ruleParamSet;

  @Builder
  public RuleParamSet(String ruleParam) {
    this.ruleParam = ruleParam;
    this.ruleParamSet = Sets.newHashSet();
    initialize();
  }

  private void initialize() {
    if (StringUtils.isEmpty(ruleParam)) {
      return;
    }

    String[] splits = ruleParam.split(SPLITTER);

    if (ArrayUtils.isEmpty(splits)) {
      return;
    }

    for (String iter : splits) {
      String[] values = iter.split(EQUAL);
      ruleParamSet.add(new RuleParam(values[0], Arrays.asList(values[1].split(COMMA))));
    }
  }

  public boolean check(Object object) {
    for (RuleParam param : ruleParamSet) {
      if (!include(object, param)) {
        return false;
      }
    }
    return true;
  }

  private boolean include(Object object, RuleParam param) {
    for (String paramValue : param.getValues()) {
      if (ObjectUtils.<String>compare(paramValue,
          (String) ComnUtils.getProperty(object, param.getName())) == 0) {
        return true;
      }
    }

    return false;
  }

  @Getter
  class RuleParam {
    private String name;
    private List<String> values;

    public RuleParam(String name, List<String> values) {
      this.name = name;
      this.values = values;
    }
  }

}
