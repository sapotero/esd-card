package sapotero.esd.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import sapotero.esd.Fragment.PDFRenderFragment;
import sapotero.esd.R;

public class PdfViewActivity extends Activity {

  public static final String FRAGMENT_PDF_RENDERER_BASIC = "doc";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pdf_view);

    PDFRenderFragment fragment = new PDFRenderFragment();

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add( R.id.container, fragment, FRAGMENT_PDF_RENDERER_BASIC )
          .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_info:
        new AlertDialog.Builder(this)
            .setMessage(R.string.intro_message)
            .setPositiveButton(android.R.string.ok, null)
            .show();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}