package com.copyflynx.helper;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.copyflynx.R;
import com.copyflynx.database.DataList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by harneev on 5/10/2015.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private static LayoutInflater inflater = null;

    private ArrayList<String> urlArrayList = DataList.getURL();
    private ArrayList<String> TitlelArrayList = DataList.getTitles();

    public ListAdapter(Context context) {
        mContext = context;

        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return urlArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        Holder holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_view_item, null);

            holder = new Holder();
            holder.url = (TextView) view.findViewById(R.id.list_view_url);
            holder.title = (TextView) view.findViewById(R.id.list_view_title);

            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        if (urlArrayList.size() <= 0) {
            holder.title.setText("No Data");
        } else {
            holder.url.setText(urlArrayList.get(i));
            holder.title.setText(urlArrayList.get(i));
        }
        return view;
    }

    public static class Holder {
        public TextView url;
        public TextView title;
    }
}
