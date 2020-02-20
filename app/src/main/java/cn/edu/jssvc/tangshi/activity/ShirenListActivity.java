package cn.edu.jssvc.tangshi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.adapter.PoetsListAdapter;
import cn.edu.jssvc.tangshi.adapter.PoetsListItem;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class ShirenListActivity extends AppCompatActivity {

    private ListView listView;
    private PoetsListItem poetsListItem;
    private PoetsListAdapter poetsListAdapter;
    private List<PoetsListItem> poetsListItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiren_list);
        findViewById(R.id.shirenListActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textView = findViewById(R.id.shirenListActivity_title);
        textView.setText(getIntent().getStringExtra("name"));

        listView = findViewById(R.id.shirenListActivity_listView);
        poetsListItemList = new ArrayList<>();
        poetsListAdapter = new PoetsListAdapter(ShirenListActivity.this, R.layout.poetslist_item, poetsListItemList);
        listView.setAdapter(poetsListAdapter);
        getData(getIntent().getStringExtra("name"));
    }

    private void getData(String string) {
        try {
            HttpRequest.post("poetsQueryPoetriesname",new JSONObject("{\"name\":\"" + string + "\"}") , new Response.Listener<JSONArray>() {
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
}
