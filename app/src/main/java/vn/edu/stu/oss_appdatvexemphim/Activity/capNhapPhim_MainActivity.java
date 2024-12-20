package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.MovieResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.FormatDate;


public class capNhapPhim_MainActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 1;
    ImageButton imageButton;
    TextView tv_valueDate, frmUpdate_edt_maPhim;
    EditText frmUpdate_edt_tenPhim, frmUpdate_edt_theLoaiPhim, frmUpdate_edt_doDaiPhim, frmUpdate_edt_moTaPhim;
    ImageView frm_update_img;
    Button btn_chonAnh, btn_them, btn_sua;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_update_movies);

        addControls();
        addEvents();

    }

    private void addControls() {
        imageButton = findViewById(R.id.frmUpdate_btn_calendar);
        tv_valueDate = findViewById(R.id.frmUpdate_tv_ngay);
        frmUpdate_edt_maPhim = findViewById(R.id.frmUpdate_edt_maPhim);
        frmUpdate_edt_theLoaiPhim = findViewById(R.id.frmUpdate_edt_theLoaiPhim);
        frmUpdate_edt_tenPhim = findViewById(R.id.frmUpdate_edt_tenPhim);
        frmUpdate_edt_doDaiPhim = findViewById(R.id.frmUpdate_edt_doDaiPhim);
        frmUpdate_edt_moTaPhim = findViewById(R.id.frmUpdate_edt_moTaPhim);
        frm_update_img = findViewById(R.id.frm_update_img);
        btn_chonAnh = findViewById(R.id.btn_chonAnh);
        btn_them = findViewById(R.id.btn_them);
        btn_sua = findViewById(R.id.btn_sua);
        try {
            if (setDetailsMovie(getResultItent())) {
                btn_them.setVisibility(View.GONE);
            } else {
                btn_sua.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

    }

    private void addEvents() {
        xulyImageButton();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setMovieName(frmUpdate_edt_tenPhim.getText().toString());
                movieResponse.setMovie_description(frmUpdate_edt_moTaPhim.getText().toString());
                movieResponse.setMovie_genres(frmUpdate_edt_theLoaiPhim.getText().toString());
                movieResponse.setMovie_release(FormatDate.formatDate(tv_valueDate.getText().toString()));
                movieResponse.setMovie_length(Integer.parseInt(frmUpdate_edt_doDaiPhim.getText().toString()));
                Bitmap bitmap = ((BitmapDrawable) frm_update_img.getDrawable()).getBitmap();
                try {
                    addMovieWithBitmap(bitmap, movieResponse.getMovieName(), movieResponse.getMovie_description(), movieResponse.getMovie_genres(),
                            movieResponse.getMovie_release(), movieResponse.getMovie_length());
                } catch (Exception e) {

                }
            }
        });
        btn_chonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnh();
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setMovieId(Integer.parseInt(frmUpdate_edt_maPhim.getText().toString()));
                movieResponse.setMovieName(frmUpdate_edt_tenPhim.getText().toString());
                movieResponse.setMovie_description(frmUpdate_edt_moTaPhim.getText().toString());
                movieResponse.setMovie_genres(frmUpdate_edt_theLoaiPhim.getText().toString());
                movieResponse.setMovie_release(FormatDate.formatDate(tv_valueDate.getText().toString()));
                int length = frmUpdate_edt_doDaiPhim.getText().toString().length();

                movieResponse.setMovie_length(Integer.parseInt(frmUpdate_edt_doDaiPhim.getText().toString().substring(0,length-1)));
                Bitmap bitmap = ((BitmapDrawable) frm_update_img.getDrawable()).getBitmap();
                try {
                    xuLyCapNhat(
                            bitmap
                            , movieResponse.getMovieId()
                            , movieResponse.getMovieName()
                            , movieResponse.getMovie_description()
                            , movieResponse.getMovie_genres()
                            , movieResponse.getMovie_release()
                            , movieResponse.getMovie_length());
                } catch (Exception e) {

                }

            }
        });

    }

    private void xuLyCapNhat(Bitmap moviePosterBitmap, int id, String movieName, String movieDes, String movieGenres, String movieRelease, int movieLength) {
        try {
            MultipartBody.Part posterPart = prepareFilePartFromBitmap(moviePosterBitmap, this, "moviePoster", movieName.trim());

            ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);

            Call<ApiResponse<MovieResponse>> call = apiService.updateMovie(
                    id,
                    movieName,
                    movieDes,
                    movieGenres,
                    movieRelease,
                    movieLength,
                    posterPart);

            call.enqueue(new Callback<ApiResponse<MovieResponse>>() {
                @Override
                public void onResponse(Call<ApiResponse<MovieResponse>> call, Response<ApiResponse<MovieResponse>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        if(response.body().getCode() == 0){
                            Toast.makeText(capNhapPhim_MainActivity.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                            finish();
                        }else
                            Toast.makeText(capNhapPhim_MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(capNhapPhim_MainActivity.this,"BODY NULL",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<MovieResponse>> call, Throwable t) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addMovieWithBitmap(Bitmap moviePosterBitmap, String movieName, String movieDes, String movieGenres, String movieRelease, int movieLength) {
        try {
            // Chuyển Bitmap thành MultipartBody.Part
            MultipartBody.Part posterPart = prepareFilePartFromBitmap(moviePosterBitmap, this, "moviePoster", movieName.replaceAll("\\s", ""));

            // Tạo đối tượng Retrofit
            ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);

            // Gọi phương thức API
            Call<ApiResponse<MovieResponse>> call = apiService.addMovies(movieName, movieDes, movieGenres, movieRelease, movieLength, posterPart);

            // Thực hiện gọi API
            call.enqueue(new Callback<ApiResponse<MovieResponse>>() {
                @Override
                public void onResponse(Call<ApiResponse<MovieResponse>> call, Response<ApiResponse<MovieResponse>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(capNhapPhim_MainActivity.this, "Thêm thành công ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Xử lý lỗi
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<MovieResponse>> call, Throwable t) {
                    // Xử lý khi không kết nối được với API
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    private void callAPIThemMovie(String movieName, String movieDes, String movieGenres,
//                                  String movieRelease, int movieLength, Bitmap posterFile) {
//        // Chuyển Bitmap thành MultipartBody.Part
//        MultipartBody.Part posterPart = prepareFilePartFromBitmap(posterFile, context, "moviePoster");
//
//        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
//        Call<ApiResponse<MovieResponse>> call = apiService.addMovies(movieName, movieGenres,movieRelease, movieLength, posterFile);
//    }
    public File convertBitmapToFile(Context context, Bitmap bitmap, String fileName) throws IOException {
        // Tạo một file mới trong bộ nhớ tạm
        File file = new File(context.getCacheDir(), fileName);

        // Tạo một FileOutputStream để ghi Bitmap vào file
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);  // Chọn định dạng JPEG hoặc PNG
        fos.flush();
        fos.close();

        return file;
    }

    public MultipartBody.Part prepareFilePartFromBitmap(Bitmap bitmap, Context context, String partName, String namePoster) throws IOException {
        // Chuyển Bitmap thành File
        File file = convertBitmapToFile(context, bitmap, namePoster.replaceAll("\\s", "") + ".jpg");

        // Tạo RequestBody từ file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);

        // Chuyển thành MultipartBody.Part
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    private void chonAnh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Chon Anh"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                frm_update_img.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
    }

    private MovieResponse getResultItent() {
        MovieResponse movieResponse = null;
        Intent intent = getIntent();
        if (intent != null) {
            movieResponse = (MovieResponse) intent.getSerializableExtra("MOVIE");

        }
        return movieResponse;
    }

    private Boolean setDetailsMovie(MovieResponse movieResponse) {
        try {
            frmUpdate_edt_tenPhim.setText(movieResponse.getMovieName());
            frmUpdate_edt_maPhim.setText(String.valueOf(movieResponse.getMovieId()));
            frmUpdate_edt_moTaPhim.setText(movieResponse.getMovie_description());
            frmUpdate_edt_theLoaiPhim.setText(movieResponse.getMovie_genres());
            tv_valueDate.setText(FormatDate.fomatDateSQL(movieResponse.getMovie_release()));
            frmUpdate_edt_doDaiPhim.setText(String.valueOf(movieResponse.getMovie_length()) + "m");
            String url = RetrofitSer.BASE_URL + movieResponse.getMovie_poster();
            Glide.with(capNhapPhim_MainActivity.this)
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_background) // Hình ảnh hiển thị trong khi tải
                    .error(R.drawable.ic_launcher_foreground)             // Hình ảnh hiển thị khi lỗi
                    .into(frm_update_img);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void xulyImageButton() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại làm ngày mặc định
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Hiển thị DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        capNhapPhim_MainActivity.this, // Activity hiện tại
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // Xử lý ngày được chọn
                                String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                tv_valueDate.setText(selectedDate);
                                // Toast.makeText(capNhapPhim_Activity.this, "Ngày đã chọn: " + selectedDate, Toast.LENGTH_SHORT).show();
                            }
                        },
                        year, // Năm mặc định
                        month, // Tháng mặc định
                        day // Ngày mặc định
                );
                datePickerDialog.show();
            }
        });
    }
}