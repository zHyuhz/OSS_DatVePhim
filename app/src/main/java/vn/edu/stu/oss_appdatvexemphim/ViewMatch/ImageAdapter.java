package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import vn.edu.stu.oss_appdatvexemphim.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private Context context;
    private List<Integer> imageList; // Danh sách hình ảnh

    public ImageAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dichvu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Tính toán các chỉ số hình ảnh cho từng ImageView trong item
        int startIndex = position * 4;
        int endIndex = Math.min(startIndex + 4, imageList.size());

        // Dùng Glide để tải hình ảnh và cắt thành hình tròn
        Glide.with(context)
                .load(imageList.get(startIndex))
                .transform(new CircleCrop()) // Cắt thành hình tròn
                .into(holder.imageView1);

        if (endIndex > startIndex + 1) {
            Glide.with(context)
                    .load(imageList.get(startIndex + 1))
                    .transform(new CircleCrop()) // Cắt thành hình tròn
                    .into(holder.imageView2);
        }

        if (endIndex > startIndex + 2) {
            Glide.with(context)
                    .load(imageList.get(startIndex + 2))
                    .transform(new CircleCrop()) // Cắt thành hình tròn
                    .into(holder.imageView3);
        }

        if (endIndex > startIndex + 3) {
            Glide.with(context)
                    .load(imageList.get(startIndex + 3))
                    .transform(new CircleCrop()) // Cắt thành hình tròn
                    .into(holder.imageView4);
        }
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(imageList.size() / 4.0); // Mỗi item chứa 4 hình
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1, imageView2, imageView3, imageView4;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các ImageView trong layout item_dichvu
            imageView1 = itemView.findViewById(R.id.item_dv_1);
            imageView2 = itemView.findViewById(R.id.item_dv_2);
            imageView3 = itemView.findViewById(R.id.item_dv_3);
            imageView4 = itemView.findViewById(R.id.item_dv_4);
        }
    }
}
