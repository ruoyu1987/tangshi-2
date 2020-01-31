package cn.edu.jssvc.tangshi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.function.User;

public class UserActivity extends AppCompatActivity {

    User user = new User(UserActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        findViewById(R.id.userActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView_name = findViewById(R.id.userActivity_name);
        textView_name.setText("当前用户：" + user.getUserName());

        findViewById(R.id.userActivity_shoucang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,CollectionActivity.class));
            }
        });

        findViewById(R.id.userActivity_lishijilu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,LishiActivity.class));
            }
        });

        findViewById(R.id.userActivity_moxie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, MoxieListActivity.class));
            }
        });

        findViewById(R.id.userActivity_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("确定退出？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.setUserId("");
                        user.setUserName("");
                        user.setUserPassword("");

                        Intent inent = new Intent(UserActivity.this, MainActivity.class);
                        inent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
            }
        });
    }
}
