package wusong.com.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by SongUp on 2018/2/24.
 */

public class WebActivity extends AppCompatActivity {
    WebView webView;
    WebSettings settings;
    WebViewClient webViewClient;

    public static void start(Context context){
        context.startActivity(new Intent(context, WebActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
    }

    public void initView(){
        webView = (WebView) findViewById(R.id.mWebView);
        webView.loadUrl("http://www.huya.com/");

        //是用工具类对webView进行配置和管理

        setWebSetting();

    }

    public void setWebSetting(){
        //1.利用webSetting, 进行配置
        settings = webView.getSettings();
        //settings.setJavaScriptEnabled(true); //设置访问页面要与JavaScript交互
        //若html中有js执行动画等操作，会造成资源浪费（cpu,电量）, 可以在onStop和onResume中分别设置true和false
        //设置自适应屏幕
        settings.setUseWideViewPort(true); //图片调整到webView的大小
        settings.setLoadWithOverviewMode(true); //缩放至屏幕的大小

        //缩放操作
        //settings.setSupportZoom(true);  //支持缩放操作，默认true
        //settings.setBuiltInZoomControls(true); //设置内置的缩放控件，若false， webView不可缩放

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webView中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持Js打开新的窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8"); //文字编码方式

        //是否优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        //结合使用（离线加载）
//        if (NetStatusUtil.isConnected(getApplicationContext())) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
//
//        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
//        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
//        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
    }

    /**
     * 处理各种通知
     */
    public void setWebViewClick(){
        //webViewClient = webView.getWebViewClient();
        webViewClient = new WebViewClient(){
            //打开网页时肯定会走这个方法，
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  //设置打开网页时，强制是用webView，不使用系统浏览器
                return true;
            }

            //开始载入页面时
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //页面加载完成时调用
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
            //加载页面资源时调用，且只调用一次,例如图片
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
            //加载页面时的服务器出错时（如404）调用
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                switch (errorCode){
                    case 404:
                        view.loadUrl("");
                        break;
                }
            }

            //处理https请求，webView默认不处理https请求，页面显示空白，需要进行如下设置
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();  //表示等待证书相应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        };

        webView.setWebViewClient(webViewClient);
    }

    /**
     * WebChromeClient类: 辅助WebView处理JavaScript的,进度对话框，网站图标，网站标题等等
     */
    public void setWebChromeClime(){
        WebChromeClient webChromeClient = new WebChromeClient(){
            //获得网页进度进行显示
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
            //获取web页中的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            //支持JavaScript的警告框，一般为Android中的Toast，在文本中加入\n可以换行
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //将html中的弹出框内容获取，替换成安卓Android的弹出框
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("JsAlert")
//                        .setMessage(message)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm();
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();
                return true;
            }

            //替换JavaScript确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                //是用Android应用的提示框替换JavaScript的提示框
                return true;
            }

            //点击确认，返回输入框中的值，点击取消则返回null
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//                final EditText et = new EditText(MainActivity.this);
//                et.setText(defaultValue);
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle(message)
//                        .setView(et)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm(et.getText().toString());
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.cancel();
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();

                return true;
            }
        };
        webView.setWebChromeClient(webChromeClient);
    }




    @Override
    protected void onResume() {
        super.onResume();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        settings.setJavaScriptEnabled(false);
    }
    //点击back键页面回退而不是退出浏览器


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && webView.canGoForward()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        //注意销毁webView
        webView.destroy();

//        //如果webview不在xml中
//        if (webView != null){
//            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8",null);
//            ((ViewGroup)webView.getParent()).removeView(webView);
//            webView.destroy();
//            webView = null;
//        }
        super.onDestroy();

    }

    //清除访问留下的缓存，由于内存缓存针对的是全局的，所有清除缓存夜神针对整个应用程序的
    public void clearWebCache(){
        webView.clearCache(true); //清理缓存这个很有用
        webView.clearHistory(); //清理历史
        webView.clearFormData(); //知识清理自动完成填充的表单数据，不会清理存储到本地的数据

    }


}
