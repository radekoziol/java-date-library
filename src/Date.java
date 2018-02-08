
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static java.lang.Math.abs;


/**
 *
 */
public class Date{

    private String year;
    private String month;
    private String day;

    public static void main(String[] args) {
        Date date = new Date("2012-11-44");
    }

    //        ISO 4217
    public Date(String date){
        checkDate(date);
        String [] temp = date.split("-",3);
        this.year = temp[0];
        this.month = temp[1];
        this.day = temp[2];
    }

    public Date(String year, String month, String day) {
        checkDate(year + "-" + month  + "-" +  day);
        this.year = year;
        this.month = month;
        this.day = day;
    }


    private void checkDate(String input) throws IllegalArgumentException {

        if(!input.matches("\\d{4}-\\d{1,2}-\\d{1,2}"))
            throw new IllegalArgumentException("This date: " + input + " is impossible!");

        String [] temp = input.split("-",3);
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = Integer.parseInt(temp[2]);

        if(!( ((year > 0 ) && ((month > 0))
                && (month<= 12)) && ((day > 0)
                && (day <= 31)) && (dayIsWithinMonth(day,month,year)) ))
            throw new IllegalArgumentException("This date: " + input + " is impossible!");

    }


    private boolean dayIsWithinMonth(int day, int month, int year) throws IllegalArgumentException{

        Month m = Month.getMonth(month, year);
        return m.getDayAsInt() - day >= 0;

    }


    //        ISO 4217
    public boolean isLaterThan(Date endDate) {

        if(this.getYear() - endDate.getYear() > 0)
            return true;
        else if (this.getYear() - endDate.getYear() < 0)
            return false;
        else if (this.getMonth() - endDate.getMonth() > 0)
            return true;
        else if (this.getMonth() - endDate.getMonth() < 0)
            return false;
        else return this.getDay() - endDate.getDay() > 0;
    }


    public Date shiftDate(int dayNumber) {

        int yearAdd = 0;
        Month month = Month.getMonth(getMonth(), getYear() + yearAdd);

        //YEARS
        while (dayNumber >= 365) {
            if (Year.isLeapYear(getYear() + yearAdd)
                    && dayNumber >= 366
                    && month.ordinal() <= 2) {
                yearAdd++;
                dayNumber -= 366;
            } else if (!Year.isLeapYear(getYear() + yearAdd)){
                yearAdd++;
                dayNumber -= 365;
            }
            else
                break;
        }

        //MONTHS
        month = Month.getMonth(getMonth(), getYear() + yearAdd);
        while (dayNumber >= month.getDayAsInt()) {
            dayNumber -= month.getDayAsInt();
            month = month.getNextMonth(getYear() + yearAdd);
            if (month.equals(Month.January))
                yearAdd++;
        }


        //Days - now < 31
        if (getDay() + dayNumber > month.getDayAsInt()){
            dayNumber = abs(month.getDayAsInt() - getDay() - dayNumber);
            System.out.println(dayNumber);
            month = month.getNextMonth(getYear() + yearAdd);
            if (month.equals(Month.January))
                yearAdd++;
        }
        else
            dayNumber += getDay();

        return new Date(
                String.valueOf(getYear() + yearAdd),
                String.valueOf(month.getMonthAsInt()),
                String.valueOf(dayNumber));

    }

    public static int dayDifference(Date startDate, Date endDate) {

        int yearDifference = abs(startDate.getYear() - endDate.getYear());
        int sign = (int) Math.signum(startDate.getYear() - endDate.getYear());

        if(sign > 0)
            return -1 * searchBinaryDayDifference
                (0,((yearDifference + 1) * 365), endDate, startDate);

        else if(sign < 0)
            return searchBinaryDayDifference
                    (0,((yearDifference + 1) * 365), startDate, endDate);

        else if (startDate.isLaterThan(endDate))
            return -1 * searchBinaryDayDifference
                    ( 0,(365), endDate, startDate);

        return searchBinaryDayDifference
                ( 0,(365), startDate, endDate);
    }

    private static int searchBinaryDayDifference(int l, int r, Date startDate, Date endDate) {

        while(l != r) {
            System.out.println("RANGE OF: " + l + "," +r );

            int mid = (l + r) / 2;
            System.out.println("MID: " + mid);

            System.out.println(startDate);
            System.out.println(endDate);
            System.out.println(startDate.shiftDate(mid));
            System.out.println(startDate.shiftDate(mid).isLaterThan(endDate));

            if (startDate.shiftDate(mid).equals(endDate))
                return mid;

            if(l == r-1)
                return 0;

            if (startDate.shiftDate(mid).isLaterThan(endDate))
                r = mid;
            else {

                l = mid;
            }

        }
        return 0;

    }

    public static Date getCurrentDate(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String[] date = dtf.format(now).split("-",3);
        return new Date(date[0], date[1], "14");
    }


    public int getYear() {
        return Integer.parseInt(year);
    }

    private int getMonth() {
        return Integer.parseInt(month);
    }

    private int getDay() {
        return Integer.parseInt(day);
    }


    //        ISO 4217
    @Override
    public String toString() {

        String output = year + "-";

        if(month.length() == 1)
            output += "0" + month + "-";
        else output += month + "-";

        if(day.length() == 1)
            output += "0" + day;
        else output += day;

        return output;
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
