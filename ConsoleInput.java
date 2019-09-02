import java.io.*;
/*
 *  All methods of this class require you to pass in a BufferedReader object br
 *  which is connected to the console. You may construct such an object via
 *
 *  BufferedReader br = new BufferedReader (
 *      new InputStreamReader ( System.in )
 *  );
 *
 *  Any program using this class must
 *
 *  import java.io.BufferedReader;
 *  import java.io.InputStreamReader;
 *
 *  or
 *
 *  import java.io.*;
 */
public class ConsoleInput {
    /**/
    public ConsoleInput ( ) { }
    /**/
    public static int readInt( BufferedReader br, String prompt ) {
        /**/
        int i;
        boolean ok;
        String inp;
        /**/
        inp=null;   i=0;   ok=false;
        while ( !ok ) {
            System.out.print(prompt);
            try {
                inp=br.readLine();
                i=Integer.parseInt(inp);
            }
            catch ( IOException ioe ) { }
            catch ( NumberFormatException nfe ) { continue; }
            /**/
            ok=true;
        }
        /**/
        return i;
    }
    /**/
    public static double readDouble( BufferedReader br, String prompt ) {
        /**/
        double d;
        boolean ok;
        String inp;
        /**/
        inp=null;   d=0.0;   ok=false;
        while ( !ok ) {
            System.out.print(prompt);
            try {
                inp=br.readLine();
                d=Double.parseDouble(inp);
            }
            catch ( IOException ioe ) { }
            catch ( NumberFormatException nfe ) { continue; }
            /**/
            ok=true;
        }
        /**/
        return d;
    }
    /**/
}
