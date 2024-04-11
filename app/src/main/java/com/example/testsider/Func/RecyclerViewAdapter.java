package com.example.testsider.Func;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testsider.R;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Pair<String, String>> data;

    public RecyclerViewAdapter( List<Pair<String, String>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, String> pair = data.get(position);
        String time = pair.first;
        String temperature = pair.second+"°C";
        System.out.println("test"+position);
        holder.timeTextView.setText(time);
        holder.temperatureTextView.setText(temperature);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;
        TextView temperatureTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
        }
    }
}
