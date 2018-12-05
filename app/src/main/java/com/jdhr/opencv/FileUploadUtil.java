package com.jdhr.opencv;

import com.jdhr.http.RetrofitManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/10/23.
 */

public class FileUploadUtil {

    /**
     * 上传人脸
     *
     * @param file
     * @return
     */
    public static Call<FaceFResponse> uploadFile(File file) {
        final RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        HttpService service = RetrofitManager.getHttpService(HttpService.class);
        Call<FaceFResponse> fileCall = service.uploadFile(filePart);
        return fileCall;
    }

    public class FileType {
        /*
        *
        0-药品照片和病历卡，
        1-老师签名，
        2-家长签名，
        3-家长录音,
        4-孩子头像
        *
        */
        public static final int PIC_MEDICINE = 0;
        public static final int PIC_SIGNATURE_TEACHER = 1;
        public static final int PIC_SIGNATURE_PARENT = 2;
        public static final int AUDIO_RECORD = 3;
        public static final int PIC_AVATER = 4;
    }
}
