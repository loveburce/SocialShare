package dawn.sharedemo;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by dawn on 2017/2/26.
 */

public class Utils {
    public static String getEncodingString(String strUrl) {
        try {
            URI uri = new URI(strUrl);
            return uri.toASCIIString().replaceAll(" ", "%20");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return strUrl;
    }
}
