package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.BookingResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.SeatsResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.BookingAdapter;

public class dsVe_MainActivity extends AppCompatActivity {
    List<BookingResponse> bookingList;
    ListView listView;
    BookingAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.frm_danhsach_ve);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addControls() {
        listView = findViewById(R.id.lv_ve);
        bookingList = new ArrayList<>();
        callAPIBooking();

    }

    private void setAdapter() {
        adapter = new BookingAdapter(this, R.layout.item_ve, bookingList);
        listView.setAdapter(adapter);
    }

    private void callAPIBooking() {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<List<BookingResponse>>> call = apiService.getAllBooking();
        call.enqueue(new Callback<ApiResponse<List<BookingResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BookingResponse>>> call, Response<ApiResponse<List<BookingResponse>>> response) {
                if (response.isSuccessful() && response.body().getCode() == 0) {
                    bookingList.clear();
                    bookingList.addAll(response.body().getResult());
                    setAdapter();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse<List<BookingResponse>>> call, Throwable t) {

            }
        });
    }


    private void addEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookingResponse bookingResponse = bookingList.get(position);
                Intent intent = new Intent(dsVe_MainActivity.this, chiTietVe_MainActivity.class);
                ArrayList<SeatsResponse> getArraySeat = bookingResponse.getSeatsBooking();
                intent.putExtra("bookingResponse", bookingResponse);
                intent.putExtra("getArraySeat", getArraySeat);
                startActivity(intent);

            }
        });
    }
}