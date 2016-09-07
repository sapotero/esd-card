package sapotero.esd.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import sapotero.esd.Model.Document;

public class SQLiteHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "DocumentDB";

  private static final String TABLE_BOOKS = "documents";

  private static final String KEY_ID = "id";
  private static final String KEY_AUTHOR = "author";
  private static final String KEY_TITLE = "title";
  private static final String KEY_DESCRIPTION = "description";
  private static final String KEY_IMAGE = "image";
  private static final String KEY_CREATEDAT = "createdAt";
  private static final String KEY_UPDATEDAT = "updatedAt";
  private static final String KEY_URL = "url";

  private static final String[] COLUMNS = {
      KEY_ID,
      KEY_AUTHOR,
      KEY_TITLE,
      KEY_DESCRIPTION,
      KEY_IMAGE,
      KEY_CREATEDAT,
      KEY_UPDATEDAT,
      KEY_URL
  };

  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String CREATE_DOCUMENT_TABLE = "CREATE TABLE documents ( " +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "author TEXT, "+
        "title TEXT, "+
        "description TEXT, "+
        "image TEXT, "+
        "createdAt TEXT, "+
        "updatedAt TEXT, "+
        "url TEXT )";
    db.execSQL(CREATE_DOCUMENT_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS documents");
    this.onCreate(db);
  }

  public void addDocument(Document document){

    Log.d("addBook", document.toString());

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_ID, document.getId());
    values.put(KEY_AUTHOR, document.getAuthor());
    values.put(KEY_TITLE, document.getTitle());
    values.put(KEY_DESCRIPTION, document.getDescription());
    values.put(KEY_IMAGE, document.getImage());
    values.put(KEY_CREATEDAT, document.getCreatedAt());
    values.put(KEY_UPDATEDAT, document.getUpdatedAt());
    values.put(KEY_URL, document.getUrl());

    db.insert(TABLE_BOOKS,
        null, //nullColumnHack
        values);
    db.close();
  }

  public Document getDocument(int id){

    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor =
        db.query(TABLE_BOOKS, // a. table
            COLUMNS, // b. column names
            " id = ?", // c. selections
            new String[] { String.valueOf(id) }, // d. selections args
            null, // e. group by
            null, // f. having
            null, // g. order by
            null); // h. limit

    if (cursor != null){
      cursor.moveToFirst();
    }

    Document document = new Document();
    document.setId(          cursor.getString(0) );
    document.setAuthor(      cursor.getString(1) );
    document.setTitle(       cursor.getString(3) );
    document.setDescription( cursor.getString(4) );
    document.setImage(       cursor.getString(5) );
    document.setCreatedAt(   cursor.getString(6) );
    document.setUpdatedAt(   cursor.getString(7) );
    //log
    Log.d("getDocument("+id+")", document.toString());

    return document;
  }

  public List<Document> getAllDocuments() {
    List<Document> documents = new LinkedList<Document>();

    String query = "SELECT * FROM " + TABLE_BOOKS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    Document document = null;
    if (cursor.moveToFirst()) {
      do {
        document = new Document();
        document.setId(          cursor.getString(0) );
        document.setAuthor(      cursor.getString(1) );
        document.setTitle(       cursor.getString(3) );
        document.setDescription( cursor.getString(4) );
        document.setImage(       cursor.getString(5) );
        document.setCreatedAt(   cursor.getString(6) );
        document.setUpdatedAt(   cursor.getString(7) );

        documents.add(document);
      } while (cursor.moveToNext());
    }

    Log.d("getAllDocument()", documents.toString());

    return documents;
  }

  public int updateDocument(Document document) {

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_ID, document.getId());
    values.put(KEY_AUTHOR, document.getAuthor());
    values.put(KEY_TITLE, document.getTitle());
    values.put(KEY_DESCRIPTION, document.getDescription());
    values.put(KEY_IMAGE, document.getImage());
    values.put(KEY_CREATEDAT, document.getCreatedAt());
    values.put(KEY_UPDATEDAT, document.getUpdatedAt());
    values.put(KEY_URL, document.getUrl());

    int i = db.update(TABLE_BOOKS, //table
        values, // column/value
        KEY_ID+" = ?", // selections
        new String[] { String.valueOf(document.getId()) });

    db.close();
    return i;
  }

  public void deleteDocument(Document document) {

    SQLiteDatabase db = this.getWritableDatabase();

    db.delete(TABLE_BOOKS,
        KEY_ID+" = ?",
        new String[] { String.valueOf(document.getId()) });
    db.close();

    Log.d("deleteDocument", document.toString());

  }
}