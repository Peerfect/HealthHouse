package com.jkb.enjoyor.healthhouse.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jkb.enjoyor.healthhouse.R;
import com.jkb.enjoyor.healthhouse.utils.ScreenUtil;
import com.jkb.enjoyor.healthhouse.utils.ToastUtil;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class BaseActivity extends AppCompatActivity {
    //进度框
    protected Dialog mDialog;
    // 屏幕方向
    private int mOrientation;
    private static long trigleCancel;

    private Dialog dialog = null;

    /**
     * 隐藏软键盘的方法
     */
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();
        if (null != currentFocus && imm.isActive()) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘的方法
     */
    public void showKeyBoard(View focus) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();

        if (null != currentFocus) {
            imm.showSoftInput(focus, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * /按后退键时
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * 按下按键时触发事件的方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按了后退键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 退出Activity时动画
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 网络错误触发的方法
     *
     * @param content
     */
    public void showErrorDialog(String content) {
        if (TextUtils.isEmpty(content)) {
            new AlertDialog.Builder(this).setTitle(R.string.dialog_error)
                    .setMessage(R.string.error_network)
                    .setPositiveButton(R.string.dialog_close, null).show();
        } else {
            new AlertDialog.Builder(this).setTitle(R.string.dialog_error)
                    .setMessage(content)
                    .setPositiveButton(R.string.dialog_close, null).show();
        }
    }

    /**
     * 网络加载数据进度框
     *
     * @return
     */
    protected ProgressDialog progress() {
        return progress("", "", null, false);
    }


    protected ProgressDialog progress(DialogInterface.OnCancelListener listener) {
        return progress("", "", listener, true);
    }

    /**
     * 显示进度框的方法
     *
     * @param
     * @return
     */
    protected ProgressDialog progress(String pTitle, String pMessage,
                                      DialogInterface.OnCancelListener pCancelClickListener, boolean outsideCancel) {
        if (isFinishing())
            return null;
        ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(outsideCancel);
        mProgressDialog.setCancelable(outsideCancel);
        mProgressDialog.setContentView(View.inflate(this,
                R.layout.dialog_process, null));
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mProgressDialog.setOnCancelListener(pCancelClickListener);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog,
                                 int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                } else {
                    return false;
                }
            }
        });
        mDialog = mProgressDialog;
        return mProgressDialog;
    }

    /**
     * 取消进度框的方法
     */
    protected void cancel() {
        if (mDialog != null)
            mDialog.cancel();
    }

    /**
     * 双击退出程序的方法
     */
    protected void toastExtrance() {
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - trigleCancel > 2000) {
            trigleCancel = uptimeMillis;
            ToastUtil.showToast(getString(R.string.note_exit));
        } else {
            onExit();
        }
    }

    private void onExit() {
        ToastUtil.cancel();
        System.exit(0);
    }

    /**
     * 获取屏幕的方向
     */
    public int getOrientation() {
        return mOrientation;
    }

    /**
     * 沉浸式开发方法
     */
    protected void setImmerseLayout(View view) {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
//            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int statusBarHeight = ScreenUtil.getStatusBarHeight(this.getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    protected void setImmerseLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 设置导航栏文字
     *
     * @param view
     * @param id
     */
    public void setTitleBar(TextView view, int id) {
        view.setText(id);
    }
}
