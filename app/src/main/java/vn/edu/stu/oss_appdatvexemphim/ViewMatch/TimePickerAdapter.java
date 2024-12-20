package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.stu.oss_appdatvexemphim.CustomEvent.ITimePicker;
import vn.edu.stu.oss_appdatvexemphim.R;

public class TimePickerAdapter extends RecyclerView.Adapter<TimePickerAdapter.TimeViewHolder> {
    private List<String> timeList;
    private ITimePicker listener;
    private int selectedPosition = -1;

    public TimePickerAdapter(List<String> timeList,ITimePicker listener) {
        this.timeList = timeList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        String time = timeList.get(position);

        String[] timeParts = time.split(":");
        String hour = timeParts[0]; // Lấy giờ
        String minute = timeParts[1]; // Lấy phút

        holder.tvHour.setText(hour); // Hiển thị giờ
        holder.tvMinute.setText(minute); // Hiển thị phút



        // Highlight ngày được chọn
        if (selectedPosition == position) {
            holder.lnItem.setBackgroundColor(holder.itemView.getContext().getColor(R.color.Linear_Selected));
            holder.tvHour.setTextColor(holder.itemView.getContext().getColor(R.color.month_selected));
            holder.tvMinute.setTextColor(holder.itemView.getContext().getColor(R.color.month_selected));
        } else {
            holder.lnItem.setBackgroundColor(holder.itemView.getContext().getColor(R.color.Linear_UnSelected));
            holder.tvHour.setTextColor(holder.itemView.getContext().getColor(R.color.month_UnSelected));
            holder.tvMinute.setTextColor(holder.itemView.getContext().getColor(R.color.month_UnSelected));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onDateClick(time);
        });
    }


    @Override
    public int getItemCount() {
        return timeList.size();
    }


    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvHour, tvMinute;
        LinearLayout lnItem;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvMinute = itemView.findViewById(R.id.tvMinute);
            lnItem = itemView.findViewById(R.id.ln_time);
        }

    }



}
