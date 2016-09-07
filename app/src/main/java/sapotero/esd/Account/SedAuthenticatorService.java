package sapotero.esd.Account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class SedAuthenticatorService extends Service {

private SedAuthenticator mAuthenticator;

  @Override
  public void onCreate() {
    super.onCreate();
    mAuthenticator = new SedAuthenticator(getApplicationContext());
    }

  @Override
  public IBinder onBind(Intent intent) {
    return mAuthenticator.getIBinder();
    }

}
