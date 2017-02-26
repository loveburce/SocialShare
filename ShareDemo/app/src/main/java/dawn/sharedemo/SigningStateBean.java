package dawn.sharedemo;

import java.io.Serializable;

/**
 * Created by dawn on 2017/2/26.
 */

public class SigningStateBean implements Serializable {
    private String strCommentUrl;
    private int intSigningDays;
    private boolean isTodaySigning;
    private String strGiftUrl;
    private String strGiftDesc;
    private String strDesc;
    private String strDailyImageUrl;
    private String strDailyShareUrl;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getStrCommentUrl() {
        return strCommentUrl;
    }

    public void setStrCommentUrl(String strCommentUrl) {
        this.strCommentUrl = strCommentUrl;
    }

    public int getIntSigningDays() {
        return intSigningDays;
    }

    public void setIntSigningDays(int intSigningDays) {
        this.intSigningDays = intSigningDays;
    }

    public boolean isTodaySigning() {
        return isTodaySigning;
    }

    public void setTodaySigning(boolean todaySigning) {
        isTodaySigning = todaySigning;
    }

    public String getStrGiftUrl() {
        return strGiftUrl;
    }

    public void setStrGiftUrl(String strGiftUrl) {
        this.strGiftUrl = strGiftUrl;
    }

    public String getStrGiftDesc() {
        return strGiftDesc;
    }

    public void setStrGiftDesc(String strGiftDesc) {
        this.strGiftDesc = strGiftDesc;
    }

    public String getStrDesc() {
        return strDesc;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public String getStrDailyImageUrl() {
        return strDailyImageUrl;
    }

    public void setStrDailyImageUrl(String strDailyImageUrl) {
        this.strDailyImageUrl = strDailyImageUrl;
    }

    public String getStrDailyShareUrl() {
        return strDailyShareUrl;
    }

    public void setStrDailyShareUrl(String strDailyShareUrl) {
        this.strDailyShareUrl = strDailyShareUrl;
    }

    @Override
    public String toString() {
        return "SigningStateBean{" +
                "strCommentUrl='" + strCommentUrl + '\'' +
                ", intSigningDays=" + intSigningDays +
                ", isTodaySigning=" + isTodaySigning +
                ", strGiftUrl='" + strGiftUrl + '\'' +
                ", strGiftDesc='" + strGiftDesc + '\'' +
                ", strDesc='" + strDesc + '\'' +
                ", strDailyImageUrl='" + strDailyImageUrl + '\'' +
                ", strDailyShareUrl='" + strDailyShareUrl + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }
}
