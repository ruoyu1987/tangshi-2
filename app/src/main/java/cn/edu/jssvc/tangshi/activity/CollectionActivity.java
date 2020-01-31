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

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.adapter.PoetsListAdapter;
import cn.edu.jssvc.tangshi.adapter.PoetsListItem;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class CollectionActivity extends AppCompatActivity {

    private ListView listView;
    private PoetsListItem poetsListItem;
    private PoetsListAdapter poetsListAdapter;
    private List<PoetsListItem> poetsListItemList;

    User user = new User(CollectionActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        findViewById(R.id.collectionActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();
    }

    private void initView() {
        listView = findViewById(R.id.collectionActivity_listView);
        poetsListItemList = new ArrayList<>();
        poetsListAdapter = new PoetsListAdapter(CollectionActivity.this, R.layout.poetslist_item, poetsListItemList);
        listView.setAdapter(poetsListAdapter);
    }

    private void getData() {
        poetsListItemList.clear();
        poetsListAdapter.notifyDataSetChanged();
        try {
            JSONObject jsonObject = new JSONObject("{\"id\":\"" + user.getUserId() + "\"}");
            HttpRequest.post("queryCollections",jsonObject , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            poetsListItem = new PoetsListItem(jsonObject1.getString("poetrieid"),jsonObject1.getString("poetname"),jsonObject1.getString("title"));
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
