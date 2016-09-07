package sapotero.esd.Model;


public class Document {

  private String id;
  private String author;
  private String title;
  private String description;
  private String image;
  private String createdAt;
  private String updatedAt;
  private String url;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Document(){}

  public Document( String id, String author, String title, String description, String image, String createdAt, String updatedAt, String url ) {
    super();
    this.id = id;
    this.author = author;
    this.title = title;
    this.description = description;
    this.image = image;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.url = url;
  }

  @Override
  public String toString() {
    return "Document [id=" + id + ","+
        "author=" + author + ","+
        "title=" + title + ","+
        "description=" + description + ","+
        "image=" + image + ","+
        "createdAt=" + createdAt + ","+
        "updatedAt=" + updatedAt + ","+
        "url=" + url + "]";
  }
}