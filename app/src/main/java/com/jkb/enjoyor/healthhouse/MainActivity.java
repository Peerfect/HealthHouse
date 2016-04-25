package com.jkb.enjoyor.healthhouse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.jkb.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.jkb.enjoyor.healthhouse.ui.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
//    private String url = "http://115.28.37.145:9008/healthstationserver/health/getrecordnewinfo.action?origin=AndroidApp&userId=35";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImmerseLayout(findViewById(R.id.common_back));
    }
}
