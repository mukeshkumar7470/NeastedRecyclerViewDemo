package com.mukeshkpdeveloper.neastedrecyclerviewdemo.networking;

import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.LocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.SubLocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.SubSubLocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/posts")
    Call<List<LocationModel>> getAllPosts();

    @GET("/posts")
    Call<List<SubLocationModel>> getPosts();

    @GET("/posts")
    Call<List<SubSubLocationModel>> getPos();


}
