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
import cn.edu.jssvc.tangshi.activity.MoxieList2Activity;

public class MoxieAllAdapter extends ArrayAdapter<MoxieAll> {

    private Context mContext;
    private int resourceId;
    private List<MoxieAll> moxieAllList;

    public MoxieAllAdapter(Context context, int resource, List<MoxieAll> moxieAllList) {
        super(context, resource, moxieAllList);
        mContext = context;
        resourceId = resource;
        this.moxieAllList = moxieAllList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MoxieAll moxieAll = getItem(position);
        View view;
        MoxieAllAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new MoxieAllAdapter.ViewHolder();
            viewHolder.linearLayout = view.findViewById(R.id.moxieAll_LinearLayout);
            viewHolder.textView_title = view.findViewById(R.id.moxieAll_Title);
            viewHolder.textView_name = view.findViewById(R.id.moxieAll_Name);
            viewHolder.textView_time = view.findViewById(R.id.moxieAll_Time);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (MoxieAllAdapter.ViewHolder) view.getTag();
        }
        viewHolder.textView_title.setText(moxieAll.getTitle());
        viewHolder.textView_name.setText(moxieAll.getPoetname());
        viewHolder.textView_time.setText(moxieAll.getTime());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoxieList2Activity.class);
                intent.putExtra("poetrieid", moxieAllList.get(position).getPoetrieid());
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private class ViewHolder {
        LinearLayout linearLayout;
        TextView textView_title;
        TextView textView_name;
        TextView textView_time;
    }

}
