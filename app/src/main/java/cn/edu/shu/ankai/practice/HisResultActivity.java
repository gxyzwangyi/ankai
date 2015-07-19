package cn.edu.shu.ankai.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.sqlite.DBManager;
import cn.edu.shu.ankai.sqlite.HisResult;
import cn.edu.shu.ankai.sqlite.HistoryResultColumns;
import cn.edu.shu.ankai.utils.StreamTool;
import cn.edu.shu.ankai.utils.ViewHolder;


public class HisResultActivity extends Activity implements OnClickListener {
	private static final int COMPLETED = 0;
	private ArrayList<HisResult> list;
	private MyAdapter myAdapter;
	private  String json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		// 先读取数据库中的缓存, 数据量较多比较耗时，应使用AsyncTask
		new QueryTask().execute();
		setContentView(R.layout.activity_his_result);
		resetTitlebar();
		ListView listView = (ListView) findViewById(R.id.listView1);
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if(list.size() == 0){
				return 0;
			}else{
				return list.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_listview_hisresult, null);
			}
			TextView name = ViewHolder.get(convertView, R.id.name);
			TextView curTime = ViewHolder.get(convertView, R.id.curTime);
			TextView useTime = ViewHolder.get(convertView, R.id.useTime);
			TextView hisResult = ViewHolder.get(convertView, R.id.hisResult);
			HisResult myData = list.get(position);
			name.setText(myData.getName());
			curTime.setText(myData.getCurTime());
			useTime.setText(myData.getUseTime());
			hisResult.setText(myData.getHisResult());
			return convertView;
		}
	}
	
	private class QueryTask extends AsyncTask<Void, Void, ArrayList<HisResult>> {
		@Override
		protected ArrayList<HisResult> doInBackground(Void... params) {
			list = DBManager.getInstance(HisResultActivity.this).queryHisResult(HistoryResultColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<HisResult> list) {
			if (list.size() == 0) {
				return;
			}
			myAdapter.notifyDataSetChanged();
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
		final TextView title = (TextView) findViewById(R.id.titlebar_title);
		TextView right = (TextView) findViewById(R.id.titlebar_right_text);
		TextView tongbu = (TextView) findViewById(R.id.titlebar_tongbu_text);

		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		title.setText("历史成绩");
		right.setText("清空");
		tongbu.setText("同步");

		right.setOnClickListener(this);
		tongbu.setOnClickListener(this);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_right_text:
			DBManager.getInstance(this).removeAll(HistoryResultColumns.TABLE_NAME);
			list.clear();
			myAdapter.notifyDataSetChanged();
			break;


		case R.id.titlebar_tongbu_text:
			DBManager.getInstance(this).removeAll(HistoryResultColumns.TABLE_NAME);
			list.clear();
			list=getData();
			Intent intent2 = new Intent(this, ResultProgressActivity.class);
			startActivity(intent2);
			finish();
			//myAdapter.notifyDataSetChanged();
			default:
			break;
		}
	}





	private ArrayList<HisResult> getData() {
		ArrayList<HisResult> list = new ArrayList();
		json = StreamTool.load(HisResultActivity.this, "test.txt");
		Log.e("请问", json);
		try {
			JSONObject jsonObject = new JSONObject(json);
			int code = jsonObject.getInt("code");
			if (code == 1) {
				String content = jsonObject.getString("content");
				JSONArray array = new JSONArray(content);
				Log.e("请问1",content.length()+"");
				Log.e("请问2",array.length()+"");
				for (int i = 0; i < array.length(); i++) {
					Log.e("请问3",i+"");
					JSONObject object = array.getJSONObject(i);
					String UserAccount = object.getString("UserAccount");
					String TestTime  = object.getString("TestTime");
					String UseTime = object.getString("UseTime");
					String Grade = object.getString("Grade");
					HisResult myData = new HisResult(TestTime, UseTime, Grade, UserAccount);
					DBManager.getInstance(HisResultActivity.this).insertHisResult(myData);
					list.add(myData);
				}
			} else {
				Toast.makeText(HisResultActivity.this, "数据解析出现异常，请联系管理员", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			Log.e("请问4",e.getMessage());
		}
		return list;
	}






	
}
