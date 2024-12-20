package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.stu.oss_appdatvexemphim.CustomEvent.IDatePicker2;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ScheduleDateTimeResponse;
import vn.edu.stu.oss_appdatvexemphim.R;

public class DatePickerAdapter2 extends RecyclerView.Adapter<DatePickerAdapter2.DateViewHolder> {
    private List<ScheduleDateTimeResponse> scheduleList;
    private int selectedPosition = -1;
    private IDatePicker2 listener;

    public DatePickerAdapter2(List<ScheduleDateTimeResponse> dateList, IDatePicker2 listener) {
        this.scheduleList = dateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ngaythang, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        ScheduleDateTimeResponse schedule = scheduleList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int month = 0;
        int day = 0;
        try {
            Date date = dateFormat.parse(schedule.getScheduleDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nhớ điều chỉnh nếu cần
        } catch (ParseException e) {
            e.printStackTrace();
            day = 0;
            month = 0;
        }


        holder.tvDay.setText(String.valueOf(day));  // Đặt ngày
        holder.tvMonth.setText(String.valueOf(month));  // Đặt tháng

        // Highlight ngày được chọn
        if (selectedPosition == position) {
            holder.lnItem.setBackgroundColor(holder.itemView.getContext().getColor(R.color.Linear_Selected));
            holder.tvMonth.setTextColor(holder.itemView.getContext().getColor(R.color.month_selected));
            holder.Month.setTextColor(holder.itemView.getContext().getColor(R.color.month_selected));
            holder.tvDay.setTextColor(holder.itemView.getContext().getColor(R.color.day_selected));
        } else {
            holder.lnItem.setBackgroundColor(holder.itemView.getContext().getColor(R.color.Linear_UnSelected));
            holder.tvMonth.setTextColor(holder.itemView.getContext().getColor(R.color.month_UnSelected));
            holder.Month.setTextColor(holder.itemView.getContext().getColor(R.color.month_UnSelected));
            holder.tvDay.setTextColor(holder.itemView.getContext().getColor(R.color.day_UnSelected));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onDateClick(schedule);
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }


    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvMonth,Month;
        LinearLayout lnItem;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            Month = itemView.findViewById(R.id.Month);
            lnItem = itemView.findViewById(R.id.ln_item_ngaythang);
        }
    }
}
