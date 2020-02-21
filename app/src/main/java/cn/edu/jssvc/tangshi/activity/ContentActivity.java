package cn.edu.jssvc.tangshi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

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

    SpeechSynthesizer mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        SpeechUtility.createUtility ( this,SpeechConstant.APPID+"=5cc7a0e8" );
        mTts=SpeechSynthesizer.createSynthesizer ( this,mInitListener );
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


        findViewById(R.id.contentActivity_yuedu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTtsParams ();
                int code = mTts.startSpeaking(title + "，" + name + "，" + content, mTtsListener);
                if (code!= ErrorCode.SUCCESS){
                    Toast.makeText ( ContentActivity.this,"合成失败，错误码"+code,Toast.LENGTH_LONG ).show ();
                    Log.d ("tag-ttsint","合成失败，错误码"+code);
                }
            }
        });
    }

    InitListener mInitListener = new InitListener () {
        @Override
        public void onInit(int i) {
            if (i!= ErrorCode.SUCCESS){
//                Toast.makeText ( ContentActivity.this,"初始化失败，错误码"+i,Toast.LENGTH_LONG ).show ();
                Log.d ("tag-ttsint","初始化错误，错误码"+i);
            }else {
//                Toast.makeText ( ContentActivity.this,"初始化成功",Toast.LENGTH_LONG ).show ();
                Log.d ("tag-ttsint","初始化成功");
            }
        }
    };

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

    String voiceFenale="xiaoyan";
    String mEngineType= SpeechConstant.TYPE_CLOUD;
    void setTtsParams() {
        mTts.setParameter(SpeechConstant.PARAMS, null);//清空
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {//云端
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.VOICE_NAME, voiceFenale);
            mTts.setParameter(SpeechConstant.SPEED, "20");
            mTts.setParameter(SpeechConstant.PITCH, "50");
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {//离线
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, voiceFenale);
        }
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/xunfei/" + getFileName() + "");//保存路径及名称
    }

    String getFileName(){
        Date now=new Date (  );
        SimpleDateFormat format=new SimpleDateFormat ( "yyyyMMddHHmmss" );
        return format.format ( now );
    }

    SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
//            Toast.makeText(ContentActivity.this, "合成开始", Toast.LENGTH_LONG).show();
            Log.d("tag-onSpeakBegin", "合成开始");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                Toast.makeText(ContentActivity.this, "阅读完成，快来练习阅读吧！", Toast.LENGTH_LONG).show();
                Log.d("tag-onSpeakBegin", "合成完成");
            } else {
                Toast.makeText(ContentActivity.this, "合成失败", Toast.LENGTH_LONG).show();
                Log.d("tag-onSpeakBegin", "合成失败");
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
}
