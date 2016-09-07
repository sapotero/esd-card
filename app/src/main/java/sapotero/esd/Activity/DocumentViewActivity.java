package sapotero.esd.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import sapotero.esd.R;

public class DocumentViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Intent intent = getIntent();


    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_document_view);

    String id = intent.getStringExtra(DocumenListActivity.ID);
    String title = intent.getStringExtra(DocumenListActivity.TITLE);
    String description = intent.getStringExtra(DocumenListActivity.DESCRIPTION);
    String author = intent.getStringExtra(DocumenListActivity.AUTHOR);
    String image = intent.getStringExtra(DocumenListActivity.IMAGE);

    TextView mId = (TextView) findViewById(R.id.id);
    mId.setText( id );

    TextView mTitle = (TextView) findViewById(R.id.title);
    mTitle.setText( title );

    TextView mDescription = (TextView) findViewById(R.id.description);
    mDescription.setText( description );

    TextView mAuthor = (TextView) findViewById(R.id.author);
    mAuthor.setText( author );

    TextView mImage = (TextView) findViewById(R.id.image);
    mImage.setText( image );

    
  }
}
