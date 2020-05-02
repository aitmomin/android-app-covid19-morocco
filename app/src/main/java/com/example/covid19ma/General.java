package com.example.covid19ma;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class General extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView lastUpdated;
    private TextView confirmed;
    private TextView recovered;
    private TextView deaths;
    private TextView negative;

    public General() {
        // Required empty public constructor
    }

    public static General newInstance(int index) {
        General fragment = new General();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_general, container, false);
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
                            JSONObject general = array.getJSONObject(0);
                            lastUpdated = root.findViewById(R.id.lastUpdated);
                            confirmed = root.findViewById(R.id.confirmed);
                            recovered = root.findViewById(R.id.recovered);
                            deaths = root.findViewById(R.id.deaths);
                            negative = root.findViewById(R.id.negative);

                            String str = general.getString("last_updated");
                            String last_updated = str.substring(0, str.indexOf("+"));

                            lastUpdated.setText(last_updated);
                            confirmed.setText(general.getInt("confirmed")+"");
                            recovered.setText(general.getInt("recovered")+"");
                            deaths.setText(general.getInt("deaths")+"");
                            negative.setText(general.getInt("negative")+"");

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
