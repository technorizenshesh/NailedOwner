package com.example.NailedOwner.Utills;

import com.example.NailedOwner.AddServicesTwo.GetServicesModel;
import com.example.NailedOwner.AddTiming.DaysModel;
import com.example.NailedOwner.AllSalonPhoto.GetImageSaloon;
import com.example.NailedOwner.AllServices.UpdateServices.ServicesModelUpdate;
import com.example.NailedOwner.SignUpScreen.SignUpModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    String USER_provider_signup = "provider_signup";
    String USER_add_service = "add_service";
    String update_service = "update_service";
    String USER_SaloonImageUpload = "add_salon";
    String USER_get_service = "get_service";
    String get_services_details = "get_services_details";
    String get_salon_image = "get_salon_image";
    String get_certificate = "get_certificate";
    String delete_service = "delete_service";
    String change_password = "change_password";
    String get_day = "get_day";
    String USER_add_certificate = "add_certificate";


    @Multipart
    @POST(USER_add_service)
    Call<ResponseBody>USER_add_service(
            @Part("user_id") RequestBody id,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("service_duration") RequestBody service_duration,
            @Part("category_id") RequestBody category_id,
            @Part("customization") RequestBody customization,
            @Part("description") RequestBody description,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part imgFile_one,
            @Part MultipartBody.Part imgFile_two,
            @Part MultipartBody.Part imgFile_three,
            @Part MultipartBody.Part imgFile_four,
            @Part MultipartBody.Part imgFile_five,
            @Part  MultipartBody.Part imgFile_sex,
            @Part MultipartBody.Part imgFile_seven
    );

   @Multipart
    @POST(update_service)
    Call<ResponseBody>update_service(
            @Part("service_id") RequestBody service_id,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("service_duration") RequestBody service_duration,
            @Part("category_id") RequestBody category_id,
            @Part("customization") RequestBody customization,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part imgFile_one,
            @Part MultipartBody.Part imgFile_two,
            @Part MultipartBody.Part imgFile_three,
            @Part MultipartBody.Part imgFile_four,
            @Part MultipartBody.Part imgFile_five,
            @Part  MultipartBody.Part imgFile_sex,
            @Part MultipartBody.Part imgFile_seven
    );

    @Multipart
    @POST(USER_SaloonImageUpload)
    Call<ResponseBody>USER_SaloonImageUpload(
            @Part("user_id") RequestBody id,
            @Part MultipartBody.Part imgFile_one,
            @Part MultipartBody.Part imgFile_two,
            @Part MultipartBody.Part imgFile_three,
            @Part MultipartBody.Part imgFile_four,
            @Part MultipartBody.Part imgFile_five,
            @Part  MultipartBody.Part imgFile_sex,
            @Part MultipartBody.Part imgFile_seven
    );

    @Multipart
    @POST(USER_add_certificate)
    Call<ResponseBody>USER_addcertificate(
            @Part("user_id") RequestBody id,
            @Part("category_id") RequestBody category_id,
            @Part MultipartBody.Part imgFile_one
    );


    @FormUrlEncoded
    @POST(USER_get_service)
    Call<GetServicesModel>USER_get_service(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_services_details)
    Call<ServicesModelUpdate>get_services_details(
            @Field("service_id") String service_id
    );

    @FormUrlEncoded
    @POST(get_salon_image)
    Call<GetImageSaloon>get_salon_image(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_certificate)
    Call<GetImageSaloon>get_certificate(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(delete_service)
    Call<ResponseBody>delete_service(
            @Field("service_id") String service_id
    );

    @FormUrlEncoded
    @POST(change_password)
    Call<ResponseBody>change_password(
            @Field("user_id") String user_id,
            @Field("password") String password
    );


    @POST(get_day)
    Call<DaysModel> get_day();


    @Multipart
    @POST(USER_provider_signup)
    Call<SignUpModel> USER_signup1(
            @Part("user_name") RequestBody user_name,
            @Part("address") RequestBody address,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("mobile") RequestBody mobile,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part("city") RequestBody city,
            @Part("zip_code") RequestBody zip_code,
            @Part("dob") RequestBody dob,
            @Part("gender") RequestBody gender,
            @Part("register_id") RequestBody register_id,
            @Part MultipartBody.Part imgFile_one,
            @Part MultipartBody.Part imgFile_two,
            @Part MultipartBody.Part imgFile_three
    );

}
