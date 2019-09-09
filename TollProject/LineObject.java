/*
 *  For use with class IOUtil
 */
public class LineObject {
  /**/
  private boolean eofB;
  private String lineS;
  /**/
  public LineObject ( boolean eof, String line ) {
    eofB=eof;
    lineS=line;
  }
  /*
   *  Return true if end-of-file has been reached,
   *  false otherwise.
   */
  public boolean eof ( ) {
    return eofB;
  }
  /*
   *  Return the line.
   *  It will be null if lineObject.eof() == true
   */
  public String line ( ) {
    return lineS;
  }
}