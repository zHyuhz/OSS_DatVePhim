package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ScheduleResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.SeatsResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;

public class chonChoNgoi_MainActivity extends AppCompatActivity implements View.OnClickListener {

    ScheduleResponse scheduleResponse = null;
    private final int[] gheNgoi = {R.id.btn_A1, R.id.btn_A2, R.id.btn_A3, R.id.btn_A4, R.id.btn_A5,
            R.id.btn_B1, R.id.btn_B2, R.id.btn_B3, R.id.btn_B4, R.id.btn_B5,
            R.id.btn_C1, R.id.btn_C2, R.id.btn_C3, R.id.btn_C4, R.id.btn_C5,
            R.id.btn_D1, R.id.btn_D2, R.id.btn_D3, R.id.btn_D4, R.id.btn_D5,
            R.id.btn_E1, R.id.btn_E2, R.id.btn_E3, R.id.btn_E4, R.id.btn_E5};

    boolean[] selected = new boolean[gheNgoi.length];
    boolean isChecked = false;
    Button btn_comfirm;

    ImageView btn_troLai;

    List<String> isSelectedSeatList = null;
    Set<SeatsResponse> sendList;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_chon_cho_ngoi_activity);

        hienThiGhe();
        addControls();
        addEvents();
        Log.d("Delay", "check" + username);

    }

    private void hienThiGhe() {
        scheduleResponse = new ScheduleResponse();
        Intent intent = getIntent();
        scheduleResponse = (ScheduleResponse) intent.getSerializableExtra("scheduleSelected");
        username = intent.getStringExtra("username");
        isSelectedSeatList = new ArrayList<>();
        int roomID = scheduleResponse.getRoom().getRoomId();

        // Gọi API và xử lý danh sách ghế
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<List<SeatsResponse>>> call = apiService.getSeatByMovie(roomID);
        call.enqueue(new Callback<ApiResponse<List<SeatsResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<SeatsResponse>>> call, Response<ApiResponse<List<SeatsResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SeatsResponse> seatsResponseList = response.body().getResult();
                    for (SeatsResponse seat : seatsResponseList) {
                        if (seat.getIsAvailable()) {
                            isSelectedSeatList.add(seat.getSeatRow() + String.valueOf(seat.getSeatNumber()));
                        }
                    }
                    displaySeats();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<SeatsResponse>>> call, Throwable t) {
                Toast.makeText(chonChoNgoi_MainActivity.this, "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addControls() {

        btn_comfirm = findViewById(R.id.btn_comfirm);
        btn_troLai = findViewById(R.id.img_ChoNgoi_troLai);
        sendList= new HashSet<>();

//        for (int id : gheNgoi) {
//            findViewById(id).setOnClickListener(this);
//        }
//        btn_comfirm = findViewById(R.id.btn_comfirm);
//        btn_troLai = findViewById(R.id.img_ChoNgoi_troLai);


    }

    private void displaySeats() {
        for (int id : gheNgoi) {
            Button button = findViewById(id);

            if (!isSelectedSeatList.contains(button.getText().toString())) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.textView_selected));
                button.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
            if (button != null) {
                button.setOnClickListener(this);
            }
        }
    }

    private void addEvents() {
        btn_comfirm.setOnClickListener(v -> {
            List<Integer> selectedSeats = getSelectedSeats();
            if (selectedSeats.isEmpty()) {
                Toast.makeText(chonChoNgoi_MainActivity.this, "Bạn chưa chọn ghế nào!", Toast.LENGTH_SHORT).show();
            } else {
                confirmSelected( selectedSeats);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ArrayList<SeatsResponse> sendListArray = new ArrayList<>(sendList);
                    Intent intent = new Intent(chonChoNgoi_MainActivity.this,thanhToan_MainActivity.class);
                    intent.putExtra("list_seats",sendListArray);
                    intent.putExtra("username",username);
                    intent.putExtra("scheduleResponse",scheduleResponse);

                    startActivity(intent);

                    Log.d("Check3", "Check: " + scheduleResponse.getRoom().getRoomId());
                    Log.d("Check4", "Check: " + sendList);

                }, 1500);
            }
        });
        btn_troLai.setOnClickListener(v -> {
            finish();
        });

    }


    public void confirmSelected(List<Integer> selectedSeats) {

        if (selectedSeats.isEmpty()) {
            Toast.makeText(this, "Chưa chọn ghế!", Toast.LENGTH_SHORT).show();
        } else {
            for (int seat : selectedSeats) {
                Button buttons = findViewById(gheNgoi[seat]);
                String temp = buttons.getText().toString();
                char row = ' ';
                int number = 0;
                for (char ch : temp.toCharArray()) {
                    if (Character.isLetter(ch)) {
                        row = ch;
                    } else if (Character.isDigit(ch)) {
                        number = Character.getNumericValue(ch);
                    }
                }
                getSeat(row, number, scheduleResponse.getRoom().getRoomId());


            }
        }

    }

    private void getSeat(char seatRow, int seatNumber, int roomId) {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<SeatsResponse>> call = apiService.getSearch(seatRow, seatNumber, roomId);
        call.enqueue(new Callback<ApiResponse<SeatsResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<SeatsResponse>> call, Response<ApiResponse<SeatsResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sendList.add(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<SeatsResponse>> call, Throwable t) {

            }
        });
    }

    public List<Integer> getSelectedSeats() {
        List<Integer> selectedSeats = new ArrayList<>();
        for (int i = 0; i < gheNgoi.length; i++) {
            if (selected[i]) {
                selectedSeats.add(i);
            }
        }
        return selectedSeats;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Button button = findViewById(id);

        for (int i = 0; i < gheNgoi.length; i++) {
            if (id == gheNgoi[i]) {
                if (isSelectedSeatList.contains(button.getText().toString().trim())) {
                    selected[i] = !selected[i];
                    if (selected[i]) {
                        button.setBackgroundColor(ContextCompat.getColor(this, R.color.backGround_selected));
                        button.setTextColor(ContextCompat.getColor(this, R.color.textView_selected));
                    } else {
                        button.setBackgroundColor(ContextCompat.getColor(this, R.color.backGround_unSelected));
                        button.setTextColor(ContextCompat.getColor(this, R.color.textView_unSelected));
                    }
                } else {
                    Toast.makeText(this, "Ghế Đã Có Người Đặt", Toast.LENGTH_SHORT).show();
                }
                Log.d("Check7", String.valueOf(i) + " =" + selected[i]);
                //break;
            }
            Log.d("Check5", String.valueOf(i) + " =" + selected[i]);
            Log.d("Check6", "selected: " + gheNgoi.length);
        }

    }

}
