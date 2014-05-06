package com.nd.teacherplatform.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.activity.BaseActivity;
import com.nd.teacherplatform.constant.FileConst;
import com.nd.teacherplatform.interfaces.RemoveGlobalOnLayoutInterface;

/**
 *  辅助显示 底部 存储容量使用
 * @author zmp
 *
 */
public class RenderStoryInfo implements OnGlobalLayoutListener
{

	private TextView totalSizeTextView;
	private TextView nativeVideoSizeTextView;
	private TextView leaveSizeTextView;
	private TextView otherStoryTextView;

	private ImageView otherSoftImageView;
	private ImageView videoStoryImageView;
	private ImageView storyBgImageView;

	private BaseActivity baseActivity;
	
	private RemoveGlobalOnLayoutInterface removeGlobalOnLayoutInterface;
	
	public RenderStoryInfo(BaseActivity baseActivity)
	{
		 this.baseActivity = baseActivity;
	}
	
	public void setRemoveInterface(RemoveGlobalOnLayoutInterface removeGlobalOnLayoutInterface)
	{
		this.removeGlobalOnLayoutInterface = removeGlobalOnLayoutInterface;
	}

	@Override
	public void onGlobalLayout()
	{
		System.out.println("这个不能一直打印的");
		otherStoryTextView = (TextView) baseActivity.findViewById(R.id.otherStory);
		nativeVideoSizeTextView = (TextView) baseActivity.findViewById(R.id.nativeVideoSize);
		leaveSizeTextView = (TextView) baseActivity.findViewById(R.id.leaveSize);
		totalSizeTextView = (TextView) baseActivity.findViewById(R.id.totalSize);
		storyBgImageView = (ImageView) baseActivity.findViewById(R.id.storyBgPic);
		otherSoftImageView = (ImageView) baseActivity.findViewById(R.id.otherSoftPic);
		videoStoryImageView = (ImageView) baseActivity.findViewById(R.id.videoStoryPic);
		if(removeGlobalOnLayoutInterface != null)
		{
			removeGlobalOnLayoutInterface.removeGlobalOnLayout();
		}
		

		String videoPath = Environment.getExternalStorageDirectory().getPath() + FileConst.VIDEO_PATH;
		long videoPathSize = FileUtils.getSize(new File(videoPath));
		// videoPathSize = 99240000;
		String videoPathSizeString = FileUtils.showFileSize(videoPathSize);
		videoPathSizeString = "本地视频：" + videoPathSizeString;
		nativeVideoSizeTextView.setText(videoPathSizeString);

		long leaveSize = FileUtils.getFileAvailableSize();
		leaveSizeTextView.setText("剩余容量：" + FileUtils.showFileSize(leaveSize));

		long totalSize = FileUtils.getFileTotalSize();
		totalSizeTextView.setText("总容量：" + FileUtils.showFileSize(totalSize));

		long otherSize = totalSize - leaveSize - videoPathSize;
		// otherSize = 99240000;
		otherStoryTextView.setText("其他程序：" + FileUtils.showFileSize(otherSize));

		RelativeLayout.LayoutParams otherlp = (LayoutParams) otherSoftImageView.getLayoutParams();
		RelativeLayout.LayoutParams videolp = (LayoutParams) videoStoryImageView.getLayoutParams();

		otherlp.width = (int) ((double) otherSize / (double) totalSize * storyBgImageView.getWidth());
		otherlp.height = otherSoftImageView.getHeight();
		otherSoftImageView.setLayoutParams(otherlp);

		videolp.width = (int) ((double) videoPathSize / (double) totalSize * storyBgImageView.getWidth());
		videolp.height = videoStoryImageView.getHeight();
		videolp.leftMargin = otherlp.width;
		videoStoryImageView.setLayoutParams(videolp);
	}

}
