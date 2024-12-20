package vn.edu.stu.oss_appdatvexemphim.DTO.Request;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.SeatsResponse;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    private Double price;

    private String bookingDate;

    private List<SeatsResponse> seatsBooking;

    private int user_id;

    private int schedule_id;
}
