package com.xcc.java;

import java.time.*;

public class TimeDemo {

    /**
     * 本地化日期时间API
     */
    public void testLocalDateTime() {
        // 获取当前的日期时间
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("当前时间： " + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1: " + date1);

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();
        System.out.println("月："+month+"，日："+day+"，秒："+seconds);

        LocalDateTime date2 = currentTime.withDayOfMonth(12).withYear(2022);
        System.out.println("date2: " + date2);

        // 4 APRIL 2024
        LocalDate date3 = LocalDate.of(2024, Month.APRIL, 1);
        System.out.println("date3: " + date3);

        // 12 小时 12 分钟
        LocalTime date4 = LocalTime.of(12,12);
        System.out.println("date4：" + date4);

        // 解析字符串
        LocalTime date5 = LocalTime.parse("20:15:22");
        System.out.println("date5：" + date5);
    }

    /**
     * 使用时区的日期时间API
     */
    public void testZoneDateTime() {
        //获取当前时间日期
        ZonedDateTime date1 = ZonedDateTime.parse("2023-05-25T10:15:30+05:30[Asia/Shanghai]");
        System.out.println("date1: " + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("当期时区: " + currentZone);
    }

    public static void main(String[] args) {
        TimeDemo timeDemo = new TimeDemo();
        timeDemo.testLocalDateTime();
        timeDemo.testZoneDateTime();
    }

}
