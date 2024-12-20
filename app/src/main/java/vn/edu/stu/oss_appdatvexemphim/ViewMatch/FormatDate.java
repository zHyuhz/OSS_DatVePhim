package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    public static String formatDate(String inputDate) {
        try {
            // Định dạng của chuỗi đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            // Định dạng cần đầu ra
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Chuyển đổi chuỗi sang Date
            Date date = inputFormat.parse(inputDate);
            // Trả về chuỗi định dạng mới
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Xử lý nếu có lỗi
        }
    }
    public static String fomatDateSQL(String inputDate) {
        try {
            // Định dạng của chuỗi đầu vàooutputFormat
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            // Định dạng cần đầu ra
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Chuyển đổi chuỗi sang Date
            Date date = inputFormat.parse(inputDate);
            // Trả về chuỗi định dạng mới
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Xử lý nếu có lỗi
        }
    }
    public static String fomatDatePay(String inputDate) {
        try {
            // Định dạng của chuỗi đầu vàooutputFormat
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Định dạng cần đầu ra
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            // Chuyển đổi chuỗi sang Date
            Date date = inputFormat.parse(inputDate);
            // Trả về chuỗi định dạng mới
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Xử lý nếu có lỗi
        }
    }
}
