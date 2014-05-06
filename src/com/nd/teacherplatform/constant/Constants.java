package com.nd.teacherplatform.constant;


/**
 * 通用的常量
 * 
 * @author zmp
 * 
 */
public class Constants {

	public static final int USER_EDIT_WORD_NUM = 300; // 開箱文收起時最多顯示字數

	/* 1MB */
	public static final int MB = 1024 * 1024;
	/* 本地缓存大小 */
	public static final int CACHE_SIZE = 100;
	/* 需要缓存的空间 */
	public static final int FREE_SD_SPACE_NEEDED_TO_CACHE = CACHE_SIZE * MB;
	/* 本地圖片缓存的存储位置 */
	public static final String FILE_CACHE = FileConst.PIC_PATH;

	// 视频搜索结果排序 0--默认 1--好评度
	public static final int SEARCHVIDEO_ORDER_DEFAULT = 0;
	public static final int SEARCHVIDEO_ORDER_FAVORABLE = 1;

	// 视频搜索结果分页显示大小
	public static final int SEARCHVIDEO_PAGESIZE = 10;

	// PadServiceApi 相关常量
	public static final int PadServiceApi_Action_Common = 1001; // 完成某项学习
	public static final int PadServiceApi_Action_TeacherPlat = 2000;// 名师讲堂
	public static final String PadServiceApi_Key_Common = "strName"; // 完成某项学习-参数
	public static final String PadServiceApi_Key_TeacherPlat = "nStepFlag"; // 名师讲堂-参数
	public static final int PadServiceApi_Flag_Begin = 1;// 名师讲堂开始
	public static final int PadServiceApi_Flag_End = 2;// 名师讲堂结束

	/**
	 * 讲师：
	 */
	public static final String TEACHER = "讲师：";

	/**
	 * 节
	 */
	public static final String TOTAL_VIDEO_NUM = "节";

	/**
	 * 已下载
	 */
	public static final String HAS_DOWN_LOAD_VIDEO = "已下载 ";

}
