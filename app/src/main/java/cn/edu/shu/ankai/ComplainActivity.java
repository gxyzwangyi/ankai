package cn.edu.shu.ankai;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;


public class ComplainActivity extends Activity {
        private EditText mFeedBackEt;
        private ButtonRectangle mSendBtn;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //隐藏标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //隐藏状态栏
            //定义全屏参数
            int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //获得当前窗体对象
            Window window=ComplainActivity.this.getWindow();
            //设置当前窗体为全屏显示
            window.setFlags(flag, flag);
            setContentView(R.layout.feed_back_view);
            mFeedBackEt = (EditText) findViewById(R.id.fee_back_edit);
            mSendBtn = (ButtonRectangle) findViewById(R.id.feed_back_btn);
            mSendBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String content = mFeedBackEt.getText().toString();
                    if (!TextUtils.isEmpty(content)) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "软件评价");
                        intent.putExtra(Intent.EXTRA_TEXT, content);
                        intent.setData(Uri.parse("shuwangyi@yeah.net"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ComplainActivity.this.startActivity(intent);
                        Toast.makeText(v.getContext(), "评价成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), "写点评价吧", Toast.LENGTH_LONG).show();

                    }
                }
            });




        }
    }



