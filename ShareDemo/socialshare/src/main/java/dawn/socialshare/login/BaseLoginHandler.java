package dawn.socialshare.login;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dawn.socialshare.listener.AuthListener;


public abstract class BaseLoginHandler {
    protected static final String TAG = "LoginHandler";

    protected boolean mRequestInfoEnable = true;
    protected boolean mLogEnable = true;

    protected AuthListener xtAuthListener;

    public BaseLoginHandler() {

    }

    /**
     * set request user info able.
     *
     * @param requestEnable true, it's will be request user information after auth success.
     */
    public void setRequestUserInfo(boolean requestEnable) {
        mRequestInfoEnable = requestEnable;
    }

    /**
     * set can be log able.
     *
     * @param enable true, it's print log. otherwise did't.
     */
    public void setLogEnable(boolean enable) {
        mRequestInfoEnable = mLogEnable;
    }

    /**
     * set login callback
     *
     * @param listener the callback
     */
    protected void setCallBack(AuthListener listener) {
        xtAuthListener = listener;
    }

    /**
     * callback
     *
     * @param statusCode status code
     * @param data       callback data
     */
    protected synchronized void callBack(int statusCode, Object data) {
        if (null != xtAuthListener) {
//            mLoginListener.onComplete(statusCode, data);
        }
    }

    /**
     * format date with yyyy-MM-dd hh:MM:ss
     *
     * @param time the date time
     * @return format string
     */
    protected String formatDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd hh:MM:ss", Locale.CHINA).format(new Date(time));
    }

    /**
     * print log if {@link #mLogEnable} is true.
     *
     * @param message the log message
     */
    protected void log(String message) {
        if (mLogEnable) {
            Log.i(TAG, message);
        }
    }
}
