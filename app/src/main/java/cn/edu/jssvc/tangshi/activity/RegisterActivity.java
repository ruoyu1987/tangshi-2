package cn.edu.jssvc.tangshi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class RegisterActivity extends AppCompatActivity {

    private EditText editText_id,editText_pwd,editText_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.registerActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.registerActivity_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editText_id = findViewById(R.id.registerActivity_id);
        editText_pwd = findViewById(R.id.registerActivity_pwd);
        editText_name = findViewById(R.id.registerActivity_name);

        findViewById(R.id.registerActivity_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editText_id.getText().toString().trim();
                String pwd = editText_pwd.getText().toString().trim();
                String name = editText_name.getText().toString().trim();
                if(id.equals("") || pwd.equals("")){
                    Toast.makeText(RegisterActivity.this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if(name.equals("")){
                        Toast.makeText(RegisterActivity.this,"给自己取个昵称吧！",Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            HttpRequest.post2("register", new JSONObject("{\"id\":\"" + id + "\",\"pwd\":\"" + pwd +  "\",\"name\":\"" + name + "\"}"), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.getString("isOk").equals("true")){
                                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(RegisterActivity.this,"账号已存在！",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
