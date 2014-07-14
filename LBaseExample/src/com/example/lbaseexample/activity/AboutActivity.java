package com.example.lbaseexample.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lbaseexample.R;
import com.leo.base.activity.LActivity;

public class AboutActivity extends LActivity {

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about);
		((TextView) findViewById(R.id.about_textview)).setText(getAboutStr());
	}

	private String getAboutStr() {
		return "作者(Developer)：陈磊 (Chen Lei)\n版本号(Version)：1.1.8\nEMail：objectes@126.com\nQQ：75806772\n"
				+ "制作时间(Time)：2014.07.11\n状态(State)：开源-未停止更新(Open Source - Not Stop Updating)";
	}
}
