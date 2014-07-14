package com.example.lbaseexample.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lbaseexample.R;
import com.example.lbaseexample.common.MApplication;
import com.example.lbaseexample.entity.ListEntity;
import com.leo.base.adapter.LBaseAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ListViewAdapter extends LBaseAdapter<ListEntity> {

	private static final String KEY = ListViewAdapter.class.getSimpleName();
	
	private ImageLoader imageLoader;
	
	private RelativeLayout.LayoutParams layoutParams;
	
	public ListViewAdapter(Context context, List<ListEntity> list) {
		super(context, list, true);
		imageLoader = getAdapter().getImageLoader();
		int imgWidth = MApplication.get().getDiaplayWidth() / 3;
		int imgHeight = imgWidth;
		layoutParams = new RelativeLayout.LayoutParams(imgWidth, imgHeight);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		convertView = getConvertView(KEY + position);
		if(convertView != null) {
			return convertView;
		} else {
			final LayoutInflater inflater = getInflater();
			convertView = inflater.inflate(R.layout.item_listview, null);
			holder = new ViewHolder();
			holder.imageview = (ImageView) convertView.findViewById(R.id.item_imageview);
			holder.imageview.setLayoutParams(layoutParams);
			holder.textview = (TextView) convertView.findViewById(R.id.item_textview);
			addConvertView(KEY + position, convertView);
		}
		ListEntity entity = (ListEntity) getItem(position);
		holder.textview.setText(entity.content);
		imageLoader.displayImage(entity.url, holder.imageview);
		return convertView;
	}
	
	static class ViewHolder {
		ImageView imageview;
		TextView textview;
	}

}
