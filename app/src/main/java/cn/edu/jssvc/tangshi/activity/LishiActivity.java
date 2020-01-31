package cn.edu.jssvc.tangshi.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.adapter.PoetsListAdapter;
import cn.edu.jssvc.tangshi.adapter.PoetsListItem;
import cn.edu.jssvc.tangshi.function.MySQLiteOpenHelper;

public class LishiActivity extends AppCompatActivity {

    private ListView listView;
    private PoetsListItem poetsListItem;
    private PoetsListAdapter poetsListAdapter;
    private List<PoetsListItem> poetsListItemList;

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishi);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(LishiActivity.this, MySQLiteOpenHelper.DBNAME, null, 1);
        db = mySQLiteOpenHelper.getWritableDatabase();
        findViewById(R.id.lishiActivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.lishiActivity_qk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LishiActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("确定清空？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete("lishi", "time > ?", new String[] { "2000-01-01 00:00:00" });
                        getData();
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

        initView();
    }

    private void initView() {
        listView = findViewById(R.id.lishiActivity_listView);
        poetsListItemList = new ArrayList<>();
        poetsListAdapter = new PoetsListAdapter(LishiActivity.this, R.layout.poetslist_item, poetsListItemList);
        listView.setAdapter(poetsListAdapter);
        getData();
    }

    private void getData() {
        poetsListItemList.clear();
        poetsListAdapter.notifyDataSetChanged();
        Cursor cursor = db.rawQuery("select * from lishi order by time desc", null);
        if (cursor.moveToFirst()) {
            do {
                poetsListItem = new PoetsListItem(cursor.getString(cursor.getColumnIndex("poetrieid")),cursor.getString(cursor.getColumnIndex("poetname")),cursor.getString(cursor.getColumnIndex("title")));
                poetsListItemList.add(poetsListItem);
                poetsListAdapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
