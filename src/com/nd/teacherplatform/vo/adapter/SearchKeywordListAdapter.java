package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;

/**
 * 搜索关键词列表的适配器（热门搜索与搜索历史共用）
 * 
 * @author shj
 * 
 */
public class SearchKeywordListAdapter extends BaseAdapter {

	private ArrayList<String> hotSearchKeys;
	private LayoutInflater mAdapterInflater;

	public SearchKeywordListAdapter(ArrayList<String> hotSearchKeys,
			Context context) {
		this.hotSearchKeys = hotSearchKeys;
		this.mAdapterInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return hotSearchKeys.size();
	}

	@Override
	public String getItem(int position) {
		return hotSearchKeys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mAdapterInflater.inflate(R.layout.search_key_item,
					null);
			holder.searchKey = (TextView) convertView
					.findViewById(R.id.tv_key_item);

			convertView.setOnClickListener(new VideoSearchItemClick());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.searchKey.setText(hotSearchKeys.get(position));
		if (position > 0
				&& position % 2 == 0
				&& hotSearchKeys.get(position).length() > hotSearchKeys.get(
						position - 1).length()) {
			holder.searchKey.measure(0, 0);
			holder.searchKey.setHeight(holder.searchKey.getLineCount()
					* holder.searchKey.getLineHeight());
			convertView.measure(0, 0);
		}
		convertView.setTag(R.id.tag_second, hotSearchKeys.get(position));
		return convertView;
	}

	private class ViewHolder {
		private TextView searchKey; // 内容
	}

	// 单元项点击事件
	private class VideoSearchItemClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (mVideoSearchItemClickListener != null) {
				String keyWord = (String) v.getTag(R.id.tag_second);
				mVideoSearchItemClickListener.onClick(keyWord);
			}
		}
	}

	// 视频要点列表项点击回调主界面
	public interface videoSearchItemClickListener {
		public void onClick(String keyWord);
	}

	private videoSearchItemClickListener mVideoSearchItemClickListener;

	public void setAdapterItemClickListener(videoSearchItemClickListener l) {
		mVideoSearchItemClickListener = l;
	}

}
