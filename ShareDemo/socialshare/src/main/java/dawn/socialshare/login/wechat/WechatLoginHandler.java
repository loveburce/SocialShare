package dawn.socialshare.login.wechat;

import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendAuth;

import java.util.HashMap;

import dawn.socialshare.listener.AuthListener;
import dawn.socialshare.login.BaseLoginHandler;
import dawn.socialshare.share.wechat.WechatHelper;
import dawn.socialshare.utils.GsonUtil;
import dawn.socialshare.utils.SdkConstant;
import dawn.socialshare.utils.ThreadManager;

public class WechatLoginHandler extends BaseLoginHandler {
    /** Get token url. */
    private static final String URL_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /** Get user info url. */
    private static final String URL_WECHAT_USER = "https://api.weixin.qq.com/sns/userinfo";

    public WechatHelper mManager;
    Context mContext;

    public WechatLoginHandler(Context context) {
        mManager = WechatHelper.getInstance(context);
        mContext = context;
    }

    /**
     * login
     *
     * @param xtAuthListener callback listener
     */
    public void login(AuthListener xtAuthListener) {
        setCallBack(xtAuthListener);
        SendAuth.Req request = new SendAuth.Req();
        request.scope = SdkConstant.WEIXIN_SCOPE;
        request.state = SdkConstant.WEIXIN_STATE;
        mManager.getAPI().sendReq(request);
    }


    public void requestToken(final String code){
        ThreadManager.getSubThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                requestTokenThread(code);
            }
        });
    }

    /**
     * request token from wechat server.
     *
     * @param code the auth code
     */
    private void requestTokenThread(String code) {
//        final ParameterList paramsList = HttpNetUtils.getHttpClient().newParams();
//        paramsList.add(new ParameterList.StringParameter("appid", SdkConstant.WEIXIN_APP_ID));
//        paramsList.add(new ParameterList.StringParameter("secret", SdkConstant.WEIXIN_APP_SECRET));
//        paramsList.add(new ParameterList.StringParameter("code", code));
//        paramsList.add(new ParameterList.StringParameter("grant_type", "authorization_code"));
//
//        HttpNetUtils.getHttpClient().get(URL_TOKEN, paramsList, new NetReqCallBack() {
//            @Override
//            public void getSuccData(int statusCode, String strJson, String strUrl) {
//                final WechatLoginResult result = GsonUtil.fromJson(strJson, WechatLoginResult.class);
//
//                ThreadManager.getMainHandler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(xtAuthListener!=null){
//                            HashMap<String,String> map = new HashMap<String,String>();
//                            map.put(com.tencent.connect.common.Constants.PARAM_OPEN_ID,result.openid);
//                            map.put(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN,result.accessToken);
//                            xtAuthListener.onComplete(SHARE_MEDIA.WEIXIN, AuthListener.ACTION_AUTHORIZE,map);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void getErrData(int statusCode, final String strJson, String strUrl) {
//                super.getErrData(statusCode, strJson, strUrl);
//
//                ThreadManager.getMainHandler().post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if(xtAuthListener != null){
//                            xtAuthListener.onError(SHARE_MEDIA.QQ, AuthListener.ACTION_AUTHORIZE,new Throwable(strJson));
//                        }
//                    }
//                });
//            }
//        });
    }
}
