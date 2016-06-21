package com.henry.freakcompany.retrofit;

import com.henry.freakcompany.model.GanHuoResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by henry on 2016/6/15.
 */
public interface GankService {
    @GET("api/data/{type}/{count}/{page}")
    Observable<GanHuoResponse> getGanHuo(@Path("type") String type, @Path("count") int count, @Path("page") int page);
}
