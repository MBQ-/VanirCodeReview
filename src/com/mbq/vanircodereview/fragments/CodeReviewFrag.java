package com.mbq.vanircodereview.fragments;

import com.mbq.vanircodereview.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;

@SuppressLint("SetJavaScriptEnabled")
public class CodeReviewFrag extends Fragment {
        Context context;
        WebView wv;

          @SuppressWarnings("deprecation")
        @Override
          public View onCreateView(LayoutInflater inflater, ViewGroup container,
              Bundle savedInstanceState) {
              View view = inflater.inflate(R.layout.code_review, container, false);
        
                WebView wv = (WebView) view.findViewById(R.id.wv);

                wv.getSettings().setUserAgentString("Mozilla/5.0 " +
                                "(Windows NT 6.2; " +
                                "WOW64) AppleWebKit/537.31 " +
                                "(KHTML, like Gecko) Chrome/20 " +
                                "Safari/537.31");

                wv.loadUrl("http://vaniraosp.goo.im/#/q/status:open,n,z");

                wv.getSettings().setJavaScriptEnabled(true); 
                
                wv.clearCache(true);
                
                wv.getSettings().setLoadWithOverviewMode(true);
                
                wv.getSettings().setUseWideViewPort(true);

            WebSettings webSettings = wv.getSettings();
            
            wv.getSettings().setPluginState(PluginState.ON);
            
            webSettings.setDomStorageEnabled(true);
            
            wv.setDownloadListener(new DownloadListener() {

              @Override
              public void onDownloadStart(String url, String userAgent,
                      String contentDisposition, String mimetype,
                      long contentLength) {

                  Uri uri = Uri.parse(url);
                  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                  startActivity(intent);

              }
          });

        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
            }
        });

            wv.setWebViewClient(new WebViewClient() {

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 {
                         view.loadUrl(url);
                    return true;
                }
            }});
                return wv;
        }
}