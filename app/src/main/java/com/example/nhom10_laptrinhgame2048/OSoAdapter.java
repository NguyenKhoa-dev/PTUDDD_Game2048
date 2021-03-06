package com.example.nhom10_laptrinhgame2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OSoAdapter extends ArrayAdapter<Integer> {
    private Context context;
    private ArrayList<Integer> arr;
    private int soCot;

    public OSoAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects, int soCot) {
        super(context, resource, objects);
        this.context = context;
        this.arr = new ArrayList<>(objects);
        this.soCot = soCot;
    }

    @Override
    public void notifyDataSetChanged() {
        arr = DataGame.getDataGame().getArrSo();
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(R.layout.item_o_vuong, null);
        }
        if (arr.size() > 0) {
            OVuong o = convertView.findViewById(R.id.txtOVuong);
            o.TextFormat(arr.get(position), soCot);
        }
        return convertView;
    }
}
