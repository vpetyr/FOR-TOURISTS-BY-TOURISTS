package com.example.fortouristsbytourists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * showing the current attraction being selected booking web page
 */
public class Activity_10_www extends AppCompatActivity {
    private WebView webView;
    String bookingURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_www);
        webView = findViewById(R.id.www);
        webView.setWebViewClient(new WebViewClient());

        bookingURL = getIntent().getStringExtra("bookingURL");
        webView.loadUrl(bookingURL);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //javaScript enabled
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){ webView.goBack();  }
        else {   super.onBackPressed();}
    }
}
