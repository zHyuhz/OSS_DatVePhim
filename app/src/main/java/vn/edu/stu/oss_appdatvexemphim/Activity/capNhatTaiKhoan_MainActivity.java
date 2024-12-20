package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.stu.oss_appdatvexemphim.ApiService.ApiService;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.AccountUpdate;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.AccountResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;

public class capNhatTaiKhoan_MainActivity extends AppCompatActivity {
    ImageView img_btn_troLai;
    EditText edt_emails, frmChiTietTK_edt_username, frmChiTietTK_edt_matKhau;
    RadioButton rdo_hoatDong, rdo_khoa;
    Button frmChiTietTK_btn_Luu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.frm_capnhattaikhoan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addControls() {
        img_btn_troLai = findViewById(R.id.img_chiTietTaiKhoan_troLai);
        edt_emails = findViewById(R.id.frmChiTietTK_edt_emails);
        rdo_hoatDong = findViewById(R.id.frmChiTietTK_rdo_hoatDong);
        frmChiTietTK_edt_matKhau = findViewById(R.id.frmChiTietTK_edt_matKhau);
        rdo_khoa = findViewById(R.id.frmChiTietTK_rdo_khoa);
        frmChiTietTK_edt_username = findViewById(R.id.frmChiTietTK_edt_username);
        frmChiTietTK_btn_Luu = findViewById(R.id.frmChiTietTK_btn_Luu);
        getResult();
    }

    private void addEvents() {
        img_btn_troLai.setOnClickListener(v -> {
            Intent intent = new Intent(this, dsTaiKhoan_MainActivity.class);
            startActivity(intent);
        });
        xulyNhapEmail();
        frmChiTietTK_btn_Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountUpdate accountUpdate = new AccountUpdate();
                accountUpdate.setEmail(edt_emails.getText().toString());
                accountUpdate.setPassword(frmChiTietTK_edt_matKhau.getText().toString());

                int checked = rdo_hoatDong.isChecked() ? 1 : 0;

                accountUpdate.setStatus(checked);
                updateAccount(frmChiTietTK_edt_username.getText().toString(),accountUpdate);

            }
        });
    }

    private void updateAccount(String userName, AccountUpdate accountUpdate) {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<AccountResponse>> call = apiService.updateAccount(userName, accountUpdate);
        Log.d("TAGGG",userName + "  " + accountUpdate);
        call.enqueue(new Callback<ApiResponse<AccountResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AccountResponse>> call, Response<ApiResponse<AccountResponse>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(capNhatTaiKhoan_MainActivity.this,"Cập nhật thành công! ", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<AccountResponse>> call, Throwable t) {
                Toast.makeText(capNhatTaiKhoan_MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void xulyNhapEmail() {
        edt_emails.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String email = edt_emails.getText().toString().trim();
                if (!isValidGmail(email)) {
                    edt_emails.setError("Email sai định dạng example@gmail.com");
                }
            }
        });
    }

    private boolean isValidGmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(emailPattern);
    }

    private void getResult() {
        Intent intent = getIntent();
        AccountResponse accountResponse = (AccountResponse) intent.getSerializableExtra("accountResponse");
        if (accountResponse != null) {
            edt_emails.setText(accountResponse.getEmail());
            if (accountResponse.getStatus() == 1) {
                rdo_hoatDong.setChecked(true);
            } else {
                rdo_khoa.setChecked(true);
            }
            frmChiTietTK_edt_username.setText(accountResponse.getUserName());
            frmChiTietTK_edt_username.setEnabled(false);
        }
    }
}