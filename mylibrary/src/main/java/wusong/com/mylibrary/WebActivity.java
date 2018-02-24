package wusong.com.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by SongUp on 2018/2/24.
 */

public class WebActivity extends AppCompatActivity {
    WebView webView;

    public static void start(Context context){
        context.startActivity(new Intent(context, WebActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

    }

    public void initView(){
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://www.huya.com/");



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
        super.onDestroy();
        webView.destroy();
    }

    //清除访问留下的缓存，由于内存缓存针对的是全局的，所有清除缓存夜神针对整个应用程序的
    public void clearWebCache(){
        webView.clearCache(true); //清理缓存这个很有用
        webView.clearHistory(); //清理历史
        webView.clearFormData(); //知识清理自动完成填充的表单数据，不会清理存储到本地的数据

    }


}
