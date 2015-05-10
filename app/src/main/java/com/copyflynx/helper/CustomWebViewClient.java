package com.copyflynx.helper;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.copyflynx.database.DataList;

/**
 * Created by harneev on 5/10/2015.
 */
public class CustomWebViewClient extends WebViewClient {

    private Context mContext;

    public CustomWebViewClient(Context context) {
        mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        if (!view.getTitle().toString().contains("://")) {
            DataList.insertData(url, view.getTitle().toString());
            Toast.makeText(mContext, "inserted in db, url: " + url, Toast.LENGTH_LONG).show();
        }

    }
}
