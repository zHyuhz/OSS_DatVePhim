package vn.edu.stu.oss_appdatvexemphim.DTO.Response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse implements Serializable {

    private int schedule_id;

    private MovieResponse movies;

    private RoomResponse room;

    private String scheduleDate;

    private String scheduleStart;

    private String scheduleEnd;

    private double price;
}
