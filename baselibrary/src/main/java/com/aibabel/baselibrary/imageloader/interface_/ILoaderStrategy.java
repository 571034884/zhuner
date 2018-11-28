package com.aibabel.baselibrary.imageloader.interface_;

import com.aibabel.baselibrary.imageloader.LoaderOptions;

/**
 * Created by JohnsonFan on 2017/6/9.
 */

public interface ILoaderStrategy {

	void loadImage(LoaderOptions options);

	/**
	 * 清理内存缓存
	 */
	void clearMemoryCache();

	/**
	 * 清理磁盘缓存
	 */
	void clearDiskCache();

}
