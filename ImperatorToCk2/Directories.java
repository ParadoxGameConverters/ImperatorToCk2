import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
/**
 * Creates the basic directories for the CK II mod
 *
 * 
 */
public class Directories
{
    private int x;

    

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static int modFolders (String Dir,String modName ) throws IOException
   {
     //Nessicery to create all of the folders required for the mod
     int aqv = 0;
     boolean  endOrNot2 = true;
     String VM = "\\";
     VM = VM.substring(0);
     String mainModFolder = Dir+VM+modName;
     //double[] numFahr = new double[temperatures.length-1];
     File f = new File(mainModFolder);
        f.mkdir();
        
     File f2 = new File(mainModFolder+VM+"common");
        f2.mkdir();
     
     File f3 = new File(mainModFolder+VM+"events");
        f3.mkdir();   
        
     File f4 = new File(mainModFolder+VM+"gfx");
        f4.mkdir();   
        
     File f5 = new File(mainModFolder+VM+"history");
        f5.mkdir();   
        
     File f6 = new File(mainModFolder+VM+"localisation");
        f6.mkdir(); 
    // Sub folders
    
     File f7 = new File(mainModFolder+VM+"history"+VM+"characters");
        f7.mkdir();  
        
     File f8 = new File(mainModFolder+VM+"history"+VM+"provinces");
        f8.mkdir();  
        
     File f9 = new File(mainModFolder+VM+"history"+VM+"technology");
        f9.mkdir();  
      
     File f10 = new File(mainModFolder+VM+"history"+VM+"titles");
        f10.mkdir();  
        
     File f11 = new File(mainModFolder+VM+"history"+VM+"titles");
        f11.mkdir();  
        
     File f12 = new File(mainModFolder+VM+"common"+VM+"cultures");
        f12.mkdir();  
        
     File f13 = new File(mainModFolder+VM+"common"+VM+"dynasties");
        f13.mkdir();  
        
     File f14 = new File(mainModFolder+VM+"common"+VM+"landed_titles");
        f14.mkdir();  
        
     File f15 = new File(mainModFolder+VM+"common"+VM+"religions");
        f15.mkdir(); 
        
     File f16 = new File(mainModFolder+VM+"common"+VM+"bookmarks");
        f16.mkdir(); 
        
     File f17 = new File(mainModFolder+VM+"eu4_converter");
        f17.mkdir();
        
     File f18 = new File(mainModFolder+VM+"gfx"+VM+"interface");
        f18.mkdir(); 
        
     File f19 = new File(mainModFolder+VM+"eu4_converter"+VM+"copy");
        f19.mkdir();
        
     File f20 = new File(mainModFolder+VM+"eu4_converter"+VM+"copy"+VM+"common");
        f20.mkdir();
        
     File f21 = new File(mainModFolder+VM+"eu4_converter"+VM+"copy"+VM+"common"+VM+"religions");
        f21.mkdir();
        
     File f22 = new File(mainModFolder+VM+"eu4_converter"+VM+"copy"+VM+"common"+VM+"cultures");
        f22.mkdir();
        
     File f23 = new File(mainModFolder+VM+"common"+VM+"bloodlines");
        f23.mkdir();  
        
    return aqv;
    }
    
    public static String descriptors(String Dir, String modName, String Dir2) throws IOException 
    {
        //Each mod requires 2 .mod "descriptor" files so the game launcher can
        //read the mod files as a mod
        String VM = "\\";
        char VMq = '"';
        String VN = "//";
       VN = VN.substring(0);
        //VMq is to get around problems with printing quotation marks (")
        VM = VM.substring(0);
        int aqq = 1;
        String mainModFolder = Dir+VM+modName;
        String dotModFolder = Dir2+VN+modName;
        //FileOutputStream fileOut= new FileOutputStream(modName+".mod");
        //First .mod//
        
        FileOutputStream fileOut= new FileOutputStream(mainModFolder+".mod");
       PrintWriter out = new PrintWriter(fileOut);
       
       out.println("name="+VMq+modName+VMq);
       
       out.println("path="+VMq+"mod"+VN+modName+VMq);
       
       out.println("user_dir="+VMq+modName+VMq);
       //String QV = friends.get(0).name;
       //out.println(QV);
       out.flush();
       
       //Second .mod//
       
      //  FileOutputStream fileOut2= new FileOutputStream(mainModFolder+VM+"descriptor.mod");
      // PrintWriter out2 = new PrintWriter(fileOut2);
     //  out2.println("version="+VMq+"1.8.1"+VMq);
      // out2.println("tags={");
     //  out2.println("   "+VMq+"Alternative History"+VMq);
     //  out2.println("}");
     //  out2.println("name="+VMq+modName+VMq);
     //  out2.println("supported_version="+VMq+"1.8.1"+VMq);
       //String QV = friends.get(0).name;
       //out.println(QV);
    //   out2.flush();
       
       fileOut.close();
       //FileInputStream fileInq= new FileInputStream("hwebbUS1.txt");
       //Scanner scnr= new Scanner(fileInq);
       //String Ǵɀȭȵǳɶɱ = scnr.nextLine();
       //fileInq.close();
       // String Aq = Ǵɀȭȵǳɶɱ;
        //while (aqq < friends.size()){
        //    out.println(friends.get(aqq).name);
        //    out.flush();
        //    Ǵɀȭȵǳɶɱ = scnr.nextLine();
        //    Aq = Aq + "," + Ǵɀȭȵǳɶɱ;
        //    aqq = aqq + 1;
        //}
       // fileInq.close();
        return "q";
    }
    
    public static void files(String fileDirectory, String file) throws IOException 
    {
        
      String VM = "\\"; 
      VM = VM.substring(0);
      FileInputStream fileIn= new FileInputStream(file);
      Scanner scnr= new Scanner(fileIn);
      String line;
      
      //FileOutputStream fileOut= new FileOutputStream(mainModFolder+VM+"history"+VM+"characters"+file);
      FileOutputStream fileOut= new FileOutputStream(fileDirectory+file);
      PrintWriter out = new PrintWriter(fileOut);
      
      int flag = 0;
      
        try {
            while (flag == 0) {
                
                line = scnr.nextLine();
                out.println(line);
            
            
            }
        
        
        }catch (java.util.NoSuchElementException exception){
        flag = 1;
      
    }  
    fileIn.close(); 
    //return y;
    }
}
