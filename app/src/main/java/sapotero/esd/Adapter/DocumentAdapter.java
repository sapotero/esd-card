package sapotero.esd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

import sapotero.esd.Activity.DocumentListActivity;
import sapotero.esd.Activity.DocumentViewActivity;
import sapotero.esd.Model.Document;
import sapotero.esd.R;
import sapotero.esd.View.DocumentViewHolder;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentViewHolder> {
  private List<Document> documentList;
  private Context mContext;

  public DocumentAdapter(Context context, List<Document> documentList) {
    this.documentList = documentList;
    this.mContext = context;
  }

  @Override
  public DocumentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

    DocumentViewHolder viewHolder = new DocumentViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(DocumentViewHolder documentViewHolder, int i) {
    Document document = documentList.get(i);

    //Download image using picasso library
    Picasso.with(mContext).load(document.getImage())
        .error(R.drawable.placeholder)
        .placeholder(R.drawable.placeholder)
        .into(documentViewHolder.imageView);

    //Setting text view title
    documentViewHolder.textView.setText(Html.fromHtml(document.getTitle()));

    documentViewHolder.textView.setOnClickListener(clickListener);
    documentViewHolder.imageView.setOnClickListener(clickListener);

    documentViewHolder.textView.setTag(documentViewHolder);
    documentViewHolder.imageView.setTag(documentViewHolder);
  }

  View.OnClickListener clickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      DocumentViewHolder holder = (DocumentViewHolder) view.getTag();
      int position = holder.getPosition();

      Document document = documentList.get(position);
      Toast.makeText(mContext, document.getTitle(), Toast.LENGTH_SHORT).show();

      Intent intent = new Intent(mContext, DocumentViewActivity.class);
      intent.putExtra(DocumentListActivity.ID, document.getId());
      intent.putExtra(DocumentListActivity.TITLE, document.getTitle());
      intent.putExtra(DocumentListActivity.DESCRIPTION, document.getDescription());
      intent.putExtra(DocumentListActivity.AUTHOR, document.getAuthor());
      intent.putExtra(DocumentListActivity.IMAGE, document.getImage());
      mContext.startActivity(intent);
    }
  };

  @Override
  public int getItemCount() {
    return (null != documentList ? documentList.size() : 0);
  }
}