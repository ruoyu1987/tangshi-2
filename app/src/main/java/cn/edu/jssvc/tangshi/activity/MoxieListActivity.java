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
import cn.edu.jssvc.tangshi.adapter.MoxieAll;
import cn.edu.jssvc.tangshi.adapter.MoxieAllAdapter;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class MoxieListActivity extends AppCompatActivity {

    private ListView listView;
    private MoxieAll moxieAll;
    private List<MoxieAll> moxieAllList;
    private MoxieAllAdapter moxieAllAdapter;

    User user = new User(MoxieListActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moxie_list);
        findViewById(R.id.moxielistActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.moxielistActivity_listView);
        moxieAllList = new ArrayList<>();
        moxieAllAdapter = new MoxieAllAdapter(MoxieListActivity.this, R.layout.moxie_all_item, moxieAllList);
        listView.setAdapter(moxieAllAdapter);
        getData();
    }

    private void getData() {
        moxieAllList.clear();
        moxieAllAdapter.notifyDataSetChanged();
        try {
            JSONObject jsonObject = new JSONObject("{\"userid\":\"" + user.getUserId() + "\"}");
            HttpRequest.post("queryMoxiesAll",jsonObject , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            moxieAll = new MoxieAll(jsonObject1.getString("poetrieid"),jsonObject1.getString("title"),jsonObject1.getString("poetname"),jsonObject1.getString("time"));
                            moxieAllList.add(moxieAll);
                            moxieAllAdapter.notifyDataSetChanged();
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
