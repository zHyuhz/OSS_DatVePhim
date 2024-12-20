package vn.edu.stu.oss_appdatvexemphim.Models;

public class TimeSlot {
    private int id;
    private String hour;
    private String minute;

    public TimeSlot(int id, String hour, String minute) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public TimeSlot() {
    }

    @Override
    public String toString() {
        return this.getHour() + " : " + this.getMinute();
    }
}
