package cn.edu.jssvc.tangshi.function;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    Context context;

    public User(Context context) {
        this.context = context;
    }

    public String getUserId() {
        return context.getSharedPreferences("Users", Context.MODE_PRIVATE).getString("userId", "");
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Users", Context.MODE_PRIVATE).edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    public String getUserName() {
        return context.getSharedPreferences("Users", Context.MODE_PRIVATE).getString("userName", "");
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Users", Context.MODE_PRIVATE).edit();
        editor.putString("userName", userName);
        editor.apply();
    }

    public String getUserPassword() {
        return context.getSharedPreferences("Users", Context.MODE_PRIVATE).getString("userPassword", "");
    }

    public void setUserPassword(String userPassword) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Users", Context.MODE_PRIVATE).edit();
        editor.putString("userPassword", userPassword);
        editor.apply();
    }

}
