package com.twocity.apps.latte.data.api;

import com.twocity.apps.latte.data.api.model.Statueses;
import com.twocity.apps.latte.data.api.model.Status;
import com.twocity.apps.latte.utils.Image;

import java.util.Map;

import javax.inject.Named;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.QueryMap;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * Created by twocity on 14-2-12.
 */
public interface WeiboService {

    @GET("/statuses/user_timeline.json")
    public Statueses userTimeLine(@QueryMap Map<String, String> options);

    @GET("/statuses/home_timeline.json")
    public Statueses homeTimeLine(@QueryMap Map<String, String> options);

    @GET("/statuses/home_timeline.json")
    public Observable<Statueses> homeTimeLineRx(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("/statuses/update.json")
    public Observable<Status> postStatus(@Field("status") String status);

    @Multipart
    @POST("/statuses/upload.json")
    public Observable<Status> postStatusWithPic(@Part("status") TypedString status,
            @Part("pic") Image image);
}
