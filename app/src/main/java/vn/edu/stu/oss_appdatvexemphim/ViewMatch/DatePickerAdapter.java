package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.stu.oss_appdatvexemphim.CustomEvent.IDatePicker;
import vn.edu.stu.oss_appdatvexemphim.R;

public class DatePickerAdapter extends RecyclerView.Adapter<DatePickerAdapter.DateViewHolder> {
    private List<Calendar> dateList;
    private int selectedPosition = -1;
    private IDatePicker listener;

    public DatePickerAdapter(List<Calendar> dateList, IDatePicker listener) {
        this.dateList = dateList;
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
        Calendar date = dateList.get(position);

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());

        holder.tvDay.setText(dayFormat.format(date.getTime()));
        holder.tvMonth.setText(monthFormat.format(date.getTime()));

        // Highlight ngày được chọn
        if (selectedPosition == position) {
            holder.lnItem.setBackgroundColor(holder.itemView.getContext().getColor(R.color.Linear_Selected));
            holder.tvMonth.setTextColor(holder.itemView.getContext().getColor(R.color.month_selected));
            holder.tvDay.setTextColor(holder.itemView.getContext().getColor(R.color.day_selected));
        } else {
            holder.lnItem.setBackgroundColor(holder.itemView.getContext().getColor(R.color.Linear_UnSelected));
            holder.tvMonth.setTextColor(holder.itemView.getContext().getColor(R.color.month_UnSelected));
            holder.tvDay.setTextColor(holder.itemView.getContext().getColor(R.color.day_UnSelected));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onDateClick(date);
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


    public static class DateViewHolder extends RecyclerView.ViewHolder{
        TextView tvDay, tvMonth;
        LinearLayout lnItem;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            lnItem = itemView.findViewById(R.id.ln_item_ngaythang);
        }
    }
}
