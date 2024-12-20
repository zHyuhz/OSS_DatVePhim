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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse implements Serializable {
    private int account_id;

    private String userName;

    private String email;

    private String createdAt;

    private int status;

    private String accountRole;

    private UserResponse user;
}
