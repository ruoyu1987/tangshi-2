package cn.edu.jssvc.tangshi.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.activity.CollectionActivity;
import cn.edu.jssvc.tangshi.activity.LishiActivity;
import cn.edu.jssvc.tangshi.activity.LoginActivity;
import cn.edu.jssvc.tangshi.activity.MoxieListActivity;
import cn.edu.jssvc.tangshi.function.User;

public class Fragment_tab3 extends Fragment {

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_tab3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = new User(getContext());
        initView();
    }

    private void initView() {
        TextView textView_name = getActivity().findViewById(R.id.fragment2_name);
        textView_name.setText("当前用户：" + user.getUserName());

        getActivity().findViewById(R.id.fragment2_shoucang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CollectionActivity.class));
            }
        });

        getActivity().findViewById(R.id.fragment2_lishijilu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LishiActivity.class));
            }
        });

        getActivity().findViewById(R.id.fragment2_moxie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MoxieListActivity.class));
            }
        });

        getActivity().findViewById(R.id.fragment2_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("提示");
                dialog.setMessage("确定退出？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.setUserId("");
                        user.setUserName("");
                        user.setUserPassword("");

                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
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
