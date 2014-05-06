package com.nd.teacherplatform.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.teacherplatform.constant.SearchResultTypeConst;
import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.BackSearchListInterface;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.vo.SearchListVo;
import com.nd.teacherplatform.vo.VideoInfoVo;
import com.nd.teacherplatform.vo.VideoSetVo;

/**
 * 获取搜索结果
 * @author zmp
 *
 */
public class GetSearchList
{

	 public static void getSearchList(String keyWord,int tab, int sort,int index,int pageSize,BackSearchListInterface backSearchListInterface)
	 {
		 String paramString = "{\"op\":\"video.getsearchlist\",\"sid\":\"\", \"uid\":0,\"key\":\""+keyWord+"\", \"tab\":"+tab+", \"sort\":"+sort+", \"pageIndex\":"+index+", \"pageSize\":"+pageSize+" }";
	     LoadServerAPIHelp.loadServerApi(URLConst.GET_SEARCH_LIST_URL, new LoadApiBackClass(backSearchListInterface), paramString);
	 }
	 
	 
	 /**
	  * 搜索结果的列表返回
	 * @author zmp
	 *
	 */
	private static class LoadApiBackClass implements LoadApiBackInterface
	 {
        private BackSearchListInterface backSearchListInterface;
		public LoadApiBackClass(BackSearchListInterface backSearchListInterface)
		{
			 this.backSearchListInterface = backSearchListInterface;
		}

		@Override
		public void loadApiBackString(String jsonString)
		{
			ArrayList<SearchListVo> slvos = new ArrayList<SearchListVo>();
			
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
			    for (int i = 0; i < jsonArray.length(); i++)
			    {
			    	SearchListVo slvo = new SearchListVo();
					JSONObject tempJsonObject = jsonArray.getJSONObject(i);
					int type = tempJsonObject.getInt("type");
					
					VideoSetVo videoSetVo = null;
					VideoInfoVo videoInfoVo = null;
					
					if(type == SearchResultTypeConst.RETURN_VIDEO_SET)
					{
						videoSetVo = new VideoSetVo();
						tempJsonObject = tempJsonObject.getJSONObject("data1");
						
						int id = tempJsonObject.getInt("id");
						String videoSetName = tempJsonObject.getString("videoSetName");
						int totalVideoNum = tempJsonObject.getInt("totalVideoNum");
						String preViewUrl = tempJsonObject.getString("preViewUrl");
						
						videoSetVo.id = id;
						videoSetVo.videoSetName = videoSetName;
						videoSetVo.totalVideoNum = totalVideoNum;
						videoSetVo.preViewPicUrl = preViewUrl;
					}else 
					{
						videoInfoVo = new VideoInfoVo();
						tempJsonObject = tempJsonObject.getJSONObject("data2");
						int id = tempJsonObject.getInt("id");
						String name = tempJsonObject.getString("name");
						String author = tempJsonObject.getString("author");
						String teacher = tempJsonObject.getString("teacher");
						int allTime = tempJsonObject.getInt("allTime");
						int size = tempJsonObject.getInt("size");
						String rectImageUrl = tempJsonObject.getString("rectImageUrl");
						String squareImageUrl = tempJsonObject.getString("squareImageUrl");
						String fileFormat = tempJsonObject.getString("fileFormat");
						
						videoInfoVo.id = id;
						videoInfoVo.videoName = name;
						videoInfoVo.authorName = author;
						videoInfoVo.teacherName = teacher;
						videoInfoVo.totalTime = allTime;
						videoInfoVo.videoSize = size;
						videoInfoVo.preViewUrlBig = rectImageUrl;
						videoInfoVo.preViewUrlCom = squareImageUrl;
						videoInfoVo.videoFormat = fileFormat;
					}
					
					slvo.type = type;
					slvo.videoInfoVo = videoInfoVo;
					slvo.videoSetVo = videoSetVo;
					
					slvos.add(slvo);
				}
			    
			    if(backSearchListInterface != null)
			    {
			    	backSearchListInterface.setSearchListVos(slvos);
			    }
			    
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}
			
		}
		 
	 }
	 
}
