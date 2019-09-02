import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.PrintWriter;
/**/
public class DB {
    /**/
    private static final String DB_NAME = "db.xml";
    private ArrayList<Record> recList;
    /**/
    /*
     *  Read the file DB_NAME into the private field ArrayList<Record> recList
     */
    public DB ( ) {
        /**/
        recList=DBIO.loadDB(DB_NAME);
    }
    public String[] getRecNumbers(){
        String [] recStrings = new String[recList.size()];
        for(int i=0; i<recList.size(); i++){
            Record r= recList.get(i);
            recStrings[i]= (i+1) + ": "+ r.getName();
        }
        return recStrings;
    }
    public Record getRecord(int index){
        return recList.get(index);
    }

    /*
     *  Print a record to the console
     */
    public String [] printRecord( int index ){

        /**/
        if(index > recList.size()){
            return null;
        }
        return ConsoleIO.printRecord( recList.get(index -1 ) );
        /**/
    
    }

    /*
     *  Add a record to the database
     */
    public void addRecord (Record r) {
        /**/
        recList.add(r);
        return;
    }

    /*
     *  Delete a record from database
     */
    public void deleteRecord (int index) {
        /**/
        if(index<recList.size()){
            recList.remove(index);
        }
        /**/
        return;
    }

    /*
     *  This method calls methods in the DBExport class which makes a resume file based on
     *  the Record object. 
     */
    public void exportRecords( ArrayList<Integer> numbers, String header){
        PrintWriter pw; 
        BufferedReader br;

        pw=IOUtil.openPW("Resumes.html");

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title> Resume </title>");
        pw.println("<link rel='stylesheet' type='text/css' href='pageStyle.css'/>");
        pw.println("</head>");
        pw.println("<body>");

        for(int num: numbers){
            br =IOUtil.openBR("template.html");
            DBExport.makeResume(recList.get(num-1), br, header, pw);
        }
        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    /*
     *  Perform a search on the records
     */
    public String [] tokenSearch( String look) {

        ArrayList<String> foundRecords= new ArrayList<String>();
        Search s = new Search();
        boolean found = false;

        for(int num=0; num < recList.size(); num++){
            Record r= new Record();
            r = recList.get(num);
            String name = r.getName();
            String position = r.getPosition();
            Education [] education= r.getEducation();
            String bio=r.getBio();
            Certificate [] certificates = r.getCertificate();
            String [] expertise=r.getExpertise();
            String [] skills=r.getSkills();
            Work [] work= r.getWork();

            boolean inName = s.stringContains(name,look);
            boolean inPosition = s.stringContains(position,look);

            boolean inEducation = s.educationArrayContains(education ,look);

            boolean inBio = s.stringContains(bio,look);

            boolean inCertificate = s.certificateArrayContains(certificates,look);

            boolean inExpertise = s.stringArrayContains(expertise,look);
            boolean inSkills = s.stringArrayContains(skills,look);
            boolean inProjects = s.workArrayContains(work,look);

            if( inName || inPosition || inEducation || inBio || inCertificate || inExpertise || inSkills || inProjects){
                foundRecords.add("Record Number:"+(num+1)+ "/Name:" + recList.get(num).getName());
                found = true;

            }
        }

        if(found){
                //search
            String [] foundRecordsArray= new String[foundRecords.size()];

            for(int i=0; i < foundRecords.size(); i++){
                foundRecordsArray[i]=foundRecords.get(i);
            }
            return foundRecordsArray;
        }
        else{
            return null;
        }
    }

    /*
     *  Save the modified database to the file DB_NAME
     */
    public void saveAndExit() {
        /**/
    
        DBIO.dumpDB(DB_NAME,recList);
        System.exit(0);
        /**/
        return;
    }
}