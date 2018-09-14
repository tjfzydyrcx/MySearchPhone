package com.example.mycalendarexaple.Api;

import com.example.mycalendarexaple.Model.Fuli;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public interface Apiservice {

    //http://www.jianshu.com/p/7687365aa946

    /**
     * 【Android】Retrofit网络请求参数注解，@Path、@Query、@QueryMap...
     */
    @GET("%E7%A6%8F%E5%88%A9/{shu}/{page}")
    Call<Fuli> logresult(@Path("shu") int shu, @Path("page") int page);
}
