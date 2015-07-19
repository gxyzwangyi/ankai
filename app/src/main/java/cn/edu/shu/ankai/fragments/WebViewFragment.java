package cn.edu.shu.ankai.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gc.materialdesign.views.ButtonFloat;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.sosinformation.SOSInfoActivity;


/**
 * Created by florentchampigny on 24/04/15.
 */
public class WebViewFragment extends Fragment {

    private ObservableWebView mWebView;

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }
    ButtonFloat button;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = (ObservableWebView) view.findViewById(R.id.webView);
        mWebView.getSettings().setLoadsImagesAutomatically(true);

        //must be called before loadUrl()
        MaterialViewPagerHelper.preLoadInjectHeader(mWebView);

        button = (ButtonFloat) view.findViewById(R.id.buttonFloat);



        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SOSInfoActivity.class);
                startActivity(intent);
            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);



                //have to inject header when WebView page loaded
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        MaterialViewPagerHelper.injectHeader(mWebView, true);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

        mWebView.loadUrl("http://202.121.199.198/AnKaiAPPRes/Bootstrap%20%E4%B8%AD%E6%96%87%E6%96%87%E6%A1%A3.html");

        MaterialViewPagerHelper.registerWebView(getActivity(), mWebView, null);
    }




}
