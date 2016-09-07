package sapotero.esd.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sapotero.esd.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;


public class PDFRenderFragment extends Fragment implements View.OnClickListener {

  private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";

  private ParcelFileDescriptor mFileDescriptor;
  private PdfRenderer mPdfRenderer;
  private PdfRenderer.Page mCurrentPage;
  private ImageView mImageView;
  private Button mButtonPrevious;
  private Button mButtonNext;

  public PDFRenderFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_pdfrender, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    mImageView = (ImageView) view.findViewById(R.id.image);
    mButtonPrevious = (Button) view.findViewById(R.id.previous);
    mButtonNext = (Button) view.findViewById(R.id.next);

    mButtonPrevious.setOnClickListener(this);
    mButtonNext.setOnClickListener(this);

    int index = 0;

    if (null != savedInstanceState) {
      index = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
    }
    showPage(index);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      openRenderer(activity);
    } catch (IOException e) {
      e.printStackTrace();
      Toast.makeText(activity, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
      activity.finish();
    }
  }

  @Override
  public void onDetach() {
    try {
      closeRenderer();
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.onDetach();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (null != mCurrentPage) {
      outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex());
    }
  }


  private void openRenderer(Context context) throws IOException {


    String tContents = "";
    try {
      InputStream stream = getActivity().getAssets().open("sample.pdf");

      int size = stream.available();
      byte[] buffer = new byte[size];
      stream.read(buffer);
      stream.close();
      tContents = new String(buffer);
    } catch (IOException e) {

    }
    Log.d( "FILE", tContents.substring(0, 100) );

    AssetFileDescriptor file = getActivity().getAssets().openFd("sample.pdf");
    mFileDescriptor = file.getParcelFileDescriptor();


    mPdfRenderer = new PdfRenderer(mFileDescriptor);
    Log.d( "FILE", mPdfRenderer.toString()  );
  }

  private void closeRenderer() throws IOException {
    if (null != mCurrentPage) {
      mCurrentPage.close();
    }
    mPdfRenderer.close();
    mFileDescriptor.close();
  }


  private void showPage(int index) {
    if (mPdfRenderer.getPageCount() <= index) {
      return;
    }
    // Make sure to close the current page before opening another one.
    if (null != mCurrentPage) {
      mCurrentPage.close();
    }

    mCurrentPage = mPdfRenderer.openPage(index);
    Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

    mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
    // We are ready to show the Bitmap to user.
    mImageView.setImageBitmap(bitmap);
    updateUi();
  }

  private void updateUi() {
    int index = mCurrentPage.getIndex();
    int pageCount = mPdfRenderer.getPageCount();
    mButtonPrevious.setEnabled(0 != index);
    mButtonNext.setEnabled(index + 1 < pageCount);
    getActivity().setTitle(getString(R.string.app_name_with_index, index + 1, pageCount));
  }

  public int getPageCount() {
    return mPdfRenderer.getPageCount();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.previous: {
        showPage(mCurrentPage.getIndex() - 1);
        break;
      }
      case R.id.next: {
        showPage(mCurrentPage.getIndex() + 1);
        break;
      }
    }
  }

}