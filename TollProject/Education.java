public class Education{ 

	private String school;
	private String degree;
	private String major;

public Education () {
    /**/
    school    = null;
    degree    = null;
    major     = null;
  }
  /**/
  public String getSchool() {
    return school;
  }
  /**/
  public String getDegree() {
    return degree;
  }
  /**/
  public String getMajor() {
    return major;
  }
  /**/
  public void setSchool( String s ) {
    school=s;
    return;
  }
  /**/
  public void setDegree( String s ) {
    degree=s;
    return;
  }
  /**/
  public void setMajor( String s ) {
    major=s;
    return;
  }
}