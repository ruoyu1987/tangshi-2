package cn.edu.jssvc.tangshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_id, editText_pwd;

    User user = new User(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.loginActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.loginActivity_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.loginActivity_goRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        initView();
    }

    private void initView() {
        editText_id = findViewById(R.id.loginActivity_id);
        editText_pwd = findViewById(R.id.loginActivity_pwd);

        findViewById(R.id.loginActivity_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_id.getText().toString();
                String pwd = editText_pwd.getText().toString();
                if (name.trim().equals("") && pwd.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名和密码均不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    HttpRequest.post2("login", new JSONObject("{\"id\":\"" + name + "\",\"pwd\":\"" + pwd + "\"}"), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            if(jsonObject.toString().equals("{}")){
                                Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                            }else{
                                try {
                                    user.setUserId(jsonObject.getString("id"));
                                    user.setUserName(jsonObject.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    },null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
