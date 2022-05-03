package com.mukeshkpdeveloper.neastedrecyclerviewdemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.mukeshkpdeveloper.neastedrecyclerviewdemo.R;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.SubLocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.SubSubLocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedSubLocationAdapter extends RecyclerView.Adapter<CompletedSubLocationAdapter.MyViewHolder> {
    Context mContext;
    private final ArrayList<SubLocationModel> data;
    ArrayList<SubSubLocationModel> subLocationModels = new ArrayList<>();
    boolean clicked = false;
    SubSubLocationAdapter recyclerDataAdapter;

    public CompletedSubLocationAdapter(ArrayList<SubLocationModel> data) {
        this.data = data;
    }

    @Override
    public CompletedSubLocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.on_rv_item, parent, false);
        return new CompletedSubLocationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompletedSubLocationAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubLocationModel dummyParentDataItem = data.get(position);
        Log.e("TAG-MUKESH1", "onBindViewHolder: "+dummyParentDataItem.toString() );

        holder.tv_location_name.setText(""+dummyParentDataItem.getTitle());
        holder.tv_location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG_mukesh", "onClick: "+clicked);
                if (clicked) {
                    subLocationModels.clear();
                    recyclerDataAdapter.notifyDataSetChanged();
                    clicked = false;
                } else {
                    getAllPosts(holder);
                    clicked = true;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rv_sub_loaction;
        private Context context;
        private TextView tv_location_name, tv_count;
        private LinearLayout linearLayout_childItems;

        MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            tv_location_name = itemView.findViewById(R.id.tv_location_name);
            rv_sub_loaction = itemView.findViewById(R.id.rv_sub_loaction);
            tv_count = itemView.findViewById(R.id.tv_count);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.VISIBLE);

        }
    }

    private void getAllPosts(MyViewHolder holder) {
        Call<List<SubSubLocationModel>> getAllPostsCall = RetrofitClient.getInstance().getApiInterface().getPos();

        getAllPostsCall.enqueue(new Callback<List<SubSubLocationModel>>() {
            @Override
            public void onResponse(Call<List<SubSubLocationModel>> call, Response<List<SubSubLocationModel>> response) {
                subLocationModels.addAll(response.body());
                recyclerDataAdapter = new SubSubLocationAdapter(subLocationModels);
                SnapHelper helper = new LinearSnapHelper();
                LinearLayoutManager layoutManager = new  GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false);
                holder.rv_sub_loaction.setLayoutManager(layoutManager);
                holder.rv_sub_loaction.setOnFlingListener(null);
                holder.rv_sub_loaction.setAdapter(recyclerDataAdapter);
                helper.attachToRecyclerView(holder.rv_sub_loaction);
                holder.rv_sub_loaction.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<SubSubLocationModel>> call, Throwable t) {
                Log.e("TAG", "Error occured while fetching post.");
            }
        });
    }
}