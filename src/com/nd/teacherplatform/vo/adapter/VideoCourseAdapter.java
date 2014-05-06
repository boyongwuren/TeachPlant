package com.nd.teacherplatform.vo.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.DataFormatUtil;
import com.nd.teacherplatform.util.ImageDownloader;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.util.SingleToolClass;
import com.nd.teacherplatform.vo.VideoInfoVo;

/**
 * 视频列表--课程适配器
 * 
 * @author shj
 * 
 */
public class VideoCourseAdapter extends BaseAdapter {

	private ArrayList<VideoInfoVo> videoInfoVos;
	private LayoutInflater mAdapterInflater;

	public VideoCourseAdapter(ArrayList<VideoInfoVo> videoInfoVos,
			Context context) {
		this.videoInfoVos = videoInfoVos;
		this.mAdapterInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return videoInfoVos.size();
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
		ViewHolder_course holder = null;
		if (convertView == null) {
			holder = new ViewHolder_course();
			convertView = mAdapterInflater.inflate(R.layout.video_course_item,
					null);
			holder.courseIcon = (ImageView) convertView
					.findViewById(R.id.iv_videolist_item);
			holder.courseName = (TextView) convertView
					.findViewById(R.id.tv_videolist_item_name);
			holder.teacherName = (TextView) convertView
					.findViewById(R.id.tv_videolist_item_teacher);
			holder.totalTime = (TextView) convertView
					.findViewById(R.id.tv_videolist_item_duration);
			holder.btnOper = (ImageButton) convertView
					.findViewById(R.id.btn_videolist_item_op);

			convertView.setOnClickListener(new videoCourseItemClickListener());
			convertView.setTag(R.id.tag_first, holder);

		} else {
			holder = (ViewHolder_course) convertView.getTag(R.id.tag_first);
		}

		VideoInfoVo videoInfoVo = videoInfoVos.get(position);
		holder.courseName.setText(videoInfoVo.videoName);
		holder.teacherName.setText(videoInfoVo.teacherName);
		holder.totalTime.setText(DataFormatUtil
				.second2Format(videoInfoVo.totalTime));
		holder.btnOper.setTag(videoInfoVo);
		holder.btnOper.setOnClickListener(btnOperOnClick);

		ImageDownloader imageDownloader = new ImageDownloader(
				SingleToolClass.curContext);
		imageDownloader.download(videoInfoVo.preViewUrlCom, holder.courseIcon,
				R.drawable.error_net_pic, R.drawable.error_net_pic, true);

		convertView.setTag(R.id.tag_second, videoInfoVo);

		return convertView;
	}

	private class ViewHolder_course {
		private ImageView courseIcon;
		private TextView courseName; // 课程名
		private TextView teacherName; // 讲师
		private TextView totalTime; // 视频时长
		private ImageButton btnOper; // 播放或下载
	}

	// 单元项点击事件
	private class videoCourseItemClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			VideoInfoVo videoInfoVo = (VideoInfoVo) v.getTag(R.id.tag_second);
			if (mCourseItemClickListener != null)
				mCourseItemClickListener.onClick(videoInfoVo.videoURL);
		}
	}

	// 播放或下载按钮点击
	private ImageButton.OnClickListener btnOperOnClick = new ImageButton.OnClickListener() {
		@Override
		public void onClick(View v) {
			VideoInfoVo videoInfoVo = (VideoInfoVo) v.getTag();
			if (null != videoInfoVo.videoURL && videoInfoVo.videoURL.isEmpty()) {
				// 还没有视频地址
				String paramString = "{\"op\":\"Video.getdownloadurl\",\"sid\":\"\", \"uid\":\"\",\"videoId\":"
						+ videoInfoVo.id
						+ ",\"terminal\":1,\"source\":1,\"deviceId\":\""
						+ SingleToolClass.getServerId()
						+ "\",\"username\":\"\",\"module\":1}";
				LoadServerAPIHelp.loadServerApi(URLConst.GET_VIDEO_PLAY_URL,
						new LoadApiBackClass(), paramString);
			} else if (mCourseItemClickListener != null)
				mCourseItemClickListener.onClick(videoInfoVo.videoURL);
		}
	};

	/**
	 * 加载 视频播放的地址 完成
	 * 
	 * @author zmp
	 * 
	 */
	private static class LoadApiBackClass implements LoadApiBackInterface {
		@Override
		public void loadApiBackString(String jsonString) {
			String urlString = "";
			try {
				JSONObject jsonObject = new JSONObject(jsonString);
				jsonObject = jsonObject.getJSONObject("data");
				urlString = jsonObject.getString("url");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (mCourseItemClickListener != null)
				mCourseItemClickListener.onClick(urlString);

			// 获取到了视频对应的下载地址了。更新数据表
			// ContentValues cv = new ContentValues();
			// cv.put(VideoInfoTableConst.VIDEOINFO__VIDEO_URL, urlString);
			// HandlerVideoInfoTable.updataTable(clickVideoVo.id, cv);
		}
	};

	// 视频要点列表项点击回调主界面
	public interface courseItemClickListener {
		public void onClick(String videoURL);
	}

	private static courseItemClickListener mCourseItemClickListener;

	public void setCourseItemClickListener(courseItemClickListener l) {
		mCourseItemClickListener = l;
	}
}
