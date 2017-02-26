package dawn.socialshare.listener;


import dawn.socialshare.SHARE_MEDIA;

/**
 * Created by dawn on 2017/2/8.
 */

public interface ShareListener {
    void onResult(SHARE_MEDIA platform);
    void onError(SHARE_MEDIA platform, Throwable throwable);
    void onCancel(SHARE_MEDIA platform);
}
