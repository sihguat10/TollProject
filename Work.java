public class Work{ 
	private String position;
	private String org;
	private String startMonth;
  private String startYear;
	private String endMonth;
  private String endYear;
  private String description;
  //if its till present the month should be present so check if end_year is null
public Work () {
    /**/
    position    = null;
    org	         = null;
    startMonth  = null;
    startYear   = null;
    endMonth    = null;
    endYear     = null;
    description  = null;

  }
  /**/
  public String getPosition() {
    return position;
  }
  /**/
  public String getOrg() {
    return org;
  }
  /**/
  public String getStartMonth() {
    return startMonth;
  }
  /**/
  public String getStartYear() {
    return startYear;
  }
  /**/
  public String getEndMonth() {
    return endMonth;
  }
  /**/
  public String getEndYear() {
    return endYear;
  }
  /**/
  public String getDescription() {
    return description;
  }
  /**/
  public void setPosition( String position ) {
    this.position=position;
    return;
  }
  /**/
  public void setOrg( String org ) {
    this.org=org;
    return;
  }
  /**/
  public void setStartMonth( String startMonth ) {
    this.startMonth = startMonth;
    return;
  }
  /**/
  public void setStartYear( String startYear ) {
    this.startYear = startYear;
    return;
  }
  /**/
  public void setEndMonth( String endMonth ) {
    this.endMonth = endMonth;
    return;
  }
  /**/
  public void setEndYear( String endYear ) {
    this.endYear = endYear;
    return;
  }
    /**/
  public void setDescription( String description ) {
    this.description = description;
    return;
  }
}