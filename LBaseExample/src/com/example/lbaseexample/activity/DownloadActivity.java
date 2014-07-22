package com.example.lbaseexample.activity;

import java.io.File;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.lbaseexample.R;
import com.example.lbaseexample.common.MApplication;
import com.example.lbaseexample.handler.DownloadHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.util.LFormat;

public class DownloadActivity extends LActivity implements OnClickListener {

	private ProgressBar progress;
	private Button button;
	private ImageView imageview;

	private boolean isDown = false;

	private DownloadHandler handler;

	private String fileUrl;

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_download);
		progress = (ProgressBar) findViewById(R.id.download_progress);
		button = (Button) findViewById(R.id.download_button);
		imageview = (ImageView) findViewById(R.id.download_imageview);
		button.setOnClickListener(this);
		handler = new DownloadHandler(this);
	}

	@Override
	public void onResultHandler(LMessage msg, int requestId) {
		super.onResultHandler(msg, requestId);
		if (msg != null) {
			Bitmap bitmap = (Bitmap) msg.getObj();
			if (bitmap != null) {
				imageview.setImageBitmap(bitmap);
				imageview.setVisibility(View.VISIBLE);
				fileUrl = msg.getStr();
				button.setText("下载完成");
				isDown = false;
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(handler != null) {
			handler.stopAllThread();
		}
		if (!LFormat.isEmpty(fileUrl)) {
			File file = new File(fileUrl);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public ProgressBar getProgressBar() {
		return progress;
	}

	@Override
	public void onClick(View v) {
		if (!isDown) {
			String url = "http://www.zhuoku.org/uploadfile/2013/0125/20130125102609585.jpg";
			handler.download(url, MApplication.getInstance().getCacheFile()
					.getPath(), LFormat.getMD5Url(url), 0);
			button.setText("正在下载");
			imageview.setVisibility(View.GONE);
		} else {
			handler.stopDownloadThread(0);
			button.setText("下载已取消，点击重新下载");
		}
		isDown = !isDown;
	}

}
