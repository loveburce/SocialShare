package dawn.socialshare.share.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;

import dawn.socialshare.R;
import dawn.socialshare.SHARE_MEDIA;
import dawn.socialshare.ShareAction;
import dawn.socialshare.listener.ShareListener;
import dawn.socialshare.share.ShareContent;
import dawn.socialshare.share.ShareType;
import dawn.socialshare.utils.SdkConstant;


public class QQHelper {
    public static final String TAG = "QQHelper";
    private int shareType = QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN;
    private int mExtarFlag = 0x00;
    //有弹窗  ，显示分享到QQ空间
//    mExtarFlag = QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN;
    //无弹窗	，无显示分享到QQ空间
    //mExtarFlag |= QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE;
    String appName = "";

    private static final int TYPE_QQ = 0;
    private static final int TYPE_QZONE = 1;
    private static QQHelper sInstance;
    private static Tencent mTencent;
    private ShareAction shareAction;
    private ShareListener xtShareListener;

    private QQHelper(Context context) {
        mTencent = Tencent.createInstance(SdkConstant.QQ_APP_ID, context.getApplicationContext());
    }

    public synchronized static QQHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QQHelper(context);
        }
        return sInstance;
    }

    public QQHelper setAction(ShareAction shareAction){
        this.shareAction = shareAction;
        return this;
    }

    public QQHelper setShareListener(ShareListener xtShareListener){
        this.xtShareListener = xtShareListener;
        return this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResultData(requestCode, resultCode, data,baseUiListener);
    }

    /**
     * 获取QQ API
     *
     * @return Tencent api object
     */
    public Tencent getTencent() {
        return mTencent;
    }

    /**
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void wbShare(Activity activity, int flag){
        if(null==shareAction){
            return;
        }
        ShareContent shareContent = shareAction.getShareContent();
        Bundle params = new Bundle();
        // 1. 初始化从分享内容
        if(shareContent!=null && shareContent.shareType == ShareType.WEBPAGE){

            if(flag ==TYPE_QQ) {
                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.mPath);
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.mTargetUrl);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.mTitle);
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.mText);

                appName = activity.getString(R.string.appName);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                baseUiListener = new BaseUiListener(flag);
                mTencent.shareToQQ(activity, params, baseUiListener);

            }else if(flag ==TYPE_QZONE){
                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.mPath);
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.mTargetUrl);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.mTitle);
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.mText);

                appName = activity.getString(R.string.appName);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);


                baseUiListener = new BaseUiListener(flag);
                mTencent.shareToQQ(activity, params, baseUiListener);
            }
        }else if(shareContent!=null && shareContent.shareType == ShareType.IMAGE){
            if(flag ==TYPE_QQ) {
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                File file = new File(shareContent.mPath);
                if(!file.exists()){
                    Log.e("QQShare","the path is null");
                    return;
                }
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.mPath);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "LPS CRM");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                baseUiListener = new BaseUiListener(flag);
                mTencent.shareToQQ(activity, params, baseUiListener);

            }else if(flag ==TYPE_QZONE){
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.mTargetUrl);
                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.mPath);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.mTitle);
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.mText);

                appName = activity.getString(R.string.appName);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);

                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);


                baseUiListener = new BaseUiListener(flag);
                mTencent.shareToQQ(activity, params, baseUiListener);
            }
        }else if(shareContent!=null && shareContent.shareType == ShareType.MUSIC){

        }else if(shareContent!=null && shareContent.shareType == ShareType.TEXT){

        }else if(shareContent!=null && shareContent.shareType == ShareType.VEDIO){

        }


    }

    private BaseUiListener baseUiListener;

    private class BaseUiListener implements IUiListener {
        private final int mShareType;

        public BaseUiListener(int shareType) {
            mShareType = shareType;
        }

        @Override
        public void onCancel() {
            switch (mShareType) {
                case TYPE_QQ:
                    if(null != xtShareListener){
                        xtShareListener.onCancel(SHARE_MEDIA.QQ);
                    }
                    break;
                case TYPE_QZONE:
                    if(null != xtShareListener){
                        xtShareListener.onCancel(SHARE_MEDIA.QZONE);
                    }
                    break;
            }
        }

        @Override
        public void onComplete(Object response) {
            switch (mShareType) {
                case TYPE_QQ:
                    if(null != xtShareListener){
                        xtShareListener.onResult(SHARE_MEDIA.QQ);
                    }
                    break;
                case TYPE_QZONE:
                    if(null != xtShareListener){
                        xtShareListener.onResult(SHARE_MEDIA.QZONE);
                    }
                    break;
            }
        }

        @Override
        public void onError(UiError e) {
            switch (mShareType) {
                case TYPE_QQ:
                    if(null != xtShareListener){
                        xtShareListener.onError(SHARE_MEDIA.QQ,new Error(e.errorMessage));
                    }
                    break;
                case TYPE_QZONE:
                    if(null != xtShareListener){
                        xtShareListener.onError(SHARE_MEDIA.QZONE,new Error(e.errorMessage));
                    }
                    break;
            }
        }
    }


}
