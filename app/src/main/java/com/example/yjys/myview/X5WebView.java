package com.example.yjys.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.example.yjys.application.Application;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {

    private Context context;

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://") || url.contains("https://")){
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);

            //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
            int w = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int h = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            //重新测量
            webView.measure(w, h);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            return super.shouldInterceptRequest(webView, s);
        }

    };



    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context context) {
        super(context);
        this.context = context;
        setWebViewClient(client);
        initWebViewSettings();
        getView().setClickable(true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context context, AttributeSet arg1) {
        super(context, arg1);
        this.context = context;
        setWebViewClient(client);
        initWebViewSettings();
        getView().setClickable(true);
    }


    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);// 设置可以访问文件
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);// 不显示缩放工具
        webSetting.setUseWideViewPort(true);// 自适应手机屏幕
        webSetting.setLoadWithOverviewMode(true); // 适应手机屏幕的分辨率 且可以缩放
        webSetting.setSupportMultipleWindows(false);// 支持多窗口
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);// 开启DOM 缓存
        webSetting.setGeolocationEnabled(true);// 启用地理定位
        webSetting.setAppCacheMaxSize(1024*1024*100);// 设置缓存文件的大小
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 不使用cache 全部从网络上获取
        webSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSetting.setUserAgent("Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Mobile Safari/537.36");

        String appCachePath = Application.getInstance().getCacheDir().getAbsolutePath();
        webSetting.setAppCachePath(appCachePath);
        webSetting.setAllowFileAccess(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        setSaveEnabled(false);
    }


}