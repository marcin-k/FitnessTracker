package com.marcin_k.gui_development.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.model.FitnessActivity;
import java.util.ArrayList;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Custom adapter for individual rows on the listView
 * class overrides BaseAdapter and all required
 * methods only
 *
 */


public class ActivityAdapter extends BaseAdapter{

    //context passed in
    private Context mContext;

    //arrayList passed in
    private ArrayList<FitnessActivity> mActivities;

    //Constructor
    public ActivityAdapter(Context context, ArrayList<FitnessActivity> activities){
        mContext = context;
        mActivities = activities;
    }

    //count of items in the activity array
    @Override
    public int getCount() {
        return mActivities.size();
    }

    //returns the fitnessActivity on a given position
    @Override
    public Object getItem(int position) {
        return mActivities.get(position);
    }

    //unused method provided by BaseAdapter class
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //used to reuse rows in ListView during scrolling to improve app performance
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //creates a view holder for new item
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.progress_list_item,null);
            holder = new ViewHolder();
            holder.icon = (ImageView)convertView.findViewById(R.id.icon);
            holder.activityType = (TextView) convertView.findViewById(R.id.activityType);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.durationValue = (TextView) convertView.findViewById(R.id.durationValue);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        FitnessActivity activity = mActivities.get(position);
        holder.icon.setImageResource(activity.getIconId());
        holder.activityType.setText(activity.getActivityType());
        holder.date.setText(activity.getTime());
        holder.durationValue.setText(activity.getDuration());
        return convertView;
    }


    //list view - holds a views added to list item layout
    private static class ViewHolder{
        ImageView icon;
        TextView activityType;
        TextView date;
        TextView durationValue;
    }
}
