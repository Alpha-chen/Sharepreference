package com.example.shareference;

import junit.framework.Test;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mEditor;
	private Button mButton;
	private SharedPreferences preferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (loginStatus() == true) {
			Intent intent = new Intent(MainActivity.this, TestActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
		initView();

	}

	private boolean loginStatus() {
		preferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
		String tmp = preferences.getString("login", null);
		if ("test".equals(tmp)) {
			return true;
		}
		return false;

	}

	private void initView() {
		mEditor = (EditText) findViewById(R.id.editText1);
		mButton = (Button) findViewById(R.id.button1);

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				preferences = getSharedPreferences("test",
						Activity.MODE_PRIVATE);
				Intent intent = new Intent();

				if ("test".equals(mEditor.getText().toString())) {
					Editor editor = preferences.edit();
					editor.putString("login", mEditor.getText().toString());
					editor.commit();
					intent.setClass(MainActivity.this, TestActivity.class);
					startActivity(intent);
					MainActivity.this.finish();

				} else {
					Toast.makeText(getApplicationContext(), "密码错误",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
