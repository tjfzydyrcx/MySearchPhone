package com.example.administrator.mysearchphone.Api;

import com.example.administrator.mysearchphone.MyModel.Phone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public interface Apiservice {

    //http://www.jianshu.com/p/7687365aa946
    /**
    * 【Android】Retrofit网络请求参数注解，@Path、@Query、@QueryMap...
    * */
    @GET("query")
    Call<Phone> logresult(@Query("appkey") String appkey,  @Query("shouji") String shouji);


}
