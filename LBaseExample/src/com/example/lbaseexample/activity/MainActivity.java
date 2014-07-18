package com.example.lbaseexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.lbaseexample.R;
import com.example.lbaseexample.db.DBManager;
import com.leo.base.activity.LActivity;
import com.leo.base.util.L;
import com.leo.base.util.LMobileInfo;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

public class MainActivity extends LActivity implements OnClickListener {

	private static final String KEY = MainActivity.class.getSimpleName();

	private TextView tvInfos;

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		initLayout();
		// ... 添加用户信息到SharedPreferences存储
		LSharePreference.getInstance(this).setString("username", "admin");
		LSharePreference.getInstance(this).setString("password", "admin");
	}

	private void initLayout() {
		tvInfos = (TextView) findViewById(R.id.main_infos);

		((Button) findViewById(R.id.main_listview)).setOnClickListener(this);

		((Button) findViewById(R.id.main_fragment_viewpager))
				.setOnClickListener(this);

		((Button) findViewById(R.id.main_many_request))
				.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String strs = "手机型号：" + LMobileInfo.getMobileType() + "\n" + "系统版本："
				+ LMobileInfo.getVersionRelease() + "\n" + "IMEI："
				+ LMobileInfo.getImei() + "\n" + "IMSI："
				+ LMobileInfo.getImsi();
		L.i(KEY, strs);
		tvInfos.setText(strs);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_listview:
			L.i("进入 ListView 示例");
			getJumpIntent(ListViewActivity.class);
			break;
		case R.id.main_fragment_viewpager:
			L.i("进入 Fragment ViewPager 示例");
			getJumpIntent(FragmentViewPagerActivity.class);
			break;
		case R.id.main_many_request:
			getJumpIntent(ManyRequestActivity.class);
			break;
		}
	}

	private void getJumpIntent(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		this.startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_clear_all_data:
			boolean b = DBManager.get().delListEntitys();
			if (b) {
				T.ss("清理完成！");
			} else {
				T.ss("清理失败");
			}
			break;
		case R.id.action_about_me:
			T.ss("关于我");
			getJumpIntent(AboutActivity.class);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
