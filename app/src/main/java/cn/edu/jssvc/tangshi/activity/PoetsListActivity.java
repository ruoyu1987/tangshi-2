package cn.edu.jssvc.tangshi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.adapter.PoetsListAdapter;
import cn.edu.jssvc.tangshi.adapter.PoetsListItem;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class PoetsListActivity extends AppCompatActivity {
    //故事作者列表活动

    private ListView listView;
    private PoetsListItem poetsListItem;
    private PoetsListAdapter poetsListAdapter;
    private List<PoetsListItem> poetsListItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poets_list);

        findViewById(R.id.poetlistActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.poetlistActivity_listView);
        poetsListItemList = new ArrayList<>();
        poetsListAdapter = new PoetsListAdapter(PoetsListActivity.this, R.layout.poetslist_item, poetsListItemList);
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
