package com.nd.teacherplatform.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.teacherplatform.constant.URLConst;
import com.nd.teacherplatform.interfaces.BackHotWordListInterface;
import com.nd.teacherplatform.interfaces.LoadApiBackInterface;
import com.nd.teacherplatform.util.LoadServerAPIHelp;

/**
 * 加载热词
 * @author zmp
 *
 */
public class GetHotWordList
{
	public static void getHotWordList(BackHotWordListInterface backHotWordListInterface)
	{
		String paramString = "{\"op\":\"Video.gethotwordlist\",\"sid\":\"\",\"uid\":0, \"pageSize\":10 }";
		LoadServerAPIHelp.loadServerApi(URLConst.GET_HOT_WORD_URL, new LoadApiBackClass(backHotWordListInterface), paramString);
	}
	
	
	/**
	 * 服务端数据返回了
	 * @author zmp
	 *
	 */
	private static class LoadApiBackClass implements LoadApiBackInterface
	{

		private BackHotWordListInterface backHotWordListInterface;
		
		public LoadApiBackClass(BackHotWordListInterface backHotWordListInterface)
		{
			this.backHotWordListInterface = backHotWordListInterface;
		}

		@Override
		public void loadApiBackString(String jsonString)
		{
			ArrayList<String> nameStrings = new ArrayList<String>();
			try 
			{
				JSONObject jsonObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) 
				{
					JSONObject tempJsonObject = jsonArray.getJSONObject(i);
					String nameString = tempJsonObject.getString("name");
					nameStrings.add(nameString);
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			
			if(backHotWordListInterface != null)
			{
				backHotWordListInterface.setHotWordList(nameStrings);
			}
		}
		
	}
}
