package com.leo.base.net;

import android.util.SparseArray;

import com.leo.base.entity.LReqEntity;
import com.leo.base.util.L;

/**
 * 
 * @author Chen Lei
 * @version 1.3.5
 * 
 */
public abstract class LNetwork implements ILNetwork {

	/**
	 * 线程状态
	 * 
	 * @author object
	 * 
	 */
	enum LReqState {

		/**
		 * 线程状态为等待执行
		 */
		PENDING,

		/**
		 * 线程状态为正在执行
		 */
		RUNNING,

		/**
		 * 线程状态为执行结束
		 */
		FINISHED;
	}

	/**
	 * 存放当前所有线程的集合
	 */
	private SparseArray<LRequest> mRequestThreads;

	private SparseArray<LDownload> mDownloadThreads;

	/**
	 * 构造函数
	 */
	public LNetwork() {
		mRequestThreads = new SparseArray<LRequest>();
		mDownloadThreads = new SparseArray<LDownload>();
	}

	@Override
	public void request(LReqEntity reqEntity, int requestId,
			ILNetworkCallback callback) {
		if (reqEntity == null) {
			throw new NullPointerException(
					"The network requests the LReqEntity parameter cannot be empty!");
		}
		if (callback == null) {
			throw new NullPointerException(
					"This is an invalid request,because you did not realize the callback interface!");
		}
		LRequest task = mRequestThreads.get(requestId);
		if (task == null || task.getState() == LReqState.FINISHED) {
			task = new LRequest(reqEntity, requestId, callback, this);
			task.execute();
			mRequestThreads.put(requestId, task);
		} else {
			L.i(TAG, "requestId " + requestId + " thread is running!");
		}
	}

	@Override
	public void stopAllThread() {
		for (int index = 0, size = mRequestThreads.size(); index < size; index++) {
			int key = mRequestThreads.keyAt(index);
			stopRequestThread(key);
		}
		for (int index = 0, size = mDownloadThreads.size(); index < size; index++) {
			int key = mDownloadThreads.keyAt(index);
			stopDownloadThread(key);
		}
	}

	@Override
	public void stopRequestThread(int requestId) {
		LRequest task = mRequestThreads.get(requestId);
		if (task != null) {
			task.stop();
		}
	}

	@Override
	public void stopDownloadThread(int requestId) {
		LDownload task = mDownloadThreads.get(requestId);
		if (task != null) {
			task.stop();
		}
	}

	@Override
	public void download(String url, String savePath, String saveName,
			int requestId, ILNetworkCallback callback) {
		LDownload task = mDownloadThreads.get(requestId);
		if (task == null || task.getState() == LReqState.FINISHED) {
			task = new LDownload(url, savePath, saveName, requestId, callback,
					this);
			task.execute();
			mDownloadThreads.put(requestId, task);
		} else {
			L.i(TAG, "requestId " + requestId + " thread is running!");
		}
	}

}
