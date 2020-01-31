package cn.edu.jssvc.tangshi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.function.MySQLiteOpenHelper;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class ContentActivity extends AppCompatActivity {

    private TextView textView_title;
    private TextView textView_name;
    private TextView textView_content;
    private ImageView imageViewShoucang;

    private String poetriesid;
    private String title;
    private String name;
    private String content;
    private int contentLength;

    User user = new User(ContentActivity.this);

    private boolean isShoucang = false;

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(ContentActivity.this, MySQLiteOpenHelper.DBNAME, null, 1);
        db = mySQLiteOpenHelper.getWritableDatabase();
        findViewById(R.id.contentActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        Intent intent = getIntent();
        getData(intent.getStringExtra("id"));
    }

    private void initView() {
        textView_title = findViewById(R.id.contentActivity_title);
        textView_name = findViewById(R.id.contentActivity_name);
        textView_content = findViewById(R.id.contentActivity_content);
        imageViewShoucang = findViewById(R.id.contentActivity_shoucang);

        imageViewShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getUserId().equals("")){
                    Toast.makeText(ContentActivity.this, "暂未登录，请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ContentActivity.this,LoginActivity.class));
                }else{
                    if(isShoucang){
                        try {
                            JSONObject jsonObject_1 = new JSONObject("{\"id\":\"" + user.getUserId() + "\",\"poetrieid\":\"" + poetriesid + "\"}");
                            HttpRequest.post2("delCollections",jsonObject_1 , new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.getString("isOk").equals("true")){
                                            Toast.makeText(ContentActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                            isShoucang = false;
                                            imageViewShoucang.setImageResource(R.drawable.shoucang_1);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            JSONObject jsonObject_1 = new JSONObject("{\"id\":\"" + user.getUserId() + "\",\"poetrieid\":\"" + poetriesid + "\"}");
                            HttpRequest.post2("addCollections",jsonObject_1 , new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.getString("isOk").equals("true")){
                                            Toast.makeText(ContentActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                            isShoucang = true;
                                            imageViewShoucang.setImageResource(R.drawable.shoucang_2);
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
            }
        });

        findViewById(R.id.contentActivity_moxie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getUserId().equals("")){
                    Toast.makeText(ContentActivity.this, "暂未登录，请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ContentActivity.this,LoginActivity.class));
                }else {
                    Intent intent = new Intent(ContentActivity.this,MoxieActivity.class);
                    intent.putExtra("poetriesid", poetriesid);
                    intent.putExtra("title", title);
                    intent.putExtra("name", name);
                    intent.putExtra("content", content);
                    intent.putExtra("contentLength", contentLength);
                    startActivity(intent);
                }
            }
        });
    }

    private void getData(String id) {
        try {
            JSONObject json = new JSONObject("{\"poetriesid\":\"" + id + "\"}");
            HttpRequest.post2("queryPoetriescontent",json , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        poetriesid = jsonObject.getString("poetriesid");
                        title = jsonObject.getString("title");
                        name = jsonObject.getString("poetsname");
                        content = jsonObject.getString("content");
                        textView_title.setText(title);
                        textView_name.setText(name + "-唐代");
                        String[] contentArray = content.split("。");
                        contentLength = contentArray.length;
                        String contentStr = "";
                        for (int i = 0; i < contentArray.length; i++) {
                            if (i == contentArray.length - 1) {
                                contentStr += contentArray[i] + "。";
                            }else{
                                contentStr += contentArray[i] + "。\n";
                            }
                        }
                        textView_content.setText(contentStr);

                        db.delete("lishi", "poetrieid = ?", new String[] { poetriesid });

                        ContentValues values = new ContentValues();
                        values.put("poetrieid", poetriesid);
                        values.put("poetname", name);
                        values.put("title", title);
                        values.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                        db.insert("lishi", null, values);

                        if(!user.getUserId().equals("")){
                            try {
                                JSONObject jsonObject_1 = new JSONObject("{\"id\":\"" + user.getUserId() + "\"}");
                                HttpRequest.post("queryCollections",jsonObject_1 , new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray jsonArray) {
                                        try {
                                            int index = 0;
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                if (jsonObject1.getString("poetrieid").equals(poetriesid)) {
                                                    index++;
                                                }
                                            }
                                            if (index > 0) {
                                                imageViewShoucang.setImageResource(R.drawable.shoucang_2);
                                                isShoucang = true;
                                            }else{
                                                imageViewShoucang.setImageResource(R.drawable.shoucang_1);
                                                isShoucang = false;
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
