package com.nd.teacherplatform.vo;

import java.io.Serializable;

import com.nd.teacherplatform.constant.SubjectTypeConst;
import com.nd.teacherplatform.constant.VideoRequireConst;
import com.nd.teacherplatform.vo.sqlite.HandlerVideoSetTable;

/**
 * ��Ƶ��Ϣ
 * 
 * @author zmp
 * 
 */
public class VideoInfoVo implements Serializable
{

	private static final long serialVersionUID = 100;

	/**
	 * ��Ƶ�ı�ʶ
	 */
	public int id = 0;

	/**
	 * ��Ƶ����
	 */
	public String videoName = "";

	/**
	 * ��Ƶ��������Ƶ��id
	 */
	private int videoSetID = 0;
	
	/**
	 * ��Ƶ��������Ƶ������
	 */
	public String videoSetName = "��Ƶ������";

	/**
	 * ������Ƶ����ID
	 * @param videoSetID
	 */
	public void setVideoSetID(int videoSetID)
	{
		this.videoSetID = videoSetID;
		videoSetName = HandlerVideoSetTable.getVideoSetName(videoSetID);
	}
	
	/**
	 * ��ȡ��Ƶ��id
	 * @return
	 */
	public int getVideoSetID()
	{
		return this.videoSetID;
	}
	
	/**
	 * ��Ƶ������ ѧ�ƣɣ�
	 */
	public int sujectID = 0;
	
	/**
	 * ��ȡѧ������
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
	 * ��Ƶ������ ���޿� ���޿�
	 * 
	 * @link VideoTypeConst
	 */
	private int videoRequireType = 0;

	/**
	 * ��Ƶ�������ַ��� ���޿� ���޿�
	 */
	public String videoRequireTypeName = "";
	

	/**
	 * ������Ƶ����
	 * 
	 * @param type
	 */
	public void setVideoRequireType(int type)
	{
		videoRequireType = type;

		switch (type)
		{
			case VideoRequireConst.MINOR_COURSE:
				videoRequireTypeName = "���޿�";
				break;

			case VideoRequireConst.REQUIRED_COURSE_1:
				videoRequireTypeName = "����һ";
				break;

			case VideoRequireConst.REQUIRED_COURSE_2:
				videoRequireTypeName = "���޶�";
				break;

			case VideoRequireConst.REQUIRED_COURSE_3:
				videoRequireTypeName = "������";
				break;

			case VideoRequireConst.REQUIRED_COURSE_4:
				videoRequireTypeName = "������";
				break;

			case VideoRequireConst.REQUIRED_COURSE_5:
				videoRequireTypeName = "������";
				break;

			case VideoRequireConst.REQUIRED_COURSE_6:
				videoRequireTypeName = "������";
				break;

			default:
				break;
		}
	}

	/**
	 * ��ȡ��Ƶ������
	 * 
	 * @return
	 */
	public int getVideoRequireType()
	{
		return videoRequireType;
	}

	/**
	 * ��Ԫ���
	 */
	public int cellID = 0;

	/**
	 * ���ߵ�����
	 */
	public String authorName = "";

	/***
	 * ������ʦ������
	 */
	public String teacherName = "";

	/**
	 * ��Ƶ�ܵ�ʱ��  ��λ������
	 */
	public long totalTime = 0;

	/**
	 * ��Ƶ�ļ���С
	 */
	public int videoSize = 0;

	/**
	 * ��ƵԤ��ͼƬ�ĵ�ַ(��ͼƬ--����ۿ���һ��ʹ��)
	 */
	public String preViewUrlBig = "";

	/**
	 * ��ƵԤ��ͼƬ�ĵ�ַ(������С)
	 */
	public String preViewUrlCom = "";

	/**
	 * ��Ƶ�Ĳ��ŵ�ַ
	 */
	public String videoURL = "";

	/**
	 * ��Ƶ�ļ��ĸ�ʽ
	 */
	public String videoFormat = "";

	/**
	 * ��Ƶ�ϴβ��ŵ���ʱ��
	 */
	public long playTime = 0;

	/**
	 * ��Ƶ��󲥷�ʱ��
	 */
	public String lastPlayTime = "";

}
