package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.util.DataFormatUtil;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.KnowLedgeVo;

/**
 * 视频列表--要点适配器
 * 
 * @author shj
 * 
 */
public class VideoGistAdapter extends BaseAdapter {

	private ArrayList<KnowLedgeVo> knowLedgeVos;
	private LayoutInflater mAdapterInflater;

	public VideoGistAdapter(ArrayList<KnowLedgeVo> knowLedgeVos, Context context) {
		this.knowLedgeVos = knowLedgeVos;
		this.mAdapterInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return knowLedgeVos.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
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
			convertView = mAdapterInflater.inflate(R.layout.video_gist_item,
					null);
			holder.img = (ImageView) convertView
					.findViewById(R.id.iv_videogist_item_icon);
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_videogist_item_content);
			holder.showTime = (TextView) convertView
					.findViewById(R.id.tv_videogist_item_duration);

			convertView.setOnClickListener(new VideoGistItemClickListener());
			convertView.setTag(R.id.tag_first, holder);
		} else {
			holder = (ViewHolder_unit) convertView.getTag(R.id.tag_first);
		}

		KnowLedgeVo knowLedgeVo = knowLedgeVos.get(position);
		holder.content.setText(knowLedgeVo.content);
		holder.showTime.setText(DataFormatUtil
				.second2Format(knowLedgeVo.showTime));

		ImageDownloader imageDownloader = new ImageDownloader(
				SingleToolClass.curContext);
		imageDownloader.download(knowLedgeVo.imgUrl, holder.img,
				R.drawable.error_net_pic, R.drawable.error_net_pic, true);
		convertView.setTag(R.id.tag_second, knowLedgeVo);

		return convertView;
	}

	private class ViewHolder_unit {
		private ImageView img;
		private TextView content; // 内容
		private TextView showTime; // 时间点
	}

	// 单元项点击事件
	private class VideoGistItemClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			KnowLedgeVo knowLedgeVo = (KnowLedgeVo) v.getTag(R.id.tag_second);
			if (mGistItemClickListener != null)
				mGistItemClickListener.onClick(knowLedgeVo.showTime);
		}
	}

	// 视频要点列表项点击回调主界面
	public interface gistItemClickListener {
		public void onClick(int time);
	}

	private gistItemClickListener mGistItemClickListener;

	public void setGistItemClickListener(gistItemClickListener l) {
		mGistItemClickListener = l;
	}
}
