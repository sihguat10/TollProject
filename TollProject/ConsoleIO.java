import java.io.BufferedReader;
import java.util.ArrayList;
/*
 *  This class contains static methods for printing and reading from
 *  the console, which methods are use by class DB.
 */
public class ConsoleIO {
  /**/
  private static final String NAME         = "NAME: ";
  private static final String POSITION     = "POSITION: ";
  private static final String EDUCATION    = "EDUCATION: ";
  private static final String BIO          = "BIO: ";
  private static final String CERTIFICATE  = "CERTIFICATE: ";
  private static final String EXPERTISE    = "EXPERTISE: ";
  private static final String SKILL        = "SKILLS: ";
  private static final String WORK      = "WORK EXPERIENCE: ";
  /*
   *  Sets up all the fields that will be printed out to the User.
   *
   * Return Value - String [] rv which contains all lines which will be printed
   *
   *  This method is called by:
   *  printRecord(int indexh)
   *  of class DB.
   */
  public static String [] printRecord ( Record r ) {
     ArrayList<String> als = new ArrayList<String>();
     als.add(NAME);
     als.add(r.getName());
     als.add(POSITION);
     als.add(r.getPosition());
     //
     Education [] educations = r.getEducation();
     als.add(EDUCATION);
     als.add(addEducationArray(educations));
     als.add(BIO);
     als.add(r.getBio());
     Certificate [] certificates = r.getCertificate();
     als.add(CERTIFICATE);
     als.add(addCertificateArray(certificates));
     String [] expertise = r.getExpertise();
     als.add(EXPERTISE);
     als.add(addStringArray(expertise));
     String [] skills = r.getSkills();
     als.add(SKILL);
     als.add(addStringArray(skills));
     Work [] work = r.getWork();
     als.add(WORK);
     als.add(addWorkArray(work));
    
     //
     int len = als.size();
     String [] rv = new String[len];
     for(int i=0; i<len; i++)rv[i]=als.get(i);

     return rv;
  }
  /*
   *  Print a Education [] e to the console.
   *
   *  label = e.g., NAME
   *
   *  Called by:
   *  printRecord(Record r)
   *  of this class.
   */
  private static String addEducationArray ( Education [] e) {
      String rv = "";
      if (e==null ) {
          return rv;
        }
    
      for(Education education: e) {
         rv+= education.getSchool()+ "\n";
         rv += education.getDegree()+","+education.getMajor() + "\n";
         rv += " \n";
        }
      return rv;
  }
    /*
   *  Print a Project [] s to the console.
   *
   *  label = e.g., NAME
   *
   *  Called by:
   *  printRecord(Record r)
   *  of this class.
   */
  private static String addWorkArray ( Work [] w) {
      String rv = "";
      if (w==null ) {
          return rv;
        }
    
      for (Work work: w) {
         rv += work.getPosition() +"\n";
         rv += work.getOrg() + "\n";
         rv += work.getStartMonth()+" "+work.getStartYear()+"-"+work.getEndMonth();
         if(work.getEndYear() != null){
          rv+= " "+work.getEndYear() + "\n";
         }
         else{
          rv += "\n";
         }
         rv += work.getDescription() + "\n";
         rv += " \n";
        }
      return rv;
  }
     /*
   *  Print a Certificate [] s to the console.
   *
   *  label = e.g., NAME
   *
   *  Called by:
   *  printRecord(Record r)
   *  of this class.
   */
  private static String addCertificateArray ( Certificate [] c) {
      String rv = "";
      if (c==null ) {
          return rv;
        }
      for (Certificate certificate: c) {
         rv += certificate.getOrg() + ", " + certificate.getTitle() + "\n";
        }
      return rv;
  }
  /*
   *  Print a String [] s to the console.
   *
   *  label = e.g., NAME
   *
   *  Called by:
   *  printRecord(Record r)
   *  of this class.
   */
  private static String addStringArray ( String [] s) {
      String rv = "";
      if (s==null ) {
          return rv;
        }
      
      for (int i=0; i<s.length; ++i) {
         rv += (s[i] + "\n");
        }
      return rv;
  }
}