package com.nd.teacherplatform.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.BackVideoKnowLedgleInterface;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.vo.KnowLedgeVo;

/**
 * 获取知识要点
 * @author zmp
 *
 */
public class GetKnowLedgeList
{
      /**
       * 获取知识要点
     * @param videoId 视频id
     */
    public static void getKnowLedgeList(int videoId,BackVideoKnowLedgleInterface backVideoKnowLedgleInterface)
      {
    	String paramString = "{\"op\":\"Video.getknowledgelist\",\"sid\": \"0\",\"uid\":0, \"videoId\":"+videoId+" }";
		LoadServerAPIHelp.loadServerApi(URLConst.GET_VIDEO_KNOW_URL, new LoadApiBackClass(backVideoKnowLedgleInterface),paramString);
      }
    
    
    private static class LoadApiBackClass implements LoadApiBackInterface
    {

    	private BackVideoKnowLedgleInterface backVideoKnowLedgleInterface;
    	
    	public LoadApiBackClass(BackVideoKnowLedgleInterface backVideoKnowLedgleInterface)
    	{
    		this.backVideoKnowLedgleInterface = backVideoKnowLedgleInterface;
    	}
    	
		@Override
		public void loadApiBackString(String jsonString)
		{
			ArrayList<KnowLedgeVo> vos = new ArrayList<KnowLedgeVo>();
			
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) 
				{
					JSONObject tempJsonObject = jsonArray.getJSONObject(i);
					int videoId = tempJsonObject.getInt("id");
					String content = tempJsonObject.getString("content");
					int showTime = tempJsonObject.getInt("showTime");
					String imgUrl = tempJsonObject.getString("imgUrl");
				    
					KnowLedgeVo vo = new KnowLedgeVo();
					vo.videoId = videoId;
					vo.content = content;
					vo.showTime = showTime;
					vo.imgUrl = imgUrl;
					
					vos.add(vo);
				}
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}
			
			if(backVideoKnowLedgleInterface != null)
			{
				backVideoKnowLedgleInterface.setKonwLedgle(vos);
			}
		}
    	
		 
		
		
    }
    
    
    
}
