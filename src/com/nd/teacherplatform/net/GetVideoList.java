package com.nd.teacherplatform.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.BackVideoListInterface;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.vo.UnitVideosVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoInfoTable;

public class GetVideoList
{

	/**
	 * ��ȡ ����� ���޿γ� ���� ��Ӧ�� ��Ƶ�б�
	 * 
	 * @param subjectID
	 *            ѧ��id
	 * @param videoSetID
	 *            ��Ƶ��id
	 * @param requireID
	 *            ����id
	 * @param backVideoListInterface
	 *            ���ݼ�����ɵĻص�����
	 */
	public static void getVideoList(int subjectID, int videoSetID, int requireID, BackVideoListInterface backVideoListInterface)
	{
		String paramString = "{\"op\":\"Video.getcellvideolist\",\"sid\":\"\",\"uid\":\"\",\"subjectId\":" + subjectID + ",\"collectId\":" + videoSetID + ",\"stageId\":" + requireID + "}";
		LoadServerAPIHelp.loadServerApi(URLConst.GET_REQUIRE_VIDEO_URL, new LoadApiBackClass(subjectID, videoSetID, requireID, backVideoListInterface), paramString);
	}

	private static class LoadApiBackClass implements LoadApiBackInterface
	{
		private videoInfoEntity videoInfo;// = new videoInfoEntity();
		private BackVideoListInterface backVideoListInterface;

		public LoadApiBackClass(int subjectID, int videoSetID, int requireID, BackVideoListInterface backVideoListInterface)
		{
			this.videoInfo = new videoInfoEntity();
			this.videoInfo.setRequireID(requireID);
			this.videoInfo.setSubjectID(subjectID);
			this.videoInfo.setVideoSetID(videoSetID);
			this.backVideoListInterface = backVideoListInterface;
		}

		@Override
		public void loadApiBackString(String jsonString)
		{
			// TODO Auto-generated method stub
			// ArrayList<UnitVideosVo> uvos = parseVideoListInfo(jsonString,
			// subjectID, videoSetID, requireID);
			// if (backVideoListInterface != null) {
			// backVideoListInterface.setVideoList(uvos);
			// }
			new loadVideoListInfo().execute(jsonString, videoInfo, backVideoListInterface);
		}
	}

	private static class videoInfoEntity
	{
		private int subjectID;
		private int requireID;
		private int videoSetID;

		public videoInfoEntity()
		{
			subjectID = 0;
			requireID = 0;
			videoSetID = 0;
		}

		public int getSubjectID()
		{
			return subjectID;
		}

		public void setSubjectID(int subjectID)
		{
			this.subjectID = subjectID;
		}

		public int getRequireID()
		{
			return requireID;
		}

		public void setRequireID(int requireID)
		{
			this.requireID = requireID;
		}

		public int getVideoSetID()
		{
			return videoSetID;
		}

		public void setVideoSetID(int videoSetID)
		{
			this.videoSetID = videoSetID;
		}
	}

	/**
	 * {
	 * 'data':[{'title':'��һ��Ԫ','video':[{'id':1,'name':'��Ƶ����','author':'����','teacher':'��ʦ','allTime':83,'size':1250,'rectImageUrl':'www.baidu.com/favion.icon','squareImageUrl':'www.google.com/favion.icon','fileFormat':'�ļ���ʽ'},{'id':1,'name':'��Ƶ����','author':'����','teacher':'��ʦ','allTime':83,'size':1250,'rectImageUrl':'www.baidu.com/favion.icon','squareImageUrl':'www.google.com/favion.icon','fileFormat':'�ļ���ʽ'},{'id':1,'name':'��Ƶ����','author':'����','teacher':'��ʦ','allTime':83,'size':1250,'rectImageUrl':'www.baidu.com/favion.icon','squareImageUrl':'www.google.com/favion.icon','fileFormat':'�ļ���ʽ
	 * ' } ] } , { } , { } ] } ���ر��޿� ӵ�е� ��Ƶ��Ϣ
	 * 
	 * @author zmp
	 * 
	 */

	/**
	 * ����ÿһ����������� ��Ƶ�б���Ϣ
	 */
	private static class loadVideoListInfo extends AsyncTask<Object, Object, Object>
	{
		private BackVideoListInterface backVideoListInterface;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... arg0)
		{
			String jsonString = (String) arg0[0];
			videoInfoEntity videoInfo = (videoInfoEntity) arg0[1];
			backVideoListInterface = (BackVideoListInterface) arg0[2];
			ArrayList<UnitVideosVo> unitVideosVos = new ArrayList<UnitVideosVo>();
			try {
				JSONObject jsonObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonObject.getJSONArray("data");

				// ���浥Ԫ��Ϣ������

				for (int i = 0; i < jsonArray.length(); i++) {
					UnitVideosVo unitVideosVo = new UnitVideosVo();

					JSONObject tempJsonObject = jsonArray.getJSONObject(i);
					int unitId = tempJsonObject.getInt("id");// ��ԪID
					String title = tempJsonObject.getString("title");// ��Ԫ����
					JSONArray tempJsonArray = tempJsonObject.getJSONArray("video");

					// ��ŵ�Ԫ������Ƶ��Ϣ������
					ArrayList<VideoInfoVo> videoInfoVos = new ArrayList<VideoInfoVo>();

					// ������Ԫ������Ƶ����Ϣ
					for (int j = 0; j < tempJsonArray.length(); j++) {
						tempJsonObject = tempJsonArray.getJSONObject(j);
						int id = tempJsonObject.getInt("id");
						String name = tempJsonObject.getString("name");
						String author = tempJsonObject.getString("author");
						String teacher = tempJsonObject.getString("teacher");
						int allTime = tempJsonObject.getInt("allTime");
						int size = tempJsonObject.getInt("size");
						String rectImageUrl = tempJsonObject.getString("rectImageUrl");
						String squareImageUrl = tempJsonObject.getString("squareImageUrl");
						String fileFormat = tempJsonObject.getString("fileFormat");

						// ��Ƶ��Ϣ
						VideoInfoVo vo = new VideoInfoVo();
						vo.id = id;
						vo.videoName = name;
						vo.authorName = author;
						vo.teacherName = teacher;
						vo.totalTime = allTime;
						vo.videoSize = size;
						vo.preViewUrlBig = rectImageUrl;
						vo.preViewUrlCom = squareImageUrl;
						vo.videoFormat = fileFormat;
						vo.sujectID = videoInfo.getSubjectID();
						vo.setVideoSetID(videoInfo.getVideoSetID());
						vo.setVideoRequireType(videoInfo.getRequireID());

						videoInfoVos.add(vo);

						// �������ݿ�
						HandlerVideoInfoTable.insertDataToVideoInfoTable(vo);
					}

					// ��Ԫ��Ϣ
					unitVideosVo.id = unitId;
					unitVideosVo.unitName = title;
					unitVideosVo.videoInfoVos = videoInfoVos;

					// ��Ԫ��Ϣ�ļ���
					unitVideosVos.add(unitVideosVo);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return unitVideosVos;
		}

		@Override
		protected void onProgressUpdate(Object... values)
		{
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Object result)
		{
			super.onPostExecute(result);
			if (backVideoListInterface != null) {
				backVideoListInterface.setVideoList((ArrayList<UnitVideosVo>) result);
			}
		}
	}
}
