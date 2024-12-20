package vn.edu.stu.oss_appdatvexemphim.DTO.Response;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatsResponse implements Parcelable {
    private int seat_id;

    private String seatType;

    private String seatRow;

    private int seatNumber;

    private Boolean isAvailable; // Giá trị mặc định ở mức ứng dụng

    protected SeatsResponse(Parcel in) {
        seat_id = in.readInt();
        seatType = in.readString();
        seatRow = in.readString();
        seatNumber = in.readInt();
        byte tmpIsAvailable = in.readByte();
        isAvailable = tmpIsAvailable == 0 ? null : tmpIsAvailable == 1;
    }

    public static final Creator<SeatsResponse> CREATOR = new Creator<SeatsResponse>() {
        @Override
        public SeatsResponse createFromParcel(Parcel in) {
            return new SeatsResponse(in);
        }

        @Override
        public SeatsResponse[] newArray(int size) {
            return new SeatsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    public SeatsResponse() {
    }
    public SeatsResponse(int seat_id, String seatType, String seatRow, int seatNumber, Boolean isAvailable) {
        this.seat_id = seat_id;
        this.seatType = seatType;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
    }
    @SuppressLint("NewApi")
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(seat_id);
        dest.writeString(seatType);
        dest.writeString(seatRow);
        dest.writeInt(seatNumber);
        dest.writeBoolean(isAvailable);

    }
}
