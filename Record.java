public class Record {
 /*
  * This is the class for Record which is an
  * object with all the wanted fields for the
  * resume to display
  */
  private String name;
  private String position;
  private Education [] education;
  private String bio;
  private Certificate [] certificates;
  private String image;
  private String [] expertise; 
  private String [] skills;
  private Work [] work; 

  /**/
  public Record () {
    /**/
    name          = null;
    position      = null;
    education     = null;
    bio           = null;
    certificates  = null;
    image         = null;
    expertise     = null;
    skills        = null;
    work          = null;
  }
  /**/
  public String getName() {
    return name;
  }
  /**/
  public String getPosition() {
    return position;
  }
  /**/
  public Education [] getEducation() {
    return education;
  }
  /**/
  public String getBio() {
    return bio;
  }
  /**/
  public Certificate [] getCertificate() {
    return certificates;
  }
  /**/
  public String getImage() {
    return image;
  }
  /**/
  public String [] getExpertise() {
    return expertise;
  }
  /**/
  public String [] getSkills() {
    return skills;
  }
  /**/
  public Work [] getWork() {
    return work;
  }
  /**/
  /**/
  /**/
  public void setName( String s ) {
    name=s;
    return;
  }
  /**/
  public void setPosition( String s ) {
    position=s;
    return;
  }
  /**/
  public void setEducation( Education [] s ) {
    education=s;
    return;
  }
  /**/
  public void setBio( String s ) {
    bio=s;
    return;
  }
  /**/
  public void setCertificate( Certificate [] s ) {
    certificates=s;
    return;
  }
  /**/
  public void setImage( String s ) {
    image=s;
    return;
  }
  /**/
  public void setExpertise( String [] s ) {
    expertise=s;
    return;
  }
  /**/
   public void setSkills( String [] s ) {
    skills=s;
    return;
  }
  /**/
   public void setWork( Work [] s ) {
    work=s;
    return;
  }
}