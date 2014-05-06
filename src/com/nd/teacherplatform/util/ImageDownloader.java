package com.nd.teacherplatform.util;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.nd.teacherplatform.constant.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * **********************************************************
 * 
 * @内容摘要 ：此类为第三方 异步加载图片 工具类
 *       <p>
 * 
 * @作者 ：胡文婷
 * @创建时间 ：2012-12-22 上午9:55:37
 * @历史记录 :
 * @日期 : 2012-12-22 上午9:55:37 修改人：劉文強
 * @描述 : This helper class download images from the Internet and binds those
 *     with the provided ImageView.
 */
public class ImageDownloader
{
	private static final String LOG_TAG = "ImageDownloader";

	public enum Mode
	{
		NO_ASYNC_TASK, NO_DOWNLOADED_DRAWABLE, CORRECT
	}

	private Mode mode = Mode.CORRECT;
	private static Context context;

	public ImageDownloader(Context context)
	{
		ImageDownloader.context = context;
	}

	/**
	 * 
	 * 
	 * 类/接口描述：下载回调接口 项目名称： MyGoPlusV3
	 * 
	 * <pre>
	 * 修改日期	修改人             修改说明
	 * 2013-6-14	劉文強		新建
	 * </pre>
	 */
	public interface ImageDownLoaderAction
	{
		void downFinish();
	}

	/**
	 * Download the specified image from the Internet and binds it to the
	 * provided ImageView. The binding is immediate if the image is found in the
	 * cache and will be done asynchronously otherwise. A null bitmap will be
	 * associated to the ImageView if an error occurs.
	 * 
	 * @param url
	 *            The URL of the image to download.
	 * @param imageView
	 *            The ImageView to bind the downloaded image to.
	 */
	public void download(String url, ImageView imageView, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal)
	{
		Bitmap bitmap = getBitmapFromCache(url);
		/* 判断本地是否有图片 */
		if (bitmap == null && isSaveLocal) {
			bitmap = getBitmapFromLocal(url, Constants.FILE_CACHE);
		}
		/* 去网络拿图片 */
		if (bitmap == null) {
			forceDownload(url, imageView, loadingDrawable_ID, failDrawable_ID, isSaveLocal, null);
		} else {
			cancelPotentialDownload(url, imageView);
			imageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * 
	 * 方法描述：下載圖片方法，帶有對應回調
	 * 
	 * @param url
	 *            图片下载地址
	 * @param imageView
	 *            用于显示图片的imageview控件
	 * @param loadingDrawable_ID
	 *            图片正在下载时控件显示的图片
	 * @param failDrawable_ID
	 *            图片下载失败后控件显示的图片
	 * @param isSaveLocal
	 *            图片是否保存在本地
	 * @param action
	 *            回调接口 项目名称： MyGoPlusV3
	 * 
	 *            <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-13   劉文強		新建
	 * </pre>
	 */
	public void download(String url, ImageView imageView, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
	{
		Bitmap bitmap = getBitmapFromCache(url);
		/* 判断本地是否有图片 */
		if (bitmap == null && isSaveLocal) {
			bitmap = getBitmapFromLocal(url, Constants.FILE_CACHE);
		}
		/* 去网络拿图片 */
		if (bitmap == null) {
			forceDownload(url, imageView, loadingDrawable_ID, failDrawable_ID, isSaveLocal, action);
		} else {
			cancelPotentialDownload(url, imageView);
			imageView.setImageBitmap(bitmap);
			if (action != null) {
				action.downFinish();
			}
		}
	}

	/**
	 * 
	 * 方法描述：下載圖片後處理圖片，并帶有對應回調，沒有使用緩存機制
	 * 
	 * @param url图片下载地址
	 * @param imageView
	 *            显示图片用imageview控件
	 * @param width
	 *            设置图片显示的宽度，压缩后的图片宽度，实际返回宽度大于或等于参数值
	 * @param height
	 *            设置图片显示的高度，压缩后的图片高度，实际返回高度大于或等于参数值
	 * @param loadingDrawable_ID
	 *            图片下载中所显示的图片
	 * @param failDrawable_ID
	 *            图片下载失败后所显示的图片
	 * @param isSaveLocal
	 *            图片是否保存在本地
	 * @param action
	 *            回调接口 项目名称： MyGoPlusV3
	 * 
	 *            <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-13   劉文強		新建
	 * </pre>
	 */
	public void download(String url, ImageView imageView, int width, int height, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
	{
		// Bitmap bitmap = getBitmapFromCache(url);
		Bitmap bitmap = null;
		/* 判断本地是否有图片 */
		if (bitmap == null && isSaveLocal) {
			bitmap = getBitmapFromLocal(url, Constants.FILE_CACHE, width, height);
		}
		/* 去网络拿图片 */
		if (bitmap == null) {
			forceDownload(url, imageView, width, height, Constants.FILE_CACHE, loadingDrawable_ID, failDrawable_ID, isSaveLocal, action);
		} else {
			cancelPotentialDownload(url, imageView);
			imageView.setImageBitmap(bitmap);
			if (action != null) {
				action.downFinish();
			}
		}
	}

	/*
	 * Same as download but the image is always downloaded and the cache is not
	 * used. Kept private at the moment as its interest is not clear. private
	 * void forceDownload(String url, ImageView view) { forceDownload(url, view,
	 * null); }
	 */

	/**
	 * Same as download but the image is always downloaded and the cache is not
	 * used. Kept private at the moment as its interest is not clear.
	 */
	private void forceDownload(String url, ImageView imageView, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
	{
		if (url == null) {
			imageView.setImageResource(failDrawable_ID);
			return;
		}

		if (cancelPotentialDownload(url, imageView)) {
			/* mode为图片下载模式，现在都为CORRECT模式，其余模式无用 */
			switch (mode)
			{
				case NO_ASYNC_TASK:
					Bitmap bitmap = downloadBitmap(url);
					addBitmapToCache(url, bitmap);
					imageView.setImageBitmap(bitmap);
					break;

				case NO_DOWNLOADED_DRAWABLE:
					imageView.setMinimumHeight(156);
					BitmapDownloaderTask task = new BitmapDownloaderTask(imageView, failDrawable_ID, isSaveLocal, action);
					task.execute(url);
					break;

				case CORRECT:
					task = new BitmapDownloaderTask(imageView, failDrawable_ID, isSaveLocal, action);
					DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task, loadingDrawable_ID);
					imageView.setImageDrawable(downloadedDrawable);
					imageView.setMinimumHeight(156);
					try {
						task.execute(url);
					} catch (RejectedExecutionException localRejectedExecutionException) {
					}
					break;
			}
		}
	}

	/**
	 * Same as download but the image is always downloaded and the cache is not
	 * used. Kept private at the moment as its interest is not clear.
	 */
	private void forceDownload(String url, ImageView imageView, int width, int height, String parentFullFileName, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
	{
		// State sanity: url is guaranteed to never be null in
		// DownloadedDrawable and cache keys.
		if (url == null) {
			imageView.setImageResource(failDrawable_ID);
			return;
		}
		if (cancelPotentialDownload(url, imageView)) {
			/* mode为图片下载模式，现在都为CORRECT模式，其余模式无用 */
			switch (mode)
			{
				case NO_ASYNC_TASK:
					Bitmap bitmap = downloadBitmap(url);
					addBitmapToCache(url, bitmap);
					imageView.setImageBitmap(bitmap);
					break;

				case NO_DOWNLOADED_DRAWABLE:
					imageView.setMinimumHeight(156);
					BitmapDownloaderTask task = new BitmapDownloaderTask(imageView, failDrawable_ID, isSaveLocal, action);
					task.execute(url);
					break;

				case CORRECT:
					task = new BitmapDownloaderTask(imageView, width, height, parentFullFileName, failDrawable_ID, isSaveLocal, action);
					DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task, loadingDrawable_ID);
					imageView.setImageDrawable(downloadedDrawable);
					imageView.setMinimumHeight(156);
					try {
						task.execute(url);
					} catch (RejectedExecutionException localRejectedExecutionException) {
					}
					break;
			}
		}
	}

	/**
	 * Returns true if the current download has been canceled or if there was no
	 * download in progress on this image view.
	 * 返回true，如果当前下载已被取消或如果没有下载这个图像视图的进展。 Returns false if the download in
	 * progress deals with the same url. The download is not stopped in that
	 * case. 如果使用相同的URL下载正在进行的交易，则返回false。在这种情况下，还没有停止的下载。
	 */
	private static boolean cancelPotentialDownload(String url, ImageView imageView)
	{
		BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

		if (bitmapDownloaderTask != null) {
			String bitmapUrl = bitmapDownloaderTask.url;
			if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
				bitmapDownloaderTask.cancel(true);
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param imageView
	 *            Any imageView
	 * @return Retrieve the currently active download task (if any) associated
	 *         with this imageView. null if there is no such task.
	 */
	private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView)
	{
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof DownloadedDrawable) {
				DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
				return downloadedDrawable.getBitmapDownloaderTask();
			}
		}
		return null;
	}

	/**
	 * 
	 * 方法描述：下载图片，先下载图片到本地SD卡，然后根据宽高参数压缩返回图片
	 * 
	 * @param url
	 *            图片下载地址
	 * @param width
	 *            压缩后的图片宽度，实际返回宽度大于或等于参数值
	 * @param height
	 *            压缩后的图片高度，实际返回高度大于或等于参数值
	 * @param parentFullFileName
	 *            存储图片的文件夹全路径
	 * @param isSaveLoacl
	 *            是否保存本地
	 * @return 图片 项目名称： MyGoPlusV3
	 * 
	 *         <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-14   劉文強		新建
	 * </pre>
	 */
	Bitmap downloadBitmap(String url, int width, int height, String parentFullFileName, boolean isSaveLoacl)
	{

		final HttpClient client = (mode == Mode.NO_ASYNC_TASK) ? new DefaultHttpClient() : AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					saveInputStreamToSd(inputStream, url, isSaveLoacl, parentFullFileName);
					return getBitmapFromLocal(url, parentFullFileName, width, height);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
			getRequest.abort();
			Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			if ((client instanceof AndroidHttpClient)) {
				((AndroidHttpClient) client).close();
			}
		}
		return null;
	}

	/**
	 * 
	 * 方法描述：下载图片，不保存本地 直接返回
	 * 
	 * @param url
	 * @return 项目名称： MyGoPlusV3
	 * 
	 *         <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-14   劉文強		新建
	 * </pre>
	 */
	Bitmap downloadBitmap(String url)
	{

		// AndroidHttpClient is not allowed to be used from the main thread
		System.out.println("报错的地址"+url+"什么情况");
		final HttpClient client = (mode == Mode.NO_ASYNC_TASK) ? new DefaultHttpClient() : AndroidHttpClient.newInstance("Android");
		try {

			final HttpGet getRequest = new HttpGet(url);
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					// return BitmapFactory.decodeStream(inputStream);
					// Bug on slow connections, fixed in future release.
					return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
//			getRequest.abort();
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
//			getRequest.abort();
			Log.w(LOG_TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
//			getRequest.abort();
			Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			if ((client instanceof AndroidHttpClient)) {
				((AndroidHttpClient) client).close();
			}
		}
		return null;
	}

	/*
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 */
	static class FlushedInputStream extends FilterInputStream
	{
		public FlushedInputStream(InputStream inputStream)
		{
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException
		{
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	/**
	 * The actual AsyncTask that will asynchronously download the image.
	 */
	class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap>
	{
		private String url;
		private final WeakReference<ImageView> imageViewReference;
		private int failDrawable_ID;
		private boolean isSaveLocal;
		private ImageDownLoaderAction action;
		private int width;
		private int height;
		private String parentFullFileName;

		public BitmapDownloaderTask(ImageView imageView, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
		{
			imageViewReference = new WeakReference<ImageView>(imageView);
			BitmapDownloaderTask.this.failDrawable_ID = failDrawable_ID;
			BitmapDownloaderTask.this.isSaveLocal = isSaveLocal;
			BitmapDownloaderTask.this.action = action;
		}

		public BitmapDownloaderTask(ImageView imageView, int width, int height, String parentFullFileName, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
		{
			imageViewReference = new WeakReference<ImageView>(imageView);
			BitmapDownloaderTask.this.failDrawable_ID = failDrawable_ID;
			BitmapDownloaderTask.this.isSaveLocal = isSaveLocal;
			BitmapDownloaderTask.this.action = action;
			BitmapDownloaderTask.this.width = width;
			BitmapDownloaderTask.this.height = height;
			BitmapDownloaderTask.this.parentFullFileName = parentFullFileName;
		}

		/**
		 * Actual download method.
		 */
		@Override
		protected Bitmap doInBackground(String... params)
		{
			Bitmap bmp = null;
			url = params[0];
			System.out.println("下载图片的地址 =" + url + "是否为空");
			/* 根据参数判断图片的下载方式 */
			if (width > 0 && height > 0 && parentFullFileName != null && parentFullFileName.trim().length() != 0) {
				bmp = downloadBitmap(url, width, height, parentFullFileName, isSaveLocal);
			} else {
				bmp = downloadBitmap(url);
				/* 存本地 */
				if (bmp != null) {
					ImageDownloader.this.saveBmpToSd(bmp, url, isSaveLocal, Constants.FILE_CACHE);
				}
			}

			return bmp;
		}

		/**
		 * Once the image is downloaded, associates it to the imageView
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap)
		{
			if (isCancelled()) {
				bitmap = null;
			}
			addBitmapToCache(url, bitmap);
			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
				if ((this == bitmapDownloaderTask) || (mode != Mode.CORRECT)) {
					if (bitmap == null) {
						imageView.setImageResource(failDrawable_ID);
					} else {
						imageView.setImageBitmap(bitmap);
					}
					if (action != null) {
						action.downFinish();
					}
				}
			}
		}
	}

	/**
	 * A fake Drawable that will be attached to the imageView while the download
	 * is in progress.
	 * 
	 * <p>
	 * Contains a reference to the actual download task, so that a download task
	 * can be stopped if a new binding is required, and makes sure that only the
	 * last started download process can bind its result, independently of the
	 * download finish order.
	 * </p>
	 */
	static class DownloadedDrawable extends BitmapDrawable
	{
		private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

		public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask, int loadingDrawable_ID)
		{
			super(readBitmap(context, loadingDrawable_ID));
			bitmapDownloaderTaskReference = new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
		}

		public BitmapDownloaderTask getBitmapDownloaderTask()
		{
			return bitmapDownloaderTaskReference.get();
		}
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
		clearCache();
	}

	/*
	 * Cache-related fields and methods.
	 * 
	 * We use a hard and a soft cache. A soft reference cache is too
	 * aggressively cleared by the Garbage Collector.
	 */

	private static final int HARD_CACHE_CAPACITY = 10;
	private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds

	// Hard cache, with a fixed maximum capacity and a life duration
	private final HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true)
	{
		@Override
		protected boolean removeEldestEntry(Entry<String, Bitmap> eldest)
		{
			if (size() > HARD_CACHE_CAPACITY) {
				// Entries push-out of hard reference cache are transferred to
				// soft reference cache
				sSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
				return true;
			} else
				return false;
		}
	};

	// Soft cache for bitmaps kicked out of hard cache
	private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);

	private final Handler purgeHandler = new Handler(Looper.getMainLooper());

	private final Runnable purger = new Runnable()
	{
		public void run()
		{
			clearCache();
		}
	};

	/**
	 * Adds this bitmap to the cache.
	 * 
	 * @param bitmap
	 *            The newly downloaded bitmap.
	 */
	private void addBitmapToCache(String url, Bitmap bitmap)
	{
		if (bitmap != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(url, bitmap);
			}
		}
	}

	/**
	 * @param url
	 *            The URL of the image that will be retrieved from the cache.
	 * @return The cached bitmap or null if it was not found.
	 */
	private Bitmap getBitmapFromCache(String url)
	{
		// First try the hard reference cache
		synchronized (sHardBitmapCache) {
			final Bitmap bitmap = sHardBitmapCache.get(url);
			if (bitmap != null) {
				// Bitmap found in hard cache
				// Move element to first position, so that it is removed last
				sHardBitmapCache.remove(url);
				sHardBitmapCache.put(url, bitmap);
				return bitmap;
			}
		}

		// Then try the soft reference cache
		SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
		if (bitmapReference != null) {
			final Bitmap bitmap = bitmapReference.get();
			if (bitmap != null) {
				// Bitmap found in soft cache
				return bitmap;
			} else {
				// Soft reference has been Garbage Collected
				sSoftBitmapCache.remove(url);
			}
		}

		return null;
	}

	/**
	 * 
	 * @函数名称 : getBitmapFromLocal
	 * @功能描述 : 从本地拿图片
	 * @参数及返回值说明：
	 * @param url
	 *            图片下载地址
	 * @return 图片
	 * 
	 * @修改记录：
	 * @日期：2013-1-5 下午4:19:23 修改人：刘文强
	 * @描述 ：
	 * 
	 */
	public Bitmap getBitmapFromLocal(String url, String parentFullFileName)
	{
		try {
			String filename = parentFullFileName + File.separator + convertUrlToFileName(url);
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeFile(filename, opt);
			if (bitmap != null) {
				sHardBitmapCache.put(convertUrlToFileName(url), bitmap);
			}
			return bitmap;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * 方法描述：从本地那图片
	 * 
	 * @param url
	 *            图片下载地址
	 * @param parentFullFileName
	 *            图片所在目录全路径
	 * @param width
	 *            需要返回的图片宽度，实际值大于或等于参数值
	 * @param height
	 *            需要返回的图片高度，实际值大于或等于参数值
	 * @return 项目名称： MyGoPlusV3
	 * 
	 *         <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-14   劉文強		新建
	 * </pre>
	 */
	public Bitmap getBitmapFromLocal(String url, String parentFullFileName, int width, int height)
	{
		String filename = parentFullFileName + File.separator + convertUrlToFileName(url);
		return loadResizedBitmap(filename, width, height);
	}

	/**
	 * 
	 * 方法描述：压缩图片
	 * 
	 * @param filename
	 *            图片文件全路径文件名
	 * @param width
	 *            需要返回的图片宽度，实际值大于或等于参数值
	 * @param height
	 *            需要返回的图片高度，实际值大于或等于参数值
	 * @return 项目名称： MyGoPlusV3
	 * 
	 *         <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-14   劉文強		新建
	 * </pre>
	 */
	public Bitmap loadResizedBitmap(String filename, double width, double height)
	{
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(filename, options);
		if (options.outHeight > 0 && options.outWidth > 0) {
			options.inJustDecodeBounds = false;
			if (options.outWidth > width && options.outHeight > height) {
				double scaleWidth = options.outWidth / width;
				double scaleHeight = options.outHeight / height;
				double minScale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
				options.inSampleSize = (int) minScale;
			}

			bitmap = BitmapFactory.decodeFile(filename, options);
		}
		return bitmap;
	}

	/**
	 * Clears the image cache used internally to improve performance. Note that
	 * for memory efficiency reasons, the cache will automatically be cleared
	 * after a certain inactivity delay.
	 */
	public void clearCache()
	{
		sHardBitmapCache.clear();
		sSoftBitmapCache.clear();
	}

	/**
	 * Allow a new delay before the automatic cache clear is done.
	 */
	private void resetPurgeTimer()
	{
		purgeHandler.removeCallbacks(purger);
		purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);
	}

	/**
	 * 
	 * 方法描述：保存文件
	 * 
	 * @param in
	 *            文件流
	 * @param url
	 *            文件网络路径
	 * @param isSaveLocal
	 *            是否保存本地
	 * @param parentFullFileName
	 *            保存文件的文件夹全路径 项目名称： MyGoPlusV3
	 * 
	 *            <pre>
	 * 修改日期      修改人	   修改说明
	 * 2013-6-14   劉文強		新建
	 * </pre>
	 */
	public void saveInputStreamToSd(InputStream in, String url, boolean isSaveLocal, String parentFullFileName)
	{
		if (in == null || !isSaveLocal) {
			return;
		}
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// 判断sdcard上的空间
			if (Constants.FREE_SD_SPACE_NEEDED_TO_CACHE < freeSpaceOnSd()) {
				removeCache(parentFullFileName);
			}
			String filename = convertUrlToFileName(url);
			File fileCachePath = new File(parentFullFileName);
			if (!fileCachePath.exists()) {
				fileCachePath.mkdir();
			}
			// 把图片存在SD卡上
			File file = new File(parentFullFileName + File.separator + filename);
			try {
				FileOutputStream fos = new FileOutputStream(file, false);
				byte buffer[] = new byte[4096];
				int readsize = 0;
				while ((readsize = in.read(buffer)) > 0) {
					fos.write(buffer, 0, readsize);
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 保存图片到SD卡上，并加入缓存
	 * 
	 **/
	public void saveBmpToSd(Bitmap bm, String url, boolean isSaveLocal, String parentFullFileName)
	{
		if (bm == null || !isSaveLocal) {
			return;
		}
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// 判断sdcard上的空间
			if (Constants.FREE_SD_SPACE_NEEDED_TO_CACHE < freeSpaceOnSd()) {
				removeCache(parentFullFileName);
			}
			String filename = convertUrlToFileName(url);
			File fileCachePath = new File(parentFullFileName);
			if (!fileCachePath.exists()) {
				fileCachePath.mkdir();
			}
			// 把图片存在SD卡上
			File file = new File(parentFullFileName + File.separator + filename);
			try {
				file.createNewFile();
				OutputStream outStream = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.flush();
				outStream.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
			// 把图片存入缓存
			if (filename != null || bm != null) {
				sHardBitmapCache.put(filename, bm);
			}
			// 把图片路径存入SQLite数据库
		}

	}

	/**
	 * 
	 * @函数名称 : freeSpaceOnSd
	 * @功能描述 : 计算sdcard上的剩余空间
	 * @参数及返回值说明：
	 * @return
	 * 
	 * @修改记录：
	 * @日期：2012-10-20 下午12:18:41 修改人：肖鸿飞
	 * @描述 ：
	 * 
	 */
	private int freeSpaceOnSd()
	{
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize());
		return (int) sdFreeMB;
	}

	/**
	 * @功能描述: 从URL转化为文件名
	 * @param url
	 * @return
	 * @作者： xhf
	 * @创建时间： 2012-7-10 下午02:53:48
	 * @当前版本号：V1.0
	 */
	public String convertUrlToFileName(String url)
	{
		String[] strings = url.split("/");
		String str = strings[strings.length - 1];
		str = str.split("&")[0];
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//]<>~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？?]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim().toLowerCase();
		// return str;
	}

	/**
	 * *计算存储目录下的文件大小，当文件总大小大于规定的CACHE_SIZE或者
	 * sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件（也要同时删除数据库的文件）
	 * 
	 * @param dirPath
	 *            * @param filename
	 * */
	public void removeCache(String parentFullFileName)
	{
		File dir = new File(parentFullFileName);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			// if(files[i].getName().contains(WHOLESALE_CONV)) {
			dirSize += files[i].length();
			// }
		}
		if (dirSize > Constants.CACHE_SIZE * Constants.MB || Constants.FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());
			for (int i = 0; i < removeFactor; i++) {
				// if(files[i].getName().contains(WHOLESALE_CONV)) {
				files[i].delete();
				// }
			}
		}
	}

	/**
	 * * 根据文件的最后修改时间进行排序 *
	 */
	class FileLastModifSort implements Comparator<File>
	{
		public int compare(File arg0, File arg1)
		{
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * 
	 * @函数名称 : readBitmap
	 * @功能描述 : 优化读取图片
	 * @参数及返回值说明：
	 * @param context
	 *            上下文
	 * @param id
	 *            图片
	 * @return
	 * 
	 * @修改记录：
	 * @日期：2012-12-19 上午9:53:48 修改人：刘文强
	 * @描述 ：
	 * 
	 */
	public static Bitmap readBitmap(Context context, int id) throws OutOfMemoryError
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;/*
													 * ??????16λλ??
													 * 565??????????
													 * ??????????????λ????
													 */
		opt.inInputShareable = true;
		opt.inPurgeable = true;/* ?????????????????????????????? */
		InputStream is = context.getResources().openRawResource(id);
		return BitmapFactory.decodeStream(is, null, opt);
	}
}