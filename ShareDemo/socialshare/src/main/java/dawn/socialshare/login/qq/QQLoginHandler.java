package dawn.socialshare.login.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.util.HashMap;

import dawn.socialshare.SHARE_MEDIA;
import dawn.socialshare.listener.AuthListener;
import dawn.socialshare.login.AuthResult;
import dawn.socialshare.login.BaseLoginHandler;
import dawn.socialshare.share.qq.QQHelper;
import dawn.socialshare.utils.GsonUtil;


public class QQLoginHandler extends BaseLoginHandler {
    private QQHelper mManager;
    private Context mContext;

    public QQLoginHandler(Context context) {
        mContext = context.getApplicationContext();
        mManager = QQHelper.getInstance(context);
    }

    public void login(Activity activity, AuthListener xtAuthListener) {
        setCallBack(xtAuthListener);
        mManager.getTencent().login(activity, "all", mAuthListener);
    }

    /**
     * logout
     *
     * @param activity activity
     */
    public void logout(Activity activity) {
        mManager.getTencent().logout(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mManager.getTencent().onActivityResultData(requestCode, resultCode, data,mAuthListener);
    }

    public IUiListener mAuthListener = new IUiListener() {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                mManager.getTencent().releaseResource();
                return;
            }

            QQLoginResult result = GsonUtil.fromJson(response + "", QQLoginResult.class);
            AuthResult auth = new AuthResult();
            auth.from = AuthResult.TYPE_QQ;
            auth.id = result.openid;
            auth.accessToken = result.access_token;
            auth.expiresIn = result.expires_in;

            mManager.getTencent().setAccessToken(auth.accessToken, auth.expiresIn + "");
            mManager.getTencent().setOpenId(auth.id);


            if(xtAuthListener!=null){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put(com.tencent.connect.common.Constants.PARAM_OPEN_ID,result.openid);
                map.put(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN,result.access_token);
                xtAuthListener.onComplete(SHARE_MEDIA.QQ, AuthListener.ACTION_AUTHORIZE,map);
            }
        }

        @Override
        public void onError(UiError uiError) {
            if(xtAuthListener != null){
                xtAuthListener.onError(SHARE_MEDIA.QQ, AuthListener.ACTION_AUTHORIZE,new Throwable(uiError.errorMessage));
            }
            mManager.getTencent().releaseResource();
        }

        @Override
        public void onCancel() {
            if(xtAuthListener != null){
                xtAuthListener.onCancel(SHARE_MEDIA.QQ, AuthListener.ACTION_AUTHORIZE);
            }
            mManager.getTencent().releaseResource();
        }
    };


}
