package com.example.shareference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cachetest.xpg.ClearCache;

public class TestActivity extends Activity {
	private SharedPreferences preferences = null;
	private TextView mTestTV;
	private Button mClearCacheBtn, mClearShareBTn, mClearfilesBtn,
			mClearInnerCacheBtn;
	private Context context;

	private ListenerTest listenerTest = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		listenerTest = new ListenerTest();
		context = getApplicationContext();
		initView();
	}

	private void initView() {
		mTestTV = (TextView) findViewById(R.id.test);
		preferences = getApplicationContext().getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		mTestTV.setText(preferences.getString("login", null));

		mClearCacheBtn = (Button) findViewById(R.id.clearOutCache);

		mClearfilesBtn = (Button) findViewById(R.id.Clearfiles);
		mClearInnerCacheBtn = (Button) findViewById(R.id.innerCache);
		mClearShareBTn = (Button) findViewById(R.id.ClearSharedPreference);

		mClearCacheBtn.setOnClickListener(listenerTest);
		mClearfilesBtn.setOnClickListener(listenerTest);
		mClearInnerCacheBtn.setOnClickListener(listenerTest);
		mClearShareBTn.setOnClickListener(listenerTest);
	}

	class ListenerTest implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Clearfiles:
				ClearCache.cleanFiles(context);
				Toast.makeText(getApplicationContext(),
						"清除/data/data/com.xxx.xxx/files下的内容", Toast.LENGTH_LONG)
						.show();
				break;
			case R.id.clearOutCache:
				ClearCache.cleanExternalCache(context);
				Toast.makeText(
						getApplicationContext(),
						"清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache",
						Toast.LENGTH_LONG).show();
				break;
			case R.id.ClearSharedPreference:
				ClearCache.cleanSharedPreference(context);

				Toast.makeText(
						getApplicationContext(),
						"清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)",
						Toast.LENGTH_LONG).show();
				break;
			case R.id.innerCache:
				ClearCache.cleanInternalCache(context);
				Toast.makeText(getApplicationContext(),
						"清除本应用内部缓存(/data/data/com.xxx.xxx/cache)",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
