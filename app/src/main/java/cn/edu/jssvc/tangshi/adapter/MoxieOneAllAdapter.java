package cn.edu.jssvc.tangshi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.jssvc.tangshi.R;
import cn.edu.jssvc.tangshi.activity.ChengjiActivity;

public class MoxieOneAllAdapter extends ArrayAdapter<MoxieOneAll> {

    private Context mContext;
    private int resourceId;
    private List<MoxieOneAll> moxieOneAllList;

    public MoxieOneAllAdapter(Context context, int resource, List<MoxieOneAll> moxieOneAllList) {
        super(context, resource, moxieOneAllList);
        mContext = context;
        resourceId = resource;
        this.moxieOneAllList = moxieOneAllList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MoxieOneAll moxieOneAll = getItem(position);
        View view;
        MoxieOneAllAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new MoxieOneAllAdapter.ViewHolder();
            viewHolder.linearLayout = view.findViewById(R.id.moxieoneAll_LinearLayout);
            viewHolder.textView_index = view.findViewById(R.id.moxieoneAll_index);
            viewHolder.textView_title = view.findViewById(R.id.moxieoneAll_Title);
            viewHolder.textView_fenshu = view.findViewById(R.id.moxieoneAll_Fenshu);
            viewHolder.textView_name = view.findViewById(R.id.moxieoneAll_Name);
            viewHolder.textView_time = view.findViewById(R.id.moxieoneAll_Time);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (MoxieOneAllAdapter.ViewHolder) view.getTag();
        }
        viewHolder.textView_index.setText(String.valueOf(position + 1));
        viewHolder.textView_title.setText(moxieOneAll.getTitle());
        viewHolder.textView_fenshu.setText(moxieOneAll.getFenshu());
        viewHolder.textView_name.setText(moxieOneAll.getPoetname());
        viewHolder.textView_time.setText(moxieOneAll.getTime());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChengjiActivity.class);
                intent.putExtra("title", moxieOneAllList.get(position).getTitle());
                intent.putExtra("name", moxieOneAllList.get(position).getPoetname());
                intent.putExtra("content", moxieOneAllList.get(position).getContent());
                intent.putExtra("contentXian", moxieOneAllList.get(position).getContentmo());
                intent.putExtra("fenshu", moxieOneAllList.get(position).getFenshu());
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private class ViewHolder {
        LinearLayout linearLayout;
        TextView textView_index;
        TextView textView_title;
        TextView textView_fenshu;
        TextView textView_name;
        TextView textView_time;
    }

}
