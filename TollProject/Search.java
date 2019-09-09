import java.lang.*;
public class Search {
  /*
   *  Given Education [] e, return whether or not any of the elements of s
   *  contain token.
   */
  public boolean educationArrayContains ( Education [] e, String token ) {
   
    boolean figure=false;
    boolean figure1=false;
    boolean figure2=false;
    boolean figure3=false;
    for (Education education: e) {
        figure1 = stringContains(education.getSchool(),token);
        figure2 = stringContains(education.getDegree(),token);
        figure3 = stringContains(education.getMajor(),token);
        if(figure1 || figure2 || figure3){
          figure=true;
          break;
        }
    }
    return figure;
  }
    /*
   *  Given Certificate [] c, return whether or not any of the elements of s
   *  contain token.
   */
  public boolean certificateArrayContains ( Certificate [] c, String token ) {

    boolean figure=false;
    boolean figure1=false;
    boolean figure2=false;
    for (Certificate certificate: c) {
        figure1 = stringContains(certificate.getOrg(),token);
        figure2 = stringContains(certificate.getTitle(),token);
        if(figure1 || figure2){
          figure=true;
          break;
        }
    }
    return figure;
  }
      /*
   *  Given Project [] p, return whether or not any of the elements of s
   *  contain token.
   */
  public boolean workArrayContains ( Work [] w, String token ) {

    boolean figure=false;
    boolean figure1=false;
    boolean figure2=false;
    boolean figure3=false;
    boolean figure4=false;
    boolean figure5=false;
    boolean figure6=false;
    boolean figure7=false;
    for (Work work: w) {
        figure1 = stringContains(work.getPosition(),token);
        figure2 = stringContains(work.getOrg(),token);
        figure3 = stringContains(work.getStartMonth(),token);
        figure4 = stringContains(work.getStartYear(),token);
        figure5 = stringContains(work.getEndMonth(),token);
        figure6 = stringContains(work.getEndYear(),token);
        figure7 = stringContains(work.getDescription(),token);
        if(figure1 || figure2 || figure3|| figure4||figure5 || figure6 || figure7){
          figure=true;
          break;
        }
    }
    return figure;
  }
  /*
   *  Given String [] s, return whether or not any of the elements of s
   *  contain token.
   */
  public boolean stringArrayContains ( String [] s, String token ) {
     // Put required code here. This method should call
     // stringContains (String s,String token) of this class.
    if(s== null){
      return false;
    }
    boolean figure=false;
    for (int i=0;i<s.length;++i) {
        figure = stringContains(s[i],token);
        if(figure==true) break;
    }
    return figure;
  }
  // *  Given String s, return whether or not s contains token.
  public boolean stringContains ( String s, String token ) {
    if(s== null){
      return false;
    }
    if(s.contains(token)) return true;
    else return false;
  }
}