package com.strish.android.test.api;

import com.strish.android.test.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MostPopularApi {

    String API_KEY = "5224f85fb17142b083c39ee037f881d8";

    @GET("svc/mostpopular/v2/mostemailed/all-sections/30.json?api-key=" + API_KEY)
    Call<JSONResponse> getMostEmailedArticles();

    @GET("svc/mostpopular/v2/mostshared/all-sections/30.json?api-key=" + API_KEY)
    Call<JSONResponse> getMostSharedArticles();

    @GET("svc/mostpopular/v2/mostviewed/all-sections/30.json?api-key=" + API_KEY)
    Call<JSONResponse> getMostViewedArticles();

}
