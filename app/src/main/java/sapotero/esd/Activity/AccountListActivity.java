package sapotero.esd.Activity;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;

import sapotero.esd.Account.EsdAccount;
import sapotero.esd.R;

public class AccountListActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    final AccountManager am = AccountManager.get(this);

    if (am.getAccountsByType(EsdAccount.TYPE).length == 0) {
      addNewAccount(am);
    }

    if (savedInstanceState == null) {
      Intent intent = new Intent(this, DocumentListActivity.class);
      startActivity(intent);
    }
  }

  private void addNewAccount(AccountManager am) {
    am.addAccount(EsdAccount.TYPE, EsdAccount.TOKEN_FULL_ACCESS, null, null, this,
        new AccountManagerCallback<Bundle>() {
          @Override
          public void run(AccountManagerFuture<Bundle> future) {
            try {
              future.getResult();
            } catch (OperationCanceledException | IOException | AuthenticatorException e) {
              AccountListActivity.this.finish();
            }
          }
        }, null
    );
  }

}
