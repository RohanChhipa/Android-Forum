package com.forum.retrofit.ui.picsum;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PicsumService {

    //https://picsum.photos/{width}/{height}
    //https://picsum.photos/1920/1080 -> returns 302 redirect
    @GET("{width}/{height}")
    Single<ResponseBody> getImage(@Path("width") int width, @Path("height") int height);
}
