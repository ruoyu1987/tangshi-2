package cn.edu.jssvc.tangshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.jssvc.tangshi.R;

public class ChengjiActivity extends AppCompatActivity {

    private TextView textView_title,textView_name,textView_content,textView_contentXian, textView_fenshu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengji);
        findViewById(R.id.chengjiActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        textView_title = findViewById(R.id.chengjiActivity_title);
        textView_name = findViewById(R.id.chengjiActivity_name);
        textView_content = findViewById(R.id.chengjiActivity_content);
        textView_contentXian = findViewById(R.id.chengjiActivity_content_2);
        textView_fenshu = findViewById(R.id.chengjiActivity_fenshu);
        getDate();
    }

    private void getDate() {
        Intent intent = getIntent();
        textView_title.setText(intent.getStringExtra("title"));
        textView_name.setText(intent.getStringExtra("name")+"-唐代");
        String content = intent.getStringExtra("content");
        String contentXian = intent.getStringExtra("contentXian");
        String[] contentArray = content.split("。");
        String[] contentXianArray = contentXian.split("。");
        String contentStr = "";
        String contentXianStr = "";
        for (int i = 0; i < contentArray.length; i++) {
            if (i == contentArray.length - 1) {
                contentStr += contentArray[i] + "。";
                contentXianStr += contentXianArray[i] + "。";
            }else{
                contentStr += contentArray[i] + "。\n";
                contentXianStr += contentXianArray[i] + "。\n";
            }
        }
        textView_content.setText(contentStr);
        textView_contentXian.setText(contentXianStr);
        textView_fenshu.setText(intent.getStringExtra("fenshu") + "分");
    }
}
