package dawn.socialshare.login.wechat;

import android.os.Parcel;
import android.os.Parcelable;

class WechatLoginResult implements Parcelable {
    String accessToken;
    long expiresIn;
    String refreshToken;
    String openid;
    String scope;
    String unionid;
    int errcode;
    String errmsg;

    public static final Creator<WechatLoginResult> CREATOR = new Creator<WechatLoginResult>() {
        @Override
        public WechatLoginResult createFromParcel(Parcel in) {
            return new WechatLoginResult(in);
        }

        @Override
        public WechatLoginResult[] newArray(int size) {
            return new WechatLoginResult[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeLong(this.expiresIn);
        dest.writeString(this.refreshToken);
        dest.writeString(this.openid);
        dest.writeString(this.scope);
        dest.writeString(this.unionid);
        dest.writeInt(this.errcode);
        dest.writeString(this.errmsg);
    }

    public WechatLoginResult() {}

    protected WechatLoginResult(Parcel in) {
        this.accessToken = in.readString();
        this.expiresIn = in.readLong();
        this.refreshToken = in.readString();
        this.openid = in.readString();
        this.scope = in.readString();
        this.unionid = in.readString();
        this.errcode = in.readInt();
        this.errmsg = in.readString();
    }

    @Override
    public String toString() {
        return "WechatLoginResult{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                ", unionid='" + unionid + '\'' +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
