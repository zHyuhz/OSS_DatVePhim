package vn.edu.stu.oss_appdatvexemphim.DTO.Request;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data // tạo các hàm getter/setter...
@NoArgsConstructor // tạo construstor không đối số
@AllArgsConstructor
@Builder // tạo Obj nhanh hơn Ex: Object.builder().Attribute1...buid()
@FieldDefaults(level = AccessLevel.PRIVATE) // Đặt mặc định các thuộc tính không khai báo cụ thể là private
public class MovieRequest {

    private int movie_id;

    private String movie_name;

    private String movie_description;

    private String movie_genres;

    private LocalDate movie_release;

    private int movie_length;
}