package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.BookingResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.SeatsResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.FormatDate;

public class chiTietVe_MainActivity extends AppCompatActivity {
    ImageView img_btn_troLai, img_posterMovie;
    TextView frmChiTietVe_tenPhim, frmChiTietVe_thoiLuong, frmChiTietVe_theLoai,
            frmChiTietVe_gioChieu, frmChiTietVe_lichChieu, frmChiTietVe_phongChieu, frmChiTietVe_ghe, frmChiTietVe_giaTien;
    BookingResponse bookingResponse;
    ArrayList<SeatsResponse> getArraySeat;
    ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_chitietve);

        addControls();
        addEvents();

    }

    private void addControls() {
        img_btn_troLai = findViewById(R.id.img_veDaDat_troLai);
        img_posterMovie = findViewById(R.id.img_posterMovie);
        frmChiTietVe_tenPhim = findViewById(R.id.frmChiTietVe_tenPhim);
        frmChiTietVe_thoiLuong = findViewById(R.id.frmChiTietVe_thoiLuong);
        frmChiTietVe_theLoai = findViewById(R.id.frmChiTietVe_theLoai);
        frmChiTietVe_gioChieu = findViewById(R.id.frmChiTietVe_gioChieu);
        frmChiTietVe_lichChieu = findViewById(R.id.frmChiTietVe_lichChieu);
        frmChiTietVe_phongChieu = findViewById(R.id.frmChiTietVe_phongChieu);
        frmChiTietVe_ghe = findViewById(R.id.frmChiTietVe_ghe);
        frmChiTietVe_giaTien = findViewById(R.id.frmChiTietVe_giaTien);
        getResult();
        setLayout();

    }

    private void addEvents() {
//        img_btn_troLai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(chiTietVe_MainActivity.this, trangChu_MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    private void getResult() {
        Intent intent = getIntent();
        bookingResponse = (BookingResponse) intent.getSerializableExtra("bookingResponse");
        getArraySeat = intent.getParcelableArrayListExtra("getArraySeat");

    }

    private void setLayout() {
        Log.d("log3", "Check: " + bookingResponse);
        String url = RetrofitSer.BASE_URL + bookingResponse.getSchedule().getMovies().getMovie_poster();

//        Glide.with(chiTietVe_MainActivity.this)
//                .load(url)
//                .placeholder(R.drawable.custom_cardview_login) // Hình ảnh hiển thị trong khi tải
//                .error(R.drawable.custom_cardview_login)// Hình ảnh hiển thị khi lỗi
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(img_posterMovie);


       frmChiTietVe_tenPhim.setText(bookingResponse.getSchedule().getMovies().getMovieName());
       frmChiTietVe_thoiLuong.setText(bookingResponse.getSchedule().getMovies().getMovie_length()+" Phút");
       frmChiTietVe_theLoai.setText(bookingResponse.getSchedule().getMovies().getMovie_genres());
        frmChiTietVe_gioChieu.setText(bookingResponse.getSchedule().getScheduleStart().substring(0,bookingResponse.getSchedule().getScheduleStart().length() - 3));
        frmChiTietVe_lichChieu.setText(FormatDate.fomatDatePay(bookingResponse.getSchedule().getScheduleDate()));
        frmChiTietVe_phongChieu.setText(bookingResponse.getSchedule().getRoom().getRoomName());
        String dsGhe = "";
        for (SeatsResponse ghe : getArraySeat) {
            if (dsGhe.equals(""))
                dsGhe = dsGhe + ghe.getSeatRow() + ghe.getSeatNumber();
            else {
                dsGhe = dsGhe + ", ";
                dsGhe = dsGhe + ghe.getSeatRow() + ghe.getSeatNumber();
            }
        }
        frmChiTietVe_ghe.setText(dsGhe);
        frmChiTietVe_giaTien.setText(bookingResponse.getPrice() + "00 VND");

    }
}