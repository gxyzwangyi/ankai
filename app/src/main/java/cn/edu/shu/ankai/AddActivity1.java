package cn.edu.shu.ankai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.shu.ankai.utils.App;


/**
 * Created by gxyzw_000 on 2015/6/18.
 */
public class AddActivity1 extends Activity {


    private static final int IMAGE_PICK_REQUEST = 0;
    private static final int CEMERA = 1;
    public String profilelocal = "定位没成功";
    private String profilelolLL = "定位经纬度没成功";
    private App app;
    Context context;
    AVUser currentUser = AVUser.getCurrentUser();
    Button imageAction;
    boolean haveImage = false;
    Bitmap bitmap;
    private Button button;
    String str1;
    String str2;
    String str3;
    String str4;
//+
    private com.gc.materialdesign.views.ButtonRectangle btnSend;
    private static final String URL = "http://202.121.199.198/AnKaiAPPWebService/WebService_AnKaiDaTi.asmx";
    private static final String METHOD = "AddSOSInfo";
    private ProgressDialog proDialog;

    @InjectView(R.id.editText)
    EditText editText;

    @InjectView(R.id.image)
    ImageView imageView;

    @InjectView(R.id.send_local)
    com.rey.material.widget.EditText local_edit;

    @InjectView(R.id.send_ll)
    TextView ll_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = AddActivity1.this.getWindow();
        window.setFlags(flag, flag);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_send_layout);

        context = this;
        imageAction = (Button) findViewById(R.id.imageAction);
        ButterKnife.inject(this);
        setButtonAndImage();
        app = (App) getApplication();
        button = (Button) findViewById(R.id.camerabutton);
//+
        btnSend = (com.gc.materialdesign.views.ButtonRectangle) findViewById(R.id.send);
        setCemeraImage();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (haveImage == false) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else {
                    bitmap = null;
                    haveImage = false;
                    setCemeraImage();
                }

            }

        });
//+
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送请求给webservice
                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected void onPreExecute() {
                        if (proDialog == null) {
                            proDialog = ProgressDialog.show(AddActivity1.this, "", "正在发送求救...", true);
                        } else {
                            proDialog.show();
                        }
                        super.onPreExecute();
                    }

                    @Override
                    protected String doInBackground(Void... params) {
                        String userName = currentUser.getUsername();
                        Log.d("lhy", userName);
                        String description = editText.getText().toString();
                        String pic = "";
                        String location = profilelocal;
                        String longitudes = profilelolLL;
                        //String fileName = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                        String time = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

                        //读取图片转换成 String
                        if (haveImage) {
                            try {
                                pic = Bmp2Str(bitmap);
                            } catch (IOException e) {
                                Log.d("lhy", e.getMessage().toString());
                                e.printStackTrace();
                            }
                        }

                        //设置参数
                        SoapHelper sh = new SoapHelper(URL, METHOD);
                        sh.addProperty("key", "sdhr66135152");
                        sh.addProperty("userAccount", userName);
                        sh.addProperty("description", description);
                        sh.addProperty("pic", pic);
                        sh.addProperty("location", location);
                        sh.addProperty("longitudes", longitudes);
                        //sh.addProperty("fileName", fileName);
                        sh.addProperty("time", time);

                        //执行
                        try {
                            return sh.call();
                        } catch (IOException e) {
                            Log.d("lhy", e.getMessage().toString());
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            Log.d("lhy", e.getMessage().toString());
                            e.printStackTrace();
                        }
                        return "ERROR";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if (s.equals("OK")) {
                            Toast.makeText(AddActivity1.this, "发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddActivity1.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }
                        if (proDialog != null) {
                            proDialog.dismiss();
                        }
                        super.onPostExecute(s);
                    }
                }.execute();
            }
        });

        if (app.getCurrentLocation() != null && !isConnectionAvailable(this)) {
            profilelocal = app.getCurrentLocation().getAddr();
            profilelolLL = app.getCurrentLocation().getLatitude() + "," + app.getCurrentLocation().getLongitude();
            Log.v("我的", profilelocal + profilelolLL);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        str1 = currentUser.getUsername();
        str2 = formatter.format(curDate);
        str3 = profilelocal;
        str4 = profilelolLL;


        local_edit.setText(str3);
        ll_text.setText(str4);

        /*//位置记录
        AVObject locationdata = new AVObject("Locationdata");
        locationdata.put("name", str1);
        locationdata.put("time", str2);
        locationdata.put("addr", local_edit.getText());
        locationdata.put("ll", str4);
        locationdata.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.i("LeanCloud", "Save successfully.");
                } else {
                    Log.e("LeanCloud", e.getMessage());
                }
            }
        });
        */

    }


    public void onResume() {
        super.onResume();
//-
        //位置记录
       /* AVObject locationdata = new AVObject("Locationdata");
        locationdata.put("name", str1);
        locationdata.put("time", str2);
        locationdata.put("addr", str3);
        locationdata.put("ll", str4);
        locationdata.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.i("LeanCloud", "Save successfully.");
                } else {
                    Log.e("LeanCloud", e.getMessage());
                }
            }
        });*/

    }


    public static boolean isConnectionAvailable(Context cotext) {
        Context c = cotext;
        boolean isConnectionFail = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) cotext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                isConnectionFail = true;
                Toast.makeText(c, "请检查您的网络配置", Toast.LENGTH_LONG).show();

            } else {
                isConnectionFail = false;
            }
        } else {

        }
        return isConnectionFail;
    }


    void setButtonAndImage() {
        imageView.setImageBitmap(bitmap);
        if (haveImage) {
            String str1 = getString(R.string.status_cancelImage);
            imageAction.setText(str1);
            imageView.setVisibility(View.VISIBLE);
        } else {
            String str2 = getString(R.string.status_addImage);
            imageAction.setText(str2);
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    void setCemeraImage() {
        imageView.setImageBitmap(bitmap);
        if (haveImage) {
            String str3 = getString(R.string.status_delcamera);
            button.setText(str3);
            imageView.setVisibility(View.VISIBLE);
        } else {
            String str4 = getString(R.string.status_addcamera);
            button.setText(str4);
            imageView.setVisibility(View.INVISIBLE);

        }
    }
//-
   /* @OnClick(R.id.send)
    void send() {
        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text) == false || bitmap != null) {
            final ProgressDialog dialog = StatusUtils.showSpinnerDialog(this);
            StatusService.sendStatus(text, bitmap, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    dialog.dismiss();
                    if (StatusUtils.filterException(context, e)) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
        }
    }*/


    public static void pickImage(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    @OnClick(R.id.imageAction)
    void imageAction() {
        if (haveImage == false) {
            pickImage(this, IMAGE_PICK_REQUEST);
        } else {
            bitmap = null;
            haveImage = false;
            setButtonAndImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_REQUEST) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    haveImage = true;
                    setButtonAndImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CEMERA) {

                String sdStatus = Environment.getExternalStorageState();

                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    Log.i("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }
                String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");

                FileOutputStream b = null;

                File file = new File("/sdcard/myImage/");
                file.mkdirs();//
                String fileName = "/sdcard/myImage/" + name;

                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, b);
                    haveImage = true;
                    imageView.setImageBitmap(bitmap);
                    setCemeraImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


        }
    }

    //+
    //SOAP助手
    class SoapHelper {

        private final String NAMESPACE = "http://tempuri.org/";//默认命名空间
        // 网址
        private String URL;
        // 方法名
        private String METHOD_NAME;
        // SOAPACTION
        private String SOAP_ACTION;

        private SoapObject req;

        public SoapHelper(String url, String method) {
            this.URL = url;
            this.METHOD_NAME = method;
            SOAP_ACTION = NAMESPACE + METHOD_NAME;
            req = new SoapObject(NAMESPACE, METHOD_NAME);
        }

        public void addProperty(String name, Object value) {
            req.addProperty(name, value);
        }

        public String call() throws IOException, XmlPullParserException {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = req;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(req);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            ht.call(SOAP_ACTION, envelope);

            return envelope.getResponse().toString();
        }
    }

    //将bitmap转换成string
    public String Bmp2Str(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);//压缩
        byte[] datas = baos.toByteArray();// 转为byte数组
        baos.close();
        return Base64.encodeToString(datas, Base64.DEFAULT);//编码
    }
}
