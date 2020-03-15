package domain;

public class TimeStamp {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public TimeStamp(int aYear, int aMonth, int aDay, int aHour, int aMinute, int aSecond){
        this.year = aYear;
        this.month = aMonth;
        this.day = aDay;
        this.hour = aHour;
        this.minute = aMinute;
        this.second = aSecond;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int[] getCoordinates(){
        int[] coordinates = {year+month+day, hour+minute+second};
        return coordinates;
    }

    public boolean isYoungerThan(TimeStamp stampToBeChecked) {
        if (this.year < stampToBeChecked.getYear()){
            return true;}
        else if(this.year == stampToBeChecked.getYear()){
            if (this.month < stampToBeChecked.getMonth()){
                return true;}
            else if(this.month == stampToBeChecked.getMonth()){
                if (this.day < stampToBeChecked.getDay()){
                    return true;}
                else if(this.day == stampToBeChecked.getDay()){
                    if (this.hour < stampToBeChecked.getHour()){
                        return true;}
                    else if(this.hour == stampToBeChecked.getHour()){
                        if (this.minute < stampToBeChecked.getMinute()){
                            return true;}
                        else if(this.minute == stampToBeChecked.getMinute()){
                            if (this.second < stampToBeChecked.getSecond()){
                                return true;}
                            else{return false;}}
                        else{return false;}}
                    else{return false;}}
                else{return false;}}
            else{return false;}}
        else{return false;}}

    public String getFormattedTimeStamp() {
        String monthString = findMonthFromNumber(month);
        String meridiam = AMorPM(hour);
        return String.format("%2s:%2s:%2s %s %-10s %-5s %d", makeDoubleDigits(hour), makeDoubleDigits(minute), makeDoubleDigits(second), meridiam, monthString, day+getSuffix(day)+",", year);
                          //ex 10:2 7:34  PM February 2nd, 2020
    }

    private String findMonthFromNumber(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month-1];
    }

    private String AMorPM(int hour) {
        String AmOrPm = "AM";
        if (hour > 12){
            AmOrPm = "PM";
            this.hour = this.hour - 12;
        }
        return AmOrPm;
    }

    private String makeDoubleDigits(int number) {
        if (number < 10){
            return "0"+number;
        }
        return Integer.toString(number);
    }

    private String getSuffix(int number){
        String[] numberSuffixes = {"th","st","nd","rd","th","th","th","th","th","th"};
        if(number < 10){
            return numberSuffixes[number];
        }
        if(10 <= number && number < 20){
            return "th";
        }
        if(20 <= number && number < 30){
            number = number - 20;
            return numberSuffixes[number];
        }
        if (30 <= number){
            number = number - 30;
            return  numberSuffixes[number];
        }
        else{
            return "?";
        }
    }

}
