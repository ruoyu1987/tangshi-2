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
import cn.edu.jssvc.tangshi.activity.ContentActivity;

public class PoetsListAdapter extends ArrayAdapter<PoetsListItem> {

    private Context mContext;
    private int resourceId;
    private List<PoetsListItem> poetsListItemList;

    public PoetsListAdapter( Context context, int resource,  List<PoetsListItem> poetsListItemList) {
        super(context, resource, poetsListItemList);
        mContext = context;
        resourceId = resource;
        this.poetsListItemList = poetsListItemList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PoetsListItem poetsListItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout = view.findViewById(R.id.poetlistItem_LinearLayout);
            viewHolder.textViewTitle = view.findViewById(R.id.poetlistItem_Title);
            viewHolder.textViewName = view.findViewById(R.id.poetlistItem_Name);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewTitle.setText(poetsListItem.getTitle());
        viewHolder.textViewName.setText(poetsListItem.getName());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra("id", poetsListItemList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    private class ViewHolder {
        LinearLayout linearLayout;
        TextView textViewTitle;
        TextView textViewName;
    }
}
