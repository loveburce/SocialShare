package dawn.socialshare.share.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import dawn.socialshare.ShareAction;
import dawn.socialshare.share.ShareContent;
import dawn.socialshare.share.ShareType;
import dawn.socialshare.utils.SdkConstant;

public class WeiboHelper {
    private static WeiboHelper sInstance;
    private final IWeiboShareAPI mWeiboShareAPI;
    private ShareAction shareAction;


    private WeiboHelper(Context context) {
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context.getApplicationContext(),
                SdkConstant.SINA_WEIBO_APP_KEY);
        mWeiboShareAPI.registerApp();
    }

    public synchronized static WeiboHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WeiboHelper(context);
        }
        return sInstance;
    }

    public WeiboHelper setAction(ShareAction shareAction){
        this.shareAction = shareAction;
        return this;
    }

    public IWeiboShareAPI getWeiboShareAPI() {
        return mWeiboShareAPI;
    }

    /**
     * 是否安装微博APP
     *
     * @return is installed
     */
    public boolean isInstalled() {
        return mWeiboShareAPI.isWeiboAppInstalled();
    }

    /**
     * 处理分享结果
     *
     * @param intent   {@link Intent}
     * @param response IWeiboHandler.Response
     */
    public void handleResponse(Intent intent, IWeiboHandler.Response response) {
        mWeiboShareAPI.handleWeiboResponse(intent, response);
    }

    public void wbShare(Activity activity){

        if(null==shareAction){
            return;
        }
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        ShareContent shareContent = shareAction.getShareContent();

        File file = new File(shareContent.mPath);
        if(!file.exists()){
            Log.e("WXShare","the path is null");
            return;
        }

        // 1. 初始化从分享内容
        if(shareContent!=null && shareContent.shareType == ShareType.WEBPAGE){
            weiboMessage.imageObject = getImageObj(activity,shareContent);
            weiboMessage.textObject = getTextObj(shareContent);
        }else if(shareContent!=null && shareContent.shareType == ShareType.IMAGE){
            weiboMessage.textObject = getTextObj(shareContent);
            weiboMessage.mediaObject = getImageObj(activity,shareContent);
        }else if(shareContent!=null && shareContent.shareType == ShareType.MUSIC){
            weiboMessage.mediaObject = getMusicObj(shareContent);
        }else if(shareContent!=null && shareContent.shareType == ShareType.TEXT){
            weiboMessage.mediaObject = getTextObj(shareContent);
        }else if(shareContent!=null && shareContent.shareType == ShareType.VEDIO){
            weiboMessage.mediaObject = getVideoObj(shareContent);
        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        Log.d("shareContent","shareContent : "+request.toString());

        mWeiboShareAPI.sendRequest(activity, request);
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(ShareContent shareContent) {
        TextObject textObject = new TextObject();
        textObject.text = shareContent.mText;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Activity activity, ShareContent shareContent) {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(shareContent.mPath, bmpFactoryOptions);

        imageObject.setImageObject(bitmap);
        bitmap.recycle();
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(ShareContent shareContent) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareContent.mTitle;
        mediaObject.description = shareContent.mText;

//        Bitmap  bitmap = BitmapFactory.decodeFile(shareContent.mPath);
//        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        mediaObject.setThumbImage(bitmap);
//        bitmap.recycle();

        mediaObject.actionUrl = shareContent.mTargetUrl;
        mediaObject.defaultText = shareContent.mText;
        return mediaObject;
    }


    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj(ShareContent shareContent) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = shareContent.mTitle;
        musicObject.description = shareContent.mText;

        Bitmap bitmap = BitmapFactory.decodeFile(shareContent.mPath);
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(bitmap);
        bitmap.recycle();

        musicObject.actionUrl = shareContent.mTargetUrl;
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = shareContent.mText;
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj(ShareContent shareContent) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = shareContent.mTitle;
        videoObject.description = shareContent.mText;
        Bitmap bitmap = BitmapFactory.decodeFile(shareContent.mPath);
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        videoObject.setThumbImage(bitmap);
        bitmap.recycle();

        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = shareContent.mTargetUrl;
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = shareContent.mText;
        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj(ShareContent shareContent) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = shareContent.mTitle;
        voiceObject.description = shareContent.mText;
        Bitmap bitmap = BitmapFactory.decodeFile(shareContent.mPath);
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        voiceObject.setThumbImage(bitmap);
        bitmap.recycle();
        voiceObject.actionUrl = shareContent.mTargetUrl;
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = shareContent.mText;
        return voiceObject;
    }
}
