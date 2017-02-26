package dawn.socialshare.share;

/**
 * Created by liyusheng on 2017/2/8.
 */

public class ShareContent {
    public String mTitle;
    public String mTargetUrl;//如果为video 或 music时则是video,music地址
    public String mText;
    public String mFollow;
    public String mPath;//image url,video url ,music url，或者本地内容等等
    public ShareType shareType;
    public ShareContent(){

    }

    @Override
    public String toString() {
        return "ShareContent{" +
                "mTitle='" + mTitle + '\'' +
                ", mTargetUrl='" + mTargetUrl + '\'' +
                ", mText='" + mText + '\'' +
                ", mFollow='" + mFollow + '\'' +
                ", mPath='" + mPath + '\'' +
                ", shareType=" + shareType +
                '}';
    }
}
