package com.example.user.crudwithretrofit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 10/1/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    private List<Trains> trains;

    public RecyclerAdapter(List<Trains> trains) {
        this.trains = trains;

    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        holder.name.setText(trains.get(position).getName());
        holder.type.setText(trains.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return trains.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView name,type;

        public MyviewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            type = (TextView)itemView.findViewById(R.id.type);
        }
    }
}
