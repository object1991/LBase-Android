package com.leo.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.leo.base.application.LApplication;
import com.leo.base.entity.LMessage;
import com.leo.base.handler.LHandler.OnLHandlerCallback;

/**
 * <h1>来源：</h1> 
 * LActivity 继承自 {@link android.support.v4.app.FragmentActivity} 
 * 
 * <h1>用途：</h1> 
 * 所有 Activity 需继承此类
 * 
 * <h1>说明：</h1> 
 * <li>
 * 继承 LActivity 类后，需要实现
 * {@linkplain com.leo.base.activity.LActivity#onLCreate(Bundle)
 * onLCreate(Bundle)} 方法，onLCreate 方法与
 * {@linkplain android.app.Activity#onCreate(Bundle savedInstanceState)
 * onCreate} 方法使用相同， 抽象出此方法是为了方便使用者继承之后，不需要再手动 Override
 * </li> 
 * <li>
 * 当你使用了
 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.net.LReqEntity)
 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此类的
 * {@linkplain com.leo.base.activity.LActivity#resultHandler(com.leo.base.entity.LMessage)
 * LActivity.resultHandler(LMessage)} 方法，并将解析的结果
 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回
 * </li>
 * 
 * @author Chen Lei
 * @version 1.1.5
 * 
 */
public abstract class LActivity extends FragmentActivity implements OnLHandlerCallback {

	/**
	 * 全局的上下文对象
	 */
	public Context mContext;

	/**
	 * 全局的 {@linkplain com.leo.base.application.LApplication LApplication} 对象
	 */
	public LApplication mLApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		mLApplication = LApplication.getInstance();
		mLApplication.setContext(mContext);
		mLApplication.setDestroyActivitys(false);
		mLApplication.addActivity(this);
		mLApplication.setFragmentManager(this.getSupportFragmentManager());
		onLCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 继承LActivity类后，需要实现
	 * {@linkplain com.leo.base.activity.LActivity#onLCreate(Bundle)
	 * onLCreate(Bundle)} 方法，onLCreate 方法与
	 * {@linkplain android.app.Activity#onCreate(Bundle savedInstanceState)
	 * onCreate} 方法使用相同， 抽象出此方法是为了方便使用者继承之后，不需要再手动 Override
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void onLCreate(Bundle savedInstanceState);

	/**
	 * 当你使用了
	 * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.net.LReqEntity)
	 * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
	 * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此方法，并将解析的结果
	 * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回
	 * 
	 * @param msg
	 */
	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		// ... 写入你需要的代码
	}

}
