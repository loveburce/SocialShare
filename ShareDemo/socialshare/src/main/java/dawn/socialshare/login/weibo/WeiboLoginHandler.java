package dawn.socialshare.login.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.util.HashMap;

import dawn.socialshare.SHARE_MEDIA;
import dawn.socialshare.listener.AuthListener;
import dawn.socialshare.login.BaseLoginHandler;
import dawn.socialshare.utils.SdkConstant;

public class WeiboLoginHandler extends BaseLoginHandler {
    /** weibo api. */
    private static final String URL_GET_USER_INFO = "https://api.weibo.com/2/users/show.json";

    private SsoHandler mSsoHandler;

    public WeiboLoginHandler() {
    }

    /**
     * login
     * @param activity the activity
     * @param xtAuthListener callback listener
     */
    public void login(Activity activity, AuthListener xtAuthListener) {
        setCallBack(xtAuthListener);
        AuthInfo weiboAuth = new AuthInfo(activity, SdkConstant.SINA_WEIBO_APP_KEY,SdkConstant.SINA_WEIBO_REDIRECT_URL,"");

        mSsoHandler = new SsoHandler(activity, weiboAuth);
        mSsoHandler.authorize(mAuthListener);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mSsoHandler) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    private WeiboAuthListener mAuthListener = new WeiboAuthListener() {

        @Override
        public void onComplete(Bundle values) {
            // parse authorize result
            WeiboLoginResult result = new WeiboLoginResult();
            result.access_token = values.getString("access_token");
            result.expires_in = values.getString("expires_in");
            result.remind_in = values.getString("remind_in");
            result.uid = values.getString("uid");
            result.userName = values.getString("userName");
            result.refresh_token = values.getString("refresh_token");

            if(xtAuthListener!=null){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put(com.tencent.connect.common.Constants.PARAM_OPEN_ID,result.uid);
                map.put(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN,result.access_token);
                xtAuthListener.onComplete(SHARE_MEDIA.SINA, AuthListener.ACTION_AUTHORIZE,map);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            if(xtAuthListener != null){
                xtAuthListener.onError(SHARE_MEDIA.SINA, AuthListener.ACTION_AUTHORIZE,new Throwable(e));
            }
        }

        @Override
        public void onCancel() {
            if(xtAuthListener != null){
                xtAuthListener.onCancel(SHARE_MEDIA.SINA, AuthListener.ACTION_AUTHORIZE);
            }
        }
    };
}
