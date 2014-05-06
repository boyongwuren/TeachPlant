package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.SearchResultTypeConst;
import com.nd.teacherplatform.vo.SearchListVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;

/**
 * 搜索结果--视频列表的适配器
 * 
 * @author shj
 * 
 */
public class SearchVideoListAdapter extends BaseAdapter {

	private ArrayList<SearchListVo> slvos;
	private LayoutInflater mAdapterInflater;

	public SearchVideoListAdapter(ArrayList<SearchListVo> slvos, Context context) {
		this.slvos = slvos;
		this.mAdapterInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return slvos.size();
	}

	@Override
	public SearchListVo getItem(int position) {
		return slvos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder_unit holder = null;
		if (convertView == null) {
			holder = new ViewHolder_unit();
			convertView = mAdapterInflater.inflate(R.layout.video_result_item,
					null);
			holder.videoName = (TextView) convertView
					.findViewById(R.id.tv_videoresult_item_videoname);
			holder.videosetName = (TextView) convertView
					.findViewById(R.id.iv_videoresult_item_videosetname);
			holder.videoTeacherName = (TextView) convertView
					.findViewById(R.id.tv_videoresult_item_teachername);
			holder.videoTeacherValue = (TextView) convertView
					.findViewById(R.id.tv_videoresult_item_teachervalue);
			holder.videoTypeName = (TextView) convertView
					.findViewById(R.id.iv_videoresult_item_requiretype);

			convertView.setOnClickListener(new VideoGistItemClickListener());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder_unit) convertView.getTag();
		}

		SearchListVo scVo = slvos.get(position);
		if (scVo.type == SearchResultTypeConst.RETURN_VIDEO_INFO) { // 视频信息
			VideoInfoVo videoInfoVo = scVo.videoInfoVo;
			holder.videoTeacherName.setVisibility(View.VISIBLE);
			holder.videoTeacherValue.setVisibility(View.VISIBLE);
			holder.videoName.setText(videoInfoVo.videoName);
			holder.videosetName.setText(videoInfoVo.videoSetName);
			holder.videoTeacherValue.setText(videoInfoVo.teacherName);
			holder.videoTypeName.setText(videoInfoVo.videoRequireTypeName);
		} else if (scVo.type == SearchResultTypeConst.RETURN_VIDEO_SET) {// 视频集信息
			VideoSetVo videoSetVo = scVo.videoSetVo;
			holder.videoName.setText(videoSetVo.videoSetName);
			holder.videosetName.setText("共" + videoSetVo.totalVideoNum + "个视频");
			holder.videoTeacherName.setVisibility(View.GONE);
			holder.videoTeacherValue.setVisibility(View.GONE);
			holder.videoTypeName.setText(""); // 出版社 如：人教版 、科教版
		}

		return convertView;
	}

	private class ViewHolder_unit {
		private TextView videoName; // 视频名称
		private TextView videosetName; // 视频集名称
		private TextView videoTeacherName; // 讲师
		private TextView videoTeacherValue; // 讲师
		private TextView videoTypeName; // 视频类型名称(必修、辅修)
	}

	// 单元项点击事件
	private class VideoGistItemClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (mListItemClickListener != null)
				mListItemClickListener.onClick(1);
		}
	}

	// 视频列表项点击回调主界面
	public interface videoListItemClickListener {
		public void onClick(int time);
	}

	private videoListItemClickListener mListItemClickListener;

	public void setListItemClickListener(videoListItemClickListener l) {
		mListItemClickListener = l;
	}

}
