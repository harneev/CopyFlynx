package com.copyflynx.IconHead;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.copyflynx.helper.CustomWebViewClient;
import com.copyflynx.R;

/**
 * Created by harneev on 5/10/2015.
 */
public class IconHeadService extends Service {

    private int mScreenHeight = 0, mScreenWidth = 0;

    private WindowManager mWM;
    private ImageView mIconHead;
    private RelativeLayout mMainLayout;
    private WebView mWebView;

    private WindowManager.LayoutParams mIconHeadParams;
    private WindowManager.LayoutParams mMainLayoutParams;

    private LayoutInflater mInflater;

    private String mReceivedURL;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mReceivedURL = intent.getStringExtra("received_url");

        refreshViews();

        loadURL();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Loads window manager object
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Load inflater
        mInflater = (LayoutInflater) getApplicationContext().getSystemService
                (getApplicationContext().LAYOUT_INFLATER_SERVICE);

        // update screen dimensions
        getScreenHeight();

        // draws initial views
        //refreshViews();
    }

    /**
     * Draw initial view and handle events
     */
    private void drawIconHead() {
        mIconHead = new ImageView(this);
        mIconHead.setImageResource(R.drawable.icon_head);
        mIconHead.setOnTouchListener(mTouchListener);
        //mIconHead.setOnClickListener(mClickListener);

        mIconHeadParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mIconHeadParams.gravity = Gravity.TOP | Gravity.LEFT;
        mIconHeadParams.x = 0;
        mIconHeadParams.y = 100;

                // icon head added to window manager
        mWM.addView(mIconHead, mIconHeadParams);
    }

    private void drawMainView() {
        mMainLayout = new RelativeLayout(this){
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode()==KeyEvent.KEYCODE_BACK) {
                    removeWindowViews();
                    return true;
                }
                return super.dispatchKeyEvent(event);
            }
        };

        View child =  mInflater.inflate(R.layout.intent_receiver_activity, null);
        mMainLayout.addView(child);

        mWebView = (WebView) mMainLayout.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new CustomWebViewClient(this)); // load URL in local webView


        mMainLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                mScreenHeight - 300,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.UNKNOWN);
        mMainLayoutParams.gravity = Gravity.BOTTOM;

    }

    private void refreshViews() {
        if (mMainLayout == null)
            drawMainView();

        if (mIconHead == null)
            drawIconHead();
    }

    /**
     * actions performed when main view is inflated
     */
    private void inflateMainView() {
        mIconHeadParams.gravity = Gravity.TOP | Gravity.RIGHT;
        mIconHeadParams.x = 20 - mScreenWidth ;
        //mIconHeadParams.y = mScreenHeight - 300;

        mWM.updateViewLayout(mIconHead, mIconHeadParams);

        // main layout window added to window manager
        mWM.addView(mMainLayout, mMainLayoutParams);
    }

    private void loadURL() {
        mWebView.clearHistory();

        if (!mReceivedURL.toString().equals("")) {
            mWebView.loadUrl(mReceivedURL);
        }
    }

    /**
     * Remove all the views from Window
     */
    private void removeWindowViews() {
        if (mMainLayout != null)
            mWM.removeView(mMainLayout);
    }

    // Head view touch listener
    View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = mIconHeadParams.x;
                    initialY = mIconHeadParams.y;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;
                case MotionEvent.ACTION_UP:
                    inflateMainView();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    /*mIconHeadParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                    mIconHeadParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                    mWM.updateViewLayout(mIconHead, mIconHeadParams);*/
                    return true;
            }
            return false;
        }
    };

    /**
     * updates mScreenHeight & mScreenWidth with screen dimen values.
     */
    private void getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        mWM.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScreenWidth = dm.widthPixels;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        removeWindowViews();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
