package dawn.sharedemo;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import dawn.socialshare.ShareAction;
import dawn.socialshare.SharePlatform;
import dawn.socialshare.share.ShareType;
import dawn.socialshare.ui.SharePopupWindow;

public class MainActivity extends AppCompatActivity implements PopupWindow.OnDismissListener {
    private Button button;
    private RelativeLayout relativeLayout;
    private SharePopupWindow mShare;

    /**
     * 分享弹框的ID.
     */
    public static final String EID_SHARE_BTN = "TOSHARE";
    public static final String EID_SHARE_SUCCESS = "TOSHARE#SUCCESS";
    public static final String BID_SIGIN = "SIGNIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initView(){
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        button = (Button) findViewById(R.id.share_submit);
    }


    private void initListener(){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    private void initData(){

    }


    @Override
    public void onDismiss() {

    }

    public void share(){
        String phoneUrl = "dd";
        String imageUrl = Utils.getEncodingString(phoneUrl);
            mShare = new SharePopupWindow(this, ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0));
            String strTitle = getString(R.string.signin_sharetitle);
            mShare.setSpam(SharePlatform.QZONE, "qq_signin_android.share");
            mShare.setSpam(SharePlatform.WEIBO, "weibo_signin_android.share");
            ShareAction actionQZone = new ShareAction(this).setPlatform(SharePlatform.QZONE.getShareMedia());
            actionQZone.withMedia(ShareType.IMAGE).withTitle(strTitle).withShareIcon(imageUrl)
                    .withText(getString(R.string.sign_sharecontent_qzone))
                    .withTargetUrl("http://www.xuetangx.com/mobile?spam=qq_signin_android.share");
            mShare.initSpecialContent(SharePlatform.QZONE, actionQZone);
            ShareAction actionWb = new ShareAction(this).setPlatform(SharePlatform.WEIBO.getShareMedia());
            actionWb.withMedia(ShareType.IMAGE).withShareIcon(imageUrl).withTitle(strTitle).withText(getString(R.string.sign_sharecontent_wb));
            mShare.initSpecialContent(SharePlatform.WEIBO, actionWb);
            mShare.initShareContent(ShareType.WEBPAGE,"", getString(R.string.sign_sharecontent_sms) , imageUrl, strTitle, false, true);
            ShareAction actionQQ = new ShareAction(this).setPlatform(SharePlatform.QQ.getShareMedia());
            actionQQ.withMedia(ShareType.IMAGE).withShareIcon(imageUrl);
            mShare.initSpecialContent(SharePlatform.QQ, actionQQ);
            ShareAction actionWechat = new ShareAction(this).setPlatform(SharePlatform.WECHAT.getShareMedia());
            actionWechat.withMedia(ShareType.IMAGE).withShareIcon(imageUrl);
            mShare.initSpecialContent(SharePlatform.WECHAT, actionWechat);
            ShareAction actionCircle = new ShareAction(this).setPlatform(SharePlatform.CIRCLE.getShareMedia());
            actionCircle.withMedia(ShareType.IMAGE).withShareIcon(imageUrl);
            mShare.initSpecialContent(SharePlatform.CIRCLE, actionCircle);

        mShare.show();
    }
}
