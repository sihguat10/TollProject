import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.lang.*;

/*
 *  This class contains static methods for printing and reading from
 *  the console, which methods are use by class DB.
 */
public class DBExport {
  /**/
  private static final String NAME        = "<!--name-->";
  private static final String END_NAME    = "<!--/name-->";
  private static final String POSITION     = "<!--position-->";
  private static final String END_POSITION = "<!--/position-->";
  private static final String BIO       = "<!--bio-->";
  private static final String END_BIO   = "<!--/bio-->";
  private static final String HEADER       = "<!--header-->";
  private static final String END_HEADER   = "<!--/header-->";
  private static final String IMAGE       = "<!--image-->";
  private static final String END       = "<!--end-->";
  /*
   *  These fields can consist of zero lines or one line
   */
   public static final String EDUCATION        = "<!--education-->";
  public static final String END_EDUCATION    = "<!--/education-->";
  public static final String CERTIFICATE       = "<!--certificate-->";
  public static final String END_CERTIFICATE   = "<!--/certificate-->";
  public static final String EXPERTISE         = "<!--expertise-->";
  public static final String END_EXPERTISE     = "<!--/expertise-->";
  public static final String SKILL         = "<!--skills-->";
  public static final String END_SKILL     = "<!---/skills-->";
  public static final String WORKSUMMARY          = "<!--work_summary-->";
  public static final String END_WORKSUMMARY     = "<!--/work_summary-->";
  public static final String WORK          = "<!--work-->";
  public static final String END_WORK     = "<!--/work-->";

  public static final int MAX             = 1400;
  public static final int HEADERCOUNT = 120;
  public static final int BIOMAX             = 1635;
  /*
   *  Print Record r to the console.
   *
   *  This method is called by:
   *  printRecord(BufferedReader consoleBR)
   *  of class DB.
   */
  public static void makeResume( Record r, BufferedReader br, PrintWriter pw){
    /**/
    /**/
      writeRecordToFile(pw,br,r);
    /**/
    return;
  }
  public static void writeRecordToFile( PrintWriter pw, BufferedReader fileBR, Record r){
    String line;
    LineObject lo;

    String certifications = " (";
    Certificate [] certificates=r.getCertificate();
    String [] parts;
    String abreviation;
    Certificate c;
    int i=0;

     c=certificates[i];
     parts = (c.getTitle()).split(" ");
     abreviation= "";
     for(int j=0; j<parts.length; j++){
      abreviation += (parts[j]).substring(0,1);
     } 
     certifications+=abreviation;
     i++;
    while( i < certificates.length ){
     certifications+=", ";
     c=certificates[i];
     parts = (c.getTitle()).split(" ");
     abreviation= "";
     for(int j=0; j<parts.length; j++){
      abreviation += (parts[j]).substring(0,1);
     } 
     certifications+=abreviation;
     i++;
    }
    certifications+= ")";
    
    changeTagInFile(NAME, END_NAME,fileBR,pw, (r.getName()) + certifications);
    changeTagInFile(POSITION, END_POSITION, fileBR, pw, r.getPosition());
    swapTagInFile(IMAGE, fileBR, pw, r.getImage());
    changeEducationInFile(EDUCATION, END_EDUCATION, fileBR, pw, r.getEducation());
    changeCertificateInFile(CERTIFICATE, END_CERTIFICATE, fileBR,pw, r.getCertificate());
    changeStringArrayInFile(SKILL, END_SKILL,fileBR,pw, r.getSkills());
    changeStringArrayInFile(EXPERTISE, END_EXPERTISE, fileBR,pw, r.getExpertise());
    changeWorkSummaryInFile(WORKSUMMARY, END_WORKSUMMARY, fileBR,pw, r.getWork());
    changeTagInFile(BIO, END_BIO, fileBR,pw, r.getBio());
    changeTagInFile(NAME, END_NAME,fileBR,pw, (r.getName()) + certifications);
    changeTagInFile(POSITION, END_POSITION, fileBR, pw, r.getPosition());
    changeWorkInFile(WORK, END_WORK, fileBR,pw, r.getWork());

    lo=IOUtil.readLine(fileBR);
    line = lo.line();
    while(!(line.trim().equals(END))){
      pw.println(line);
      lo=IOUtil.readLine(fileBR);
      line=lo.line();
    }

  }
  /*
  * This method givcen the Tags you want to replace (beg,end) it will go into the html file and 
  * copy everything over until it finds the desired place holder. It replaces it with "input".
  *
  *       String beg = the palceholder in the html file that tells us where to put in our input
  *       String end = the placeholder in the html file that indicates the end of our input
  */
  public static void changeTagInFile(String beg, String end, BufferedReader fileBR,PrintWriter pw, String input){
    String line=null;
    LineObject lo = IOUtil.readLine(fileBR);
    line=lo.line();

    while(!(line.trim().equals(beg))){
      pw.println(line);
      lo=IOUtil.readLine(fileBR);
      line=lo.line();

    }
    pw.println(beg);
    pw.println(input);
    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    if(line.trim().equals(end)){
      pw.println(line);
    }
    else{
      System.out.println("Error with template placeholder");
    }
  }
  /*
  * This method given the Tag you want to replace (token) it will go into the html file and 
  * swap the input for token everything over until it finds the desired place holder. 
  * It replaces it with "input".
  *
  *       String token = the placeholder in the html file that tells us where to put in our input
  *   
  */
  public static void swapTagInFile(String token,  BufferedReader fileBR,PrintWriter pw, String input){
    String line=null;
    LineObject lo = IOUtil.readLine(fileBR);
    line=lo.line();

    while(!(line.trim().equals(token))){
      pw.println(line);
      lo=IOUtil.readLine(fileBR);
      line=lo.line();

    }
    pw.println(input);
  }
  /*
  * This is in charge of printing the education list into the html file.
  *
  */
  public static void changeEducationInFile( String beg, String end,BufferedReader fileBR, PrintWriter pw, Education [] e){
    String line=null;
    LineObject lo = IOUtil.readLine(fileBR);
    line=lo.line();

      while(!(line.trim().equals(beg))){
        pw.println(line);
        lo=IOUtil.readLine(fileBR);
        line=lo.line();
      }

      pw.println(beg);
    for(Education education:e){
     pw.println("<b>");
      pw.println(education.getSchool());
      pw.println("</b>");
      pw.println("<br>");
      pw.println(education.getDegree() + ", "+ education.getMajor());
      pw.println("<br>");
  
    }
    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    if(line.trim().equals(end)){
      pw.println(line);
    }
    else{
      System.out.println("Error with template placeholder");
    }
  }
   /*
  * This is in charge of printing the certificate list into the html file.
  *
  */
  public static void changeCertificateInFile( String beg, String end,BufferedReader fileBR, PrintWriter pw, Certificate [] c){
    String line=null;
    LineObject lo = IOUtil.readLine(fileBR);
    line=lo.line();

      while(!(line.trim().equals(beg))){
        pw.println(line);
        lo=IOUtil.readLine(fileBR);
        line=lo.line();
      }
      pw.println(beg);
    for(Certificate certificate:c){
      pw.println("<b>");
      pw.println(certificate.getOrg());
      pw.println("</b>");
      pw.println("<br>");
      pw.println(certificate.getTitle());
      pw.println("<br>");
  
    }

    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    if(line.trim().equals(end)){
      pw.println(line);
    }
    else{
      System.out.println("Error with template placeholder");
    }
  }
   /*
  * This is in charge of printing the strinf list into the html file.
  *
  */
  public static void changeStringArrayInFile( String beg, String end,BufferedReader fileBR, PrintWriter pw, String [] s){
    String line=null;
    LineObject lo = IOUtil.readLine(fileBR);
    line=lo.line();

      while(!(line.trim().equals(beg))){
        pw.println(line);
        lo=IOUtil.readLine(fileBR);
        line=lo.line();
      }
    pw.println(beg);
    for(String string:s){
      pw.println(string);
      pw.println("<br>");
      
    }

    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    if(line.trim().equals(end)){
      pw.println(line);
    }
    else{
      System.out.println("Error with template placeholder");
    }
  }
   /*
  * This is in charge of printing the work summary list into the html file(page 1).
  *
  */
  public static void changeWorkSummaryInFile( String beg, String end,BufferedReader fileBR, PrintWriter pw, Work [] w){
    String line = null;
    LineObject lo = IOUtil.readLine(fileBR);
    line=lo.line();

      while(!(line.trim().equals(beg))){
        pw.println(line);
        lo=IOUtil.readLine(fileBR);
        line=lo.line();
      }
    pw.println(beg);
    for(Work work:w){
      String startYear = (work.getStartYear()).trim();
      String startMonth = (work.getStartMonth()).trim();
      String org = (work.getOrg()).trim();
      if(org.equals("")){
        continue;
      }
        String endYear = work.getEndYear();
        if( endYear==null || (endYear.equals(""))){
          if(!(startYear.equals(""))){
            pw.println(work.getOrg() + ", "+ startYear+ "-"+ "Present");
          }
          else{
            pw.println(work.getOrg());
          }
        }
        else{
          if(!(startYear.equals(""))){
            pw.println(work.getOrg() + ", "+ startYear+ "-" + work.getEndYear()); 
          }
          else{
            pw.println(work.getOrg());
          }
        }

        pw.println("<br>");

    }

    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    if(line.trim().equals(end)){
      pw.println(line);
    }
    else{
      System.out.println("Error with template placeholder.");
    }
  }
   /*
  * This is in charge of printing the work list into the html file.(page 2)
  *
  */
  public static void changeWorkInFile( String beg, String end, BufferedReader fileBR, PrintWriter pw, Work [] w){
    
    String line=null;
    LineObject lo = IOUtil.readLine(fileBR);
    line = lo.line();
    int endingIndex=0;
    //this gets to the left containter in template
while(!(line.trim().equals(beg))){
      pw.println(line);
      lo=IOUtil.readLine(fileBR);
      line=lo.line();

    }
    pw.println(beg);

    /*
    * This will be in charger of printing in the left containter
    *
    */
    int wordCount = 0;
    int i=0;
    
    while(i< w.length){
      wordCount += HEADERCOUNT;
      Work work = w[i];
      String [] bullets = (work.getDescription()).split("\n");
      for(String bullet:bullets){
        wordCount+= bullet.length();
      }
      if(wordCount<MAX){
        String datesLEFT;
        String startYear = (work.getStartYear()).trim();
        String startMonth = (work.getStartMonth()).trim();
        if((startYear.equals(""))){
          addWork(pw, work.getPosition(), work.getOrg(), "", bullets);
        }
        else{
          if(work.getEndYear()!=null){
            datesLEFT = work.getStartMonth()+ " "+ work.getStartYear()+ " - "+ work.getEndMonth()+ " "+ work.getEndYear();
          }
          else{
            datesLEFT = work.getStartMonth()+ " "+ work.getStartYear()+ " - "+ work.getEndMonth();
          }
          addWork(pw, work.getPosition(), work.getOrg(), datesLEFT, bullets);
        }
      }
      else{
        break;
      }
      i++;
    }

    //code to get to right container 
    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    while(!(line.trim().equals(beg))){
      pw.println(line);
      lo=IOUtil.readLine(fileBR);
      line=lo.line();
    }
    pw.println(beg);


    /*
    * This will be in charge of printing in the right containter
    *
    */
    wordCount = 0;
     while(i< w.length){
      wordCount += HEADERCOUNT;
      Work work = w[i];
      String [] bullets = (work.getDescription()).split("\n");
      for(String bullet:bullets){
        wordCount+= bullet.length();
      }
      if(wordCount<MAX){
        String datesRIGHT;
        String startYear = (work.getStartYear()).trim();
        String startMonth = (work.getStartMonth()).trim();
        if((startYear.equals(""))){
          addWork(pw, work.getPosition(), work.getOrg(), "", bullets);
        }
        else{
          if(work.getEndYear()!=null){
            datesRIGHT = work.getStartMonth()+ " "+ work.getStartYear()+ " - "+ work.getEndMonth()+ " "+ work.getEndYear();
          }
          else{
            datesRIGHT = work.getStartMonth()+ " "+ work.getStartYear()+ " - "+ work.getEndMonth();
          }
          addWork(pw, work.getPosition(), work.getOrg(), datesRIGHT, bullets);
        }
      }
      else{
        break;
      }
      i++;
    }
    //finish off the rest
    lo=IOUtil.readLine(fileBR);
    line=lo.line();
    if(line.trim().equals(end)){
      pw.println(line);
    }
    else{
      System.out.println("Error with template placeholder");
    }

  }
  public static void addWork(PrintWriter pw, String position, String org, String dates, String [] bullets){
    pw.println("<font color=#386ca2>");
    pw.println("<br>");
    pw.println("<b>");
    pw.println(position);
    pw.println("</b>");
    pw.println("<br>");
    if(!org.equals("")){
      pw.println("<i>");
      pw.println(org);
      pw.println("</i>");
      pw.println("<br>");
    }
    if(!dates.equals("")){
      pw.println(dates);
      pw.println("<br>");
    }
    pw.println("</font>");
    for(String bullet: bullets){
      pw.println("<li>");
      pw.println(bullet);
      pw.println("</li>");
    }   
  }
}