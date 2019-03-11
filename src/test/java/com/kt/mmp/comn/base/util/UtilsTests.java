package com.kt.mmp.comn.base.util;

import com.google.common.collect.Lists;
import com.kt.mmp.comn.TestConstants;
import com.kt.mmp.comn.base.split.Split;
import com.kt.mmp.comn.base.split.SplitSet;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsTests {

  private static final Logger log = LoggerFactory.getLogger(UtilsTests.class);

  @Test
  public void 날짜_변환() {
    // 일자 가져오기
    log.info("{}", Year.of(2019).atMonth(2).atDay(1).atTime(10, 30));

    // 기간 가져오기 (Period: 년,월,주,일, Duration: 시,분,초
    log.info("{}", Period.ofYears(2));  // 2년간(P2Y)
    log.info("{}", Period.ofDays(20));  // 20일간(P20D)

    log.info("{}", Duration.ofDays(2)); // 48시간(PT48H)
    log.info("{}", Duration.ofSeconds(30)); // 30초간(PT30S)

    // 일자 + 기간 = 날짜
    log.info("{}", LocalDateTime.of(2019, 02, 01, 00, 00, 00)
        .plus(Period.ofDays(1))); // 2019-02-02T00:00
    log.info("{}", LocalDateTime.now().plusHours(5));

    // 일자 - 일자 =  기간
    log.info("{}", Period.between(LocalDate.of(2016, 1, 26), LocalDate.of(2018, 3, 1)));  // P2Y1M3D
    log.info("{}",
        Period.between(LocalDate.of(2016, 1, 26), LocalDate.of(2018, 3, 1)).getDays());  //3일
    log.info("{}", ChronoUnit.DAYS.between(LocalDate.of(2016, 1, 26), LocalDate.of(2018, 3, 1)));

    log.info("{}", Duration.between(LocalTime.of(10, 20), LocalTime.of(18, 30))); // PT8H10M

    // 일자 변환

    // LocalDate -> String
    log.info("{}", LocalDate.of(2019, 2, 1).format(DateTimeFormatter.BASIC_ISO_DATE));
    log.info("{}",
        LocalDateTime.of(2019, 2, 1, 13, 29, 20).format(DateTimeFormatter.ISO_DATE_TIME));
    log.info("{}", LocalDateTime.of(2019, 2, 1, 13, 29, 20)
        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

    // String -> LocalDate
    log.info("{}", LocalDate.parse("2019-02-01"));
    log.info("{}", LocalDate.parse("20190201", DateTimeFormatter.BASIC_ISO_DATE));
    log.info("{}", LocalDateTime.parse("2019-02-01T10:15:12"));
    log.info("{}",
        LocalDateTime.parse("20190201101512", DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

    // 요일로 날짜 가져오기
    log.info("{}", LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY))); // 다음 토요일
    log.info("{}", LocalDate.of(2018, 5, 1)
        .with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.SUNDAY))); // 2018년 5월 세번째 일요일

    // 언어별 출력
    log.info("{}", DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.ENGLISH)); // Monday
    log.info("{}", DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.KOREAN)); // 월요일

    // 이번달
    LocalDate date = LocalDate.parse("201902" + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
    log.info("{}", date);
    LocalDateTime stDt = date.atTime(0, 0, 0);
    log.info("{}", stDt);
    LocalDateTime fnsDt = stDt.plusMonths(1).minusSeconds(1);
    log.info("{}", fnsDt);
  }

}
