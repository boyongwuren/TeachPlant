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
 * ��Ƶ�б�--�γ�������
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
		private TextView courseName; // �γ���
		private TextView teacherName; // ��ʦ
		private TextView totalTime; // ��Ƶʱ��
		private ImageButton btnOper; // ���Ż�����
	}

	// ��Ԫ�����¼�
	private class videoCourseItemClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			VideoInfoVo videoInfoVo = (VideoInfoVo) v.getTag(R.id.tag_second);
			if (mCourseItemClickListener != null)
				mCourseItemClickListener.onClick(videoInfoVo.videoURL);
		}
	}

	// ���Ż����ذ�ť���
	private ImageButton.OnClickListener btnOperOnClick = new ImageButton.OnClickListener() {
		@Override
		public void onClick(View v) {
			VideoInfoVo videoInfoVo = (VideoInfoVo) v.getTag();
			if (null != videoInfoVo.videoURL && videoInfoVo.videoURL.isEmpty()) {
				// ��û����Ƶ��ַ
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
	 * ���� ��Ƶ���ŵĵ�ַ ���
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

			// ��ȡ������Ƶ��Ӧ�����ص�ַ�ˡ��������ݱ�
			// ContentValues cv = new ContentValues();
			// cv.put(VideoInfoTableConst.VIDEOINFO__VIDEO_URL, urlString);
			// HandlerVideoInfoTable.updataTable(clickVideoVo.id, cv);
		}
	};

	// ��ƵҪ���б������ص�������
	public interface courseItemClickListener {
		public void onClick(String videoURL);
	}

	private static courseItemClickListener mCourseItemClickListener;

	public void setCourseItemClickListener(courseItemClickListener l) {
		mCourseItemClickListener = l;
	}
}
