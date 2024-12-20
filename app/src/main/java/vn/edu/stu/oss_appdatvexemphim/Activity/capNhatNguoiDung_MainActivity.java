package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.UserRequest;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.UserResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;

public class capNhatNguoiDung_MainActivity extends AppCompatActivity {
    ImageView img_troLai;
    EditText edt_hoTen, edt_thanhPho, edt_sdt, edt_ma, edt_taiKhoan;
    TextView tv_ngaySinh;
    RadioButton rdo_nam, rdo_nu;
    ImageButton imgBtn_ngaySinh;
    Button btn_them, btn_sua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_capnhatnguoidung);

        addControls();
        addEvents();
    }

    private void addControls() {
        img_troLai = findViewById(R.id.img_capNhatTaiKhoan_troLai);

        imgBtn_ngaySinh = findViewById(R.id.frmCapNhatNguoiDung_btn_ngaySinh);

        edt_ma = findViewById(R.id.frmCapNhatNguoiDung_edt_ma);
        edt_hoTen = findViewById(R.id.frmCapNhatNguoiDung_edt_hoTen);
        edt_thanhPho = findViewById(R.id.frmCapNhatNguoiDung_edt_thanhPho);
        edt_sdt = findViewById(R.id.frmCapNhatNguoiDung_edt_sdt);
        edt_taiKhoan = findViewById(R.id.frmCapNhatNguoiDung_edt_taiKhoan);

        tv_ngaySinh = findViewById(R.id.frmCapNhatNguoiDung_tv_ngaySinh);

        rdo_nam = findViewById(R.id.frmCapNhatNguoiDung_rdo_nam);
        rdo_nu = findViewById(R.id.frmCapNhatNguoiDung_rdo_nu);

        btn_them = findViewById(R.id.btn_them);
        btn_sua = findViewById(R.id.btn_sua);


    }

    private void addEvents() {
        img_troLai.setOnClickListener(v -> {
            finish();
        });
        getDataFromIntent();
        imgBtn_ngaySinh.setOnClickListener(v -> showDatePickerDialog());
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRequest userRequest = new UserRequest();

                int checked = rdo_nam.isChecked() ? 1 : 0;

                int id = Integer.parseInt(edt_ma.getText().toString());
                userRequest.setFullName(edt_hoTen.getText().toString());
                userRequest.setBirthday(tv_ngaySinh.getText().toString());
                userRequest.setGender(checked);
                userRequest.setCity(edt_thanhPho.getText().toString());
                userRequest.setPhoneNumber(edt_sdt.getText().toString());

                updateUsers(id, userRequest);
            }
        });
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        final Calendar calendar = Calendar.getInstance();

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    // Xử lý khi chọn ngày
                    calendar.set(year, month, dayOfMonth);

                    // Định dạng ngày thành dd/MM/yyyy
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = dateFormat.format(calendar.getTime());

                    // Set ngày đã chọn lên TextView
//                    tv_ngaySinh.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }


    private void updateUsers(int id, UserRequest userRequest) {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<UserResponse>> call = apiService.updateUsers(id, userRequest);
        call.enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == 0) {
                        Log.d("LOGG ",userRequest+"");
                        Toast.makeText(capNhatNguoiDung_MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(capNhatNguoiDung_MainActivity.this, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(capNhatNguoiDung_MainActivity.this, "BODY NULL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable t) {

            }
        });
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USER")) {
            UserResponse userResponse = (UserResponse) intent.getSerializableExtra("USER");
            if (userResponse != null) {
                btn_them.setVisibility(View.GONE);
                edt_hoTen.setText(userResponse.getUser_id() + "");
                edt_ma.setText(userResponse.getFullName());
                tv_ngaySinh.setText(userResponse.getBirthday());
                int gender = userResponse.getGender();

                if (gender == 1){
                    rdo_nam.setChecked(true);
                    rdo_nu.setChecked(false);
                }

                else if (gender == 0) {
                    rdo_nu.setChecked(true);
                    rdo_nam.setChecked(false);
                } else {
                    Toast.makeText(this, gender, Toast.LENGTH_LONG).show();
                }
                edt_thanhPho.setText(userResponse.getCity());
                edt_sdt.setText(userResponse.getPhoneNumber());
                edt_taiKhoan.setText(userResponse.getAccounts().getUserName());
            }
        }
    }
}