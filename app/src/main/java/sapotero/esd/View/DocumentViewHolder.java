package sapotero.esd.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sapotero.esd.R;

public class DocumentViewHolder extends RecyclerView.ViewHolder {
  public ImageView imageView;
  public TextView textView;

  public DocumentViewHolder(View view) {
    super(view);
    this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
    this.textView = (TextView) view.findViewById(R.id.title);
  }
}