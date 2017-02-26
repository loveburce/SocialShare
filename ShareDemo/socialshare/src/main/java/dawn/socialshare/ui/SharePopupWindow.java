package dawn.socialshare.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dawn.socialshare.R;
import dawn.socialshare.SHARE_MEDIA;
import dawn.socialshare.ShareAPI;
import dawn.socialshare.ShareAction;
import dawn.socialshare.ShareContentController;
import dawn.socialshare.SharePlatform;
import dawn.socialshare.listener.ShareListener;
import dawn.socialshare.share.ShareType;

/**
 * Created by dawn on 2017/2/26.
 */

public class SharePopupWindow {
    private PopupWindow window;
    protected GridView gridView;
    private View parent;
    private Activity activity;
    private ShareAdapter adapter;
    private LinearLayout layout;
    private List<SharePlatform> list;
    private ShareContentController contentController;
    private ShareAPI mShareAPI;
    private OnShareListener onShareListener;

    public SharePopupWindow(Activity activity, View parent){
        this.activity = activity;
        this.parent = parent;
        mShareAPI = ShareAPI.getInstance(activity);
        onCreateView();
        onCreateData();
        onCreateListener();
    }

    public void onCreateView(){
        layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.popupwindow_share_layout, null);
        gridView = (GridView) layout.findViewById(R.id.popupwindow_share_gridview);
        window = new PopupWindow(activity);
        window.setContentView(layout);
        window.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        window.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        window.update();
        window.setOutsideTouchable(true);
        ColorDrawable drawable = (ColorDrawable) activity.getResources().getDrawable(R.drawable.popupwindow_background);
        window.setBackgroundDrawable(drawable);
        layout.setFocusableInTouchMode(true);
    }

    public void onCreateData(){
        list = new ArrayList<>();
        list.add(SharePlatform.WECHAT);
        list.add(SharePlatform.CIRCLE);
        list.add(SharePlatform.QZONE);
        list.add(SharePlatform.WEIBO);
        list.add(SharePlatform.QQ);
        list.add(SharePlatform.SMS);
        adapter = new ShareAdapter(activity,list);
        gridView.setAdapter(adapter);
        contentController = new ShareContentController();
    }

    public void onCreateListener(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    public void show(){
        if(window == null){
            onCreateView();
        }
        window.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void dismiss(){
        if(window != null && window.isShowing()){
            window.dismiss();
        }
    }

    public boolean isShow(){
        if (window != null && window.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置回调监听
     * @param onShareListener
     */
    public void setOnShareListener(OnShareListener onShareListener){
        this.onShareListener = onShareListener;
    }

    public void initShareContent(ShareType shareType, String targetUrl, String content,
                                 String imageUrl, boolean isAddLastForSina, boolean isNeedSpam){
        initShareContent(shareType,targetUrl, content, imageUrl, activity.getString(R.string.app_name), isAddLastForSina,
                isNeedSpam);
    }

    public void initShareContent(ShareType shareType,String targetUrl, String content, String imageUrl,
                                 String title, boolean isAddLastForSina, boolean isNeedSpam) {
        contentController.setShareContent(shareType,title, content, imageUrl, targetUrl, isNeedSpam, isAddLastForSina);
    }

    /**
     * 为某些平台设置特殊的分享内容.
     * @param platform
     * @param action
     */
    public void initSpecialContent(SharePlatform platform, ShareAction action){
        contentController.setSepicalAction(platform.getShareMedia(), action);
    }

    public void setSpam(SharePlatform platform, String spam){
        contentController.setSpam(platform.getShareMedia(), spam);
    }

    /**
     * 分享的页面回调.
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onShareActivityResult(Activity activity, int requestCode, int resultCode, Intent data){
        if (data != null) {
            ShareAPI.getInstance(activity).onActivityResult(requestCode, resultCode, data);
        }
    }

    private ShareListener shareListener = new ShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };


    private class ShareAdapter extends BaseAdapter {
        protected Context mContext;
        protected List<SharePlatform> listData;

        public ShareAdapter(Context context, List list) {
            this.mContext = context;
            this.listData = list;
        }

        @Override
        public int getCount() {
            return this.listData != null?this.listData .size():0;
        }

        @Override
        public Object getItem(int position) {
            return this.listData != null?this.listData.get(position):null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SharePlatform platform = listData.get(position);
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_share, null);
                holder.iv = (ImageView) convertView.findViewById(R.id.item_share_iv);
                holder.tv = (TextView) convertView.findViewById(R.id.item_share_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.iv.setImageResource(platform.getDrawable());
            holder.tv.setText(platform.getString());
            return convertView;
        }
    }

    class ViewHolder{
        TextView tv;
        ImageView iv;
    }

    public static interface OnShareListener{
        public void onShareStart(SharePlatform platform);
        public void onShareComplete(SHARE_MEDIA platform, int code);
        public void onShareError(SHARE_MEDIA platform, int code);
    }

    public String getErrorMessage(int code){
        if(code == 5016){
            return "分享内容重复";
        }
        if (code == 5035 || code == 5039) {
            return "发布内容频率太高";
        }
        if (code == 5005) {
            return "访问频率超限，可一会儿再试";
        }
        if (code == 5017) {
            return "图像文件大小不正确";
        }
        if (code == 5018) {
            return "appurl不正确";
        }
        if (code == 5019) {
            return "图像url不正确";
        }
        return code + "";
    }
}
