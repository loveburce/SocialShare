package dawn.socialshare;

import android.app.Activity;
import android.text.TextUtils;

import java.util.HashMap;

import dawn.socialshare.share.ShareType;
import dawn.socialshare.utils.Utils;

/**
 * Created by dawn on 2017/2/26.
 */

public class ShareContentController {
    private String strTitle;
    private String strImageUrl;
    private String strContent;
    private String strTargetUrl;
    private boolean isNeedSpam;
    private boolean addLastForSina;
    private ShareType shareType;
    private HashMap<SHARE_MEDIA, ShareAction> specialAction = new HashMap<SHARE_MEDIA, ShareAction>();
    private HashMap<SHARE_MEDIA, String> spamMap = new HashMap<SHARE_MEDIA, String>();

    /**
     * 设置分享内容.
     *
     * @param shareType
     * @param strTitle
     * @param strContent
     * @param strImage
     * @param strTarget
     * @param isNeedSpam
     * @param isAddLastForSina
     */
    public void setShareContent(ShareType shareType, String strTitle, String strContent, String strImage, String strTarget,
                                boolean isNeedSpam, boolean isAddLastForSina) {
        this.strTitle = strTitle;
        this.strImageUrl = strImage;
        this.strContent = strContent;
        this.strTargetUrl = strTarget;
        this.isNeedSpam = isNeedSpam;
        this.addLastForSina = isAddLastForSina;
        this.shareType = shareType;
    }

    /**
     * @param platform
     * @param action
     */
    public void setSepicalAction(SHARE_MEDIA platform, ShareAction action) {
        specialAction.put(platform, action);
    }

    public void setSpam(SHARE_MEDIA platform, String spam) {
        spamMap.put(platform, spam);
    }

    public ShareAction getPlatformContent(Activity activity, SharePlatform platform){
        if(specialAction.get(platform.getShareMedia()) != null){
            return specialAction.get(platform.getShareMedia());
        }
        ShareAction action = new ShareAction(activity).setPlatform(platform.getShareMedia());
        String strAction = addSinaLast(this.strContent, strTargetUrl, platform.getShareMedia(), addLastForSina);
        if(platform.getShareMedia().compareTo(SHARE_MEDIA.SINA)!=0 &&  !TextUtils.isEmpty(strTargetUrl)){
            action.withTargetUrl(addSpam(strTargetUrl, platform.getShareMedia(), isNeedSpam));
        }
        action.withText(strContent)
        .withMedia(shareType).withShareIcon(Utils.getEncodingString(strImageUrl))
                .withTitle(platform.getShareMedia().compareTo(SHARE_MEDIA.WEIXIN_CIRCLE) == 0 ?strContent:strTitle);
        return action;
    }

    private String addSpam(String url, SHARE_MEDIA platform, boolean isNeed){
        if(!isNeed){
            return url;
        }
        String seperator = "?";
        if(url.contains("?")){
            seperator = "&";
        }
        String spam = spamMap.get(platform);
        if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
            url += (seperator + "spam=" + (spam == null?"wechat_fragment_android.share": spam));
            return url;
        }
        if (platform == SHARE_MEDIA.SINA) {
            url += (seperator + "spam=" + (spam == null?"weibo_fragment_android.share":spam));
            return url;
        }
        if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
            url += (seperator + "spam=" + (spam == null?"qq_fragment_android.share":spam));
            return url;
        }
        if (platform == SHARE_MEDIA.SMS || platform == SHARE_MEDIA.EMAIL) {
            url += (seperator + "spam=" + (spam == null? "email_fragment_android.share":spam));
            return url;
        }
        return url;

    }

    private String addSinaLast(String content, String targetUrl, SHARE_MEDIA platform, boolean isLastForSina) {
        if (platform == SHARE_MEDIA.SMS) {
            return content + targetUrl;
        }
        if (platform != SHARE_MEDIA.SINA || !isLastForSina) {
            return content;
        }
        int i = content.lastIndexOf("》");

        String sinaStr = content;
        if (i >= 0 && i < content.length() - 1 && !addLastForSina) {
            sinaStr = content.substring(0, i + 1) + "，" + targetUrl + content.substring(i + 1, content.length());
        }
        if (addLastForSina) {
            sinaStr += (" " + addSpam(targetUrl, platform, isNeedSpam) + " ");
        }
        return sinaStr + "新浪微博";
    }

}
