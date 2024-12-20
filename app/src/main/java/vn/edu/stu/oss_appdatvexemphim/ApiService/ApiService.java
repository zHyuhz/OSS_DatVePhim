package vn.edu.stu.oss_appdatvexemphim.ApiService;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.AccountUpdate;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.BookingRequest;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.LoginRequest;
import vn.edu.stu.oss_appdatvexemphim.DTO.Request.UserRequest;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.AccountResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ApiResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.BookingResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.MovieResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ScheduleDateTimeResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.ScheduleResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.SeatsResponse;
import vn.edu.stu.oss_appdatvexemphim.DTO.Response.UserResponse;

public interface ApiService {
    //    @POST("/accounts/register")
//    Call<ApiResponse<AccountResponse>> register(@Body RegisterAccountRequest registerRequest);
    @GET("/movies/{id}")
    // Thay {id} bằng ID phim
    Call<ApiResponse<MovieResponse>> getMovie(@Path("id") int movieId);

    @GET("/movies")
        // Thay {id} bằng ID phim
    Call<ApiResponse<List<MovieResponse>>> getAllMovie();

    @Multipart
    @POST("/movies")
    Call<ApiResponse<MovieResponse>> addMovies(
            @Query("movieName") String movieName,
            @Query("movieDes") String movieDes,
            @Query("movieGenres") String movieGenres,
            @Query("movieRelease") String movieRelease,
            @Query("movieLength") int movieLength,
            @Part MultipartBody.Part moviePoster
    );

    @DELETE("/movies/{id}")
    Call<ApiResponse<String>> deleteMovie(@Path("id") int movieId);

    @Multipart
    @PUT("/movies/{id}")
    Call<ApiResponse<MovieResponse>> updateMovie(
            @Path("id") int id,
            @Query("movieName") String movieName,
            @Query("movieDes") String movieDes,
            @Query("movieGenres") String movieGenres,
            @Query("movieRelease") String movieRelease,
            @Query("movieLength") int movieLength,
            @Part MultipartBody.Part moviePoster
    );

    @GET("/users")
    Call<ApiResponse<List<UserResponse>>> getAllUsers();

    @POST("/users")
    Call<ApiResponse<List<UserResponse>>> addUsers();

    @PUT("/users/{id}")
    Call<ApiResponse<UserResponse>> updateUsers(@Path("id") int id,
                                                @Body UserRequest userRequest);

    @DELETE("/users/{id}")
    Call<ApiResponse<String>> deleteUser(@Path("id") int user_id);

    @POST("/auth/login")
    Call<ApiResponse<Void>> login(@Body LoginRequest loginRequest);

    @GET("/accounts/{username}")
    Call<ApiResponse<AccountResponse>> findAccountByUsername(@Path("username") String username);


    @GET("/accounts")
    Call<ApiResponse<List<AccountResponse>>> getAllAccount();

    @DELETE("/accounts/{userName}")
    Call<ApiResponse<String>> deleteAccount(@Path("userName") String userName);

    @PUT("/accounts/{userName}")
    Call<ApiResponse<AccountResponse>> updateAccount(@Path("userName") String userName, @Body AccountUpdate accountUpdate);


    @GET("/schedules/{movieID}")
    Call<ApiResponse<List<ScheduleDateTimeResponse>>> findScheduleDateTimeResponse(@Path("movieID") int movieID);

    @GET("/schedules/search")
    Call<ApiResponse<ScheduleResponse>> findSchedules(
            @Query("scheduleDate") String scheduleDate,  // Chuyển LocalDate thành String
            @Query("scheduleStart") String scheduleStart, // Chuyển LocalTime thành String
            @Query("movieId") int movieId
    );

    @GET("/seats")
    Call<ApiResponse<List<SeatsResponse>>> getAllSeats();
    @GET("/seats/{movieID}")
    Call<ApiResponse<List<SeatsResponse>>> getSeatByMovie(@Path("movieID") int movieID);

    @GET("/seats/search")
    Call<ApiResponse<SeatsResponse>> getSearch(@Query("seatRow") char seatRow, @Query("seatNumber") int seatNumber, @Query("roomId") int roomId);

    @POST("/bookings")
    Call<ApiResponse<BookingResponse>> addBooking(@Body BookingRequest bookingRequest);

    @PUT("/seats/choose")
    Call<ApiResponse<SeatsResponse>> updateAvailable(@Query("seatRow") char seatRow, @Query("seatNumber") int seatNumber, @Query("roomId") int roomId);

    @GET("/accounts/{userName}")
    Call<ApiResponse<AccountResponse>> getUserIdByUserName(@Path("userName") String userName);

    @GET("/bookings")
    Call<ApiResponse<List<BookingResponse>>> getAllBooking();
}
