package com.example.covid19ma;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19ma.ui.main.PlaceholderFragment;
import com.example.covid19ma.ui.main.RegionInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Region extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RegionListModel model;
    private List<RegionInfo> data;

    public Region() {
        // Required empty public constructor
    }

    public static Region newInstance(int index) {
        Region fragment = new Region();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data = new ArrayList<>();
        View root = inflater.inflate(R.layout.fragment_region, container, false);
        ListView listInfos = root.findViewById(R.id.listRegion);
        model = new RegionListModel(getActivity(), R.layout.region_item, data);
        listInfos.setAdapter(model);

        data.clear();
//        model.notifyDataSetChanged();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://covidma.herokuapp.com/api?fbclid=IwAR0bSwp6jzwqS8qk7r6P00GxjAuMypr8lRaoYq0-jW4G8eVW_7D-D3UcCgc";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONArray regions = array.getJSONArray(1);
                            for (int i = 0; i < regions.length(); i++) {
                                JSONObject r = regions.getJSONObject(i);
                                RegionInfo region = new RegionInfo();
                                region.regionCode = r.getString("regionCode");
                                region.region = r.getString("region");
                                region.cases = r.getInt("cases");
                                data.add(region);
                            }
                            model.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("MyLog", "Connection problem!");
                    }
                }
        );
        queue.add(stringRequest);

        return root;
    }


}
