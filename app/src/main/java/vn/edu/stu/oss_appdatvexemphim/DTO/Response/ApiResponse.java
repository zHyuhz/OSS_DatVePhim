package vn.edu.stu.oss_appdatvexemphim.DTO.Response;


import com.google.gson.annotations.SerializedName;

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
public class ApiResponse<T> {
    @SerializedName("code")
    int code;

    String message;

    T result;

}