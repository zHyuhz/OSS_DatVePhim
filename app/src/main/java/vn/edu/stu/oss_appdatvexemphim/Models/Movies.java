package vn.edu.stu.oss_appdatvexemphim.Models;

import java.util.List;

public class Movies {

    private String movie_name;


    private String movie_genres;

    private String movie_length;

    private int movie_poster;
    private List<Schedule> scheduleList;

    public Movies(String movie_name, String movie_length, String movie_genres, int movie_poster) {
        this.movie_name = movie_name;
        this.movie_genres = movie_genres;
        this.movie_length = movie_length;
        this.movie_poster = movie_poster;
    }

    public Movies(String movie_name, String movie_length, String movie_genres, int movie_poster, List<Schedule> scheduleList) {
        this.movie_name = movie_name;
        this.movie_genres = movie_genres;
        this.movie_length = movie_length;
        this.movie_poster = movie_poster;
        this.scheduleList = scheduleList;
    }


    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }



    public String getMovie_genres() {
        return movie_genres;
    }

    public void setMovie_genres(String movie_genres) {
        this.movie_genres = movie_genres;
    }



    public String getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(String movie_length) {
        this.movie_length = movie_length;
    }



    public int getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(int movie_poster) {
        this.movie_poster = movie_poster;
    }


    public Movies() {
    }
}
