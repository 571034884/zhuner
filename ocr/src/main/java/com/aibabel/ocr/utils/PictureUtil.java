package com.aibabel.ocr.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.aibabel.ocr.app.BaseApplication;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * =====================================================================
 *
 * 作者 : 张文颖
 *
 * 时间: 2018/3/29
 *
 * 描述:片工具类
 *
 * =====================================================================
 */

public class PictureUtil {
	private static String img_path;

	/**
	 * 图片旋转
	 * @param bm
	 * @param orientationDegree
	 * @return
	 */
	public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

		try {
			Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

			return bm1;

		} catch (OutOfMemoryError ex) {
		}
		return null;

	}

	/**
	 * 获取图片旋转角度
	 */
	//判断图片的旋转角度
	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			//Log.e("---->", ex.getMessage());
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;
					default:
						break;
				}
			}
		}
		return degree;
	}

	/**
	 * 将bitmap转化成字符串String
	 *
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToString(Bitmap bitmap) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 将图片转换成Base64编码的字符串
	 * @param path
	 * @return base64编码的字符串
	 */
	public static String imageToBase64(String path){
		if(TextUtils.isEmpty(path)){
			return null;
		}
		InputStream is = null;
		byte[] data = null;
		String result = null;
		try{
			is = new FileInputStream(path);
			//创建一个字符流大小的数组。
			data = new byte[is.available()];
			//写入数组
			is.read(data);
			//用默认的编码格式进行编码
			result = Base64.encodeToString(data,Base64.DEFAULT);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(null !=is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	/**
	 *base64编码字符集转化成图片文件。
	 * @param base64Str
	 * @param path 文件存储路径
	 * @return 是否成功
	 */
	public static boolean base64ToFile(String base64Str,String path){
		byte[] data = Base64.decode(base64Str,Base64.DEFAULT);
		for (int i = 0; i < data.length; i++) {
			if(data[i] < 0){
				//调整异常数据
				data[i] += 256;
			}
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
			os.write(data);
			os.flush();
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}catch (IOException e){
			e.printStackTrace();
			return false;
		}

	}


	/**
	 * 将bitmap转化成字符串String
	 *
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {
		Bitmap bm = getSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * 将字符串String转化成Bitmap
	 *
	 * @param string
	 * @return bitmap
	 */
	public static Bitmap stringtoBitmap(String string) {

		Bitmap bitmap = null;
		try {
			byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inDither = true;
//			Bitmap bm = BitmapFactory.decodeFile(path, options);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length,options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 计算图片的缩放值
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(String imagePath, BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		if (options.outHeight == -1 || options.outWidth == -1) {
			try {
				ExifInterface exifInterface = new ExifInterface(imagePath);
				int ac_height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
				int ac_width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度

				options.outWidth = ac_width;
				options.outHeight = ac_height;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final int height = options.outHeight;
		final int width = options.outWidth;


		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

		}
		return inSampleSize;

	}


	public static int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

		}
		return inSampleSize;

	}

	/**
	 * 根据路径获取图片并压缩返回bitmap用于显示
	 *
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(filePath,options, BaseApplication.screenW, BaseApplication.screenH-Constant.HEIGHT_TITLE);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据路径获取图片并压缩返回bitmap用于显示
	 *
	 * @param
	 * @return
	 */
	public static Bitmap getSmallBitmap(Bitmap bitmap) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
		byte[] data = outputStream.toByteArray();

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data,0,data.length,options);
		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, 540, 876);
		options.inJustDecodeBounds = false;

		Bitmap result =BitmapFactory.decodeByteArray(data,0,data.length, options);
		Log.d("22222",bitmap.getWidth()+"");
		Log.d("22222",bitmap.getHeight()+"");

		return result;
	}

	/**
	 * 根据路径获取图片(图片自动矫正)
	 *
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int degree) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(filePath,options, 480, 800);
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		if (degree != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(degree);
			Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			return bm;
		}

		return bitmap;
	}

	/**
	 * 根据路径获取图片并压缩返回bitmap用于显示
	 *
	 * @param context
	 * @param id
	 * @return
	 */
	public static Bitmap getSmallBitmap(Context context, int id) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), id, options);
		// 计算 缩略图大小为原始图片大小的几分之一 inSampleSize:缩略图大小为原始图片大小的几分之一
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(context.getResources(), id, options);
	}

	public static Bitmap getBitmapFromUri(Uri uri, Activity activity) {
		try {
			// File profileImgFile = new File(activity.getCacheDir(), "headphoto_");
			// 读取uri所在的图片
			// 读取uri所在的图片
			Bitmap bitmap = getBitmap(activity, uri);
			// MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
//			int newWidth = 480;
//			int newHeight = (newWidth * bitmap.getHeight()) / bitmap.getWidth();
//			Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
//			// bitmap.compress(Bitmap.CompressFormat.JPEG, 60,
//			// new FileOutputStream(profileImgFile));
//
//			// Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 3);
//			// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
//			bitmap.recycle();
//			// canvas是画布对象，将缩小过后的bitmap对象传进画布，在进行保存
//			Canvas canvas = new Canvas(newBitmap);
//			canvas.save(Canvas.ALL_SAVE_FLAG);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	private static Bitmap getThumbnail(Activity activity, Uri uri, int size) throws IOException {
		InputStream input = activity.getContentResolver().openInputStream(uri);
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
			return null;
		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		input = activity.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();
		return bitmap;
	}

	private static Bitmap getBitmap(Activity activity, Uri uri) throws IOException {



		InputStream input = activity.getContentResolver().openInputStream(uri);
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		onlyBoundsOptions.inJustDecodeBounds = true;
//		onlyBoundsOptions.inDither = true;// optional
//		options.inDither = true;
//		options.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, null);
		input.close();
//		if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
//			return null;
//		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
//		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
//		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
//		bitmapOptions.inDither = true;// optional
//		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
//		input = activity.getContentResolver().openInputStream(uri);
//		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
//		input.close();
		return bitmap;
	}





	public static Bitmap createBitmap(Bitmap bitMap) {
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		// 设置想要的大小
		int newWidth = 540;
		int newHeight = 800;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		return newBitMap;
	}

	private static int getPowerOfTwoForSampleRatio(double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0)
			return 1;
		else
			return k;
	}

	/**
	 * 根据路径删除图片
	 *
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}

	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取图片保存目录
	 *
	 * @return
	 */
	public static File getAlbumDir() {
		// File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getAlbumName());
		File dir = new File(Environment.getExternalStorageDirectory() + "/zhuner");
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dir;
	}

	/**
	 * 获取保存图片文件夹名称
	 *
	 * @return
	 */
	public static String getAlbumName() {
		return "zhuner";
	}

	/**
	 * 截屏
	 *
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		b = getSmallBitmap(img_path);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * 保存图片到本地sdcard
	 *
	 * @param bitmap
	 * @return
	 * @throws IOException
	 */
	public static String saveBitmap(Bitmap bitmap) throws IOException {
		String mCurrentPhotoPath = "";
		String imageDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(System.currentTimeMillis()));

		img_path = PictureUtil.getAlbumDir() + "/" + imageDate + ".jpeg";
		File file = new File(img_path);
		if (!file.exists()) {
			file.createNewFile();
		}
		mCurrentPhotoPath = file.getAbsolutePath();
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bitmap != null) {
					bitmap.recycle();
					bitmap = null;
				}
			} catch (Exception e) {

			}
		}

		return mCurrentPhotoPath;
	}

	public static Bitmap getBitmapByUri(Uri uri, Activity activity){
		Bitmap bitmap = null;
		try{
			if (uri != null) {
				bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return bitmap;

	}


	/**
	 * 将图片变小后取路径
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String saveBitmapPath(String path) throws IOException {
		String smallPath = saveBitmap(revitionImageSize(path));
		return smallPath;

	}

	/**
	 * 获取图片大小
	 *
	 * @param bitmap
	 * @return
	 */
	public static long getBitmapsize(Bitmap bitmap) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight();

	}

	/**
	 * 通过文件地址获取文件的bitmap
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) {
		int i = 0;
		Bitmap bitmap = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();

			while (true) {
				if ((options.outWidth >> i <= 800) && (options.outHeight >> i <= 800)) {
					in = new BufferedInputStream(new FileInputStream(new File(path)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}


	/**
	 * 将图片纠正到正确方向
	 *
	 * @param degree
	 *            ： 图片被系统旋转的角度
	 * @param bitmap
	 *            ： 需纠正方向的图片
	 * @return 纠向后的图片
	 */
	public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);

		Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return bm;
	}


	public static Bitmap getBitmapFromUri(Uri uri,Context context) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}


	public static int getHeight(Uri uri, Context context){
		String path = getRealFilePath(context,uri);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path,opts);
		opts.inSampleSize = 1;
		opts.inJustDecodeBounds = false;
		Bitmap mBitmap =BitmapFactory.decodeFile(path,opts);
		int width=opts.outWidth;
		int height=opts.outHeight;
		return height;
	}

	public static String getRealFilePath( final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}
	/**
	 * 删除目录
	 */
	public static void deleteDir(String FOLDER_NAME) {
		File dir = new File(FOLDER_NAME);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile()){
				file.delete();
			}else if (file.isDirectory()) {
				deleteDir(FOLDER_NAME);
			}
		}
		dir.delete();
	}

	/**
	 * 删除指定路径下所有文件
	 */
	public static void deleteFile(String dir) {
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}
		dirFile.delete();
	}



	public static Bitmap compress(Context context,Uri uri){
		Luban.with(context)
				.load(uri)           //传人要压缩的图片
				.putGear(80)      //设定压缩档次，默认三挡
				.setCompressListener(new OnCompressListener() { //设置回调
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(File file) {
						//压缩成功后调用，返回压缩后的图片文件
						 Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//						 return bitmap;
					}

					@Override
					public void onError(Throwable e) {

					}
				}).launch();

		return null;
	}

}
