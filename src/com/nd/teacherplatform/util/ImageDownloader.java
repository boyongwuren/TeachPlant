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
 * @����ժҪ ������Ϊ������ �첽����ͼƬ ������
 *       <p>
 * 
 * @���� ��������
 * @����ʱ�� ��2012-12-22 ����9:55:37
 * @��ʷ��¼ :
 * @���� : 2012-12-22 ����9:55:37 �޸��ˣ����ď�
 * @���� : This helper class download images from the Internet and binds those
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
	 * ��/�ӿ����������ػص��ӿ� ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 * <pre>
	 * �޸�����	�޸���             �޸�˵��
	 * 2013-6-14	���ď�		�½�
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
		/* �жϱ����Ƿ���ͼƬ */
		if (bitmap == null && isSaveLocal) {
			bitmap = getBitmapFromLocal(url, Constants.FILE_CACHE);
		}
		/* ȥ������ͼƬ */
		if (bitmap == null) {
			forceDownload(url, imageView, loadingDrawable_ID, failDrawable_ID, isSaveLocal, null);
		} else {
			cancelPotentialDownload(url, imageView);
			imageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * 
	 * �������������d�DƬ���������Ќ������{
	 * 
	 * @param url
	 *            ͼƬ���ص�ַ
	 * @param imageView
	 *            ������ʾͼƬ��imageview�ؼ�
	 * @param loadingDrawable_ID
	 *            ͼƬ��������ʱ�ؼ���ʾ��ͼƬ
	 * @param failDrawable_ID
	 *            ͼƬ����ʧ�ܺ�ؼ���ʾ��ͼƬ
	 * @param isSaveLocal
	 *            ͼƬ�Ƿ񱣴��ڱ���
	 * @param action
	 *            �ص��ӿ� ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *            <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-13   ���ď�		�½�
	 * </pre>
	 */
	public void download(String url, ImageView imageView, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
	{
		Bitmap bitmap = getBitmapFromCache(url);
		/* �жϱ����Ƿ���ͼƬ */
		if (bitmap == null && isSaveLocal) {
			bitmap = getBitmapFromLocal(url, Constants.FILE_CACHE);
		}
		/* ȥ������ͼƬ */
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
	 * �������������d�DƬ��̎��DƬ�������Ќ������{���]��ʹ�þ���C��
	 * 
	 * @param urlͼƬ���ص�ַ
	 * @param imageView
	 *            ��ʾͼƬ��imageview�ؼ�
	 * @param width
	 *            ����ͼƬ��ʾ�Ŀ�ȣ�ѹ�����ͼƬ��ȣ�ʵ�ʷ��ؿ�ȴ��ڻ���ڲ���ֵ
	 * @param height
	 *            ����ͼƬ��ʾ�ĸ߶ȣ�ѹ�����ͼƬ�߶ȣ�ʵ�ʷ��ظ߶ȴ��ڻ���ڲ���ֵ
	 * @param loadingDrawable_ID
	 *            ͼƬ����������ʾ��ͼƬ
	 * @param failDrawable_ID
	 *            ͼƬ����ʧ�ܺ�����ʾ��ͼƬ
	 * @param isSaveLocal
	 *            ͼƬ�Ƿ񱣴��ڱ���
	 * @param action
	 *            �ص��ӿ� ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *            <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-13   ���ď�		�½�
	 * </pre>
	 */
	public void download(String url, ImageView imageView, int width, int height, int loadingDrawable_ID, int failDrawable_ID, boolean isSaveLocal, ImageDownLoaderAction action)
	{
		// Bitmap bitmap = getBitmapFromCache(url);
		Bitmap bitmap = null;
		/* �жϱ����Ƿ���ͼƬ */
		if (bitmap == null && isSaveLocal) {
			bitmap = getBitmapFromLocal(url, Constants.FILE_CACHE, width, height);
		}
		/* ȥ������ͼƬ */
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
			/* modeΪͼƬ����ģʽ�����ڶ�ΪCORRECTģʽ������ģʽ���� */
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
			/* modeΪͼƬ����ģʽ�����ڶ�ΪCORRECTģʽ������ģʽ���� */
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
	 * ����true�������ǰ�����ѱ�ȡ�������û���������ͼ����ͼ�Ľ�չ�� Returns false if the download in
	 * progress deals with the same url. The download is not stopped in that
	 * case. ���ʹ����ͬ��URL�������ڽ��еĽ��ף��򷵻�false������������£���û��ֹͣ�����ء�
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
	 * ��������������ͼƬ��������ͼƬ������SD����Ȼ����ݿ�߲���ѹ������ͼƬ
	 * 
	 * @param url
	 *            ͼƬ���ص�ַ
	 * @param width
	 *            ѹ�����ͼƬ��ȣ�ʵ�ʷ��ؿ�ȴ��ڻ���ڲ���ֵ
	 * @param height
	 *            ѹ�����ͼƬ�߶ȣ�ʵ�ʷ��ظ߶ȴ��ڻ���ڲ���ֵ
	 * @param parentFullFileName
	 *            �洢ͼƬ���ļ���ȫ·��
	 * @param isSaveLoacl
	 *            �Ƿ񱣴汾��
	 * @return ͼƬ ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *         <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-14   ���ď�		�½�
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
	 * ��������������ͼƬ�������汾�� ֱ�ӷ���
	 * 
	 * @param url
	 * @return ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *         <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-14   ���ď�		�½�
	 * </pre>
	 */
	Bitmap downloadBitmap(String url)
	{

		// AndroidHttpClient is not allowed to be used from the main thread
		System.out.println("����ĵ�ַ"+url+"ʲô���");
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
			System.out.println("����ͼƬ�ĵ�ַ =" + url + "�Ƿ�Ϊ��");
			/* ���ݲ����ж�ͼƬ�����ط�ʽ */
			if (width > 0 && height > 0 && parentFullFileName != null && parentFullFileName.trim().length() != 0) {
				bmp = downloadBitmap(url, width, height, parentFullFileName, isSaveLocal);
			} else {
				bmp = downloadBitmap(url);
				/* �汾�� */
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
	 * @�������� : getBitmapFromLocal
	 * @�������� : �ӱ�����ͼƬ
	 * @����������ֵ˵����
	 * @param url
	 *            ͼƬ���ص�ַ
	 * @return ͼƬ
	 * 
	 * @�޸ļ�¼��
	 * @���ڣ�2013-1-5 ����4:19:23 �޸��ˣ�����ǿ
	 * @���� ��
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
	 * �����������ӱ�����ͼƬ
	 * 
	 * @param url
	 *            ͼƬ���ص�ַ
	 * @param parentFullFileName
	 *            ͼƬ����Ŀ¼ȫ·��
	 * @param width
	 *            ��Ҫ���ص�ͼƬ��ȣ�ʵ��ֵ���ڻ���ڲ���ֵ
	 * @param height
	 *            ��Ҫ���ص�ͼƬ�߶ȣ�ʵ��ֵ���ڻ���ڲ���ֵ
	 * @return ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *         <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-14   ���ď�		�½�
	 * </pre>
	 */
	public Bitmap getBitmapFromLocal(String url, String parentFullFileName, int width, int height)
	{
		String filename = parentFullFileName + File.separator + convertUrlToFileName(url);
		return loadResizedBitmap(filename, width, height);
	}

	/**
	 * 
	 * ����������ѹ��ͼƬ
	 * 
	 * @param filename
	 *            ͼƬ�ļ�ȫ·���ļ���
	 * @param width
	 *            ��Ҫ���ص�ͼƬ��ȣ�ʵ��ֵ���ڻ���ڲ���ֵ
	 * @param height
	 *            ��Ҫ���ص�ͼƬ�߶ȣ�ʵ��ֵ���ڻ���ڲ���ֵ
	 * @return ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *         <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-14   ���ď�		�½�
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
	 * ���������������ļ�
	 * 
	 * @param in
	 *            �ļ���
	 * @param url
	 *            �ļ�����·��
	 * @param isSaveLocal
	 *            �Ƿ񱣴汾��
	 * @param parentFullFileName
	 *            �����ļ����ļ���ȫ·�� ��Ŀ���ƣ� MyGoPlusV3
	 * 
	 *            <pre>
	 * �޸�����      �޸���	   �޸�˵��
	 * 2013-6-14   ���ď�		�½�
	 * </pre>
	 */
	public void saveInputStreamToSd(InputStream in, String url, boolean isSaveLocal, String parentFullFileName)
	{
		if (in == null || !isSaveLocal) {
			return;
		}
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// �ж�sdcard�ϵĿռ�
			if (Constants.FREE_SD_SPACE_NEEDED_TO_CACHE < freeSpaceOnSd()) {
				removeCache(parentFullFileName);
			}
			String filename = convertUrlToFileName(url);
			File fileCachePath = new File(parentFullFileName);
			if (!fileCachePath.exists()) {
				fileCachePath.mkdir();
			}
			// ��ͼƬ����SD����
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
	 * ����ͼƬ��SD���ϣ������뻺��
	 * 
	 **/
	public void saveBmpToSd(Bitmap bm, String url, boolean isSaveLocal, String parentFullFileName)
	{
		if (bm == null || !isSaveLocal) {
			return;
		}
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// �ж�sdcard�ϵĿռ�
			if (Constants.FREE_SD_SPACE_NEEDED_TO_CACHE < freeSpaceOnSd()) {
				removeCache(parentFullFileName);
			}
			String filename = convertUrlToFileName(url);
			File fileCachePath = new File(parentFullFileName);
			if (!fileCachePath.exists()) {
				fileCachePath.mkdir();
			}
			// ��ͼƬ����SD����
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
			// ��ͼƬ���뻺��
			if (filename != null || bm != null) {
				sHardBitmapCache.put(filename, bm);
			}
			// ��ͼƬ·������SQLite���ݿ�
		}

	}

	/**
	 * 
	 * @�������� : freeSpaceOnSd
	 * @�������� : ����sdcard�ϵ�ʣ��ռ�
	 * @����������ֵ˵����
	 * @return
	 * 
	 * @�޸ļ�¼��
	 * @���ڣ�2012-10-20 ����12:18:41 �޸��ˣ�Ф���
	 * @���� ��
	 * 
	 */
	private int freeSpaceOnSd()
	{
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize());
		return (int) sdFreeMB;
	}

	/**
	 * @��������: ��URLת��Ϊ�ļ���
	 * @param url
	 * @return
	 * @���ߣ� xhf
	 * @����ʱ�䣺 2012-7-10 ����02:53:48
	 * @��ǰ�汾�ţ�V1.0
	 */
	public String convertUrlToFileName(String url)
	{
		String[] strings = url.split("/");
		String str = strings[strings.length - 1];
		str = str.split("&")[0];
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//]<>~��@#��%����&*��������+|{}������������������������?]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim().toLowerCase();
		// return str;
	}

	/**
	 * *����洢Ŀ¼�µ��ļ���С�����ļ��ܴ�С���ڹ涨��CACHE_SIZE����
	 * sdcardʣ��ռ�С��FREE_SD_SPACE_NEEDED_TO_CACHE�Ĺ涨
	 * ��ôɾ��40%���û�б�ʹ�õ��ļ���ҲҪͬʱɾ�����ݿ���ļ���
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
	 * * �����ļ�������޸�ʱ��������� *
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
	 * @�������� : readBitmap
	 * @�������� : �Ż���ȡͼƬ
	 * @����������ֵ˵����
	 * @param context
	 *            ������
	 * @param id
	 *            ͼƬ
	 * @return
	 * 
	 * @�޸ļ�¼��
	 * @���ڣ�2012-12-19 ����9:53:48 �޸��ˣ�����ǿ
	 * @���� ��
	 * 
	 */
	public static Bitmap readBitmap(Context context, int id) throws OutOfMemoryError
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;/*
													 * ??????16�˦�??
													 * 565??????????
													 * ??????????????��????
													 */
		opt.inInputShareable = true;
		opt.inPurgeable = true;/* ?????????????????????????????? */
		InputStream is = context.getResources().openRawResource(id);
		return BitmapFactory.decodeStream(is, null, opt);
	}
}