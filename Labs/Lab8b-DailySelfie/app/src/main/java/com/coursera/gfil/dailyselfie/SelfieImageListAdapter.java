package com.coursera.gfil.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelfieImageListAdapter extends BaseAdapter {

    private final List<SelfieImageItem> mItems = new ArrayList<SelfieImageItem>();
    private final Context mContext;

    private static final String TAG = "SelfieImageListAdapter";

    public SelfieImageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final SelfieImageItem selfieItem = mItems.get(position);

        RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.selfie_item, null);

        final TextView titleView = (TextView) itemLayout.findViewById(R.id.imageName);
        titleView.setText(selfieItem.getImageName());

        final ImageView imageView = (ImageView) itemLayout.findViewById(R.id.imageView);
        imageView.setImageBitmap(selfieItem.getThumbnailImage());

        return itemLayout;
    }

    public void add(SelfieImageItem item) {
        mItems.add(item);
        notifyDataSetChanged();
    }
}

