
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.abs;


/**
 *
 */
public class Date{

    private String year;
    private String month;
    private String day;

    public static void main(String[] args) {
        System.out.println(Date.dayDifference(
                (new Date("2012-02-11")), (new Date("2013-02-11"))));
//        System.out.println(new Date("2001-11-11").shiftDate(22));

    }

    //        ISO 4217
    public Date(String date){
        String [] temp = date.split("-",3);
        checkDate(temp[0],temp[1],temp[2]);
        this.year = temp[0];
        this.month = temp[1];
        this.day = temp[2];
    }

    public Date(String year, String month, String day) {
        checkDate(year,month,day);
        this.year = year;
        this.month = month;
        this.day = day;
    }


    private void checkDate(String year, String month, String day) throws IllegalArgumentException {

        if(!( (Integer.parseInt(year) > 0 ) ||
                ((Integer.parseInt(month) > 0) && (Integer.parseInt(month) <= 12)) ||
                ((Integer.parseInt(day) > 0) && (Integer.parseInt(day) <= 31)) ||
                (dayIsWithinMonth(Integer.parseInt(day),Integer.parseInt(month))) ))
            throw new IllegalArgumentException("This date is impossible!");

    }

    private boolean dayIsWithinMonth(int day, int month) throws IllegalArgumentException{

        switch (month){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:{
                return (day >= 0) && (day <= 31);
            }
            case 4: case 6: case 9: case 11:{
                return (day >= 0) && (day <= 30);
            }
            default:
                return (day >= 0) && (day <= 29);
        }

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
