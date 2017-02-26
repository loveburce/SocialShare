package dawn.socialshare.share.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

import dawn.socialshare.ShareAction;
import dawn.socialshare.share.ShareContent;
import dawn.socialshare.share.ShareType;
import dawn.socialshare.utils.ImageUtil;
import dawn.socialshare.utils.SdkConstant;
import dawn.socialshare.utils.Utils;

public class WechatHelper {
    public static final int TYPE_WECHAT_FRIEND = 0;
    public static final int TYPE_WECHAT_TIMELINE = 1;

    /** Min supported version. */
    private static final int MIN_SUPPORTED_VERSION = 0x21020001;

    private static final int MAX_IMAGE_LENGTH = 32 * 1024;
    private static final int DEFAULT_MAX_SIZE = 150;
    private static final int THUMB_SIZE = 60;

    private static WechatHelper sInstance;
    public IWXAPI mApi;
    private ShareAction shareAction;

    private WechatHelper(Context context) {
        mApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), SdkConstant.WEIXIN_APP_ID);
        mApi.registerApp(SdkConstant.WEIXIN_APP_ID);
    }

    public synchronized static WechatHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WechatHelper(context);
        }
        return sInstance;
    }

    public WechatHelper setAction(ShareAction shareAction){
        this.shareAction = shareAction;
        return this;
    }

    /**
     * 微信是否已安装
     *
     * @return is installed
     */
    public boolean isInstalled() {
        return mApi.isWXAppInstalled();
    }

    /**
     * 是否支持发送朋友圈
     *
     * @return is supported
     */
    public boolean isSupported() {
        return mApi.isWXAppSupportAPI();
    }

    /**
     * 是否支持发送朋友圈
     *
     * @return is supported
     */
    public boolean isSupportedTimeline() {
        return mApi.getWXAppSupportAPI() >= MIN_SUPPORTED_VERSION;
    }


    /**
     * 获取微信API
     *
     * @return IWXAPI
     */
    public IWXAPI getAPI() {
        return mApi;
    }

    /**
     * 根据微信的要求缩放缩略图
     *
     * @param bitmap 图片
     * @return 图片
     */
    public Bitmap zoomOut(Bitmap bitmap) {
        Bitmap dstBitmap = null;
        if (null != bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            if (width <= 0 || height <= 0) return null;

            int w, h;
            float scale = height * 1.0f / width;
            if (width > height) {
                w = DEFAULT_MAX_SIZE;
                h = (int) (w * scale);
            } else {
                h = DEFAULT_MAX_SIZE;
                w = (int) (h / scale);
            }

            dstBitmap = ImageUtil.zoom(bitmap, w, h);
            byte[] data = ImageUtil.bitmapToBytes(dstBitmap, false);

            while (data.length > MAX_IMAGE_LENGTH) {
                dstBitmap.recycle();

                w -= 10;
                h = (int) (w * scale);

                dstBitmap = ImageUtil.zoom(bitmap, w, h);
                data = ImageUtil.bitmapToBytes(dstBitmap, false);
            }
        }

        return dstBitmap;
    }

    /**
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void wxShare(int flag){
        if(null==shareAction){
            return;
        }
        WXMediaMessage msg = new WXMediaMessage();
        ShareContent shareContent = shareAction.getShareContent();
        if(shareContent!=null && shareContent.shareType == ShareType.WEBPAGE){
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = shareAction.getShareContent().mTargetUrl;
            msg.mediaObject = webpage;
            addShareIcon(msg, shareContent);
        }else if(shareContent!=null && shareContent.shareType == ShareType.IMAGE){
            WXImageObject imgObj = new WXImageObject();
            msg.mediaObject = imgObj;
            File file = new File(shareContent.mPath);
            if(!file.exists()){
                Log.e("WXShare","the path is null");
                return;
            }
            imgObj.setImagePath(shareContent.mPath);
            addShareIcon(msg, shareContent);
        }else if(shareContent!=null && shareContent.shareType == ShareType.MUSIC){
            WXMusicObject musicObj = new WXMusicObject();
            musicObj.musicUrl = shareContent.mTargetUrl;
            addShareIcon(msg, shareContent);
            msg.mediaObject = musicObj;
        }else if(shareContent!=null && shareContent.shareType == ShareType.TEXT){
            WXTextObject textObj = new WXTextObject();
            textObj.text = shareContent.mText;
        }else if(shareContent!=null && shareContent.shareType == ShareType.VEDIO){
            WXVideoObject videoObj = new WXVideoObject();
            addShareIcon(msg, shareContent);
            videoObj.videoUrl = shareContent.mTargetUrl;

        }
        msg.title = shareAction.getShareContent().mTitle;
        msg.description = shareContent.mText;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        getAPI().sendReq(req);

    }

    private void addShareIcon(WXMediaMessage msg, ShareContent shareContent) {
        if(!TextUtils.isEmpty(shareContent.mPath)){
            Bitmap bmp = Utils.readBitmap(shareContent.mPath);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        }
    }


}
