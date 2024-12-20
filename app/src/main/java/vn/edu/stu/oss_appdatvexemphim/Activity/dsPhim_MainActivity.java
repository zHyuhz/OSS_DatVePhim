package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.MovieResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.FilmAdapter;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.OnItemClickListener;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.ScaleCenterLinearLayoutManager;

public class dsPhim_MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btn_add;
    List<MovieResponse> movieResponseList = new ArrayList<>();
    FilmAdapter filmAdapter;
    DrawerLayout drawer_layout;
    Toolbar toolbarDS;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_danhsach_phim);

        addControls();
        addEvents();
    }

    private void addControls() {
        recyclerView = findViewById(R.id.frmDanhSach_RCV_dsPhim);
        btn_add = findViewById(R.id.frmDanhSach_btn_add);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu_frmTrangChu);
        navigationView = findViewById(R.id.nav_menu_frmTrangChu);
        Menu menu = navigationView.getMenu();


        MenuItem checkedItem = menu.findItem(R.id.nav_QLPhim);
        checkedItem.setVisible(false);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_trangchu) {
                Intent intent = new Intent(this, trangChu_MainActivity.class);

                startActivity(intent);
                finish();

            } else if (id == R.id.nav_QLPhim) {

//                startActivity(intent);
//                Toast.makeText(this, "Quản lý phim được chọn", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_dangxuat) {

            }
            return true;

        });

        toolbarDS = findViewById(R.id.toolbarDS);
        setSupportActionBar(toolbarDS);
        getSupportActionBar().setTitle("");


        toolbarDS.setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbarDS,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        khoiTaoPhim();


    }

    private void addEvents() {
        filmAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MovieResponse movieResponse = movieResponseList.get(position);
                Intent intent = new Intent(dsPhim_MainActivity.this, capNhapPhim_MainActivity.class);
                intent.putExtra("MOVIE", movieResponse);
                startActivity(intent);
                Toast.makeText(dsPhim_MainActivity.this, movieResponse.getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dsPhim_MainActivity.this, capNhapPhim_MainActivity.class);
                startActivity(intent);

            }
        });

        filmAdapter.setOnItemLongClickListener((view, position) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(dsPhim_MainActivity.this);
            builder.setTitle("XOA PHIM")
                    .setMessage("BAN CO MUON XOA HAY KHONG")
                    .setCancelable(false)
                    .setNegativeButton("DONG Y", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id = movieResponseList.get(position).getMovieId();
                            xuLyXoaMovie(id);
                        }
                    })
                    .setPositiveButton("TU CHOI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(dsPhim_MainActivity.this, String.valueOf(which), Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    private void xuLyXoaMovie(int id) {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<String>> call = apiService.deleteMovie(id);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body().getCode() == 0) {
                    // Thành công: Xóa phần tử khỏi danh sách
                    for (int i = 0; i < movieResponseList.size(); i++) {
                        if (movieResponseList.get(i).getMovieId() == id) {
                            movieResponseList.remove(i);
                            break;
                        }
                    }
                    filmAdapter.movieList.clear();
                    filmAdapter.movieList.addAll(movieResponseList);
                    filmAdapter.notifyDataSetChanged();
                    Toast.makeText(dsPhim_MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(dsPhim_MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.e("API Error", "Lỗi: " + t.getMessage());
                Toast.makeText(dsPhim_MainActivity.this, "Xay ra loi ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void layTatCaPhim() {
        List<MovieResponse> movieResponses = new ArrayList<>();
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<List<MovieResponse>>> call = apiService.getAllMovie();
        call.enqueue(new Callback<ApiResponse<List<MovieResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MovieResponse>>> call, Response<ApiResponse<List<MovieResponse>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ApiResponse<List<MovieResponse>> apiResponse = response.body();
                        if (apiResponse.getCode() == 0) {
                            filmAdapter.movieList.clear();
                            movieResponseList = apiResponse.getResult();
                            filmAdapter.movieList.addAll(apiResponse.getResult());
                            filmAdapter.notifyDataSetChanged();
                            //  Toast.makeText(dsPhim_ActivityMain.this, "Thanh cong 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(dsPhim_MainActivity.this, "Lỗi tại dsPhim_MainActivity", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(dsPhim_MainActivity.this, "That bai: Do body null", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        // Đọc nội dung lỗi từ response.errorBody()
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Gson gson = new Gson();
                        JsonObject errorJson = gson.fromJson(errorBody, JsonObject.class);

                        // Lấy giá trị của "message"
                        String errorMessage = errorJson.has("message") ? errorJson.get("message").getAsString() : "Unknown error";
                        // Bạn có thể xử lý chuỗi lỗi ở đây và hiển thị thông báo lỗi
                        Toast.makeText(dsPhim_MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(dsPhim_MainActivity.this, "Error parsing error body", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MovieResponse>>> call, Throwable t) {
                Log.e("API Error", "Lỗi: " + t.getMessage());
            }
        });
    }

    private void khoiTaoPhim() {
        filmAdapter = new FilmAdapter(this, R.layout.item_movie, new ArrayList<>());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(filmAdapter);
        layTatCaPhim();
    }

    @Override
    protected void onResume() {
        super.onResume();
        layTatCaPhim();
    }
}