package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;

import vn.edu.stu.oss_appdatvexemphim.DTO.Request.BookingRequest;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.AccountResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;

import vn.edu.stu.oss_appdatvexemphim.DTO.Response.BookingResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ScheduleResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.SeatsResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.FormatDate;

public class thanhToan_MainActivity extends AppCompatActivity {
    TextView tv_tenGhe, tv_tongTien, tv_Item_movieName, item_phimThanhToan_theLoai, tv_Item_date, tv_Item_time, tv_Item_room;
    ImageView img_Item, img_LichChieu_troLai;
    Button btn_thanhtoan;
    ArrayList<SeatsResponse> getArraySeat;
    String username = "admin";
    ScheduleResponse scheduleResponse = null;
    BookingResponse bookingResponse;

    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.frm_thanhtoann);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();


    }

    private void addControls() {

        tv_tenGhe = findViewById(R.id.tv_tenGhe);
        tv_tongTien = findViewById(R.id.tv_tongTien);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        tv_Item_movieName = findViewById(R.id.tv_Item_movieName);
        item_phimThanhToan_theLoai = findViewById(R.id.item_phimThanhToan_theLoai);
        tv_Item_date = findViewById(R.id.tv_Item_date);
        tv_Item_time = findViewById(R.id.tv_Item_time);
        img_Item = findViewById(R.id.img_Item);
        tv_Item_room = findViewById(R.id.tv_Item_room);
        img_LichChieu_troLai = findViewById(R.id.img_LichChieu_troLai);

        getResultIntent();
        getUserID();
        setInfo();
    }

    private void addEvents() {
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingRequest bookingRequest = new BookingRequest();
                bookingRequest.setPrice(scheduleResponse.getPrice() * getArraySeat.size());

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(date);

                bookingRequest.setBookingDate(formattedDate);

                bookingRequest.setUser_id(userID);


                bookingRequest.setSchedule_id(scheduleResponse.getSchedule_id());
                bookingRequest.setSeatsBooking(getArraySeat);
                createBooking(bookingRequest);

                for (SeatsResponse response : getArraySeat) {
                    updateAvailable(response.getSeatRow(), response.getSeatNumber(), scheduleResponse.getRoom().getRoomId());
                }
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (bookingResponse != null) {
                        Intent intent = new Intent(thanhToan_MainActivity.this, chiTietVe_MainActivity.class);
                        intent.putExtra("bookingResponse", bookingResponse);
                        intent.putExtra("getArraySeat", getArraySeat);
                        startActivity(intent);
                        finish();
                        Log.d("log4", "Check: " + bookingResponse);
                    }
                }, 1000);
            }
        });
        img_LichChieu_troLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateAvailable(String seatRow, int seatNumber, int roomId) {
        char row = seatRow.charAt(0);
        Log.d("log1", "Check: " + row);
        Log.d("log2", "Check: " + seatNumber);
        Log.d("log3", "Check: " + roomId);
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<SeatsResponse>> call = apiService.updateAvailable(row, seatNumber, roomId);
        call.enqueue(new Callback<ApiResponse<SeatsResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<SeatsResponse>> call, Response<ApiResponse<SeatsResponse>> response) {

            }

            @Override
            public void onFailure(Call<ApiResponse<SeatsResponse>> call, Throwable t) {

            }
        });
    }

    private void createBooking(BookingRequest bookingRequest) {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<BookingResponse>> call = apiService.addBooking(bookingRequest);
        call.enqueue(new Callback<ApiResponse<BookingResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<BookingResponse>> call, Response<ApiResponse<BookingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookingResponse = response.body().getResult();
                    Log.d("API_SUCCESS", "Booking response: " + bookingResponse);
                } else {
                    Log.e("API_ERROR", "Response body is null or unsuccessful. Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BookingResponse>> call, Throwable t) {
                Log.d("Error", "Booking response: " + t.getMessage());
            }
        });
    }


    private void sendRequestBooking() {

    }

    private void getUserID() {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<AccountResponse>> call = apiService.getUserIdByUserName(username);
        call.enqueue(new Callback<ApiResponse<AccountResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AccountResponse>> call, Response<ApiResponse<AccountResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userID = response.body().getResult().getUser().getUser_id();
                    Log.d("username", "Check: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AccountResponse>> call, Throwable t) {

            }
        });
    }

    private void getResultIntent() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        getArraySeat = intent.getParcelableArrayListExtra("list_seats");
        scheduleResponse = (ScheduleResponse) intent.getSerializableExtra("scheduleResponse");
        Log.d("username", "Check: " + username);
        Log.d("list_seats", "Check: " + getArraySeat);
        Log.d("scheduleResponse", "Đã delay 2 giây" + scheduleResponse);
    }

    public void setInfo() {
        String dsGhe = "";
        for (SeatsResponse ghe : getArraySeat) {
            if (dsGhe.equals(""))
                dsGhe = dsGhe + ghe.getSeatRow() + ghe.getSeatNumber();
            else {
                dsGhe = dsGhe + ", ";
                dsGhe = dsGhe + ghe.getSeatRow() + ghe.getSeatNumber();
            }
        }
        tv_tenGhe.setText(dsGhe);

        Double tongTien = scheduleResponse.getPrice() * getArraySeat.size();

        tv_tongTien.setText(String.valueOf(tongTien) + "00 VNĐ");

        tv_Item_movieName.setText(scheduleResponse.getMovies().getMovieName());
        item_phimThanhToan_theLoai.setText(scheduleResponse.getMovies().getMovie_genres());
        tv_Item_date.setText(FormatDate.fomatDatePay(scheduleResponse.getScheduleDate()));
        String url = RetrofitSer.BASE_URL + scheduleResponse.getMovies().getMovie_poster();
        Glide.with(thanhToan_MainActivity.this)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background) // Hình ảnh hiển thị trong khi tải
                .error(R.drawable.ic_launcher_foreground)// Hình ảnh hiển thị khi lỗi
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img_Item);


        tv_Item_time.setText(scheduleResponse.getScheduleStart().substring(0, scheduleResponse.getScheduleStart().length() - 3));
        tv_Item_room.setText(scheduleResponse.getRoom().getRoomName());

    }
}