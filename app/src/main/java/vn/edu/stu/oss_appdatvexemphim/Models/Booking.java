package vn.edu.stu.oss_appdatvexemphim.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Booking {
    private int booking_id;
    private int user_id;
    private int schedule_id;
    private double price;
    private Calendar date,hour;


    public String getHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(hour.getTime());
    }

    public void setHour(Calendar hour) {
        this.hour = hour;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date.getTime());
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Booking() {
    }

    public Booking(int booking_id, int user_id, int schedule_id, double price, Calendar date,Calendar hour) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.schedule_id = schedule_id;
        this.price = price;
        this.date = date;
        this.hour = hour;
    }

    public Booking(int booking_id, Calendar date, Calendar hour, double price) {
        this.booking_id = booking_id;
        this.date = date;
        this.hour = hour;
        this.price = price;
    }
}
