package sapotero.esd.Activity;

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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sapotero.esd.Adapter.DocumentAdapter;
import sapotero.esd.Model.Document;
import sapotero.esd.R;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "RecyclerViewExample";
  private List<Document> feedsList;
  private RecyclerView mRecyclerView;
  private DocumentAdapter adapter;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialize recycler view
    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    progressBar.setVisibility(View.VISIBLE);

    // Downloading data from below url
    final String url = "http://192.168.155.23:9000/documents.json";
    new AsyncHttpTask().execute(url);
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
        adapter = new DocumentAdapter(MainActivity.this, feedsList);
        mRecyclerView.setAdapter(adapter);
      } else {
        Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void parseResult(String result) {
    try {
      JSONObject response = new JSONObject(result);
      JSONArray posts = response.optJSONArray("documents");
      feedsList = new ArrayList<>();

      for (int i = 0; i < posts.length(); i++) {
        JSONObject post = posts.optJSONObject(i);
        Document item = new Document();
        item.setTitle(post.optString("title"));
        item.setImage(post.optString("image"));

        feedsList.add(item);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
