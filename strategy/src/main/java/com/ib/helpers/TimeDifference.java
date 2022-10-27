package com.ib.helpers;

import lombok.Data;
import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class TimeDifference {

    public int daysLapsed;
    public int DaysElapsed;
    public int LAPSE_days;

    public int hoursLapsed;
    public int lapseHours;
    public int hoursElapsed;

    public int lapseMinutes;
    public int minutesLapsed;
    public int minutesLapsedVALUE;
    public int minutesElapsed;

    public String answer;
    final public Double lengthOfMinutes = 35.0;

    public String getDifference(String dateStartLAST, String dateFinishNOW) throws NullPointerException {


        System.out.println("dateStartLAST  ==>>   " + dateStartLAST);
        System.out.println("dateFinishNOW  ==>>   " + dateFinishNOW);


        String dateStart = dateStartLAST;
        String dateStop = dateFinishNOW;

        SimpleDateFormat format_LTrade = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        SimpleDateFormat format_NOW = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

        Date d1;
        Date d2;


        try {
            d1 = format_LTrade.parse(dateStart);
            d2 = format_NOW.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);


            System.out.println(Days.daysBetween(dt1, dt2).getDays() + " days, ");
            daysLapsed = Days.daysBetween(dt1, dt2).getDays();
            System.out.println(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            hoursLapsed = Hours.hoursBetween(dt1, dt2).getHours() % 12;
            System.out.println(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
            minutesLapsedVALUE = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
            System.out.println(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

            DaysElapsed = Math.abs(daysLapsed);
            hoursElapsed = Math.abs(hoursLapsed);
            minutesElapsed = Math.abs(minutesLapsedVALUE);


            System.out.println("============Double_,_,_MinutesLapsed==============>>  " + minutesElapsed + "     || Criteria against Length of Minutes=________" + lengthOfMinutes);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.LAPSE_days = DaysElapsed;
        this.lapseHours = hoursElapsed;
        this.lapseMinutes = minutesElapsed;


        if (lapseMinutes >= lengthOfMinutes) {
            answer = "YES";
        }
        if (lapseMinutes < lengthOfMinutes) {
            answer = "NO";
        }
        if (lapseHours >= 1) {
            answer = "YES";
        }
        if (LAPSE_days >= 1) {
            answer = "YES";
        }


        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++ Answer from TimeDifference class:  --> " + answer);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        return answer;
    }


    public String findDifferenceMethod02(String dateStartLAST) throws NullPointerException {

        String dateStart = dateStartLAST; //  "01/08/2018 09:29:58";
        Date nowTimeDiff = new Date(); //Date and time at this moment
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTimeDiff);
        cal.add(Calendar.HOUR, +1);
        Date TimeNow = cal.getTime();
        System.out.println("@@@@@ TimeNow @@@@@====>>   " + TimeNow);
        SimpleDateFormat formTBs = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dateStop = formTBs.format(cal.getTime());


        System.out.println("dateStartLAST  ==>>   " + dateStartLAST);
        System.out.println("dateFinishNOW  ==>>   " + dateStop);


        SimpleDateFormat format_LTrade = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        SimpleDateFormat format_NOW = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format_LTrade.parse(dateStart);
            d2 = format_NOW.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);


            System.out.println(Days.daysBetween(dt1, dt2).getDays() + " days, ");
            daysLapsed = Days.daysBetween(dt1, dt2).getDays();
            System.out.println(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            hoursLapsed = (Hours.hoursBetween(dt1, dt2).getHours() % 12);
            System.out.println(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
            minutesLapsedVALUE = (Minutes.minutesBetween(dt1, dt2).getMinutes() % 60);
            System.out.println(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

            DaysElapsed = Math.abs(daysLapsed);
            hoursElapsed = Math.abs(hoursLapsed);
            minutesElapsed = Math.abs(minutesLapsedVALUE);


            System.out.println("============Double_,_,_MinutesLapsed==============>>  " + minutesElapsed + "     || Criteria against Length of Minutes=________" + lengthOfMinutes);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.LAPSE_days = DaysElapsed;
        this.lapseHours = hoursElapsed;
        this.lapseMinutes = minutesElapsed;


        if (lapseMinutes >= lengthOfMinutes) {
            answer = "YES";
        }
        if (lapseMinutes < lengthOfMinutes) {
            answer = "NO";
        }
        if (lapseHours >= 1) {
            answer = "YES";
        }
        if (LAPSE_days >= 1) {
            answer = "YES";
        }


        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++ Answer from TimeDifference class:  --> " + answer);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        return answer;
    }


    public double findDifferenceMethodYYYYMMDD(String dateStartLAST, String dateFinishNOW) throws NullPointerException {

        System.out.println("dateStartLAST  ==>>   " + dateStartLAST);
        System.out.println("dateFinishNOW  ==>>   " + dateFinishNOW);

        SimpleDateFormat format_LTrade = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        SimpleDateFormat format_NOW = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

        Date d1 = null;
        Date d2 = null;


        try {
            d1 = format_LTrade.parse(dateStartLAST);
            d2 = format_NOW.parse(dateFinishNOW);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);


            System.out.println(Days.daysBetween(dt1, dt2).getDays() + " days, ");
            daysLapsed = Days.daysBetween(dt1, dt2).getDays();
            System.out.println(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            hoursLapsed = Hours.hoursBetween(dt1, dt2).getHours() % 12;
            System.out.println(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
            minutesLapsedVALUE = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
            System.out.println(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

            DaysElapsed = Math.abs(daysLapsed);
            hoursElapsed = Math.abs(hoursLapsed);
            minutesElapsed = Math.abs(minutesLapsedVALUE);


            System.out.println("============Double_,_,_MinutesLapsed==============>>  " + minutesElapsed + "     || Criteria against Length of Minutes=________" + lengthOfMinutes);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.LAPSE_days = DaysElapsed;
        this.lapseHours = hoursElapsed;
        this.lapseMinutes = minutesElapsed;


        if (lapseMinutes >= lengthOfMinutes) {
            answer = "YES";
        }
        if (lapseMinutes < lengthOfMinutes) {
            answer = "NO";
        }
        if (lapseHours >= 1) {
            answer = "YES";
        }
        if (LAPSE_days >= 1) {
            answer = "YES";
        }


        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++++++++ Answer from TimeDifference class:  --> " + answer);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        return minutesElapsed;
    }


}
