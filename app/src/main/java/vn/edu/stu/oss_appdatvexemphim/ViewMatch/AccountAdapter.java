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

import vn.edu.stu.oss_appdatvexemphim.DTO.Response.AccountResponse;
import vn.edu.stu.oss_appdatvexemphim.R;

public class AccountAdapter extends ArrayAdapter<AccountResponse> {
    private List<AccountResponse> accountsList;
    private Activity context;
    private int resource;


    public AccountAdapter(Activity context, int resource, List<AccountResponse> objects) {
        super(context, resource, objects);
        this.accountsList = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView tv_acMa,tv_acTaiKhoan,tv_acMatKhau,tv_acVaiTro;
        tv_acMa =item.findViewById(R.id.item_taiKhoan_ma);
        tv_acTaiKhoan =item.findViewById(R.id.item_taiKhoan_TK);
        tv_acVaiTro =item.findViewById(R.id.item_taiKhoan_vaiTro);

        AccountResponse ac = this.accountsList.get(position);
        tv_acMa.setText(ac.getAccount_id()+"");
        tv_acTaiKhoan.setText(ac.getUserName());
        tv_acVaiTro.setText(ac.getAccountRole());

        return item;
    }
}
