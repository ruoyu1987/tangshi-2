package cn.edu.jssvc.tangshi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.function.User;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class MainActivity extends AppCompatActivity {

    User user = new User(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mainActivity_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!user.getUserId().equals("")){
                    startActivity(new Intent(MainActivity.this,UserActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final AlertDialog dialog = builder.create();
                    View dialogView = View.inflate(MainActivity.this, R.layout.login_dialog, null);
                    //设置对话框布局
                    dialog.setView(dialogView);
                    dialog.show();
                    final EditText etName = (EditText) dialogView.findViewById(R.id.et_name);
                    final EditText etPwd = (EditText) dialogView.findViewById(R.id.et_pwd);
                    Button btnLogin = (Button) dialogView.findViewById(R.id.btn_login);
                    Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
                    TextView btn_goRegister = (TextView) dialogView.findViewById(R.id.btn_goRegister);

                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = etName.getText().toString();
                            String pwd = etPwd.getText().toString();
                            if (name.trim().equals("") && pwd.trim().equals("")) {
                                Toast.makeText(MainActivity.this, "用户名和密码均不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                HttpRequest.post2("login", new JSONObject("{\"id\":\"" + name + "\",\"pwd\":\"" + pwd + "\"}"), new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        if(jsonObject.toString().equals("{}")){
                                            Toast.makeText(MainActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                                        }else{
                                            try {
                                                user.setUserId(jsonObject.getString("id"));
                                                user.setUserName(jsonObject.getString("name"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                },null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btn_goRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                        }
                    });
                }
            }
        });

        findViewById(R.id.mainActivity_All).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PoetsListActivity.class));
            }
        });

        findViewById(R.id.mainActivity_Search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示：");
            builder.setMessage("您确定退出？");

            //设置确定按钮
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            //设置取消按钮
            builder.setPositiveButton("容我再想想",null);
            //显示提示框
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
