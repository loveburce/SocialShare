package dawn.socialshare.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;

import dawn.socialshare.SHARE_MEDIA;

public class SdkUtils {
	 public static boolean isClientInstalled (Context context, SHARE_MEDIA platform ) {
	        PackageManager pm;
	        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
	            return false;
	        }
	        List<PackageInfo> packages = pm.getInstalledPackages(0);
	        for (PackageInfo info : packages) {
	            String name = info.packageName.toLowerCase(Locale.ENGLISH);
	            if (platform == SHARE_MEDIA.SINA && "com.sina.weibo".equals(name)) {
	                return true;
	            }else if(platform == SHARE_MEDIA.QQ &&"com.tencent.mobileqq".equals(name)){
	                return true;
	            }else if(platform == SHARE_MEDIA.WEIXIN &&"com.tencent.mm".equals(name)){
	                return true;
	            }
	        }
	        return false;
	    }

	    public static String md5(String str) {
	        if(str == null) {
	            return null;
	        } else {
	            try {
	                byte[] bytes = str.getBytes();
	                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	                messageDigest.reset();
	                messageDigest.update(bytes);
	                byte[] bytes1 = messageDigest.digest();
	                StringBuffer stringBuffer = new StringBuffer();

	                for(int i = 0; i < bytes1.length; ++i) {
	                    stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bytes1[i])}));
	                }

	                return stringBuffer.toString();
	            } catch (Exception var6) {
	                return str.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
	            }
	        }
	    }
}
