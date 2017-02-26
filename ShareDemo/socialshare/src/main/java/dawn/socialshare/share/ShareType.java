package dawn.socialshare.share;

/**
 * Created by liyusheng on 2017/2/8.
 */

public enum  ShareType {
    IMAGE {
        public String toString() {
            return "0";
        }
    },
    VEDIO {
        public String toString() {
            return "1";
        }
    },
    MUSIC {
        public String toString() {
            return "2";
        }
    },
    TEXT {
        public String toString() {
            return "3";
        }
    },
    TEXT_IMAGE {
        public String toString() {
            return "4";
        }
    },
    WEBPAGE {
        public String toString() {
            return "5";
        }
    };

    private ShareType() {
    }

    public static ShareType convertToEmun(String type) {
        ShareType[] shareTypes1 = values();
        ShareType[] shareTypes2 = shareTypes1;
        int tempLength = shareTypes1.length;

        for(int i = 0; i < tempLength; ++i) {
            ShareType shareType = shareTypes2[i];
            if(shareType.toString().equals(shareTypes1)) {
                return shareType;
            }
        }

        return null;
    }
}
