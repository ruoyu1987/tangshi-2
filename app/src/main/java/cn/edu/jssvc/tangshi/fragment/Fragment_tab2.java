package cn.edu.jssvc.tangshi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.activity.ShirenListActivity;

public class Fragment_tab2 extends Fragment {

    private ListView listView;
    private List<String> stringList = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_tab2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        listView = getActivity().findViewById(R.id.fragment2_ListView);
        getData();
        arrayAdapter = new ArrayAdapter(getActivity(),R.layout.my_simple_spinner_item2, stringList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShirenListActivity.class);
                intent.putExtra("name", stringList.get(position));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        stringList.add("李白");
        stringList.add("杜甫");
        stringList.add("杜牧");
        stringList.add("白居易");
        stringList.add("孟浩然");
        stringList.add("王勃");
        stringList.add("王维");
        stringList.add("高适");
        stringList.add("岑参");
        stringList.add("王昌龄");
        stringList.add("贺知章");
        stringList.add("柳宗元");
        stringList.add("韩愈");
        stringList.add("李贺");
        stringList.add("韦应物");
    }
}
