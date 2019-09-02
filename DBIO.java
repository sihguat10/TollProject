import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
/*
 *  This class contains static methods for reading
 *  and writing the database from/to our database(xml file).
 */
public class DBIO {
  /**/
  private static final String RECORD      = "<record>";
  private static final String END_RECORD  = "</record>";
  private static final String PAD_1       = " ";
  private static final String PAD_2       ="  ";
  /*
   *  These fields can consist of zero or one line
   */
  private static final String NAME        = "<name>";
  private static final String END_NAME    = "</name>";
  private static final String POSITION     = "<position>";
  private static final String END_POSITION = "</position>";
  private static final String IMAGE       = "<image>";
  private static final String END_IMAGE   = "</image>";
  /*
   *  These fields can consist of more than one line
   */
  private static final String BIO       = "<bio>";
  private static final String END_BIO   = "</bio>";
  public static final String EDUCATION        = "<education>";
  public static final String END_EDUCATION    = "</education>";
  public static final String CERTIFICATE       = "<certificate>";
  public static final String END_CERTIFICATE   = "</certificate>";
  public static final String EXPERTISE         = "<expertise>";
  public static final String END_EXPERTISE     = "</expertise>";
  public static final String SKILL         = "<skills>";
  public static final String END_SKILL     = "</skills>";
  public static final String WORK         = "<work>";
  public static final String END_WORK     = "</work>";
  /*
   *  Read the database from a file with name String fileName and return it
   *  as anArrayList<Record>.
   */
  public static ArrayList<Record> loadDB ( String fileName ) {
    BufferedReader br = IOUtil.openBR(fileName);
    ArrayList<Record> recList = new ArrayList<Record>();
    while (true) {
      Record r = readRecordFromFile(br);
      if(r == null) break;
      
      recList.add(r);
        
    }
    IOUtil.closeBR(br);
    return recList;
  }
  /*
   *  Write the database ArrayList<Record> db to a file with name String fileName.
   */
  public static void dumpDB ( String fileName, ArrayList<Record> db ) {
    PrintWriter pw;
    /**/
    pw=IOUtil.openPW(fileName);
    /**/
    for(int i=0; i<db.size(); ++i){
      writeRecordToFile(pw, db.get(i));
    }
    /**/
    pw.close();
    /**/
    return;
  }
  /*
   *  Read a Record with the BufferedReader and return it.
   */
  private static Record readRecordFromFile ( BufferedReader fileBR ) {
    Record rv=new Record();
    String line=null;
    LineObject lo;
    lo=IOUtil.readLine(fileBR);
    line = lo.line();
    if(lo.eof())return null;
    if(!line.trim().equals(RECORD)){
      System.exit(0);
    }
    rv.setName( readStringFromFile(fileBR, NAME,    END_NAME));
    rv.setPosition( readStringFromFile(fileBR, POSITION,    END_POSITION));
    rv.setEducation( readEducationArrayFromFile(fileBR, EDUCATION,    END_EDUCATION));
    rv.setBio( readBioFromFile(fileBR, BIO,    END_BIO));
    rv.setCertificate( readCertificateArrayFromFile(fileBR, CERTIFICATE,    END_CERTIFICATE));
    rv.setImage(readStringFromFile(fileBR, IMAGE, END_IMAGE));
    rv.setExpertise( readStringArrayFromFile(fileBR, EXPERTISE,    END_EXPERTISE));
    rv.setSkills( readStringArrayFromFile(fileBR, SKILL,    END_SKILL));
    rv.setWork( readWorkArrayFromFile(fileBR, WORK,    END_WORK));
    line=IOUtil.readLine(fileBR).line();
    if(!line.trim().equals(END_RECORD)){

  
      System.exit(0);
    }
    return rv;
 
  }
  /*
   *  Read a Education [] with the BufferedReader and return it.
   *
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static Education [] readEducationArrayFromFile (
    BufferedReader fileBR, String beg, String end
  ) {
    ArrayList<Education> ale = new ArrayList<Education> ();
    String line;
    Education [] rv;
    int len=0;
    LineObject lo = IOUtil.readLine(fileBR);

    line=lo.line();
    if( !(line.trim().equals(beg))){
      System.out.println();
      System.out.println("\""+ beg+ "\" expected, but \"" + line + "\" found.");
      System.exit(0);
    }
    //makes sure to divide it into 3 sections for each education object
    line=null;
    int i=0;

    Education singleE= new Education();
    while(true){
      if(i==3){
        ale.add(singleE);
        singleE = new Education(); 
      }
      lo=IOUtil.readLine(fileBR);
      line=(lo.line()).trim();
      if(line.equals(end)) break;
      if(line.equals("")){
        i=0;
        continue;
      }
      if(i==0){
        singleE.setSchool(line);
      }
      if(i==1){
        singleE.setDegree(line);
      }
      if(i==2){
        singleE.setMajor(line);
      }
      i++;
    }

    len=ale.size();
    rv=new Education[len];
    for(int j=0; j<len; ++j) rv[j]=ale.get(j);
    return rv;
  }
   /*
   *  Read a Certificate [] with the BufferedReader and return it.
   *
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static Certificate [] readCertificateArrayFromFile (
    BufferedReader fileBR, String beg, String end
  ) {
    ArrayList<Certificate> alc = new ArrayList<Certificate> ();
    String line;
    Certificate [] rv;
    int len = 0;
    LineObject lo = IOUtil.readLine(fileBR);

    line=lo.line();
    if( !(line.trim().equals(beg))){
      System.out.println();
      System.out.println("\""+ beg+ "\" expected, but \"" + line + "\" found.");
      System.exit(0);
    }
  
    line=null;
    Certificate singleC = new Certificate();
    int i=0;

    while(true){
      lo=IOUtil.readLine(fileBR);
      line=lo.line().trim();
      if(line.equals(end)) break;
      if(line.equals("")){
        i=0;
        alc.add(singleC);
        singleC= new Certificate(); 
        continue;
      }
      if(i==0){
        singleC.setOrg(line);
      }
      if(i==1){
        singleC.setTitle(line);
      }
      i++;
    }

    len=alc.size();
    rv=new Certificate[len];
    for(int j=0; j<len; ++j) rv[j]=alc.get(j);
    return rv;
  }
  /*
   *  Read a Certificate [] with the BufferedReader and return it.
   *
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static Work [] readWorkArrayFromFile (
    BufferedReader fileBR, String beg, String end
  ) {
    ArrayList<Work> alw = new ArrayList<Work> ();
    String line;
    Work [] rv;
    Work singleW = new Work();
    int len = 0;
    LineObject lo = IOUtil.readLine(fileBR);

    line=(lo.line()).trim();
    if( !(line.trim().equals(beg))){
      System.exit(0);
    }
    //makes sure to divide it into 3 sections for each education object
    line=null;
    int i=0;
    String description="";
    while(true){

      lo=IOUtil.readLine(fileBR);
      line=lo.line().trim();
      if(line.equals(end)) break;
      else if(i==0){
        singleW.setPosition(line);
      }
      else if(i==1){
        singleW.setOrg(line);
      }
      else if(i==2){
        singleW.setStartMonth(line);
      }
      else if(i==3){
        singleW.setStartYear(line);
      }
      else if(i==4){
        singleW.setEndMonth(line);
      }
      else if(i==5){
        if(!(line.equals("null"))){
        singleW.setEndYear(line);
        }
      }
      else if(line.equals("")){
        //convert to list
        singleW.setDescription(description);
        description = description.substring(0,description.length()-1);
        alw.add(singleW);
        description = "";

        singleW=new Work(); 
        i=0;
        continue;
      }
      else{
        description+=(line + "\n");
      }
      i++;
    }

    len=alw.size();
    rv=new Work[len];
    for(int j=0; j<len; ++j) rv[j]=alw.get(j);
    return rv;
  }
  /*
   *  Read a String [] with the BufferedReader and return it.
   *
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static String [] readStringArrayFromFile (
    BufferedReader fileBR, String beg, String end
  ) {
    ArrayList<String> als;
    String line;
    String [] rv;
    int len=0;
    LineObject lo = IOUtil.readLine(fileBR);
    
    als=new ArrayList<String> ();

    line=(lo.line()).trim();
    if( !(line.trim().equals(beg))){
      System.out.println();
      System.out.println("\""+ beg+ "\" expected, but \"" + line + "\" found.");
      System.exit(0);
    }
    line=null;
    while(true){
      lo=IOUtil.readLine(fileBR);
      line=lo.line().trim();
      if(line.equals(end)) break;
      if(line.equals("")){continue;}
      als.add(line);
    }
    len=als.size();
    rv=new String[len];
    for(int i=0; i<len; ++i) rv[i]=als.get(i);
    return rv;
  }
  /*
   *  Read the Bio with the BufferedReader and return it.
   *
   *  beg = beginning tag, e.g., CITY
   *  end = ending tag, e.g., END_CITY
   */
  private static String readBioFromFile (
    BufferedReader fileBR, String beg, String end
  ) {
    ArrayList<String> als;
    String line;
    String rv;
    int len=0;
    LineObject lo = IOUtil.readLine(fileBR);
    
    als=new ArrayList<String> ();

    line=(lo.line()).trim();
    if( !(line.trim().equals(beg))){
      System.out.println();
      System.out.println("\""+ beg+ "\" expected, but \"" + line + "\" found.");
      System.exit(0);
    }
    line=null;
    while(true){
      lo=IOUtil.readLine(fileBR);
      line=lo.line().trim();
      if(line.equals(end)) break;
      if(line.equals("")){continue;}
      als.add(line);
    }
    len=als.size();
    rv="";
    for(int i=0; i<len; ++i){
      rv+=als.get(i);
      rv += "\n";
      rv += "\n";
    }
    return rv;
    
  }
  /*
   *  Read a String with the BufferedReader and return it.
   *
   *  beg = beginning tag, e.g., CITY
   *  end = ending tag, e.g., END_CITY
   */
  private static String readStringFromFile (
    BufferedReader fileBR, String beg, String end
  ) {
    String line, rv;
    rv=null;

    line=IOUtil.readLine(fileBR).line();
    line = line.trim();
    if( !(line.equals(beg))){
      System.out.println();
      System.out.println("\""+ beg+ "\" expected, but \"" + line + "\" found.");
      System.exit(0);
    }
    rv=IOUtil.readLine(fileBR).line().trim();
    line=IOUtil.readLine(fileBR).line();
    return rv;
    
  }
  /*
   *  Write Record r with the PrintWriter.
   */
  private static void writeRecordToFile ( PrintWriter pw, Record r ) {
    pw.println(RECORD);
    writeStringToFile(pw,r.getName(), NAME, END_NAME);
    writeStringToFile(pw,r.getPosition(), POSITION, END_POSITION);
    writeEducationArrayToFile(pw,r.getEducation(), EDUCATION, END_EDUCATION);
    writeStringToFile(pw,r.getBio(), BIO, END_BIO);
    writeCertificateArrayToFile(pw,r.getCertificate(), CERTIFICATE, END_CERTIFICATE);
    writeStringToFile(pw,r.getImage(), IMAGE, END_IMAGE);
    writeStringArrayToFile(pw,r.getExpertise(), EXPERTISE, END_EXPERTISE);
    writeStringArrayToFile(pw,r.getSkills(), SKILL, END_SKILL);
    writeWorkArrayToFile(pw,r.getWork(), WORK, END_WORK);
    pw.println(END_RECORD);
  }
    /*
   *  Write the Education [] and its associated tags with the PrintWriter.
   *  e = Educations the persone had
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static void writeEducationArrayToFile (
    PrintWriter pw, Education [] e, String beg, String end
  ) {
    int len=0;
    pw.println(PAD_1 + beg);
    
    for(Education education: e){
      pw.println(PAD_2 + education.getSchool());
      pw.println(PAD_2 + education.getDegree());
      pw.println(PAD_2 + education.getMajor());
    }
    pw.println(PAD_1 + end);
  }
      /*
   *  Write the Certificate [] and its associated tags with the PrintWriter.
   *  c = Certificates the persone had
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static void writeCertificateArrayToFile (
    PrintWriter pw, Certificate [] c, String beg, String end
  ) {
    int len=0;
    pw.println(PAD_1 + beg);
    
    for(Certificate certificate: c){
      pw.println(PAD_2 + certificate.getOrg());
      pw.println(PAD_2 + certificate.getTitle());
      pw.println(PAD_2);
    }
    pw.println(PAD_1 + end);
  }
        /*
   *  Write the Projects [] and its associated tags with the PrintWriter.
   *  p = Certificates the persone had
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static void writeWorkArrayToFile (
    PrintWriter pw, Work [] w, String beg, String end
  ) {
    pw.println(PAD_1 + beg);
    
    for(Work work: w){
      pw.println(PAD_2 + work.getPosition());
      pw.println(PAD_2 + work.getOrg());
      pw.println(PAD_2 + work.getStartMonth());
      pw.println(PAD_2 + work.getStartYear());
      pw.println(PAD_2 + work.getEndMonth());
      if(work.getEndYear()!= null){
        pw.println(PAD_2 + work.getEndYear());
      }
      else{
        pw.println(PAD_2 + "null");
      }
      String [] bullets = (work.getDescription()).split("\n");
      for(String bullet: bullets){
        System.out.println(bullet);
        pw.println(PAD_2 + bullet);
      }
      pw.println(PAD_2);
    }
    pw.println(PAD_1 + end);
  }
  /*
   *  Write the String [] and its associated tags with the PrintWriter.
   *
   *  beg = beginning tag, e.g., NAME
   *  end = ending tag, e.g., END_NAME
   */
  private static void writeStringArrayToFile (
    PrintWriter pw, String [] s, String beg, String end
  ) {
    int len=0;
    pw.println(PAD_1 +beg);

    if(s != null ){
      len=s.length;
      for(int i=0; i<len; ++i)pw.println(PAD_2 + s[i]);
    }
    
    pw.println(PAD_1 + end);
  }
  /*
   *  Write the String and its associated tags with the PrintWriter.
   *
   *  beg = beginning tag, e.g., CITY
   *  end = ending tag, e.g., END_CITY
   */
  private static void writeStringToFile (
    PrintWriter pw, String s, String beg, String end
  ) {
    int len=0;
    pw.println(PAD_1 +beg);
    if(!(s==null)) pw.println(PAD_2 + s);
    pw.println(PAD_1 + end);
  }
}
