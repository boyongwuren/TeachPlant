package com.nd.teacherplatform.constant;


/**
 * ͨ�õĳ���
 * 
 * @author zmp
 * 
 */
public class Constants {

	public static final int USER_EDIT_WORD_NUM = 300; // �_��������r����@ʾ�֔�

	/* 1MB */
	public static final int MB = 1024 * 1024;
	/* ���ػ����С */
	public static final int CACHE_SIZE = 100;
	/* ��Ҫ����Ŀռ� */
	public static final int FREE_SD_SPACE_NEEDED_TO_CACHE = CACHE_SIZE * MB;
	/* ���؈DƬ����Ĵ洢λ�� */
	public static final String FILE_CACHE = FileConst.PIC_PATH;

	// ��Ƶ����������� 0--Ĭ�� 1--������
	public static final int SEARCHVIDEO_ORDER_DEFAULT = 0;
	public static final int SEARCHVIDEO_ORDER_FAVORABLE = 1;

	// ��Ƶ���������ҳ��ʾ��С
	public static final int SEARCHVIDEO_PAGESIZE = 10;

	// PadServiceApi ��س���
	public static final int PadServiceApi_Action_Common = 1001; // ���ĳ��ѧϰ
	public static final int PadServiceApi_Action_TeacherPlat = 2000;// ��ʦ����
	public static final String PadServiceApi_Key_Common = "strName"; // ���ĳ��ѧϰ-����
	public static final String PadServiceApi_Key_TeacherPlat = "nStepFlag"; // ��ʦ����-����
	public static final int PadServiceApi_Flag_Begin = 1;// ��ʦ���ÿ�ʼ
	public static final int PadServiceApi_Flag_End = 2;// ��ʦ���ý���

	/**
	 * ��ʦ��
	 */
	public static final String TEACHER = "��ʦ��";

	/**
	 * ��
	 */
	public static final String TOTAL_VIDEO_NUM = "��";

	/**
	 * ������
	 */
	public static final String HAS_DOWN_LOAD_VIDEO = "������ ";

}
