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
public class RoomResponse implements Serializable {
    private int roomId;

    private String roomName;
}
