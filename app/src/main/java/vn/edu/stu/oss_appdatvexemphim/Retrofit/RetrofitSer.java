package vn.edu.stu.oss_appdatvexemphim.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSer {
    private static Retrofit retrofit;
//    public static final String BASE_URL = "http://192.168.100.109:8080";
     public static final String BASE_URL = "http://192.168.100.193:8080";
//172.20.10.3
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
