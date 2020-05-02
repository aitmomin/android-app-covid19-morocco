package com.example.covid19ma;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.covid19ma.ui.main.RegionInfo;

import java.util.List;

public class RegionListModel extends ArrayAdapter<RegionInfo> {

    private List<RegionInfo> listItems;
    private int resource;

    public RegionListModel(@NonNull Context context, int resource, @NonNull List<RegionInfo> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("MyLog", "RegionListModel ...... View");
        View listItem = convertView;
        if (listItem==null)
            listItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        TextView regionCode=listItem.findViewById(R.id.regionCode);
        TextView region=listItem.findViewById(R.id.region);
        TextView cases=listItem.findViewById(R.id.cases);

        regionCode.setText(String.valueOf(listItems.get(position).regionCode));
        region.setText(String.valueOf(listItems.get(position).region));
        cases.setText(String.valueOf(listItems.get(position).cases));

        return listItem;
    }

}
