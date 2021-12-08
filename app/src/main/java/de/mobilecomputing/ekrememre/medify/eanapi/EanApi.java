package de.mobilecomputing.ekrememre.medify.eanapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EanApi {
    @GET("/?cmd=query&queryid=400000000")
    Call<String> getProduct(@Query("ean") String ean);
}
