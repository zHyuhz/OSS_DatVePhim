package vn.edu.stu.oss_appdatvexemphim.DTO.Response;

import java.io.Serializable;

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
@FieldDefaults(level =  AccessLevel.PRIVATE)

public class UserResponse implements Serializable {
    int user_id;
    String fullName;
    String birthday;
    int gender;
    String city;
    String phoneNumber;
    private AccountResponse accounts;
}
