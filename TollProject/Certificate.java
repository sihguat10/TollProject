public class Certificate {
  /**/
  private String org;
  private String title;
  /**/
  public Certificate () {
    /**/
    this.org   = "";
    this.title  = "";
  }
  /**/
  public String getOrg() {
    return org;
  }
  /**/
  public String getTitle() {
    return title;
  }
  /**/
  /**/
  /**/
  public void setOrg( String org ) {
    this.org=org;
    return;
  }
  /**/
  public void setTitle( String title ) {
    this.title=title;
    return;
  }
}