package cn.edu.shu.ankai;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.edu.shu.ankai.service.AVService;
import cn.edu.shu.ankai.test.LabroomProgressActivity;
import cn.edu.shu.ankai.test.JsonCommon;
import cn.edu.shu.ankai.utils.PreferenceUtils;


public class LoginActivity extends BaseActivity {

	Button loginButton;
	Button forgetPasswordButton;
	EditText userNameEditText;
	EditText userPasswordEditText;
	private ProgressDialog progressDialog;
	TestGenAESByteKey testGenAESByteKey;
	private static final int COMPLETED = 0;
	public String json;
	public String password;
	public String username;
	public String password_raw;
public String skey;
	public String[] result = new String[2];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		testGenAESByteKey = new TestGenAESByteKey();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window window = LoginActivity.this.getWindow();
		window.setFlags(flag, flag);
		setContentView(R.layout.activity_login);
		AVAnalytics.trackAppOpened(getIntent());
		AVService.initPushService(this);

		loginButton = (Button) findViewById(R.id.button_login);
		forgetPasswordButton = (Button) findViewById(R.id.button_forget_password);
		userNameEditText = (EditText) findViewById(R.id.editText_userName);
		userPasswordEditText = (EditText) findViewById(R.id.editText_userPassword);

		if (getUserId() != null) {
			Intent mainIntent = new Intent(activity, LabroomProgressActivity.class);
			startActivity(mainIntent);
			activity.finish();
		}
		loginButton.setOnClickListener(loginListener);
		forgetPasswordButton.setOnClickListener(forgetPasswordListener);
	}

	OnClickListener loginListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@SuppressWarnings({"unchecked", "rawtypes"})
		@Override
		public void onClick(View arg0) {

			password_raw = userPasswordEditText.getText().toString();
			username = userNameEditText.getText().toString();
			Log.e("用户1",username);
			if (username.isEmpty()) {
				showUserNameEmptyError();
				return;
			}
			if (password_raw.isEmpty()) {
				showUserPasswordEmptyError();
				return;
			}

			Log.e("用户2",username);

			try {
				String key = "AKApp419";
				String jiami=java.net.URLEncoder.encode(password_raw, "utf-8");
				System.out.println("加密数据:"+jiami);
				password=testGenAESByteKey.toHexString(TestGenAESByteKey.encrypt(jiami, key));
				System.out.println("加密数据:"+password);

				} catch (Exception e) {
				e.getMessage();
			}

			progressDialogShow();
			JsonDownloadThread thread = new JsonDownloadThread();
			thread.start();
			Log.e("用户3", username);


		}


	};

	OnClickListener forgetPasswordListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			Uri uri = Uri.parse("http://services.shu.edu.cn/");
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(it);
		}
	};



	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(activity,
						activity.getResources().getText(
								R.string.dialog_message_title),
						activity.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}

	private void showLoginError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_login_error))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showAnkaiError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_login_error_ankai))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						}).show();
	}


	private void showUserPasswordEmptyError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_register_password_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserNameEmptyError() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						activity.getResources().getString(
								R.string.error_register_user_name_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						}).show();
	}


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == COMPLETED) {
				Log.e("用户4",username);

				Log.e("确定",json);
				Log.e("确定",json.substring(0,1));
				if (json.substring(0,1).equals("1"))
					{
						String[] userarray=json.substring(1, json.length()).split(",");
						Log.e("确定", userarray[0] + "+" + userarray[1] + "+" + userarray[2]);
						PreferenceUtils.setPrefString(LoginActivity.this, "school", userarray[1]);
						PreferenceUtils.setPrefString(LoginActivity.this, "dcode", userarray[2]);
						Toast.makeText(LoginActivity.this, "成功登录", Toast.LENGTH_LONG);
					Log.e("确定", json);
					AVUser.logInInBackground(username,
							password_raw,
							new LogInCallback() {
								public void done(AVUser user, AVException e) {
									if (user != null) {
										progressDialogDismiss();
										Intent mainIntent = new Intent(activity,
												LabroomProgressActivity.class);
										startActivity(mainIntent);
										//activity.finish();
									} else {
										progressDialogDismiss();
										Log.e("确定1","1");
										register();
										//showLoginError();
									}
								}
							});
				}
				else if(json.substring(0,1).equals("2")){
					progressDialogDismiss();
					showAnkaiError();
				}
				else {
					progressDialogDismiss();
					showLoginError();
				}


			}
		}
	};


	public class JsonDownloadThread extends Thread {
		public void run() {
			Looper.prepare();
			String key = "sdhr66135152";
			InputStream inStream = this.getClass().getResourceAsStream("/assets/login.xml");
			try {
				json = getMobileAddress(inStream, key, username,password);

				Message msg = new Message();
				msg.what = COMPLETED;
				handler.sendMessage(msg);

			} catch (Exception e) {
			}


		}
	}


	private String getMobileAddress(InputStream inStream, String key, String user, String pass) throws Exception {
		String soap = readSoapFile(inStream, key, user, pass);
		byte[] data = soap.getBytes();
		Log.e("搜谱",soap);
		URL url = new URL("http://202.121.199.198/AnkaiAPPWebService/WebService_AnKaiDaTi.asmx");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		Log.e("用户5",username);


		conn.setRequestMethod("POST");
		conn.setConnectTimeout(10 * 1000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			Log.e("步骤", "3");
			InputStream Stream = conn.getInputStream();
			return JsonCommon.parseResponseXML(Stream, "ShuLoginCheckResult");
		}
		Log.e("步骤1", conn.getResponseCode() + "");
		return conn.getResponseCode() + "";
	}

	private String readSoapFile(InputStream inStream, String key, String user, String pass) throws Exception {
		byte[] data = JsonCommon.readInputStream(inStream);
		String soapxml = new String(data);
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", key);
		params.put("user", user);
		params.put("pass", pass);
		Log.e("用户123", username);
		Log.e("用户123",user);

		return JsonCommon.replace(soapxml, params);

	}

	public void register() {
		Log.e("确定1","2");
		SignUpCallback signUpCallback = new SignUpCallback() {
			public void done(AVException e) {
				Log.e("确定1","3");
				progressDialogDismiss();
				if (e == null) {
					Log.e("确定1", "4");
					showRegisterSuccess();
					Intent mainIntent = new Intent(activity, LabroomProgressActivity.class);
					startActivity(mainIntent);
					//activity.finish();
				} else {
					Log.e("确定1",e.getCode()+"");
					switch (e.getCode()) {
						case 202:
							showError(activity
									.getString(R.string.error_register_user_name_repeat));
							break;
						case 203:
							showError(activity
									.getString(R.string.error_register_email_repeat));
							break;
						default:
							showError(activity
									.getString(R.string.network_error));
							break;
					}
				}
			}
		};
		AVService.signUp(username, password_raw, signUpCallback);

	}


	private void showRegisterSuccess() {
		new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						activity.getResources().getString(
								R.string.success_register_success))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						}).show();
	}


}