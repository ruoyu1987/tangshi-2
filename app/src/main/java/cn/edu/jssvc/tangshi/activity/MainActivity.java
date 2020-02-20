package cn.edu.jssvc.tangshi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.fragment.Fragment_tab1;
import cn.edu.jssvc.tangshi.fragment.Fragment_tab2;
import cn.edu.jssvc.tangshi.fragment.Fragment_tab3;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayout_tab1,linearLayout_tab2,linearLayout_tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        linearLayout_tab1 = findViewById(R.id.mainAcitivty_tab1);
        linearLayout_tab1.setOnClickListener(this);
        linearLayout_tab2 = findViewById(R.id.mainAcitivty_tab2);
        linearLayout_tab2.setOnClickListener(this);
        linearLayout_tab3 = findViewById(R.id.mainAcitivty_tab3);
        linearLayout_tab3.setOnClickListener(this);
        fragmentList.add(new Fragment_tab1());
        fragmentList.add(new Fragment_tab2());
        fragmentList.add(new Fragment_tab3());
        loadFragment(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainAcitivty_tab1:
                loadFragment(0);
                break;
            case R.id.mainAcitivty_tab2:
                loadFragment(1);
                break;
            case R.id.mainAcitivty_tab3:
                loadFragment(2);
                break;
        }
    }

    private Fragment mFrag;
    private List<Fragment> fragmentList = new ArrayList<>();
    private void loadFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(position);
        if(mFrag != null) {
            transaction.hide(mFrag);
        }
        if(!fragment.isAdded()) {
            transaction.add(R.id.mainAcitivty_Fragment, fragment);
        } else {
            transaction.show(fragment);
        }
        mFrag = fragment;
        transaction.commit();
    }
}
