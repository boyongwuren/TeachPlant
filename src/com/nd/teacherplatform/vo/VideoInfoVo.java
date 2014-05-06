package com.nd.teacherplatform.vo;

import java.io.Serializable;

import com.nd.teacherplatform.constant.SubjectTypeConst;
import com.nd.teacherplatform.constant.VideoRequireConst;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoSetTable;

/**
 * 视频信息
 * 
 * @author zmp
 * 
 */
public class VideoInfoVo implements Serializable
{

	private static final long serialVersionUID = 100;

	/**
	 * 视频的标识
	 */
	public int id = 0;

	/**
	 * 视频名称
	 */
	public String videoName = "";

	/**
	 * 视频所属的视频集id
	 */
	private int videoSetID = 0;
	
	/**
	 * 视频所属的视频集名称
	 */
	public String videoSetName = "视频集名称";

	/**
	 * 设置视频集的ID
	 * @param videoSetID
	 */
	public void setVideoSetID(int videoSetID)
	{
		this.videoSetID = videoSetID;
		videoSetName = HandlerVideoSetTable.getVideoSetName(videoSetID);
	}
	
	/**
	 * 获取视频集id
	 * @return
	 */
	public int getVideoSetID()
	{
		return this.videoSetID;
	}
	
	/**
	 * 视频所属的 学科ＩＤ
	 */
	public int sujectID = 0;
	
	/**
	 * 获取学科名称
	 * @return
	 */
	public String getSubjectName()
	{
		switch(sujectID)
		{
			case SubjectTypeConst.DILI_ID:
				return SubjectTypeConst.DILI;
			
			case SubjectTypeConst.HUAXUE_ID:
				return SubjectTypeConst.HUAXUE;

			case SubjectTypeConst.LISHI_ID:
				return SubjectTypeConst.LISHI;

			case SubjectTypeConst.QITA_ID:
				return SubjectTypeConst.QITA;

			case SubjectTypeConst.SHENGWU_ID:
				return SubjectTypeConst.SHENGWU;

			case SubjectTypeConst.SHUXUE_ID:
				return SubjectTypeConst.SHUXUE;

			case SubjectTypeConst.SUOYOU_ID:
				return SubjectTypeConst.SUOYOU;

			case SubjectTypeConst.WULI_ID:
				return SubjectTypeConst.WULI;

			case SubjectTypeConst.YINGYU_ID:
				return SubjectTypeConst.YINGYU;
				
			case SubjectTypeConst.YUWEN_ID:
				return SubjectTypeConst.YUWEN;

			case SubjectTypeConst.ZHENGZHI_ID:
				return SubjectTypeConst.ZHENGZHI;
		}
		
		return "";
	}

	/**
	 * 视频的类型 必修课 辅修课
	 * 
	 * @link VideoTypeConst
	 */
	private int videoRequireType = 0;

	/**
	 * 视频的类型字符串 必修课 辅修课
	 */
	public String videoRequireTypeName = "";
	

	/**
	 * 设置视频类型
	 * 
	 * @param type
	 */
	public void setVideoRequireType(int type)
	{
		videoRequireType = type;

		switch (type)
		{
			case VideoRequireConst.MINOR_COURSE:
				videoRequireTypeName = "辅修课";
				break;

			case VideoRequireConst.REQUIRED_COURSE_1:
				videoRequireTypeName = "必修一";
				break;

			case VideoRequireConst.REQUIRED_COURSE_2:
				videoRequireTypeName = "必修二";
				break;

			case VideoRequireConst.REQUIRED_COURSE_3:
				videoRequireTypeName = "必修三";
				break;

			case VideoRequireConst.REQUIRED_COURSE_4:
				videoRequireTypeName = "必修四";
				break;

			case VideoRequireConst.REQUIRED_COURSE_5:
				videoRequireTypeName = "必修五";
				break;

			case VideoRequireConst.REQUIRED_COURSE_6:
				videoRequireTypeName = "必修六";
				break;

			default:
				break;
		}
	}

	/**
	 * 获取视频的类型
	 * 
	 * @return
	 */
	public int getVideoRequireType()
	{
		return videoRequireType;
	}

	/**
	 * 单元编号
	 */
	public int cellID = 0;

	/**
	 * 作者的名称
	 */
	public String authorName = "";

	/***
	 * 讲课老师的名称
	 */
	public String teacherName = "";

	/**
	 * 视频总的时间  单位：毫秒
	 */
	public long totalTime = 0;

	/**
	 * 视频文件大小
	 */
	public int videoSize = 0;

	/**
	 * 视频预览图片的地址(大图片--最近观看第一个使用)
	 */
	public String preViewUrlBig = "";

	/**
	 * 视频预览图片的地址(正常大小)
	 */
	public String preViewUrlCom = "";

	/**
	 * 视频的播放地址
	 */
	public String videoURL = "";

	/**
	 * 视频文件的格式
	 */
	public String videoFormat = "";

	/**
	 * 视频上次播放到的时间
	 */
	public long playTime = 0;

	/**
	 * 视频最后播放时间
	 */
	public String lastPlayTime = "";

}
