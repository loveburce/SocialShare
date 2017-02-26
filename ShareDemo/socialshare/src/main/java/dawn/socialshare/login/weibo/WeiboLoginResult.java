package dawn.socialshare.login.weibo;

import android.os.Parcel;
import android.os.Parcelable;

class WeiboLoginResult implements Parcelable {
    String uid;
    String access_token;
    String userName;
    String expires_in;
    String remind_in;
    String refresh_token;

    public static final Creator<WeiboLoginResult> CREATOR = new Creator<WeiboLoginResult>() {
        @Override
        public WeiboLoginResult createFromParcel(Parcel in) {
            return new WeiboLoginResult(in);
        }

        @Override
        public WeiboLoginResult[] newArray(int size) {
            return new WeiboLoginResult[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.access_token);
        dest.writeString(this.userName);
        dest.writeString(this.expires_in);
        dest.writeString(this.remind_in);
        dest.writeString(this.refresh_token);
    }

    public WeiboLoginResult() {}

    protected WeiboLoginResult(Parcel in) {
        this.uid = in.readString();
        this.access_token = in.readString();
        this.userName = in.readString();
        this.expires_in = in.readString();
        this.remind_in = in.readString();
        this.refresh_token = in.readString();
    }


    @Override
    public String toString() {
        return "WeiboLoginResult{" +
                "uid='" + uid + '\'' +
                ", access_token='" + access_token + '\'' +
                ", userName='" + userName + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", remind_in='" + remind_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}