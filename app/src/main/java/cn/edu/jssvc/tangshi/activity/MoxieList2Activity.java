package cn.edu.jssvc.tangshi.activity;

import android.content.Intent;
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
import cn.edu.jssvc.tangshi.adapter.MoxieOneAll;
import cn.edu.jssvc.tangshi.adapter.MoxieOneAllAdapter;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class MoxieList2Activity extends AppCompatActivity {

    private ListView listView;
    private MoxieOneAll moxieOneAll;
    private List<MoxieOneAll> moxieOneAllList;
    private MoxieOneAllAdapter moxieOneAllAdapter;

    User user = new User(MoxieList2Activity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moxie_list2);
        findViewById(R.id.moxielist2Activity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.moxielist2Activity_listView);
        moxieOneAllList = new ArrayList<>();
        moxieOneAllAdapter = new MoxieOneAllAdapter(MoxieList2Activity.this, R.layout.moxies_one_all_item, moxieOneAllList);
        listView.setAdapter(moxieOneAllAdapter);
        getData();
    }

    private void getData() {
        moxieOneAllList.clear();
        moxieOneAllAdapter.notifyDataSetChanged();
        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject("{\"userid\":\"" + user.getUserId() + "\",\"poetrieid\":\"" + intent.getStringExtra("poetrieid") + "\"}");
            HttpRequest.post("queryMoxiesOneAll",jsonObject , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            moxieOneAll = new MoxieOneAll(jsonObject1.getString("poetrieid"),jsonObject1.getString("title"),jsonObject1.getString("poetname"),jsonObject1.getString("content"),jsonObject1.getString("contentmo"),jsonObject1.getString("fenshu"),jsonObject1.getString("time"));
                            moxieOneAllList.add(moxieOneAll);
                            moxieOneAllAdapter.notifyDataSetChanged();
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
