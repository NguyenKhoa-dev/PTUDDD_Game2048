package com.example.nhom10_laptrinhgame2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<GameScore> {
    private Context context;
    private ArrayList<GameScore> arr;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<GameScore> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arr = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(parent.getContext(),R.layout.item_bar_information,null);
        }
        TextView mode = convertView.findViewById(R.id.txtMode);
        TextView score = convertView.findViewById(R.id.txtScore);
        TextView date = convertView.findViewById(R.id.txtDate);
        mode.setText("Mode: "+arr.get(position).getName());
        score.setText("Score: "+arr.get(position).getScore());
        date.setText("Date: "+arr.get(position).getDate());
        return convertView;
    }
}
