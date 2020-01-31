package cn.edu.jssvc.tangshi.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import java.util.List;
import cn.edu.jssvc.tangshi.R;

public class MoxieListItemAdapter extends ArrayAdapter<MoxieListItem> {

    private Context mContext;
    private int resourceId;
    private List<MoxieListItem> moxieListItemList;

    public MoxieListItemAdapter(Context context, int resource, List<MoxieListItem> moxieListItemList) {
        super(context, resource, moxieListItemList);
        mContext = context;
        resourceId = resource;
        this.moxieListItemList = moxieListItemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoxieListItem moxieListItem = getItem(position);
        View view;
        final MoxieListItemAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new MoxieListItemAdapter.ViewHolder();
            viewHolder.editText = view.findViewById(R.id.moLayout_item_edit);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (MoxieListItemAdapter.ViewHolder) view.getTag();
        }

        viewHolder.editText.setTag(position);
        viewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int position = (int)viewHolder.editText.getTag();
                moxieListItemList.set(position, new MoxieListItem(s.toString()));
            }
        });

        return view;
    }

    private class ViewHolder {
        EditText editText;
    }

    public List<MoxieListItem> getMoxieListItemList(){
        return moxieListItemList;
    }
}
