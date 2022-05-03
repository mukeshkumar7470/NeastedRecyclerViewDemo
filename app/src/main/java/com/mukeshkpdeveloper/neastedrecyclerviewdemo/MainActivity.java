package com.mukeshkpdeveloper.neastedrecyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mukeshkpdeveloper.neastedrecyclerviewdemo.adapters.RvAdapter;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.LocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvLocation;
    Context mContext;
    ArrayList<LocationModel> locationMasterData = new ArrayList<>();
    RvAdapter recyclerDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        rvLocation = findViewById(R.id.rv_location);
        getAllPosts();
        recyclerDataAdapter = new RvAdapter(locationMasterData);
        rvLocation.setLayoutManager(new LinearLayoutManager(mContext));
        rvLocation.setAdapter(recyclerDataAdapter);
        rvLocation.setHasFixedSize(true);
    }

    private void getAllPosts() {
        Call<List<LocationModel>> getAllPostsCall = RetrofitClient.getInstance().getApiInterface().getAllPosts();
        getAllPostsCall.enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                locationMasterData.addAll(response.body());
               /* for (int i = 0; i <response.body().size() ; i++) {

                }*/
                recyclerDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                Log.e("TAG", "Error occured while fetching post.");
            }
        });
    }

}