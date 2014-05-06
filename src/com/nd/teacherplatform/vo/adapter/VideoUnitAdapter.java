package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.adapter.VideoCourseAdapter.courseItemClickListener;

/**
 * 视频列表--单元适配器
 * 
 * @author shj
 * 
 */
public class VideoUnitAdapter extends BaseAdapter {

	private ArrayList<UnitVideosVo> unitVideosVos;
	private LayoutInflater mAdapterInflater;
	private Context fcontext;

	public VideoUnitAdapter(ArrayList<UnitVideosVo> unitVideosVos,
			Context context) {
		this.unitVideosVos = unitVideosVos;
		this.mAdapterInflater = LayoutInflater.from(context);
		this.fcontext = context;
	}

	public void setAdapterData(ArrayList<UnitVideosVo> unitVideosVos) {
		this.unitVideosVos = unitVideosVos;
	}

	@Override
	public int getCount() {
		return unitVideosVos.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * 解决ListView 嵌套 GridView 时，嵌套的显示不完全的问题；
	 * 参见：http://tangweiye.iteye.com/blog/1434389
	 */
	private void setListViewHeightBasedOnChildren(ListView listview) {
		VideoCourseAdapter listAdapter = (VideoCourseAdapter) listview
				.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < Math.ceil((double) listAdapter.getCount()); i++) {
			View listItem = listAdapter.getView(i, null, listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight() * 1.0;
		}

		ViewGroup.LayoutParams params = listview.getLayoutParams();
		params.height = totalHeight + 1;
		listview.setLayoutParams(params);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder_unit holder = null;
		if (convertView == null) {
			holder = new ViewHolder_unit();
			convertView = mAdapterInflater.inflate(R.layout.video_unit_item,
					null);
			holder.txt_name = (TextView) convertView
					.findViewById(R.id.tv_videoitem_title);
			holder.listview = (ListView) convertView
					.findViewById(R.id.lv_videoitem_unit);

			// convertView.setOnClickListener(new VideoUnitItemClickListener());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder_unit) convertView.getTag();
		}

		UnitVideosVo unitVideosVo = unitVideosVos.get(position);
		holder.txt_name.setText(unitVideosVo.unitName);

		VideoCourseAdapter courseAdapter = new VideoCourseAdapter(
				unitVideosVo.videoInfoVos, fcontext);
		courseAdapter.setCourseItemClickListener(courseItemClick);
		holder.listview.setAdapter(courseAdapter);
		setListViewHeightBasedOnChildren(holder.listview);
		holder.listview.setOnItemClickListener(null);

		return convertView;
	}

	private class ViewHolder_unit {
		private TextView txt_name;
		private ListView listview;
	}

	// // 单元项点击事件
	// private class VideoUnitItemClickListener implements OnClickListener {
	// @Override
	// public void onClick(View v) {
	// ;
	// }
	// }

	// TODO 视频列表项点击回调
	private courseItemClickListener courseItemClick = new courseItemClickListener() {
		public void onClick(String videoURL) {
			if (mCourseUnitItemClickListener != null)
				mCourseUnitItemClickListener.onClick(videoURL);
		}
	};

	// 视频列表项点击回调主界面
	public interface courseUnitItemClickListener {
		public void onClick(String videoURL);
	}

	private courseUnitItemClickListener mCourseUnitItemClickListener;

	public void setCourseUnitItemClickListener(courseUnitItemClickListener l) {
		mCourseUnitItemClickListener = l;
	}
}
