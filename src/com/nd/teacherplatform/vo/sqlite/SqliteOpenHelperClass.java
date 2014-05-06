package com.nd.teacherplatform.vo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nd.teacherplatform.constant.databaseconst.*;
import com.nd.teacherplatform.util.SingleToolClass;

/**
 * 数据库操作
 * 
 * @author zmp
 * 
 */
public class SqliteOpenHelperClass extends SQLiteOpenHelper
{
 
	public SqliteOpenHelperClass(Context context)
	{
		super(context, DataBaseConst.DATABASE_NAME, null, DataBaseConst.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// 创建表
		db.execSQL(VideoInfoTableConst.CREATE_VIDEOINFO_TABLE);
		db.execSQL(VideoSetTableConst.CREATE_VIDEOSET_TABLE);
		db.execSQL(CollectTableConst.CREATE_COLLECT_TABLE);
		db.execSQL(SearchHistoryTableConst.CREATE_SEARCHHISTORY_TABLE);
		db.execSQL(LoadingTableConst.CREATE_DOWNLOAD_TABLE);
		db.execSQL(HasDownLoadTableConst.CREATE_HASDOWN_TABLE);
        db.execSQL(RecentPlayTableConst.CREATE_RECENTPLAY_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(VideoInfoTableConst.DELETE_VIDEOINFO_TABLE);
		db.execSQL(VideoSetTableConst.DELETE_VIDEOSET_TABLE);
		db.execSQL(CollectTableConst.DELETE_COLLECT_TABLE);
		db.execSQL(SearchHistoryTableConst.DELETE_SEARCHHISTORY_TABLE);
		db.execSQL(LoadingTableConst.DELETE_DOWNLOAD_TABLE);
		db.execSQL(HasDownLoadTableConst.DELETE_HASDOWN_TABLE);
        db.execSQL(RecentPlayTableConst.DELETE_RECENTPLAY_TABLE);
		onCreate(db);
	}

	private static SqliteOpenHelperClass sqliteOpenHelperClass;

	/**
	 * 操作数据库的入口
	 * 
	 * @return
	 */
	public static SqliteOpenHelperClass getInstance()
	{
		if (sqliteOpenHelperClass == null) 
		{
			sqliteOpenHelperClass = new SqliteOpenHelperClass(SingleToolClass.curContext);
		}

		return sqliteOpenHelperClass;
	}


}
