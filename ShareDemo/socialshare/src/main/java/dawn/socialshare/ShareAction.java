package dawn.socialshare;

import android.app.Activity;

import java.lang.ref.WeakReference;

import dawn.socialshare.listener.ShareListener;
import dawn.socialshare.share.ShareContent;
import dawn.socialshare.share.ShareType;

/**
 * Created by liyusheng on 2017/2/8.
 */

public class ShareAction {
    private Activity mActivity;
    public SHARE_MEDIA platform;
    public ShareContent shareContent = new ShareContent();
    public ShareListener shareListener;

    public  ShareAction(Activity activity){
        this.mActivity = (Activity)(new WeakReference(activity).get());

    }

    public SHARE_MEDIA getPlatform() {
        return platform;
    }

    public ShareContent getShareContent() {
        return shareContent;
    }

    public ShareAction setPlatform(SHARE_MEDIA platform){
        this.platform = platform;
        return this;
    }

    public ShareAction withTargetUrl(String targetUrl){
        this.shareContent.mTargetUrl = targetUrl;
        return this;
    }

    public ShareAction withTitle(String title){
        this.shareContent.mTitle = title;
        return this;
    }

    public ShareAction withText(String text){
        this.shareContent.mText = text;
        return this;
    }
    public ShareAction withMedia(ShareType type){
        this.shareContent.shareType = type;
        return this;
    }

    public ShareAction withShareIcon(String imgUrl){
        this.shareContent.mPath = imgUrl;
        return this;
    }

    private static final String DIR_SHARE_CACHE_PATH = "share_cache";

//    public void downloadImg(netutils.http.RequestCallBack callBack){
//        File file = FileMgr.newFile(DIR_SHARE_CACHE_PATH, Md5Util.getMD5Str(shareContent.mPath)+".png");
//        String path = file.getAbsolutePath();
//        new ThreadPoolHttp().download(shareContent.mPath,path,callBack);
//    }

    public ShareAction withFollow(String follow){
        this.shareContent.mFollow = follow;
        return this;
    }

    public ShareAction setCallback(ShareListener shareListener){
        this.shareListener = shareListener;
        return this;
    }

    public void share(){
        ShareAPI.getInstance(mActivity).doShare(mActivity,this,shareListener);
    }

}
