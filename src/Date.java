package date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;


/*
    This class cares for dates
 */

public class Date{

    private String year;
    private String month;
    private String day;

    public static void main(String[] args) {
        System.out.println("XD");
    }

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

    //TODO leap year are not considered

    public boolean dayIsWithinMonth(int day, int month) throws IllegalArgumentException{

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

    }

    //TODO  it may also work
    // TODO It may consider also length of months..
    public Integer dayDifference(Date date) {


    }



    public Date shiftDate(Integer dayNumber){


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

    public int getMonth() {
        return Integer.parseInt(month);
    }

    public int getDay() {
        return Integer.parseInt(day);
    }

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

}
