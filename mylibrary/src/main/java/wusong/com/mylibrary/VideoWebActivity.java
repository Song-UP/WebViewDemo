package wusong.com.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by wusong on 2018/2/25.
 */

public class VideoWebActivity extends AppCompatActivity {
    WebView webView;
    WebSettings webSettings;
    public static void start(Context context){
        context.startActivity(new Intent(context, VideoWebActivity.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    //--------
    //变现猫提供的设置，设置硬件加速,仅供参考
    public void setweVideo(){
        //用于webview设置
        //addJavascriptInterface(this.a, "etouch_client");
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        int i = Build.VERSION.SDK_INT;
        try {
            webSettings.setAllowFileAccess(true);
            if (i>= 5) {
                webSettings.setDatabaseEnabled(true);
                webSettings.setGeolocationEnabled(true);
            }

            if (i>= 7) {
                webSettings.setAppCacheEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadWithOverviewMode(true);
            }
            if (i>= 8) {
                webSettings.setPluginState(WebSettings.PluginState.ON);
            }
            webSettings.setBuiltInZoomControls(false);
            webSettings.setSupportZoom(false);
            webSettings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        } catch (Exception e) {
        }

       // webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }
//--------


    public void initView(){
        webView = (WebView) findViewById(R.id.mWebView);
        webView.loadUrl("http://www.youku.com");
        //是用工具类对webView进行配置和管理
        setWebSetting();
        setWebViewClient();
        //setWebViewClient();
    }

    //对WebView进行各种设置
    public void setWebSetting(){
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //解决视频无法播放
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setUseWideViewPort(true);
        //webView.addJavascriptInterface(new MyJavaScript(), "JsUtils");
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

//用语接受各种通知，
    public void setWebViewClient(){
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
        webView.setWebViewClient(webViewClient);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webSettings.setJavaScriptEnabled(false);
    }
}
