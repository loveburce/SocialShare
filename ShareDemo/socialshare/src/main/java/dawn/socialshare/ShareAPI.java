package dawn.socialshare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import dawn.socialshare.listener.AuthListener;
import dawn.socialshare.listener.ShareListener;
import dawn.socialshare.login.qq.QQLoginHandler;
import dawn.socialshare.login.wechat.WechatLoginHandler;
import dawn.socialshare.login.weibo.WeiboLoginHandler;
import dawn.socialshare.share.qq.QQHelper;
import dawn.socialshare.share.wechat.WechatHelper;
import dawn.socialshare.share.weibo.WeiboHelper;


public class ShareAPI {
	private static ShareAPI xtShareAPI = null;

	public WeiboLoginHandler weiboLoginHandler;
	public QQLoginHandler qqLoginHandler;
	public WechatLoginHandler wechatLoginHandler;

	SHARE_MEDIA currentPlatform;
	private boolean isWeChatLogin = false;

	private AuthListener listener;
	private ShareListener shareListener;
	Context mContext;//可能会长期引用activity 造成activity不能释放 需要改进

	private ShareAPI(Context context){
		mContext = context;
	}

	public static ShareAPI getInstance(Context context){
		if(null ==xtShareAPI){
			xtShareAPI = new ShareAPI(context);
		}
		return  xtShareAPI;
	}

	private void qqLogin(final Activity activity, final AuthListener listener) {
		qqLoginHandler = new QQLoginHandler(activity);
		qqLoginHandler.login(activity,listener);
	}

	private void weixinLogin(final Activity activity, final AuthListener listener){
		wechatLoginHandler = new WechatLoginHandler(activity);
		wechatLoginHandler.login(listener);
		setWeChatLogin(true);
	}

	private void weiboLogin(final Activity activity, final AuthListener listener){
		weiboLoginHandler = new WeiboLoginHandler();
		weiboLoginHandler.login(activity,listener);
	}
	public void doShare(Activity activity, ShareAction shareAction, ShareListener shareListener){
		setShareListener(shareAction.shareListener);
		this.currentPlatform = shareAction.getPlatform();//记录当前分享平台
		if(shareAction!=null && shareAction.getPlatform() == SHARE_MEDIA.WEIXIN){
			//微信分享
		//	WXShare.getInstance(activity).setAction(shareAction).wxShare(0);
			WechatHelper.getInstance(activity).setAction(shareAction).wxShare(0);
			setWeChatLogin(false);
		}else if(shareAction!=null && shareAction.getPlatform() == SHARE_MEDIA.WEIXIN_CIRCLE){
			//朋友圈分享
			WechatHelper.getInstance(activity).setAction(shareAction).wxShare(1);
		}else if(shareAction!=null && shareAction.getPlatform() == SHARE_MEDIA.QZONE){
			//QQ空间分享
			QQHelper.getInstance(activity).setAction(shareAction).setShareListener(shareAction.shareListener).wbShare(activity,1);
		}else if(shareAction!=null && shareAction.getPlatform() == SHARE_MEDIA.SINA){
			//新浪微博分享
			WeiboHelper.getInstance(activity).setAction(shareAction).wbShare(activity);
		}else if(shareAction!=null && shareAction.getPlatform() == SHARE_MEDIA.QQ){
			//QQ分享
			QQHelper.getInstance(activity).setAction(shareAction).setShareListener(shareAction.shareListener).wbShare(activity,0);
		}else if(shareAction!=null && shareAction.getPlatform() == SHARE_MEDIA.SMS){
			//短信分享
			sendSMS(activity,shareAction,shareListener);
		}
	}
	private static final int SEND_SMS_REQUEST_CODE = 102;

	/**
	 * 分享到短信，发送信息的内容待修改
	 * @param activity
	 * @param shareAction
	 * @param shareListener
     */
	private void sendSMS(Activity activity, ShareAction shareAction, ShareListener shareListener) {
			String smsBody = shareAction.getShareContent().mText ;
			Uri smsToUri = Uri.parse( "smsto:" );
			Intent sendIntent =  new Intent(Intent.ACTION_VIEW, smsToUri);
			sendIntent.putExtra( "sms_body", smsBody);
			sendIntent.setType( "vnd.android-dir/mms-sms" );
			activity.startActivityForResult(sendIntent, SEND_SMS_REQUEST_CODE );
	}

	public void doOauthVerify(Activity activity, SHARE_MEDIA platform, AuthListener listener){
		this.currentPlatform = platform;
		if(platform == SHARE_MEDIA.QQ){
			qqLogin(activity,listener);
		}else if(platform == SHARE_MEDIA.WEIXIN){
			weixinLogin(activity,listener);
		}else if(platform == SHARE_MEDIA.SINA){
			weiboLogin(activity,listener);
		}
	}


	public void deleteOauth(Activity activity, SHARE_MEDIA platform, AuthListener listener){
		if(platform == SHARE_MEDIA.QQ && null != qqLoginHandler){
			qqLoginHandler.logout(activity);
		}
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(currentPlatform == SHARE_MEDIA.QQ || currentPlatform == SHARE_MEDIA.QZONE){
			if(null != qqLoginHandler){
				qqLoginHandler.onActivityResult(requestCode, resultCode, data);
			}else{
				QQHelper.getInstance(mContext).onActivityResult(requestCode, resultCode, data);
			}
		}else if(currentPlatform == SHARE_MEDIA.WEIXIN){

		}else if(currentPlatform == SHARE_MEDIA.SINA) {
			if(null != weiboLoginHandler){
				weiboLoginHandler.onActivityResult(requestCode, resultCode, data);
			}
		}else if(currentPlatform == SHARE_MEDIA.SMS && requestCode == SEND_SMS_REQUEST_CODE ){
			//短信发送成功
		}
	}



	public boolean isWeChatLogin() {
		return isWeChatLogin;
	}

	public void setWeChatLogin(boolean wechatShare) {
		isWeChatLogin = wechatShare;
	}

	public ShareListener getShareListener() {
		return shareListener;
	}

	public void setShareListener(ShareListener shareListener) {
		this.shareListener = shareListener;
	}
}

