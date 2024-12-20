package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.LoginRequest;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;

public class dangNhap_MainActivity extends AppCompatActivity {
    EditText edt_matKhau, edt_ngaySinh, edt_gioiTinh, edt_email, edt_thanhPho, edt_loginTaiKhoan, edt_loginMatKhau, edt_taiKhoan;
    TextView tv_dangKy;
    CardView dangKyCardView, dangNhapCardView;
    Button DK_btn_quayLai, btn_dangNhap;

    private boolean isPasswordVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_dangnhap_activity);

        addControls();
        addEvents();
    }


    private void addControls() {
        edt_taiKhoan = findViewById(R.id.edt_taiKhoan);

        edt_matKhau = findViewById(R.id.edt_matKhau);
        edt_ngaySinh = findViewById(R.id.edt_ngaySinh);
        edt_gioiTinh = findViewById(R.id.edt_gioiTinh);
        edt_email = findViewById(R.id.edt_email);
        edt_thanhPho = findViewById(R.id.edt_thanhPho);

        tv_dangKy = findViewById(R.id.tv_dangKy);

        dangKyCardView = findViewById(R.id.cardView_dangKy);
        dangNhapCardView = findViewById(R.id.cardView_dangNhap);

        DK_btn_quayLai = findViewById(R.id.DK_btn_quayLai);

        edt_loginTaiKhoan = findViewById(R.id.edt_loginTaiKhoan);
        edt_loginMatKhau = findViewById(R.id.edt_loginMatKhau);
        btn_dangNhap = findViewById(R.id.btn_dangNhap);
    }

    private void addEvents() {
        xulyAnHienMatKhau();
        xulyChuyenSangGDDangKy();
        xulyQuayLayDangNhap();
        xulyHienThiDialogNgaySinh();
        xulyHienThiDialogGioiTinh();
        xulyNhapEmail();

        btn_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edt_loginTaiKhoan.getText().toString();
                String password = edt_loginMatKhau.getText().toString();
                LoginRequest loginRequest = LoginRequest.builder().userName(userName).password(password).build();
                xuLyDangNhap(loginRequest);
            }
        });

    }

    private void xuLyDangNhap(LoginRequest loginRequest) {

        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Void>> call = apiService.login(loginRequest);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse.getCode() == 0) {

                        Intent intent = new Intent(dangNhap_MainActivity.this,trangChu_MainActivity.class);
                        intent.putExtra("username",loginRequest.getUserName());
                        startActivity(intent);

                        Toast.makeText(dangNhap_MainActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(dangNhap_MainActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        if(!errorBody.equals("null")){
                            JSONObject jsonObject = new JSONObject(errorBody);
                            int errorCode = jsonObject.optInt("code",-1);
                            String errorMessage = jsonObject.optString("message","Không có thông báo lỗi");
                            Toast.makeText(dangNhap_MainActivity.this,errorMessage, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(dangNhap_MainActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e("API_ERROR", "Error: ", t); // Để kiểm tra chi tiết lỗi
            }
        });
    }

    private void xulyHienThiDialogGioiTinh() {
        // Biến lưu giá trị giới tính (0 = Nam, 1 = Nữ)
        edt_gioiTinh.setOnClickListener(v -> {
            String[] gioiTinh = {"Nam", "Nữ"};

            // Dùng AlertDialog để hiển thị danh sách giới tính
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Chọn giới tính")
                    .setItems(gioiTinh, (dialog, luaChon) -> {
                        // Lưu và hiển thị giới tính đã chọn
                        switch (luaChon) {
                            case 0: // Nam
                                edt_gioiTinh.setText("Nam");
                                edt_gioiTinh.setTag(0); // Lưu giá trị 0 (Nam)
                                break;
                            case 1: // Nữ
                                edt_gioiTinh.setText("Nữ");
                                edt_gioiTinh.setTag(1); // Lưu giá trị 1 (Nữ)
                                break;

                        }
                    });
            builder.show();
        });
    }

    private void xulyNhapEmail() {
        edt_email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Khi EditText mất focus
                String email = edt_email.getText().toString().trim();
                if (!isValidGmail(email)) {
                    edt_email.setError("Email sai định dạng example@gmail.com");
                }
            }
        });
    }

    // Hàm kiểm tra định dạng email
    private boolean isValidGmail(String email) {
        // Biểu thức chính quy kiểm tra email kết thúc bằng @gmail.com
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(emailPattern);
    }

    private void xulyHienThiDialogNgaySinh() {
        edt_ngaySinh.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        edt_ngaySinh.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });
    }

    private void xulyQuayLayDangNhap() {
        DK_btn_quayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKyCardView.setVisibility(View.GONE);
                dangNhapCardView.setVisibility(View.VISIBLE);
            }
        });
    }


    private void xulyChuyenSangGDDangKy() {
        tv_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangNhapCardView.setVisibility(View.GONE);
                dangKyCardView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void xulyAnHienMatKhau() {
        edt_matKhau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Lấy vị trí drawableEnd (phía bên phải)
                    int drawableRightWidth = edt_matKhau.getCompoundDrawables()[2].getBounds().width();

                    // Kiểm tra nếu vị trí chạm nằm trong drawableEnd
                    if (event.getRawX() >= (edt_matKhau.getRight() - drawableRightWidth)) {
                        // Toggle trạng thái hiển thị mật khẩu
                        togglePasswordVisibility();
                        return true; // Đã xử lý sự kiện, không cần xử lý tiếp
                    }
                }
                return false;
            }
        });
    }

    private void togglePasswordVisibility() {
        if (edt_matKhau.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            // Hiển thị mật khẩu
            edt_matKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_matKhau.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icons8_open_eye_32_, 0);
        } else {
            // Ẩn mật khẩu
            edt_matKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt_matKhau.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icons8_eye_32, 0);
        }
        // Đặt con trỏ về cuối văn bản
        edt_matKhau.setSelection(edt_matKhau.getText().length());
    }
}