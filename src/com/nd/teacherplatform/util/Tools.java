package com.nd.teacherplatform.util;

import java.lang.reflect.Field;

public class Tools {

	public static int getStatusBarHeight()
	{    
	    Class<?> c = null;    
	    Object obj = null;    
	    Field field = null;    
	    int x = 0,
	    statusBarHeight = 0;    
	    try
        {    
	        c = Class.forName("com.android.internal.R$dimen");    
	        obj = c.newInstance();    
	        field = c.getField("status_bar_height");    
	        x = Integer.parseInt(field.get(obj).toString());    
	        statusBarHeight = SingleToolClass.curContext.getResources().getDimensionPixelSize(x);    
	    }
	     catch(Exception e1)
	     {    
	        e1.printStackTrace();    
	     }
	    
	    return statusBarHeight;   
	}

}
