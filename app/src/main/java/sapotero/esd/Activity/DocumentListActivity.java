package sapotero.esd.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sapotero.esd.Adapter.DocumentAdapter;
import sapotero.esd.Decorator.DocumentItemDecoration;
import sapotero.esd.Model.Document;
import sapotero.esd.R;
import sapotero.esd.SQLite.SQLiteHelper;

public class DocumentListActivity extends AppCompatActivity {

  private static final String TAG = "DocumentView";

  public final static String ID = "DOCUMENT_ID";
  public final static String TITLE = "DOCUMENT_TITLE";
  public final static String DESCRIPTION = "DOCUMENT_DESCRIPTION";
  public final static String AUTHOR = "DOCUMENT_AUTHOR";
  public final static String IMAGE = "DOCUMENT_IMAGE";

  private List<Document> feedsList;
  private RecyclerView mRecyclerView;
  private DocumentAdapter adapter;
  private ProgressBar progressBar;
  private SQLiteDatabase db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialize recycler view
    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    mRecyclerView .addItemDecoration(new DocumentItemDecoration(this));
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    progressBar.setVisibility(View.VISIBLE);

    // Downloading data from below url
    final String url = "https://esd-documents.herokuapp.com/documents.json";
    new AsyncHttpTask().execute(url);

    setTitle( "Документы" );

    String tContents = "";
    try {
      InputStream stream = getAssets().open("test.pdf");

      int size = stream.available();
      byte[] buffer = new byte[size];
      stream.read(buffer);
      stream.close();
      tContents = new String(buffer);
    } catch (IOException e) {
//      Log.d(e);
    }

//    getAssets().openFd("test.pdf").getParcelFileDescriptor();

    Log.d( "FILE", tContents.substring(0, 100) );


  }

  public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

    @Override
    protected void onPreExecute() {
      setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected Integer doInBackground(String... params) {
      Integer result = 0;
      HttpURLConnection urlConnection;
      try {
        URL url = new URL(params[0]);
        urlConnection = (HttpURLConnection) url.openConnection();
        int statusCode = urlConnection.getResponseCode();

        // 200 represents HTTP OK
        if (statusCode == 200) {
          BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = r.readLine()) != null) {
            response.append(line);
          }
          parseResult(response.toString());
          result = 1; // Successful
        } else {
          result = 0; //"Failed to fetch data!";
        }
      } catch (Exception e) {
        Log.d(TAG, e.getLocalizedMessage());
      }
      return result; //"Failed to fetch data!";
    }

    @Override
    protected void onPostExecute(Integer result) {
      // Download complete. Let us update UI
      progressBar.setVisibility(View.GONE);

      if (result == 1) {
        adapter = new DocumentAdapter(DocumentListActivity.this, feedsList);
        mRecyclerView.setAdapter(adapter);
      } else {
        Toast.makeText(DocumentListActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void parseResult(String result) {
    SQLiteHelper db = new SQLiteHelper(this);

    try {
      JSONObject response = new JSONObject(result);
      JSONArray posts = response.optJSONArray("documents");
      feedsList = new ArrayList<>();

      for (int i = 0; i < posts.length(); i++) {
        JSONObject post = posts.optJSONObject(i);
        Document document = new Document();
        document.setId( post.optString("id") );
        document.setTitle(post.optString("title"));
        document.setDescription(post.optString("description"));
        document.setAuthor(post.optString("author"));
        document.setImage(post.optString("image"));

        db.addDocument( document );
        feedsList.add(document);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
