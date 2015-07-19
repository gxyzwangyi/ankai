package cn.edu.shu.ankai.practice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.sqlite.AnswerColumns;
import cn.edu.shu.ankai.sqlite.BaseColumns;
import cn.edu.shu.ankai.sqlite.CauseInfo;
import cn.edu.shu.ankai.sqlite.DBManager;
import cn.edu.shu.ankai.utils.StreamTool;
import cn.edu.shu.ankai.utils.TimeUtils;

public class MainActivity extends Activity implements OnClickListener {

	private boolean hasPressedBack;
	private ProgressBar progressBar;
	String json;
	AVUser currentUser = AVUser.getCurrentUser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (TimeUtils.isNetworkAvailable(this)) {
			DBManager.getInstance(this).removeAll(AnswerColumns.TABLE_NAME);
		}
		setContentView(R.layout.activity_practice_main);

		TextView order = (TextView) findViewById(R.id.order);
		TextView simulate = (TextView) findViewById(R.id.simulate);
		TextView refresh = (TextView) findViewById(R.id.refresh);
		LinearLayout favorite = (LinearLayout) findViewById(R.id.favorite);
		LinearLayout wrong = (LinearLayout) findViewById(R.id.wrong);
		LinearLayout history = (LinearLayout) findViewById(R.id.history);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		asynctaskInstance();
		order.setOnClickListener(this);
		simulate.setOnClickListener(this);
		refresh.setOnClickListener(this);
		favorite.setOnClickListener(this);
		wrong.setOnClickListener(this);
		history.setOnClickListener(this);
	}

	private void asynctaskInstance() {
		progressBar.setVisibility(View.VISIBLE);
		AsyncHttpClient client = new AsyncHttpClient();
		String jsonString="";
		String resultString="";
//		try {
//		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(
//				this.getResources().getAssets().open("test.txt")));
//			while ((jsonString=bufferedReader.readLine())!=null) {
//				resultString+=jsonString;
//			}
//		}
//	catch(IOException e) {
//		Log.e("没了", "");
//	}


//		try {
//			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(
//					StreamTool.getStringStream(StreamTool.load(this, "timu.txt"))));
//			while ((jsonString=bufferedReader.readLine())!=null) {
//				resultString+=jsonString;
//			}
//		}
//		catch(IOException e) {
//			Log.e("没了", "");
//		}



		json = StreamTool.load(MainActivity.this, "timu.txt");
		Log.e("请问json",json);
		//String result = new String(arg2);
		DBManager.getInstance(MainActivity.this).removeAll(AnswerColumns.TABLE_NAME);
		try {
			JSONObject jsonObject = new JSONObject(json);
			Log.e("请问2","1");
			int code = jsonObject.getInt("code");
			if (code == 1) {
				Log.e("请问1","1");
				String content = jsonObject.getString("content");
				Log.e("请问4","1");
				JSONArray array = new JSONArray(content);
				for (int i = 0; i < content.length(); i++) {
					Log.e("请问3","1");
					JSONObject object = array.getJSONObject(i);
					String timu_title = new JSONObject(object.getString("timu")).getString("title");
					String timu_one = new JSONObject(object.getString("timu")).getString("one");
					String timu_tow = new JSONObject(object.getString("timu")).getString("tow");
					String timu_three = new JSONObject(object.getString("timu")).getString("three");
					String timu_four = new JSONObject(object.getString("timu")).getString("four");
					String daan_one = new JSONObject(object.getString("daan")).getString("daan_one");
					String daan_tow = new JSONObject(object.getString("daan")).getString("daan_tow");
					String daan_three = new JSONObject(object.getString("daan")).getString("daan_three");
					String daan_four = new JSONObject(object.getString("daan")).getString("daan_four");
					String types = new JSONObject(object.getString("types")).getString("types");
					String detail = new JSONObject(object.getString("detail")).getString("detail");
					int reply = BaseColumns.NULL;
					CauseInfo myData = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
							daan_three, daan_four, detail, types, reply);
					DBManager.getInstance(MainActivity.this).insert(AnswerColumns.TABLE_NAME, myData);
				}
			} else {
				Toast.makeText(MainActivity.this, "数据解析出现异常，请联系管理员", Toast.LENGTH_SHORT).show();
			}
			progressBar.setVisibility(View.GONE);
		} catch (JSONException e) {
		Log.e("请问",e.getMessage());
			progressBar.setVisibility(View.GONE);
		}



		/*
		client.post(Contans.PATH_HOME, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String result = new String(arg2);
				try {
					JSONObject jsonObject = new JSONObject(result);

					int code = jsonObject.getInt("code");
					if (code == 1) {
						String content = jsonObject.getString("content");
						JSONArray array = new JSONArray(content);
						for (int i = 0; i < content.length(); i++) {
							JSONObject object = array.getJSONObject(i);
							String timu_title = new JSONObject(object.getString("timu")).getString("title");
							String timu_one = new JSONObject(object.getString("timu")).getString("one");
							String timu_tow = new JSONObject(object.getString("timu")).getString("tow");
							String timu_three = new JSONObject(object.getString("timu")).getString("three");
							String timu_four = new JSONObject(object.getString("timu")).getString("four");
							String daan_one = new JSONObject(object.getString("daan")).getString("daan_one");
							String daan_tow = new JSONObject(object.getString("daan")).getString("daan_tow");
							String daan_three = new JSONObject(object.getString("daan")).getString("daan_three");
							String daan_four = new JSONObject(object.getString("daan")).getString("daan_four");
							String types = new JSONObject(object.getString("types")).getString("types");
							String detail = new JSONObject(object.getString("detail")).getString("detail");
							int reply = BaseColumns.NULL;
							CauseInfo myData = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
									daan_three, daan_four, detail, types, reply);
							DBManager.getInstance(MainActivity.this).insert(AnswerColumns.TABLE_NAME, myData);
						}
					} else {
						Toast.makeText(MainActivity.this, "数据解析出现异常，请联系管理员", Toast.LENGTH_SHORT).show();
					}
					progressBar.setVisibility(View.GONE);
				} catch (JSONException e) {
					progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				progressBar.setVisibility(View.GONE);
			}
		});
		*/



	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.order:
			startActivity(new Intent(this, OrderActivity.class));
			break;
		case R.id.refresh:
			startActivity(new Intent(this, ChangeTimuActivity.class));
			finish();
			break;
		case R.id.simulate:
			View layout = getLayoutInflater().inflate(R.layout.enter_simulate, null);
			final Dialog dialog = new Dialog(this);
			dialog.setTitle("温馨提示");
			dialog.show();
			dialog.getWindow().setContentView(layout);
			//final EditText et_name = (EditText) layout.findViewById(R.id.et_name);
			TextView confirm = (TextView) layout.findViewById(R.id.confirm);
			TextView cancel = (TextView) layout.findViewById(R.id.cancel);
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				//	if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
				//		Toast.makeText(MainActivity.this, "请先输入考试备注", Toast.LENGTH_SHORT).show();
				//	} else {
						ExamActivity.intentToExamActivity(MainActivity.this,currentUser.getUsername());
						Toast.makeText(MainActivity.this, "考试开始", Toast.LENGTH_SHORT).show();
						//}
					dialog.dismiss();
				}
			});

			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			break;

		case R.id.favorite:
			startActivity(new Intent(this, CollectActivity.class));
			break;

		case R.id.wrong:
			startActivity(new Intent(this, ErrorActivity.class));
			break;

		case R.id.history:
			startActivity(new Intent(this, HisResultActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (hasPressedBack) {
				finish();
				return true;
			}
			hasPressedBack = true;
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					hasPressedBack = false;
				}
			}, 3000);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
//		SpotManager.getInstance(this).onStop();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
//		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
	}

}
