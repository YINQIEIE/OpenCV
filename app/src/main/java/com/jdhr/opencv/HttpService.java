package com.jdhr.opencv;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by admin on 2017/9/30.
 */

public interface HttpService {

    /**
     * 上传人脸
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("face/api/face/recognize")
    Call<FaceFResponse> uploadFile(@Part MultipartBody.Part file);

}
