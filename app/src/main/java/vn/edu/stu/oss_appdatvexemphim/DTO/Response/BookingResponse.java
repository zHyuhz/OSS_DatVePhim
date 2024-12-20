package vn.edu.stu.oss_appdatvexemphim.DTO.Response;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse implements Serializable {

    private int booking_id;

    private Double price;

    private String bookingDate;

    private ArrayList<SeatsResponse> seatsBooking;

    private UserResponse user;

    private ScheduleResponse schedule;
}
