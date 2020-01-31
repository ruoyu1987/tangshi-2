package cn.edu.jssvc.tangshi.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.adapter.PoetsListAdapter;
import cn.edu.jssvc.tangshi.adapter.PoetsListItem;
import cn.edu.jssvc.tangshi.link.HttpRequest;

public class SearchActivity extends AppCompatActivity {

    private EditText editText;
    private Spinner spinner;
    private String selectValue;
    private List<String> stringList;
    private ArrayAdapter arrayAdapter;

    private ListView listView;
    private PoetsListItem poetsListItem;
    private PoetsListAdapter poetsListAdapter;
    private List<PoetsListItem> poetsListItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.searchActivity_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        editText = findViewById(R.id.searchActivity_EditText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().toString().equals("")){
                    poetsListItemList.clear();
                    poetsListAdapter.notifyDataSetChanged();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getData(editText.getText().toString(),selectValue);
                        }
                    }).start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initView() {
        spinner = findViewById(R.id.searchActivity_Spinner);
        stringList = new ArrayList<>();
        stringList.add("作者");
        stringList.add("诗名");
        arrayAdapter = new ArrayAdapter(SearchActivity.this, R.layout.my_simple_spinner_item, stringList);
        arrayAdapter.setDropDownViewResource(R.layout.my_simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectValue = stringList.get(position);
                if(editText.getText().toString().equals("")){
                    poetsListItemList.clear();
                    poetsListAdapter.notifyDataSetChanged();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getData(editText.getText().toString(),selectValue);
                        }
                    }).start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView = findViewById(R.id.searchActivity_listView);
        poetsListItemList = new ArrayList<>();
        poetsListAdapter = new PoetsListAdapter(SearchActivity.this, R.layout.poetslist_item, poetsListItemList);
        listView.setAdapter(poetsListAdapter);
    }


    private void getData(String string,String selectValue) {
        poetsListItemList.clear();
        try {
            String link = "";
            JSONObject jsonObject;
            if(selectValue.equals("作者")){
                link = "poetsQueryPoetriesname";
                jsonObject = new JSONObject("{\"name\":\"" + string + "\"}");
            }else{
                link = "poetriesnameQueryPoetriesname";
                jsonObject = new JSONObject("{\"title\":\"" + string + "\"}");
            }
            HttpRequest.post(link,jsonObject , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            poetsListItem = new PoetsListItem(jsonObject1.getString("poetriesid"),jsonObject1.getString("name"),jsonObject1.getString("title"));
                            poetsListItemList.add(poetsListItem);
                            poetsListAdapter.notifyDataSetChanged();
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
