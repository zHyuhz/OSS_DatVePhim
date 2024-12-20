package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.AccountResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.MovieResponse;
import vn.edu.stu.oss_appdatvexemphim.Models.Movies;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.FilmAdapter;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.ImageAdapter;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.MoviesAdapter;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.ScaleCenterLinearLayoutManager;

public class trangChu_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SearchView searchView;
    EditText searchEditText;
    RecyclerView recyclerViewPhimDangChieu, recyclerViewPhimSapChieu, recyclerViewDichVu;
    ArrayList<Movies> movieListPhimDangChieu;
    ArrayList<Movies> movieListPhimSapChieu;
    MoviesAdapter moviesAdapter;
    ScaleCenterLinearLayoutManager layoutManagerPhimDangChieu, layoutManagerPhimSapChieu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    List<MovieResponse> movieResponseList = new ArrayList<>();
    FilmAdapter filmAdapter;
    TextView tv_dangChieu;
    LinearLayout ln_1, ln_2;
    List<MovieResponse> searchMovieList;
    private static String ROLE = "USER";
    String username;

    int[] dsPhimDangChieu = {R.drawable.avatar2_dongchaycuanuoc, R.drawable.culikhongbaogiokhoc, R.drawable.doibanhocyeu,
            R.drawable.kny_ss4, R.drawable.kny_ss5, R.drawable.modomdom, R.drawable.ngaytadayeu, R.drawable.ngayxuacomotchuyentinh,
            R.drawable.redone, R.drawable.thanduoc, R.drawable.thenun, R.drawable.venom2, R.drawable.vungdatlinhhon};

    int[] dsPhimSapChieu = {R.drawable.sc_bluepriod, R.drawable.sc_botubaothu, R.drawable.sc_kinhvanhoa, R.drawable.sc_moana2,
            R.drawable.sc_nangbachtuyet, R.drawable.sc_nguoihoathu, R.drawable.sc_quynhaptrang, R.drawable.sc_thamtukien,
            R.drawable.yourname, R.drawable.infinitywar};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_trangchu_activity);
        getRole();
        addControls();
        addEvents();
    }

    private void addControls() {
        getRole();
        searchView = findViewById(R.id.TC_searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        recyclerViewPhimDangChieu = findViewById(R.id.TC_RCV_dangChieu);
        ln_1 = findViewById(R.id.ln_1);
        ln_2 = findViewById(R.id.ln_2);
        tv_dangChieu = findViewById(R.id.tv_dangChieu);
        recyclerViewPhimSapChieu = findViewById(R.id.TC_RCV_SapChieu);
        recyclerViewDichVu = findViewById(R.id.TC_RCV_dichVu);
        movieListPhimSapChieu = new ArrayList<>();
        movieListPhimDangChieu = new ArrayList<>();
        movieResponseList = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu_frmTrangChu);
//        navigationView.setNavigationItemSelectedListener(menuItem -> {
//            int id = menuItem.getItemId();
//            if (id == R.id.nav_trangchu) {
//            } else if (id == R.id.nav_QLPhim) {
//                Intent intent = new Intent(this, dsPhim_MainActivity.class);
//                startActivity(intent);
//            } else if (id == R.id.nav_dangxuat) {
//                Intent intent = new Intent(trangChu_MainActivity.this, dangNhap_MainActivity.class);
//                ROLE = "USER";
//                startActivity(intent);
//                finish();
//            } else if (id == R.id.nav_QLTaiKhoan) {
//                Intent intent = new Intent(this, dsTaiKhoan_MainActivity.class);
//                startActivity(intent);
//            } else if (id == R.id.nav_QLNguoiDung) {
//                Intent intent = new Intent(this, dsNguoiDung_MainActivity.class);
//                startActivity(intent);
//            } else if (id == R.id.nav_QLVe) {
//                Intent intent = new Intent(this, dsVe_MainActivity.class);
//                startActivity(intent);
//            }
//            return true;

//        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        toolbar.setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        khoiTaoPhim();
        // khoiTaoPhimDangChieu();
        khoiTaoPhimSapChieu();
        khoiTaoDichVu();

    }

    private void getRole() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<AccountResponse>> call = apiService.findAccountByUsername(username);
        call.enqueue(new Callback<ApiResponse<AccountResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AccountResponse>> call, Response<ApiResponse<AccountResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse accountResponse = response.body().getResult();
                    if (accountResponse.getAccountRole().equals("ADMIN")) {
                        ROLE = "ADMIN";
                    }
                    ;
                    updateUIBasedOnRole();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse<AccountResponse>> call, Throwable t) {

            }
        });

    }


    private void addEvents() {
        xulyMauHintTrongSeachView();
        xulyCanChinhPosterPhimDangChieu();
        xulyHieuUngPosterPhimDangChieu();
        xulyCanChinhPosterPhimSapChieu();
        xulyHieuUngPosterPhimSapChieu();
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //xuLyTimKiemPhim(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                xuLyTimKiemPhim(newText);
                return true;
            }
        });
        filmAdapter.setOnItemClickListener(position -> {
            MovieResponse movieResponse = null;

            if (searchMovieList == null) {
                movieResponse = movieResponseList.get(position);

            } else {
                movieResponse = searchMovieList.get(position);
            }

            Intent intent = new Intent(trangChu_MainActivity.this, chiTietPhim_MainActivity.class);
            if (movieResponse != null) {
                intent.putExtra("MOVIE", movieResponse);
                intent.putExtra("username", username); //username
                Log.d("name1","trangchuname" + username);
                startActivity(intent);
//                Toast.makeText(this, "Bạn đã chọn phim: " + movieResponse.getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUIBasedOnRole() {
        if (ROLE.equals("USER")) {
            Menu menu = navigationView.getMenu();

            MenuItem menuItemsTrangChu = menu.findItem(R.id.nav_trangchu);
            MenuItem menuItemsQLPhim = menu.findItem(R.id.nav_QLPhim);
            MenuItem menuItemsQLTaiKhoan = menu.findItem(R.id.nav_QLTaiKhoan);
            MenuItem menuItemsQLVe = menu.findItem(R.id.nav_QLVe);
            MenuItem menuItemsQLNguoiDung = menu.findItem(R.id.nav_QLNguoiDung);

            menuItemsTrangChu.setVisible(false);
            menuItemsQLPhim.setVisible(false);
            menuItemsQLTaiKhoan.setVisible(false);
            menuItemsQLVe.setVisible(false);
            menuItemsQLNguoiDung.setVisible(false);

        }
    }

    private void xuLyTimKiemPhim(String newText) {
        searchMovieList = new ArrayList<>();

        tv_dangChieu.setText("Tìm Kiếm...");
        ln_2.setVisibility(View.GONE);
        ln_1.setVisibility(View.GONE);
        for (MovieResponse response : movieResponseList) {
            if (response.getMovieName().toLowerCase().contains(newText.toLowerCase())) {
                searchMovieList.add(response);
            }

        }
        if (searchMovieList.isEmpty()) {
            filmAdapter.setSearchList(searchMovieList);
        } else if (searchMovieList.size() == movieResponseList.size()) {
            filmAdapter.setSearchList(searchMovieList);
            tv_dangChieu.setText("Đang chiếu");
            ln_2.setVisibility(View.VISIBLE);
            ln_1.setVisibility(View.VISIBLE);
        } else {
            filmAdapter.setSearchList(searchMovieList);
        }
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
                            movieResponseList.clear();
                            movieResponseList.addAll(apiResponse.getResult());
                            filmAdapter.movieList.addAll(apiResponse.getResult());
                            filmAdapter.notifyDataSetChanged();
//                            Toast.makeText(trangChu_MainActivity.this, "Thanh cong 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(trangChu_MainActivity.this, "That bai ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(trangChu_MainActivity.this, "That bai: Do body null", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(trangChu_MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(trangChu_MainActivity.this, "Error parsing error body", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MovieResponse>>> call, Throwable t) {
                Log.e("API Error", "Lỗi: " + t.getMessage());
            }
        });
    }

    private void khoiTaoDichVu() {

        List<Integer> imageList = Arrays.asList(
                R.drawable.ultra_4dx,
                R.drawable.bondx,
                R.drawable.starium,
                R.drawable.sweetbox
        );

        // Cài đặt Adapter
        ImageAdapter adapter = new ImageAdapter(this, imageList);
        recyclerViewDichVu.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewDichVu.setAdapter(adapter);
    }

    private void khoiTaoPhimSapChieu() {
        phimSapChieu();
        moviesAdapter = new MoviesAdapter(this, R.layout.item_phimsapchieu, movieListPhimSapChieu);
        layoutManagerPhimSapChieu = new ScaleCenterLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPhimSapChieu.setLayoutManager(layoutManagerPhimSapChieu);
        recyclerViewPhimSapChieu.setAdapter(moviesAdapter);
    }

    private void khoiTaoPhim() {
        filmAdapter = new FilmAdapter(this, R.layout.item_phim, new ArrayList<>());
        layoutManagerPhimDangChieu = new ScaleCenterLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPhimDangChieu.setLayoutManager(layoutManagerPhimDangChieu);
        recyclerViewPhimDangChieu.setAdapter(filmAdapter);
        layTatCaPhim();
    }

    private void khoiTaoPhimDangChieu() {
        phimDangChieu();
        moviesAdapter = new MoviesAdapter(this, R.layout.item_phim, movieListPhimDangChieu);
        layoutManagerPhimDangChieu = new ScaleCenterLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPhimDangChieu.setLayoutManager(layoutManagerPhimDangChieu);
        recyclerViewPhimDangChieu.setAdapter(moviesAdapter);
    }

    private void phimDangChieu() {
        String[] tenPhimDangChieu = getResources().getStringArray(R.array.dsPhimDangChieu_ten);
        String[] theloaiPhimDangChieu = getResources().getStringArray(R.array.dsPhimDangChieu_theLoai);
        String length = "2h15m";
        for (int i = 0; i < tenPhimDangChieu.length; i++) {
            movieListPhimDangChieu.add(new Movies(tenPhimDangChieu[i], length, theloaiPhimDangChieu[i], dsPhimDangChieu[i]));
        }
    }

    private void phimSapChieu() {
        String[] tenPhimSapChieu = getResources().getStringArray(R.array.dsPhimSapChieu_ten);
        String[] theloaiPhimSapChieu = getResources().getStringArray(R.array.dsPhimSapChieu_ten);
        String release = "20.11.3000";
        for (int i = 0; i < tenPhimSapChieu.length; i++) {
            movieListPhimSapChieu.add(new Movies(tenPhimSapChieu[i], release, theloaiPhimSapChieu[i], dsPhimSapChieu[i]));
        }
    }


    private void xulyMauHintTrongSeachView() {
        if (searchEditText != null) {
            searchEditText.setTextColor(Color.WHITE); // Đặt màu chữ
            searchEditText.setHintTextColor(Color.GRAY); // Đặt màu gợi ý
        } else {
            Toast.makeText(this, "Error SearchView", Toast.LENGTH_LONG).show();
        }
    }

    private void xulyHieuUngPosterPhimSapChieu() {
        recyclerViewPhimSapChieu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Cập nhật hiệu ứng thu phóng
                if (layoutManagerPhimSapChieu != null) {
                    layoutManagerPhimSapChieu.scaleChildren(); // Gọi phương thức scaleChildren() từ ScaleCenterLinearLayoutManager
                }

                // Vòng lặp (Khi cuộn đến cuối, đưa về đầu)
                int visibleItemCount = layoutManagerPhimSapChieu.getChildCount();
                int totalItemCount = layoutManagerPhimSapChieu.getItemCount();
                int firstVisibleItemPosition = layoutManagerPhimSapChieu.findFirstVisibleItemPosition();

                if (visibleItemCount > 0 && firstVisibleItemPosition == totalItemCount - 1) {
                    // Nếu cuộn đến cuối, đưa về đầu danh sách
                    recyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
                }
            }
        });
    }

    private void xulyHieuUngPosterPhimDangChieu() {
        recyclerViewPhimDangChieu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Cập nhật hiệu ứng thu phóng
                if (layoutManagerPhimDangChieu != null) {
                    layoutManagerPhimDangChieu.scaleChildren(); // Gọi phương thức scaleChildren() từ ScaleCenterLinearLayoutManager
                }

                // Vòng lặp (Khi cuộn đến cuối, đưa về đầu)
                int visibleItemCount = layoutManagerPhimDangChieu.getChildCount();
                int totalItemCount = layoutManagerPhimDangChieu.getItemCount();
                int firstVisibleItemPosition = layoutManagerPhimDangChieu.findFirstVisibleItemPosition();

                if (visibleItemCount > 0 && firstVisibleItemPosition == totalItemCount - 1) {
                    // Nếu cuộn đến cuối, đưa về đầu danh sách
                    recyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
                }
            }
        });
    }

    private void xulyCanChinhPosterPhimSapChieu() {
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewPhimSapChieu);
    }

    private void xulyCanChinhPosterPhimDangChieu() {
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewPhimDangChieu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        layTatCaPhim();
        getRole();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}



