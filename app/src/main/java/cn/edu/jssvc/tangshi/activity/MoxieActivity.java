package cn.edu.jssvc.tangshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.adapter.MoxieListItem;
import cn.edu.jssvc.tangshi.adapter.MoxieListItemAdapter;
import cn.edu.jssvc.tangshi.function.MyListView;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class MoxieActivity extends AppCompatActivity {

    private TextView textView_title,textView_name;

    private MyListView myListView;
    private MoxieListItem moxieListItem;
    private List<MoxieListItem> moxieListItemList;
    private MoxieListItemAdapter moxieListItemAdapter;

    private String poetriesid;
    private String content;
    private String title;
    private String name;

    User user = new User(MoxieActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moxie);
        findViewById(R.id.moxieActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        textView_title = findViewById(R.id.moxieActivity_title);
        textView_name = findViewById(R.id.moxieActivity_name);
        myListView = findViewById(R.id.moxieActivity_MyListView);
        moxieListItemList = new ArrayList<>();
        moxieListItemAdapter = new MoxieListItemAdapter(MoxieActivity.this, R.layout.moxie_layout_item, moxieListItemList);
        myListView.setAdapter(moxieListItemAdapter);
        findViewById(R.id.moxieActivity_moxie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MoxieListItem> moxieListItemList = moxieListItemAdapter.getMoxieListItemList();
                String[] contentArray = content.split("。");
                String contentXian = "";
                int index = 0;
                for (int i = 0; i < moxieListItemList.size(); i++) {
                    String yuan = deleteStrFh(contentArray[i]);
                    String xian = moxieListItemList.get(i).getEdit();
                    contentXian += xian + " 。";
                    if (yuan.equals(deleteStrFh(xian))) {
                        index++;
                    }
                }
                final String fenshu = getFnshu(index,contentArray.length);
                try {
                    final String finalContentXian = contentXian;
                    HttpRequest.post2("addMoxie", new JSONObject("{\"userid\":\"" + user.getUserId() + "\",\"poetrieid\":\"" + poetriesid + "\",\"fenshu\":\"" + fenshu + "\",\"contentmo\":\"" + finalContentXian + "\",\"time\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + "\"}"), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getString("isOk").equals("true")) {
                                    Toast.makeText(MoxieActivity.this, "您的得分是：" + fenshu, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MoxieActivity.this, ChengjiActivity.class);
                                    intent.putExtra("title", title);
                                    intent.putExtra("name", name);
                                    intent.putExtra("content", content);
                                    intent.putExtra("contentXian", finalContentXian);
                                    intent.putExtra("fenshu", fenshu);
                                    startActivity(intent);
                                    finish();
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
        });
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        poetriesid = intent.getStringExtra("poetriesid");
        title = intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        content = intent.getStringExtra("content");
        int contentLength = intent.getIntExtra("contentLength",0);

        textView_title.setText(title);
        textView_name.setText(name+"-唐代");
        for (int i = 0; i < contentLength; i++) {
            moxieListItem = new MoxieListItem("");
            moxieListItemList.add(moxieListItem);
            moxieListItemAdapter.notifyDataSetChanged();
        }
    }

//    去除字符串
    public static String deleteStrFh(String s){
        String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    //计算分数
    public String getFnshu(int num1,int num2) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) num1 / (float) num2 * 100);
        return result;
    }
}
