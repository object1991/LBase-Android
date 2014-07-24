package com.example.lbaseexample.activity;

import org.json.JSONException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.lbaseexample.R;
import com.example.lbaseexample.common.MNetwork;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.entity.LReqEntity;
import com.leo.base.exception.LLoginException;
import com.leo.base.net.ILNetwork;
import com.leo.base.net.ILNetworkCallback;
import com.leo.base.net.ILNetwork.LReqResultState;

public class ManyRequestActivity extends LActivity implements OnClickListener {

	private Button button;

	private TextView textview;

	private boolean isStart = true;

	/**
	 * 网络请求实例
	 */
	private ILNetwork requestNetwork;

	private StringBuilder sb;

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_many_request);
		button = (Button) findViewById(R.id.many_request);
		textview = (TextView) findViewById(R.id.many_infos);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// ... 同时启动多个线程，使用不同的线程ID，启动之后，再次点击按钮，可以停止所有线程
		if (isStart) {
			button.setText("停止线程");
			sendRequest(30);
		} else {
			// 停止requestNetwork对象的所有线程
			requestNetwork.stopAllThread();
			// 指定线程的停止方法
			// requestNetwork.stopThread(9);
			// requestNetwork.stopThread(18);
			// requestNetwork.stopThread(27);
			button.setText("启动所有线程");
		}
		isStart = !isStart;
	}

	/**
	 * 同时启动多个请求, 下面的请求方法可以正常使用，但LBase不建议使用此方法直接请求，建议使用ListViewActivity中处理方式 <br/>
	 * onParse方法运行在子线程，可以解析返回结果使用 onHandlerUI运行在UI线程，将返回的封装实体在此处更新UI
	 * onException运行在UI线程，返回多种异常状态，可以相对不同的异常进行处理
	 * 
	 * @param requestCount
	 */
	private void sendRequest(int requestCount) {
		LReqEntity requestEntity = new LReqEntity();
		requestEntity.setUrl("http://www.baidu.com");
		if (requestNetwork == null) {
			requestNetwork = new MNetwork();
		}
		sb = new StringBuilder();
		for (int i = 0; i < requestCount; i++) {
			requestNetwork.request(requestEntity, i, new ILNetworkCallback() {

				@Override
				public LMessage onParse(String strs, int requestId)
						throws LLoginException, JSONException, Exception {
					// ... 运行在子线程
					LMessage msg = new LMessage();
					msg.setStr(strs);
					return msg;
				}

				@Override
				public void onHandlerUI(LMessage msg, int requestId) {
					// ... 运行在UI线程
					sb.append("请求requestId为：").append(requestId)
							.append("的请求，成功！\n");
					textview.setText(sb.toString());
				}

				@Override
				public void onException(LReqResultState state, int requestId) {
					// ... 运行在UI线程
					if (state == LReqResultState.STOP) {
						sb.append("请求requestId为：").append(requestId)
								.append("的请求，被停止！\n");
					} else {
						sb.append("请求requestId为：").append(requestId)
								.append("的请求，发现异常！\n");
					}
					textview.setText(sb.toString());
				}

				@Override
				public void onProgress(int count, int current, int requestId) {
					// ... 上传文件时，在此处可以回调进度
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (requestNetwork != null) {
			requestNetwork.stopAllThread();
		}
	}

}
