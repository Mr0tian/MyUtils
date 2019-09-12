package com.example.daotest.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/9/2
 */
public class WebTestActivity extends AppCompatActivity {

    @InjectView(R.id.web_test)
    WebView myWebView;

    private static final String LOGTAG = "MainActivity";



    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);

        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new JsInteration(),"control");
        //设置不加载缓存,项目调试阶段开启,正式上线记得关闭
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        myWebView.setWebChromeClient(new WebChromeClient(){});
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                testMethod(myWebView);
            }
        });


        myWebView.loadUrl("http://tian.cn1.utools.club/js_java_interaction.html");

    }

    private void testMethod(WebView webView) {

        String call = "javascript:sayHello()";
       /* call = "javascript:alertMessage(\""+"count"+ "\")";

*/
       //  call = "javascript:sumToJava(1,2)";
        call = "javascript:toastMessage(\"" + "content" + "\")";

        webView.loadUrl(call);

    }


    public class JsInteration {

        @JavascriptInterface
        public void toastMessage(String message){
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void onSumResult(int result){
            Log.i("TAG", "onSumResult result=" + result);
        }

    }

}
