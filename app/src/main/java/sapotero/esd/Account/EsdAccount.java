package sapotero.esd.Account;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.os.Parcel;

@SuppressLint("ParcelCreator")
public class EsdAccount extends Account {

  public static final String TYPE = "sapotero.esd";
  public static final String TOKEN_FULL_ACCESS = "sapotero.esd.TOKEN_FULL_ACCESS";
  public static final String KEY_PASSWORD = "sapotero.esd.KEY_PASSWORD";

  public EsdAccount(Parcel in) {
    super(in);
  }

  public EsdAccount(String name) {
    super(name, TYPE);
  }

}
