package dawn.socialshare.listener;

import java.util.Map;

import dawn.socialshare.SHARE_MEDIA;


public interface AuthListener {
 int ACTION_AUTHORIZE = 0;
  int ACTION_DELETE =1;
  int ACTION_GET_PROFILE = 2;
  AuthListener dummy = new AuthListener(){

	@Override
	public void onComplete(SHARE_MEDIA platform, int action,
						   Map<String, String> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(SHARE_MEDIA platform, int action, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel(SHARE_MEDIA platform, int action) {
		// TODO Auto-generated method stub
		
	}
	  
  };
  void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data);
  void onError(SHARE_MEDIA platform, int action, Throwable t);
  void onCancel(SHARE_MEDIA platform, int action);
}
