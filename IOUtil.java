import java.io.*;
import java.util.ArrayList;
/*
 *  This class has convenience static methods for file I/O
 *  which throw no exceptions.
 */
public class IOUtil {
  /**/
  public IOUtil ( ) { }
  /*
   *  Create a BufferedReader connected to file inputFile
   */
  public static BufferedReader openBR ( String inputFileName ) {
    /**/
    File inputFile=new File(inputFileName);
    BufferedReader br=null;
    try {
      br=new BufferedReader ( new FileReader (inputFile) );
    }
    catch ( FileNotFoundException fnfe ) {
      System.out.println();
      System.out.println("IOUtil.open:");
      System.out.println("Could not open " + inputFileName + " for input.");
      System.exit(0); 
    }
    /**/
    return br;
  } 
  /*
   *  Close a BufferedReader br
   */
  public static void closeBR ( BufferedReader br ) {
    /**/
    try { br.close(); }
    catch ( IOException ioe ) { }
    /**/
    return;
  }
  /*
   *  Open a PrintWriter connected to outputFileName
   */
  public static PrintWriter openPW ( String outputFileName ) {
    /**/
    File outputFile=new File(outputFileName);
    PrintWriter pw=null;
    try {
      pw=new PrintWriter (outputFile);
    }
    catch ( FileNotFoundException fnfe ) {
      System.out.println();
      System.out.println("IOUtil.open:");
      System.out.println("Could not open " + outputFileName + " for output.");
      System.exit(0); 
    }
    /**/
    return pw;
  }
  /*
   *  Reads a line of input from the input stream
   */
  public static LineObject readLine ( BufferedReader br ) {
    /**/
    String line=null;
    try {
      line=br.readLine();
    }
    catch ( IOException ioe ) {
      System.out.println();
      System.out.println("IOUtil.readLine:");
      System.out.println("IOException reading from input stream.");
      System.exit(0);
    }
    /**/
    LineObject lo;
    if ( line == null ) lo=new LineObject(true, null);
    else                lo=new LineObject(false,line);
    /**/
    return lo;
  }
  /*
   *  Tokenize the input line != null. Tokens are separated by an
   *  arbitrary number of spaces. 
   *
   *  Return a String [] of tokens, or null if there are no tokens.
   */
  public static String [] lineStrings ( String line ) {
    return IOUtil.parseLine(line);
  }
  /*
   *  Tokenize the input line != null. Tokens are separated by an
   *  arbitrary number of spaces. 
   *
   *  Return an int [] of tokens, or null if there are no tokens.
   */
  public static int [] lineInts ( String line ) {
    /**/
    String [] tokens=IOUtil.parseLine(line);
    if ( tokens == null ) return null;
    /**/
    int numTokens=tokens.length;
    int [] tokenArray=new int [numTokens];
    try {
      for ( int i=0; i<numTokens; ++i ) {
        tokenArray[i]=Integer.parseInt(tokens[i]);
      }
    }
    catch ( NumberFormatException nfe ) {
      System.out.println();
      System.out.println("IOUtil.lineInts:");
      System.out.println("Could not parse an input token.");
      System.exit(0);
    }
    /**/
    return tokenArray;
  }
  /*
   *  Tokenize the input line != null. Tokens are separated by an
   *  arbitrary number of spaces. 
   *
   *  Return a double [] of tokens, or null if there are no tokens.
   */
  public static double [] lineDoubles ( String line ) {
    /**/
    String [] tokens=IOUtil.parseLine(line);
    if ( tokens == null ) return null;
    /**/
    int numTokens=tokens.length;
    double [] tokenArray=new double [numTokens];
    try {
      for ( int i=0; i<numTokens; ++i ) {
        tokenArray[i]=Double.parseDouble(tokens[i]);
      }
    }
    catch ( NumberFormatException nfe ) {
      System.out.println();
      System.out.println("IOUtil.lineDoubles:");
      System.out.println("Could not parse an input token.");
      System.exit(0);
    }
    /**/
    return tokenArray;
  }
  /**/
  private static String [] parseLine ( String line ) {
    /**/
    int lineLen=line.length();
    if ( lineLen == 0 ) return null;
    /**/
    boolean on=false;
    char c;
    String tokenS=null;
    ArrayList<String> tokenList=new ArrayList<String> ();
    /**/
    for ( int i=0; i<lineLen; ++i ) {
      c=line.charAt(i);
      if ( on ) {
        if ( c == ' ' ) {
          tokenList.add(tokenS);
          on=false;
        }
        else {
          tokenS += Character.toString(c);
          if ( i == lineLen-1 ) tokenList.add(tokenS);
        }
      }
      else {
        if ( c == ' ' ) {
          continue;
        }
        else {
          tokenS=Character.toString(c);
          if ( i == lineLen-1 ) tokenList.add(tokenS);
          on=true;
        }
      }
    }
    /**/
    int numTokens=tokenList.size();
    if ( numTokens == 0 ) return null;
    /**/
    String [] tokenArray=new String [numTokens];
    for ( int i=0; i<numTokens; ++i ) tokenArray[i]=tokenList.get(i);
    /**/
    return tokenArray;
  }
}

