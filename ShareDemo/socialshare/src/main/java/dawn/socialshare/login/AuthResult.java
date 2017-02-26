package dawn.socialshare.login;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthResult implements Parcelable {
    /** unlogin */
    public static final int TYPE_NONE = 0;
    /** login with weibo */
    public static final int TYPE_WEIBO = 1;
    /** login with wechat */
    public static final int TYPE_WECHAT = 2;
    /** login with qq */
    public static final int TYPE_QQ = 3;

    public int from = TYPE_NONE;
    public String id;
    public String accessToken;
    public long expiresIn;
    public String refreshToken = "";

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.from);
        dest.writeString(this.id);
        dest.writeString(this.accessToken);
        dest.writeLong(this.expiresIn);
        dest.writeString(this.refreshToken);
    }

    public AuthResult() {}

    protected AuthResult(Parcel in) {
        this.from = in.readInt();
        this.id = in.readString();
        this.accessToken = in.readString();
        this.expiresIn = in.readLong();
        this.refreshToken = in.readString();
    }

    public static final Creator<AuthResult> CREATOR = new Creator<AuthResult>() {
        public AuthResult createFromParcel(Parcel source) {return new AuthResult(source);}

        public AuthResult[] newArray(int size) {return new AuthResult[size];}
    };

    @Override
    public String toString() {
        return "AuthResult{" +
                "from=" + from +
                ", id='" + id + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
