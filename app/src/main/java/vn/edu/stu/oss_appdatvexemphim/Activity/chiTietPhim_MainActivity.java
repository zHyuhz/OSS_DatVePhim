package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import vn.edu.stu.oss_appdatvexemphim.DTO.Response.MovieResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.FormatDate;

public class chiTietPhim_MainActivity extends AppCompatActivity {
    TextView tv_theLoai, tv_moTa, tv_Item_movieName, tv_Item_length, tv_Item_date;
    ImageView img_posterMovie, img_ChiTiet_troLai;
    View include_chitietphim;
    Button btn_tiepTuc;
    MovieResponse movieResponse = new MovieResponse();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_chitietphim_activity);
        addControls();
        addEvents();
        Log.d("name","nameChiTietPhim: " + username);
    }

    private void addControls() {
        //setContentView(R.layout.item_chitietphim);
        tv_theLoai = findViewById(R.id.tv_theLoai);
        tv_moTa = findViewById(R.id.tv_moTa);
        img_posterMovie = findViewById(R.id.img_posterMovie);
        img_ChiTiet_troLai = findViewById(R.id.img_ChiTiet_troLai);

        include_chitietphim = findViewById(R.id.include_chitietphim);

        tv_Item_movieName = include_chitietphim.findViewById(R.id.tv_Item_movieName);
        tv_Item_length = include_chitietphim.findViewById(R.id.tv_Item_length);
        tv_Item_date = include_chitietphim.findViewById(R.id.tv_Item_date);
        btn_tiepTuc = findViewById(R.id.frmChiTietPhim_btn_tiepTuc);

        setDetailsMovie(getResultItent());

        // MovieResponse movieResponse = getResultItent();
//        if (movieResponse != null){
//            Toast.makeText(this, "Nhận được: ", Toast.LENGTH_SHORT).show();
//        }else  Toast.makeText(this, " eo Nhận được: ", Toast.LENGTH_SHORT).show();


    }

    private void addEvents() {
        img_ChiTiet_troLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        btn_tiepTuc.setOnClickListener(v -> {
//            Intent intent = new Intent(chiTietPhim_MainActivity.this, lichChieu_MainActivity.class);
//            intent.putExtra("MovieID",movieResponse.getMovieId());
//            intent.putExtra("username",username);
//            startActivity(intent);
//        });
    }

    private MovieResponse getResultItent() {

        Intent intent = getIntent();
        if (intent != null) {
            movieResponse = (MovieResponse) intent.getSerializableExtra("MOVIE");
            username = intent.getStringExtra("username");

        }
        return movieResponse;
    }

    private void setDetailsMovie(MovieResponse movieResponse) {
        tv_Item_movieName.setText(movieResponse.getMovieName());
        tv_moTa.setText(movieResponse.getMovie_description());
        tv_theLoai.setText(movieResponse.getMovie_genres());
        tv_Item_date.setText(FormatDate.fomatDateSQL(movieResponse.getMovie_release()));
        tv_Item_length.setText(movieResponse.getMovie_length() + "m");
        String url = RetrofitSer.BASE_URL + movieResponse.getMovie_poster();
        Glide.with(chiTietPhim_MainActivity.this)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background) // Hình ảnh hiển thị trong khi tải
                .error(R.drawable.ic_launcher_foreground)             // Hình ảnh hiển thị khi lỗi
                .into(img_posterMovie);
    }

}