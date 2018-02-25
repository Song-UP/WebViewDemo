package wusong.com.webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import wusong.com.mylibrary.VideoWebActivity;
import wusong.com.mylibrary.VideoWebActivity02;
import wusong.com.mylibrary.WebActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoWebActivity02.start(this);
    }

}
