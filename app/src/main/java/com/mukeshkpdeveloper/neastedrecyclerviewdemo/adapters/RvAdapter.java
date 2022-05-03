package com.mukeshkpdeveloper.neastedrecyclerviewdemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.LocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.R;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.models.SubLocationModel;
import com.mukeshkpdeveloper.neastedrecyclerviewdemo.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    Context mContext;
    boolean clicked = false;
    private final ArrayList<LocationModel> data;
    ArrayList<SubLocationModel> subLocationModels = new ArrayList<>();
    CompletedSubLocationAdapter recyclerDataAdapter;

    public RvAdapter(ArrayList<LocationModel> data) {
        this.data = data;
    }

    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.on_going_location_rv_item, parent, false);
        return new RvAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RvAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocationModel dummyParentDataItem = data.get(position);
        Log.e("TAG-MUKESH", "onBindViewHolder: "+dummyParentDataItem.toString() );


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

        /*for (int i = 0; i <dummyParentDataItem.getSubLocationInfo().size(); i++) {
            for (int j = 0; j < dummyParentDataItem.getSubLocationInfo().get(i).getSubSubLocationInfo().size(); j++) {
                for (int k = 0; k < dummyParentDataItem.getSubLocationInfo().get(i).getSubSubLocationInfo().get(j).getPgData().size(); k++) {
                    if (dummyParentDataItem.getSubLocationInfo().get(i).getSubSubLocationInfo().get(j).getPgData().size() == 0) {
                        holder.linearLayout_childItems.setVisibility(View.GONE);
                    } else {
                        holder.tv_location_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view.getId() == R.id.tv_location_name) {
                                    if (holder.linearLayout_childItems.getVisibility() == View.VISIBLE) {
                                        holder.linearLayout_childItems.setVisibility(View.VISIBLE);
                                    } else {
                                        holder.linearLayout_childItems.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    TextView textViewClicked = (TextView) view;
                                    Toast.makeText(mContext, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //set Sub Location Data
                    }
                }
            }
        }

        RvAdapter recyclerDataAdapter = new RvAdapter(data.get(position).getSubLocationInfo());
        holder.rv_sub_loaction.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv_sub_loaction.setAdapter(recyclerDataAdapter);
        holder.rv_sub_loaction.setHasFixedSize(true);*/
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
        Call<List<SubLocationModel>> getAllPostsCall = RetrofitClient.getInstance().getApiInterface().getPosts();

        getAllPostsCall.enqueue(new Callback<List<SubLocationModel>>() {
            @Override
            public void onResponse(Call<List<SubLocationModel>> call, Response<List<SubLocationModel>> response) {
                subLocationModels.addAll(response.body());
                recyclerDataAdapter = new CompletedSubLocationAdapter(subLocationModels);
                holder.rv_sub_loaction.setLayoutManager(new LinearLayoutManager(mContext));
                holder.rv_sub_loaction.setAdapter(recyclerDataAdapter);
                holder.rv_sub_loaction.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<SubLocationModel>> call, Throwable t) {
                Log.e("TAG", "Error occured while fetching post.");
            }
        });
    }
}

