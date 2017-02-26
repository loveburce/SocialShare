package dawn.socialshare.login.qq;

import android.os.Parcel;
import android.os.Parcelable;

public class QQLoginResult implements Parcelable {
    int ret;
    public String openid;
    public String access_token;
    String pay_token;
    public long expires_in;
    String pf;
    String pfkey;
    String msg;
    long login_cost;
    long query_authority_cost;
    long authority_cost;

    public static final Creator<QQLoginResult> CREATOR = new Creator<QQLoginResult>() {
        @Override
        public QQLoginResult createFromParcel(Parcel in) {
            return new QQLoginResult(in);
        }

        @Override
        public QQLoginResult[] newArray(int size) {
            return new QQLoginResult[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ret);
        dest.writeString(this.openid);
        dest.writeString(this.access_token);
        dest.writeString(this.pay_token);
        dest.writeLong(this.expires_in);
        dest.writeString(this.pf);
        dest.writeString(this.pfkey);
        dest.writeString(this.msg);
        dest.writeLong(this.login_cost);
        dest.writeLong(this.query_authority_cost);
        dest.writeLong(this.authority_cost);
    }

    public QQLoginResult() {}

    protected QQLoginResult(Parcel in) {
        this.ret = in.readInt();
        this.openid = in.readString();
        this.access_token = in.readString();
        this.pay_token = in.readString();
        this.expires_in = in.readLong();
        this.pf = in.readString();
        this.pfkey = in.readString();
        this.msg = in.readString();
        this.login_cost = in.readLong();
        this.query_authority_cost = in.readLong();
        this.authority_cost = in.readLong();
    }

    @Override
    public String toString() {
        return "QQLoginResult{" +
                "ret=" + ret +
                ", openid='" + openid + '\'' +
                ", access_token='" + access_token + '\'' +
                ", pay_token='" + pay_token + '\'' +
                ", expires_in=" + expires_in +
                ", pf='" + pf + '\'' +
                ", pfkey='" + pfkey + '\'' +
                ", msg='" + msg + '\'' +
                ", login_cost=" + login_cost +
                ", query_authority_cost=" + query_authority_cost +
                ", authority_cost=" + authority_cost +
                '}';
    }
}