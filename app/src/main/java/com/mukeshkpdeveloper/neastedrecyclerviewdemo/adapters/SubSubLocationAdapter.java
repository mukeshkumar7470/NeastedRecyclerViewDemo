package com.mukeshkpdeveloper.neastedrecyclerviewdemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mukeshkpdeveloper.neastedrecyclerviewdemo.R;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.SubSubLocationModel;

import java.util.ArrayList;

public class SubSubLocationAdapter extends RecyclerView.Adapter<SubSubLocationAdapter.MyViewHolder> {
    Context mContext;
    private final ArrayList<SubSubLocationModel> data;
  //  ArrayList<SubSubLocationModel> subLocationModels = new ArrayList<>();
    boolean clicked = false;
   // SubSubLocationAdapter recyclerDataAdapter;

    public SubSubLocationAdapter(ArrayList<SubSubLocationModel> data) {
        this.data = data;
    }

    @Override
    public SubSubLocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.on_rv_item1, parent, false);
        return new SubSubLocationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubSubLocationAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubSubLocationModel dummyParentDataItem = data.get(position);
        Log.e("TAG-MUKESH1", "onBindViewHolder: "+dummyParentDataItem.toString() );

        holder.tv_location_name.setText(""+dummyParentDataItem.getTitle());

        /*CompletedSubLocationAdapter recyclerDataAdapter = new CompletedSubLocationAdapter(data.get(position).getSubLocationInfo());
        holder.rv_sub_loaction.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv_sub_loaction.setAdapter(recyclerDataAdapter);
        holder.rv_sub_loaction.setHasFixedSize(true);
        holder.tv_location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummyParentDataItem.getId();
            }
        });*/

        holder.tv_location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG_mukesh", "onClick: "+clicked);
                if (clicked) {
                  //  subLocationModels.clear();
                  //  recyclerDataAdapter.notifyDataSetChanged();
                    clicked = false;
                } else {
                   // getAllPosts(holder);
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

   /* private void getAllPosts(MyViewHolder holder) {
        Call<List<SubSubLocationModel>> getAllPostsCall = RetrofitClient.getInstance().getApiInterface().getPos();

        getAllPostsCall.enqueue(new Callback<List<SubSubLocationModel>>() {
            @Override
            public void onResponse(Call<List<SubSubLocationModel>> call, Response<List<SubSubLocationModel>> response) {
                subLocationModels.addAll(response.body());
                recyclerDataAdapter = new SubSubLocationAdapter(subLocationModels);
                holder.rv_sub_loaction.setLayoutManager(new LinearLayoutManager(mContext));
                holder.rv_sub_loaction.setAdapter(recyclerDataAdapter);
                holder.rv_sub_loaction.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<SubSubLocationModel>> call, Throwable t) {
                Log.e("TAG", "Error occured while fetching post.");
            }
        });
    }*/
}