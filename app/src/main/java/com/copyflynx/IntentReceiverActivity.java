package com.copyflynx;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.copyflynx.IconHead.IconHeadService;

import java.net.URL;

/**
 * Created by harneev on 5/10/2015.
 */
public class IntentReceiverActivity extends AppCompatActivity {

    private WebView mWebView;
    private URL mReceivedURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.intent_receiver_activity);

        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient()); // load URL in local webView
*/
        Intent intent = getIntent();

        Uri data = intent.getData();

        try {
            mReceivedURL = new URL(data.getScheme(), data.getHost(),
                    data.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Toast the URL
        Toast.makeText(IntentReceiverActivity.this, mReceivedURL.toString(), Toast.LENGTH_LONG).show();

        Intent sendIntent = new Intent(IntentReceiverActivity.this, IconHeadService.class);
        sendIntent.putExtra("received_url", mReceivedURL.toString());

        startService(sendIntent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.intent_receiver_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_in_browser) {
            Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(mReceivedURL.toString()));

            startActivity(openBrowser);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
