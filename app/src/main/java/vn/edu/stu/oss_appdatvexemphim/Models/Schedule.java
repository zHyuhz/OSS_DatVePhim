package vn.edu.stu.oss_appdatvexemphim.Models;

import java.io.Serializable;
import java.util.Calendar;

public class Schedule implements Serializable {
    private int schedule_id;
    private int movie_id;
    private int room_id;
    private Calendar localDate;
    private String startTime;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Schedule(int schedule_id, int movie_id, int room_id, Calendar localDate, String startTime) {
        this.schedule_id = schedule_id;
        this.movie_id = movie_id;
        this.localDate = localDate;
        this.startTime = startTime;
        this.room_id = room_id;
    }

    public Schedule() {
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public Calendar getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Calendar localDate) {
        this.localDate = localDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
