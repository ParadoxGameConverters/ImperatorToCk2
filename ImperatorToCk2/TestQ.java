import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;
/**
 * A class used to convert colors from Imperator's format to CK II's and misc. testing
 *
 * @author Shinymewtwo99
 * 
 */
public class TestQ
{

    private int x;

    

    
    
    public static String[] Test(int y) throws IOException
    {
        // put your code here
        String[] test;
               String VM = "\\";
       VM = VM.substring(0);
        String Dir1 = "C:"+VM+"Program Files (x86)"+VM+"Steam"+VM+"steamapps"+VM+"common"+VM+"Crusader Kings II";
        String Dir2 = "C:"+VM+"Users"+VM+"testuser"+VM+"Documents"+VM+"Paradox Interactive"+VM+"Crusader Kings II"+VM+"mod";
      test = Processing.importBaronyNameList(Dir2,y,Dir1);
      int aqq = 0;
      FileOutputStream fileOut= new FileOutputStream("C:"+VM+"Users"+VM+"testuser"+VM+"Documents"+VM+"Paradox Interactive"+VM+"Test.txt");
      PrintWriter out = new PrintWriter(fileOut);
      while (aqq < 2015) {
      out.println (test[aqq]);    
        }
        fileOut.close();
        return test;
    }
    
    public static String Test2(int y) throws IOException
    {
        // put your code here
               String VM = "\\";
       VM = VM.substring(0);
       String test;
        String Dir1 = "C:"+VM+"Program Files (x86)"+VM+"Steam"+VM+"steamapps"+VM+"common"+VM+"Crusader Kings II";
        String Dir2 = "C:"+VM+"Users"+VM+"testuser"+VM+"Documents"+VM+"Paradox Interactive"+VM+"Crusader Kings II"+VM+"mod";
      test = Processing.importBaronyName(Dir2,y,Dir1);
        return test;
    }
    
    public static String Color(double c1, double c2, double c3) throws IOException
    {
        // put your code here
        //String[] test;
               String VM = "\\";
      c1 = c1 * 361;
      c2 = c2 * 100;
      c3 = c3 * 100;
      String test = c1 + " " + c2 + " " + c3;
      System.out.println(test);
      
        return test;
    }
    
    public static String Color2(double c1, double c2, double c3) throws IOException
    {
        // put your code here
        //String[] test;
               String VM = "\\";
      c1 = c1 / 256;
      c2 = c2 / 256;
      c3 = c3 / 256;
      String test = c1 + " " + c2 + " " + c3;
        return test;
    }
}
