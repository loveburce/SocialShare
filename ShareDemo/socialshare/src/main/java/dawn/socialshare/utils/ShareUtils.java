package dawn.socialshare.utils;

import android.app.Activity;

import dawn.socialshare.R;
import dawn.socialshare.SHARE_MEDIA;

/**
 * Created by dawn on 2017/2/26.
 */

public class ShareUtils {
    public static int getNoClientMsg(SHARE_MEDIA media){
        if(media.equals(SHARE_MEDIA.WEIXIN) || media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
            return R.string.no_wechat;
        }
        if(media.equals(SHARE_MEDIA.QQ)){
            return R.string.no_qq;
        }
        if (media.equals(SHARE_MEDIA.QZONE)) {
            return R.string.no_qzone;
        }
        return R.string.no_client;
    }

    public static boolean isClientInstalled(SHARE_MEDIA shareMedia, Activity activity ){
        if (shareMedia == SHARE_MEDIA.QZONE) {
            shareMedia = SHARE_MEDIA.QQ;
        }
        if(shareMedia == SHARE_MEDIA.WEIXIN_CIRCLE){
            shareMedia = SHARE_MEDIA.WEIXIN;
        }
        if (shareMedia.compareTo(SHARE_MEDIA.SMS) == 0 || shareMedia.compareTo(SHARE_MEDIA.EMAIL) == 0) {
            return true;
        }
        return SdkUtils.isClientInstalled(activity,shareMedia);
    }
}
