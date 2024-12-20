package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import vn.edu.stu.oss_appdatvexemphim.DTO.Response.BookingResponse;
import vn.edu.stu.oss_appdatvexemphim.R;
import vn.edu.stu.oss_appdatvexemphim.Retrofit.RetrofitSer;

public class BookingAdapter extends ArrayAdapter<BookingResponse> {
    private List<BookingResponse> bookingList;
    private Activity context;
    private int resource;

    public BookingAdapter(@NonNull Activity context, int resource, @NonNull List<BookingResponse> objects) {
        super(context, resource, objects);
        this.bookingList = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView tv_maVe, tv_ngay, tv_gio, tv_gia;
        ImageView img_moviePoster;

        tv_maVe = item.findViewById(R.id.item_ve_maVe);
        tv_ngay = item.findViewById(R.id.item_ve_ngay);
        tv_gio = item.findViewById(R.id.item_ve_gio);
        tv_gia = item.findViewById(R.id.item_ve_gia);
        img_moviePoster = item.findViewById(R.id.img_moviePoster);

        BookingResponse ac = this.bookingList.get(position);
        tv_maVe.setText(ac.getBooking_id() + "");
        tv_ngay.setText(ac.getSchedule().getScheduleDate());
        tv_gio.setText(ac.getSchedule().getScheduleStart());
        tv_gia.setText(ac.getPrice() + "");

        String url = RetrofitSer.BASE_URL + ac.getSchedule().getMovies().getMovie_poster();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.custom_cardview_login) // Hình ảnh hiển thị trong khi tải
                .error(R.drawable.custom_cardview_login)// Hình ảnh hiển thị khi lỗi
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img_moviePoster);

        return item;
    }
}
