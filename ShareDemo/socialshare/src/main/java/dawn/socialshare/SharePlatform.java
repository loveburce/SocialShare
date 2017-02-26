package dawn.socialshare;

/**
 * Created by dawn on 2017/2/26.
 */

public enum SharePlatform {
    WECHAT(R.string.share_wechat, R.drawable.ic_share_wechat, SHARE_MEDIA.WEIXIN),
    CIRCLE(R.string.share_circle, R.drawable.ic_share_circle, SHARE_MEDIA.WEIXIN_CIRCLE),
    QZONE(R.string.share_qzone, R.drawable.ic_share_qzone, SHARE_MEDIA.QZONE),
    WEIBO(R.string.share_weibo, R.drawable.ic_share_weibo, SHARE_MEDIA.SINA),
    QQ(R.string.share_qq, R.drawable.ic_share_qq, SHARE_MEDIA.QQ),
    SMS(R.string.share_sms, R.drawable.ic_share_sms, SHARE_MEDIA.SMS),
    EMAIL(R.string.share_email, R.drawable.ic_share_email, SHARE_MEDIA.EMAIL);
    private int title;
    private int resId;
    private SHARE_MEDIA media;

    private SharePlatform(int title,int resId,SHARE_MEDIA media){
        this.title = title;
        this.resId = resId;
        this.media = media;
    }

    /**
     * 获取该分享平台的文字Title ID.
     * @return 文字Title ID.
     */
    public int getString(){
        return title;
    }

    /**
     * 获取该分享平台的Icon ID.
     * @return Icon ID
     */
    public int getDrawable(){
        return resId;
    }

    /**
     * 获取该分享对应的Share Media.
     * @return
     */
    public SHARE_MEDIA getShareMedia(){
        return media;
    }
}
