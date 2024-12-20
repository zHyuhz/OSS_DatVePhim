package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.stu.oss_appdatvexemphim.DTO.Response.UserResponse;
import vn.edu.stu.oss_appdatvexemphim.R;

public class UserAdapter extends ArrayAdapter<UserResponse> {
    public List<UserResponse> userList;
    private Activity context;
    private int resource;


    public UserAdapter(@NonNull Activity context, int resource, @NonNull List<UserResponse> objects) {
        super(context, resource, objects);
        this.userList = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView tv_hoTen,tv_sdt,tv_taiKhoan;
        tv_hoTen =item.findViewById(R.id.item_nguoiDung_hoTen);
        tv_sdt =item.findViewById(R.id.item_nguoiDung_sdt);
        tv_taiKhoan =item.findViewById(R.id.item_nguoiDung_taiKhoan);


        UserResponse ac = this.userList.get(position);
        tv_hoTen.setText(ac.getFullName());
        tv_sdt.setText(ac.getPhoneNumber());
        tv_taiKhoan.setText(ac.getAccounts().getUserName());

        return item;
    }
}
