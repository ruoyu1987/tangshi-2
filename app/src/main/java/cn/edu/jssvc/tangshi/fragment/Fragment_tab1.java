package cn.edu.jssvc.tangshi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.activity.SearchActivity;
import cn.edu.jssvc.tangshi.adapter.PoetsListAdapter;
import cn.edu.jssvc.tangshi.adapter.PoetsListItem;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class Fragment_tab1 extends Fragment {

    private ListView listView;
    private PoetsListItem poetsListItem;
    private PoetsListAdapter poetsListAdapter;
    private List<PoetsListItem> poetsListItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_tab1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        getActivity().findViewById(R.id.fragment1_sousuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        listView = getActivity().findViewById(R.id.fragment1_ListView);
        poetsListItemList = new ArrayList<>();
        poetsListAdapter = new PoetsListAdapter(getContext(), R.layout.poetslist_item, poetsListItemList);
        listView.setAdapter(poetsListAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();
    }

    private void getData() {
        try {
            String text = "";
            for (int i = 0; i < 20; i++) {
                text += getSj(2500, 1) + ",";
            }
            JSONObject jsonObject = new JSONObject("{\"dataname\":\"1\",\"datatext\":\"" + text + "\"}");
            HttpRequest.post("queryAllPoets",jsonObject , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            poetsListItem = new PoetsListItem(jsonObject1.getString("poetriesid"),jsonObject1.getString("name"),jsonObject1.getString("title"));
                            poetsListItemList.add(poetsListItem);
                            poetsListAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getSj(int max,int min){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
}
