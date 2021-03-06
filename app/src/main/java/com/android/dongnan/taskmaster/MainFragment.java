package com.android.dongnan.taskmaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.dongnan.taskmaster.data.DataConstants;

import java.util.ArrayList;

/**
 * Created by dream on 16/7/25.
 *
 * Good Luck
 */

public class MainFragment extends android.support.v4.app.Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private LayoutInflater mLayoutInflater;
    private DataAdapter mDataAdapter;
    private ArrayList<DataConstants.StoryData> mDataList = new ArrayList<>();
    private DataConstants.ActivityCallback mCallback;

    public MainFragment() {
    }

    public void registerCallback(DataConstants.ActivityCallback callback) {
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView<?> parent, View view1, int position, long id) -> {
            mCallback.onItemClicked(mDataList.get(position));
        });
        mDataAdapter = new DataAdapter();
        listView.setAdapter(mDataAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateData();
    }

    public void updateData() {
        Log.v(TAG, "updateData");
        for (int i = 0; i < 10; i++) {
            mDataList.add(DataConstants.createTestStory(i));
        }
        mDataAdapter.notifyDataSetChanged();
    }

    private class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.data_view, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.content = (TextView)convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            DataConstants.StoryData item = mDataList.get(position);
            holder.title.setText(item.title);
            holder.content.setText(item.content);
            return convertView;
        }
    }

    private static class ViewHolder {
        public TextView title;
        public TextView content;
    }
}
