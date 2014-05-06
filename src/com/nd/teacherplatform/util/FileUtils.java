package com.nd.teacherplatform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class FileUtils {
	// http://www.fileinfo.com/filetypes/video , "dat" , "bin" , "rms"
	public static final String[] VIDEO_EXTENSIONS = { "264", "3g2", "3gp", "3gp2", "3gpp", "3gpp2", "3mm", "3p2", "60d", "aep", "ajp", "amv", "amx", "arf", "asf", "asx", "avb", "avd", "avi", "avs", "avs", "axm", "bdm", "bdmv", "bik", "bix", "bmk", "box", "bs4", "bsf", "byu", "camre", "clpi", "cpi", "cvc", "d2v", "d3v", "dav", "dce", "dck", "ddat", "dif", "dir", "divx", "dlx", "dmb", "dmsm", "dmss", "dnc", "dpg", "dream", "dsy", "dv", "dv-avi", "dv4", "dvdmedia", "dvr-ms", "dvx", "dxr", "dzm", "dzp", "dzt", "evo", "eye", "f4p", "f4v", "fbr", "fbr", "fbz", "fcp", "flc", "flh", "fli", "flv", "flx", "gl", "grasp", "gts", "gvi", "gvp", "hdmov", "hkm", "ifo", "imovi", "imovi", "iva", "ivf", "ivr", "ivs", "izz", "izzy", "jts", "lsf", "lsx", "m15", "m1pg", "m1v", "m21", "m21", "m2a", "m2p", "m2t", "m2ts", "m2v", "m4e", "m4u", "m4v", "m75", "meta", "mgv", "mj2", "mjp", "mjpg", "mkv", "mmv", "mnv", "mod", "modd", "moff", "moi", "moov", "mov", "movie", "mp21", "mp21", "mp2v", "mp4", "mp4v", "mpe", "mpeg", "mpeg4", "mpf", "mpg", "mpg2", "mpgin", "mpl", "mpls", "mpv", "mpv2", "mqv", "msdvd", "msh", "mswmm", "mts", "mtv", "mvb", "mvc", "mvd", "mve", "mvp", "mxf", "mys", "ncor", "nsv", "nvc", "ogm", "ogv", "ogx", "osp", "par", "pds", "pgi", "piv", "playlist", "pmf", "prel", "pro", "prproj", "psh", "pva", "pvr", "pxv", "qt", "qtch", "qtl", "qtm", "qtz", "rcproject", "rdb", "rec", "rm", "rmd", "rmp", "rmvb", "roq", "rp", "rts", "rts", "rum", "rv", "sbk", "sbt", "scm", "scm", "scn", "sec", "seq", "sfvidcap", "smil", "smk", "sml", "smv", "spl", "ssm", "str", "stx", "svi", "swf", "swi", "swt", "tda3mt", "tivo", "tix", "tod", "tp", "tp0", "tpd", "tpr", "trp", "ts", "tvs", "vc1", "vcr", "vcv", "vdo", "vdr", "veg", "vem", "vf", "vfw", "vfz", "vgz", "vid", "viewlet", "viv", "vivo", "vlab", "vob", "vp3", "vp6", "vp7", "vpj", "vro", "vsp", "w32", "wcp", "webm", "wm", "wmd", "wmmp", "wmv", "wmx", "wp3", "wpl", "wtv", "wvx", "xfl", "xvid", "yuv", "zm1", "zm2", "zm3", "zmv" };
	// http://www.fileinfo.com/filetypes/audio , "spx" , "mid" , "sf"
	public static final String[] AUDIO_EXTENSIONS = { "4mp", "669", "6cm", "8cm", "8med", "8svx", "a2m", "aa", "aa3", "aac", "aax", "abc", "abm", "ac3", "acd", "acd-bak", "acd-zip", "acm", "act", "adg", "afc", "agm", "ahx", "aif", "aifc", "aiff", "ais", "akp", "al", "alaw", "all", "amf", "amr", "ams", "ams", "aob", "ape", "apf", "apl", "ase", "at3", "atrac", "au", "aud", "aup", "avr", "awb", "band", "bap", "bdd", "box", "bun", "bwf", "c01", "caf", "cda", "cdda", "cdr", "cel", "cfa", "cidb", "cmf", "copy", "cpr", "cpt", "csh", "cwp", "d00", "d01", "dcf", "dcm", "dct", "ddt", "dewf", "df2", "dfc", "dig", "dig", "dls", "dm", "dmf", "dmsa", "dmse", "drg", "dsf", "dsm", "dsp", "dss", "dtm", "dts", "dtshd", "dvf", "dwd", "ear", "efa", "efe", "efk", "efq", "efs", "efv", "emd", "emp", "emx", "esps", "f2r", "f32", "f3r", "f4a", "f64", "far", "fff", "flac", "flp", "fls", "frg", "fsm", "fzb", "fzf", "fzv", "g721", "g723", "g726", "gig", "gp5", "gpk", "gsm", "gsm", "h0", "hdp", "hma", "hsb", "ics", "iff", "imf", "imp", "ins", "ins", "it", "iti", "its", "jam", "k25", "k26", "kar", "kin", "kit", "kmp", "koz", "koz", "kpl", "krz", "ksc", "ksf", "kt2", "kt3", "ktp", "l", "la", "lqt", "lso", "lvp", "lwv", "m1a", "m3u", "m4a", "m4b", "m4p", "m4r", "ma1", "mdl", "med", "mgv", "midi", "miniusf", "mka", "mlp", "mmf", "mmm", "mmp", "mo3", "mod", "mp1", "mp2", "mp3", "mpa", "mpc", "mpga", "mpu", "mp_", "mscx", "mscz", "msv", "mt2", "mt9", "mte", "mti", "mtm", "mtp", "mts", "mus", "mws", "mxl", "mzp", "nap", "nki", "nra", "nrt", "nsa", "nsf", "nst", "ntn", "nvf", "nwc", "odm", "oga", "ogg", "okt", "oma", "omf", "omg", "omx", "ots", "ove", "ovw", "pac", "pat", "pbf", "pca", "pcast", "pcg", "pcm", "peak", "phy", "pk", "pla", "pls", "pna", "ppc", "ppcx", "prg", "prg", "psf", "psm", "ptf", "ptm", "pts", "pvc", "qcp", "r", "r1m", "ra", "ram", "raw", "rax", "rbs", "rcy", "rex", "rfl", "rmf", "rmi", "rmj", "rmm", "rmx", "rng", "rns", "rol", "rsn", "rso", "rti", "rtm", "rts", "rvx", "rx2", "s3i", "s3m", "s3z", "saf", "sam", "sb", "sbg", "sbi", "sbk", "sc2", "sd", "sd", "sd2", "sd2f", "sdat", "sdii", "sds", "sdt", "sdx", "seg", "seq", "ses", "sf2", "sfk", "sfl", "shn", "sib", "sid", "sid", "smf", "smp", "snd", "snd", "snd", "sng", "sng", "sou", "sppack", "sprg", "sseq", "sseq", "ssnd", "stm", "stx", "sty", "svx", "sw", "swa", "syh", "syw", "syx", "td0", "tfmx", "thx", "toc", "tsp", "txw", "u", "ub", "ulaw", "ult", "ulw", "uni", "usf", "usflib", "uw", "uwf", "vag", "val", "vc3", "vmd", "vmf", "vmf", "voc", "voi", "vox", "vpm", "vqf", "vrf", "vyf", "w01", "wav", "wav", "wave", "wax", "wfb", "wfd", "wfp", "wma", "wow", "wpk", "wproj", "wrk", "wus", "wut", "wv", "wvc", "wve", "wwu", "xa", "xa", "xfs", "xi", "xm", "xmf", "xmi", "xmz", "xp", "xrns", "xsb", "xspf", "xt", "xwb", "ym", "zvd", "zvr" };

	private static final HashSet<String> mHashVideo;
	private static final HashSet<String> mHashAudio;
	private static final double KB = 1024.0;
	private static final double MB = KB * KB;
	private static final double GB = KB * KB * KB;

	static {
		mHashVideo = new HashSet<String>(Arrays.asList(VIDEO_EXTENSIONS));
		mHashAudio = new HashSet<String>(Arrays.asList(AUDIO_EXTENSIONS));
	}

	/** 是否是音频或者视频 */
	public static boolean isVideoOrAudio(File f) {
		final String ext = getFileExtension(f);
		return mHashVideo.contains(ext) || mHashAudio.contains(ext);
	}

	/** 是否是音频或者视频 */
	public static boolean isVideoOrAudio(String f) {
		final String ext = getUrlExtension(f);
		return mHashVideo.contains(ext) || mHashAudio.contains(ext);
	}

	public static boolean isVideo(File f) {
		final String ext = getFileExtension(f);
		return mHashVideo.contains(ext);
	}

	/** 获取文件后缀 */
	public static String getFileExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

	public static String getUrlFileName(String url) {
		int slashIndex = url.lastIndexOf('/');
		int dotIndex = url.lastIndexOf('.');
		String filenameWithoutExtension;
		if (dotIndex == -1) {
			filenameWithoutExtension = url.substring(slashIndex + 1);
		} else {
			filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
		}
		return filenameWithoutExtension;
	}

	public static String getUrlExtension(String url) {
		if (!StringUtils.isEmpty(url)) {
			int i = url.lastIndexOf('.');
			if (i > 0 && i < url.length() - 1) {
				return url.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}

	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public static String showFileSize(long size) {
		String fileSize;
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + "KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + "MB";
		else
			fileSize = String.format("%.1f", size / GB) + "GB";

		return fileSize;
	}

	/** 显示SD卡剩余空间 */
	public static String showFileAvailable() 
	{
		return showFileSize(getFileAvailableSize()) + " / " + getFileTotalSize();
	}
	
	/**
	 * 得到sdcard的 总大小
	 * @return
	 */
	public static long getFileTotalSize()
	{
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			return blockSize*blockCount;
		}
		
		return 0;
	}

	/**
	 * 得到sdcard的 剩余大小
	 * @return
	 */
	public static long getFileAvailableSize()
	{
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long blockAvail = sf.getAvailableBlocks();
			return blockSize*blockAvail;
		}
		
		return 0;
	}

	/** 如果不存在就创建 */
	public static boolean createIfNoExists(String path) {
		File file = new File(path);
		boolean mk = false;
		if (!file.exists()) {
			mk = file.mkdirs();
		}
		return mk;
	}

	private static HashMap<String, String> mMimeType = new HashMap<String, String>();
	static {
		mMimeType.put("M1V", "video/mpeg");
		mMimeType.put("MP2", "video/mpeg");
		mMimeType.put("MPE", "video/mpeg");
		mMimeType.put("MPG", "video/mpeg");
		mMimeType.put("MPEG", "video/mpeg");
		mMimeType.put("MP4", "video/mp4");
		mMimeType.put("M4V", "video/mp4");
		mMimeType.put("3GP", "video/3gpp");
		mMimeType.put("3GPP", "video/3gpp");
		mMimeType.put("3G2", "video/3gpp2");
		mMimeType.put("3GPP2", "video/3gpp2");
		mMimeType.put("MKV", "video/x-matroska");
		mMimeType.put("WEBM", "video/x-matroska");
		mMimeType.put("MTS", "video/mp2ts");
		mMimeType.put("TS", "video/mp2ts");
		mMimeType.put("TP", "video/mp2ts");
		mMimeType.put("WMV", "video/x-ms-wmv");
		mMimeType.put("ASF", "video/x-ms-asf");
		mMimeType.put("ASX", "video/x-ms-asf");
		mMimeType.put("FLV", "video/x-flv");
		mMimeType.put("MOV", "video/quicktime");
		mMimeType.put("QT", "video/quicktime");
		mMimeType.put("RM", "video/x-pn-realvideo");
		mMimeType.put("RMVB", "video/x-pn-realvideo");
		mMimeType.put("VOB", "video/dvd");
		mMimeType.put("DAT", "video/dvd");
		mMimeType.put("AVI", "video/x-divx");
		mMimeType.put("OGV", "video/ogg");
		mMimeType.put("OGG", "video/ogg");
		mMimeType.put("VIV", "video/vnd.vivo");
		mMimeType.put("VIVO", "video/vnd.vivo");
		mMimeType.put("WTV", "video/wtv");
		mMimeType.put("AVS", "video/avs-video");
		mMimeType.put("SWF", "video/x-shockwave-flash");
		mMimeType.put("YUV", "video/x-raw-yuv");
	}

	/** 获取MIME */
	public static String getMimeType(String path) {
		int lastDot = path.lastIndexOf(".");
		if (lastDot < 0)
			return null;

		return mMimeType.get(path.substring(lastDot + 1).toUpperCase());
	}

	/** 多个SD卡时 取外置SD卡 */
	public static String getExternalStorageDirectory() {
		//参考文章
		//http://blog.csdn.net/bbmiku/article/details/7937745
		Map<String, String> map = System.getenv();
		String[] values = new String[map.values().size()];
		map.values().toArray(values);
		String path = values[values.length - 1];
		Log.e("nmbb", "FileUtils.getExternalStorageDirectory : " + path);
		if (path.startsWith("/mnt/") && !Environment.getExternalStorageDirectory().getAbsolutePath().equals(path))
			return path;
		else
			return null;
	}
	
	/**
	 * 计算文件夹大小
	 */
	public static final long getSize(File dir) {
		long retSize = 0;
		if (dir == null) {
			return retSize;
		}
		if (dir.isFile()) {
			return dir.length();
		}
		File[] entries = dir.listFiles();
		if(entries != null)
		{
			int count = entries.length;
			for (int i = 0; i < count; i++) {
				if (entries[i].isDirectory()) {
					retSize += getSize(entries[i]);
				} else {
					retSize += entries[i].length();
				}
			}
		}
		return retSize;
	}

	/**
	 * 创建文件
	 * 
	 * @param pathName
	 *            路径名
	 * @return void
	 */
	private static void createFile(File file) {
		//File file = new File(pathName);
		if (!file.getParentFile().exists()) {
			//System.out.println("文件所在目录不存在，准备创建...");
			if (file.getParentFile().mkdirs()) {
				LogUtil.d("createFile","目录创建成功，准备创建文件..."+file.getParentFile());
				try {
					if (file.createNewFile()) {
						return;
					} else {
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return;
			}
		} else {
			try {
				if (file.createNewFile()) {
					return;
				} else {
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    /**
     * 将字符串写入到文件中
     *
     * @param file
     * @param txt
     * @throws Exception
     */
    public static void stringWriteToFile(File file, String txt) throws Exception { 
    	BufferedReader reader = null;
    	PrintWriter writer = null;
		if (!file.exists()) {
			FileUtils.createFile(file);
		}
		try {
			String s;
			reader = new BufferedReader(new StringReader(txt));
			writer = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
			while ((s = reader.readLine()) != null)
				writer.println(s);
		} catch (Exception ex) {
			throw ex;
		}
		finally{
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
	/**
	 * 重命名文件
	 *
	 * @param fileName
	 * @param name
	 * @return
	 */
	public static boolean rename(String fileName, String name) {
		File file = new File(fileName);
		if (file.getParent() == null) {
			File newFile = new File(name);
			return file.renameTo(newFile);
		} else {
			File newFile = new File(file.getParent(), name);
			return file.renameTo(newFile);
		}
	}

	/**
	 * 移动文件或文件夹
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static boolean moveTo(String oldPath, String newPath) {
		File file = new File(oldPath);
		File newFile = new File(newPath);
		if (newFile.exists()) {
			return false;
		} else {
			return file.renameTo(newFile);
		}
	}
	
	/**
	 * 复制文件
	 *
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean copyFile(File src, File dest) {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = null;
		BufferedInputStream inBuff = null;

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			input = new FileInputStream(src);
			inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			output = new FileOutputStream(dest);
			outBuff = new BufferedOutputStream(output);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inBuff.close();
				outBuff.close();
				output.close();
				input.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 对文件流进行拷贝
	 *
	 * @param input
	 * @param dest
	 * @return
	 */
	public static boolean copyFile(InputStream input, File dest) {
		// 新建文件输入流并对它进行缓冲
		BufferedInputStream inBuff = null;

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			output = new FileOutputStream(dest);
			outBuff = new BufferedOutputStream(output);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inBuff.close();
				outBuff.close();
				output.close();
				input.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	private static boolean copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			File newFile = new File(newPathFile);
			if (!newFile.exists()) {
				FileUtils.createFile(newFile);
			}
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath 准备拷贝的目录
	 * @param newPath 指定绝对路径的新目录
	 * @return
	 */
	private static boolean copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 复制文件或者目录
	 *
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static boolean copy(String oldPath, String newPath) {
		File file = new File(oldPath);
		if (file.isDirectory()) {
			return copyFolder(oldPath, newPath);
		} else {
			return copyFile(oldPath, newPath);
		}
	}

	

	/**
	 * 删除文件或文件夹
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean delele(String filePath) {
		File file = new File(filePath);
		if(!file.exists()){
			return true;
		}
		if (file.isDirectory()) {
			return delFolder(filePath);
		} else {
			return delFile(filePath);
		}
	}

	/**
	 * 删除文件
	 *
	 * @param filePath
	 * @return
	 */
	private static boolean delFile(String filePath) {
		try {
			File myDelFile = new File(filePath);
			myDelFile.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	private static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	private static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	

	/**
	 * 删除文件，可以是单个文件或文件夹
	 * 
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			//Log.i(TAG, "delete fail：" + fileName + " no exits!");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			//Log.i(TAG, "delete the file" + fileName + " success!");
			return true;
		} else {
			//Log.i(TAG, "delete the file" + fileName + "fail!");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			//Log.i(TAG, "delete dir fail!" + dirFile + " no exit!");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			//Log.i(TAG, "delete dir fail!");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			//Log.i(TAG, "delete dir" + dir + "success!");
			return true;
		} else {
			//Log.i(TAG, "delete dir" + dir + "fail!");
			return false;
		}
	}


	/**
	 * 删除目录下的文件及目录,不删除目录
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteFilesUnderDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			//Log.i(TAG, "delete dir fail!" + dirFile + " no exit!");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			//Log.i(TAG, "delete dir fail!");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	* @Title: createFile 
	* @Description: 创建目录和文件
	* @param pathFilename
	*        路径文件名
	* @return     
	*        File
	* @throws
	 */
	public static File createFile(String pathFilename) {
		File file = new File(pathFilename);
		//判断目录是否存在
		if (!file.getParentFile().exists()) {
			if (file.getParentFile().mkdirs()) {
				try {
					boolean ls = file.createNewFile();
					if (!ls) {
						return null;
					}
					return file;
				} catch (IOException e) {
					LogUtil.v("FileUtil", e.getMessage());
				}
			}else{//目录创建失败
				return null;
			}
		} else {
			try {
				//删除原先的文件
				if(file.exists()){
					file.delete();
				}
				//创建文件
				boolean ls = file.createNewFile();
				if (!ls) {
					return null;
				}
				return file;
			} catch (IOException e) {
				LogUtil.v("FileUtil", e.getMessage());
				
			}
		}
		return null;
	}

	  public static void bitmapWriteToFile(File file, Bitmap bitmap) throws Exception {  	
	    	BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
			} catch (Exception ex) {
				throw ex;
			}
			finally{
				if(bos != null) {
					bos.close();
				}
			}
	    }

	
		/**
		 * 文件是否存在，如果不存在则创建
		 * @param file
		 * @return
		 * @throws IOException
		 */
		public static final boolean isFileExistOrCreate(File file) throws IOException {
			if(file == null) return false;
			if(file.exists() && file.isFile()) {
				return true;
			}
			if(!file.exists()) {
				File parentFile = file.getParentFile();
				if(parentFile != null && !parentFile.exists()) {
					parentFile.mkdirs();
				}
				return file.createNewFile();
			}
			return false;
		}
		
		/*
		 * 获取目录下 拥有的文件的数目
		 * **/
		public static final int getFileChildFile(String path)
		{
			File file = new File(path);
			
			if(file.exists() == false)
			{
				file.mkdirs();
			}
			
			File[] files = file.listFiles();
			if(files == null)
			{
				return 0;
			} 
			
			int count = 0;
			for(int i = 0;i<files.length;i++)
			{
				File tempFile = files[i];
				if(tempFile.isDirectory())
				{
					count++;
				}
			}
			
			return count;
		}
		
}
