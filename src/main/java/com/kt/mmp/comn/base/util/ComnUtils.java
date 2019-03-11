package com.kt.mmp.comn.base.util;

import com.kt.mmp.comn.base.exception.BaseException;
import com.kt.mmp.comn.base.exception.ErrorCode;
import com.kt.mmp.comn.base.mvc.domain.BaseEntity;
import com.kt.mmp.comn.base.split.Split;
import com.kt.mmp.comn.code.code.Yn;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class ComnUtils {

  public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
  public static final String DATE_FORMAT = "yyyyMMdd";
  public static final String TIME_FORMAT = "hhmmss";

  public static final String DEFLT_USER_ID = "admin";
  public static final String DEFLT_PGM_ID = "system";

  public static final int LEN_PGM_ID = 20;

  public static String dateTimeToString(LocalDateTime dateTime, String... format) {
    return dateTime
        .format(DateTimeFormatter.ofPattern(format.length > 0 ? format[0] : DATE_TIME_FORMAT));
  }

  public static String dateToString(LocalDate date, String... format) {
    return date.format(DateTimeFormatter.ofPattern(format.length > 0 ? format[0] : DATE_FORMAT));
  }

  public static String timeToString(LocalTime time, String... format) {
    return time.format(DateTimeFormatter.ofPattern(format.length > 0 ? format[0] : TIME_FORMAT));
  }

  public static void setCretCtrlField(Object object, BaseEntity entity) {
    entity.setCretrId(DEFLT_USER_ID);
    entity.setCretPgmId(getPgmId(object));
    entity.setCretDt(LocalDateTime.now());
  }

  public static void setAmdCtrlField(Object object, BaseEntity entity) {
    entity.setAmdrId(DEFLT_USER_ID);
    entity.setAmdPgmId(getPgmId(object));
    entity.setAmdDt(LocalDateTime.now());
  }

  private static String getPgmId(Object object) {
    String pgmId = Optional.ofNullable(object)
        .map(Object::getClass)
        .map(Class::getSimpleName)
        .orElse(DEFLT_PGM_ID);

    if (pgmId.length() > LEN_PGM_ID) {
      return pgmId.substring(0, LEN_PGM_ID - 1);
    }

    return pgmId;
  }

  public static <T> Optional<T> getAsOptional(List<T> list, int index) {
    try {
      return Optional.of(list.get(index));
    } catch (ArrayIndexOutOfBoundsException e) {
      return Optional.empty();
    }
  }

  public static LocalDateTime getFnsDt() {
    return LocalDateTime.of(9999, 12, 31, 0, 0, 0);
  }

  @Deprecated
  public static LocalDateTime stDtOfMonthOld(String ym) {
    LocalDate date = LocalDate.parse(ym + "01", DateTimeFormatter.ofPattern(DATE_FORMAT));
    return date.atTime(0, 0, 0);
  }

  public static LocalDateTime stDtOfMonth(String ym) {
    return LocalDateTime.parse(ym + "01000000", DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
  }

  public static LocalDateTime fnsDtOfMonth(String ym) {
    return stDtOfMonth(ym).plusMonths(1).minusSeconds(1);
  }

  public static String getJobParameter(StepExecution stepExecution, String key) {
    return stepExecution.getJobParameters().getString(key);
  }

  public static boolean isBetween(LocalDateTime basDt, Split split) {
    if (basDt.compareTo(split.getStDt()) >= 0 && basDt.compareTo(split.getFnsDt()) <= 0) {
      return true;
    }

    return false;
  }

  public static boolean isInclude(Split basSplit, Split diffSplit) {
    if (isStDtGreaterThanEqualAndFnsDtLessThan(basSplit.getStDt(), diffSplit)
        && isStDtGreaterThanAndFnsDtLessThanEqual(basSplit.getFnsDt(), diffSplit)) {
      return true;
    }

    return false;
  }

  public static boolean isNotBetween(LocalDateTime basDt, Split split) {
    return !isBetween(basDt, split);
  }

  public static boolean isStDtGreaterThanEqualAndFnsDtLessThan(LocalDateTime basDt,
      LocalDateTime stDt, LocalDateTime fnsDt) {
    if (basDt.compareTo(stDt) >= 0 && basDt.compareTo(fnsDt) < 0) {
      return true;
    }

    return false;
  }

  public static boolean isStDtGreaterThanEqualAndFnsDtLessThan(LocalDateTime basDt, Split split) {
    return isStDtGreaterThanEqualAndFnsDtLessThan(basDt, split.getStDt(), split.getFnsDt());
  }

  public static boolean isStDtGreaterThanAndFnsDtLessThan(LocalDateTime basDt, LocalDateTime stDt,
      LocalDateTime fnsDt) {
    if (basDt.compareTo(stDt) > 0 && basDt.compareTo(fnsDt) < 0) {
      return true;
    }

    return false;
  }

  public static boolean isStDtGreaterThanAndFnsDtLessThan(LocalDateTime basDt, Split split) {
    return isStDtGreaterThanAndFnsDtLessThan(basDt, split.getStDt(), split.getFnsDt());
  }

  public static boolean isStDtGreaterThanAndFnsDtLessThanEqual(LocalDateTime basDt,
      LocalDateTime stDt, LocalDateTime fnsDt) {
    if (basDt.compareTo(stDt) > 0 && basDt.compareTo(fnsDt) <= 0) {
      return true;
    }

    return false;
  }

  public static boolean isStDtGreaterThanAndFnsDtLessThanEqual(LocalDateTime basDt, Split split) {
    return isStDtGreaterThanAndFnsDtLessThanEqual(basDt, split.getStDt(), split.getFnsDt());
  }

  public static boolean isEqual(LocalDateTime basDt, LocalDateTime diffDt) {
    if (basDt.compareTo(diffDt) == 0) {
      return true;
    }

    return false;
  }

  public static boolean isNotEqual(LocalDateTime basDt, LocalDateTime diffDt) {
    return !isEqual(basDt, diffDt);
  }

  public static boolean isGreaterThan(LocalDateTime basDt, LocalDateTime diffDt) {
    if (basDt.compareTo(diffDt) > 0) {
      return true;
    }

    return false;
  }

  public static boolean isGreaterThanEqual(LocalDateTime basDt, LocalDateTime diffDt) {
    if (basDt.compareTo(diffDt) >= 0) {
      return true;
    }

    return false;
  }

  public static boolean isLessThan(LocalDateTime basDt, LocalDateTime diffDt) {
    if (basDt.compareTo(diffDt) < 0) {
      return true;
    }

    return false;
  }

  public static boolean isLessThanEqual(LocalDateTime basDt, LocalDateTime diffDt) {
    if (basDt.compareTo(diffDt) <= 0) {
      return true;
    }

    return false;
  }

  public static Object getProperty(Object bean, String propertyName) {
    try {
      return BeanUtils.getProperty(bean, propertyName);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new BaseException(ErrorCode.PROPERTY_GET, propertyName);
    }
  }

  public static boolean isYes(String yn) {
    return !isNo(yn);
  }

  public static boolean isNo(String yn) {
    if (Yn.N.name().equals(yn)) {
      return true;
    }

    return false;
  }

}
