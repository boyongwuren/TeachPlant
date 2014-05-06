package com.nd.teacherplatform.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.BackSearchCountInterface;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;
import com.nd.teacherplatform.vo.SearchCountVo;

/**
 *  获取搜索词结果数
 * @author zmp
 *
 */
public class GetSearchCount
{

	 public static void getSearchCount(String keyWord,BackSearchCountInterface backSearchCount)
	 {
		 String paramString = "{\"op\":\"video.getsearchcount\",\"sid\":\"\",\"uid\":0,\"key\":\""+keyWord+"\"}";
	     LoadServerAPIHelp.loadServerApi(URLConst.GET_SEARCHCOUNT_URL, new LoadApiBackClass(backSearchCount), paramString);
	 }
	 
	 /**
	  * 返回了 搜索结果数量回来了
	 * @author zmp
	 *
	 */
	private static class LoadApiBackClass implements LoadApiBackInterface
	 {

		private BackSearchCountInterface backSearchCountInterface;
		public LoadApiBackClass(BackSearchCountInterface backSearchCountInterface)
		{
			 this.backSearchCountInterface = backSearchCountInterface;
		}

		@Override
		public void loadApiBackString(String jsonString)
		{
			ArrayList<SearchCountVo> scvos = new ArrayList<SearchCountVo>();
			
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject tempJsonObject = jsonArray.getJSONObject(i);
					int tab = tempJsonObject.getInt("tab");
					int count = tempJsonObject.getInt("count");
					
					SearchCountVo vo = new SearchCountVo();
					vo.tab = tab;
					vo.count = count;
					scvos.add(vo);
				}
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}
			
			if(backSearchCountInterface != null)
			{
				backSearchCountInterface.setSearchCountVo(scvos);
			}
			
		}
		 
	 }

}
