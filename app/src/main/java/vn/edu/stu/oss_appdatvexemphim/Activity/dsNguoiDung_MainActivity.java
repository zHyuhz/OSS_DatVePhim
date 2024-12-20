package vn.edu.stu.oss_appdatvexemphim.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.UserResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;
import vn.edu.stu.oss_appdatvexemphim.ViewMatch.UserAdapter;

public class dsNguoiDung_MainActivity extends AppCompatActivity {
    ImageView img_btn_troLai;
    FloatingActionButton flBtn_themMoi;
    ListView listView;
    List<UserResponse> userList;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.frm_danhsach_nguoidung);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
        initUserResponse();
    }

    private void addControls() {
        img_btn_troLai = findViewById(R.id.img_dsNguoiDung_troLai);
        flBtn_themMoi = findViewById(R.id.frmDanhSachNguoiDung_btn_themMoi);

        listView = findViewById(R.id.lv_NguoiDung);
        userList = new ArrayList<>();

        initUserResponse();
    }

    private void initUserResponse() {
        getAllUserFromBackend();
        adapter = new UserAdapter(this, R.layout.item_nguoidung, userList);
        listView.setAdapter(adapter);

    }

    private void addEvents() {
        img_btn_troLai.setOnClickListener(v -> {
            Intent intent = new Intent(this, trangChu_MainActivity.class);
            startActivity(intent);
        });
        flBtn_themMoi.setOnClickListener(v -> {
            Intent intent = new Intent(this, capNhatNguoiDung_MainActivity.class);
            startActivity(intent);
        });
        getAllUserFromBackend();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserResponse userResponse = userList.get(position);
                Intent intent = new Intent(dsNguoiDung_MainActivity.this,capNhatNguoiDung_MainActivity.class);
                intent.putExtra("USER",userResponse);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(dsNguoiDung_MainActivity.this);
                builder.setTitle("Xóa")
                        .setMessage("Bạn có muốn xóa người dùng này không?")
                        .setCancelable(false)
                        .setPositiveButton("Cóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserResponse us = userList.get(position);
                                deleteUser1(us.getUser_id());
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(dsNguoiDung_MainActivity.this, String.valueOf(which), Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

    }

    private void deleteUser(int id){
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<String>> call = apiService.deleteUser(id);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body().getCode() == 0) {
                    // Thành công: Xóa phần tử khỏi danh sách
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUser_id() == id) {
                            userList.remove(i);
                            break;
                        }
                    }

                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(dsNguoiDung_MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.e("API Error", "Lỗi: " + t.getMessage());
                Toast.makeText(dsNguoiDung_MainActivity.this, "Xay ra loi ", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void deleteUser1(int id) {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);

        // Gọi API xóa người dùng với id
        Call<ApiResponse<String>> call = apiService.deleteUser(id);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<String>> call, @NonNull Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Kiểm tra mã trả về từ server
                    if (response.body().getCode() == 0) {
                        Toast.makeText(dsNguoiDung_MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();

                        // Xóa người dùng khỏi danh sách trong ứng dụng
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).getUser_id() == id) {
                                userList.remove(i);
                                break;
                            }
                        }

                        // Cập nhật lại dữ liệu trong adapter
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(dsNguoiDung_MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(dsNguoiDung_MainActivity.this, "BODY NULL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.e("DeleteUser", "API call failed: " + t.getMessage());
                Toast.makeText(dsNguoiDung_MainActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllUserFromBackend() {
        ApiService apiService = RetrofitSer.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<List<UserResponse>>> call = apiService.getAllUsers();
        call.enqueue(new Callback<ApiResponse<List<UserResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<UserResponse>>> call, Response<ApiResponse<List<UserResponse>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ApiResponse<List<UserResponse>> apiResponse = response.body();
                        if (apiResponse.getCode() == 0) {
                            adapter.userList.clear();
                            userList.addAll(apiResponse.getResult());
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(dsNguoiDung_MainActivity.this, "Lỗi tại dsNguoiDung_Activity", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(dsNguoiDung_MainActivity.this, "Body null", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Gson gson = new Gson();
                        JsonObject errorJson = gson.fromJson(errorBody, JsonObject.class);

                        // Lấy giá trị của "message"
                        String errorMessage = errorJson.has("message") ? errorJson.get("message").getAsString() : "Unknown error";
                        // Bạn có thể xử lý chuỗi lỗi ở đây và hiển thị thông báo lỗi
                        Toast.makeText(dsNguoiDung_MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(dsNguoiDung_MainActivity.this, "Error parsing error body", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<UserResponse>>> call, Throwable t) {
                if (t instanceof IOException) {
                    // Lỗi mạng, không thể kết nối với server
                    Log.e("API Error", "Lỗi mạng: " + t.getMessage());
                    Toast.makeText(dsNguoiDung_MainActivity.this, "Không thể kết nối với server", Toast.LENGTH_SHORT).show();
                } else {
                    // Lỗi khác (ví dụ: lỗi khi phân tích cú pháp JSON)
                    Log.e("API Error", "Lỗi: " + t.getMessage());
                    Toast.makeText(dsNguoiDung_MainActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void xoaTaiKhoan() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(dsNguoiDung_MainActivity.this);
                builder.setTitle("Xoá tài khoản");
                builder.setMessage("Bạn có thực sự muốn xóa tài khoản này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false); // bắt buộc phải chọn mới tắt dialog >< true: nhấn ra ngoài dialog sẽ tắt
                alertDialog.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserResponse();
    }
}