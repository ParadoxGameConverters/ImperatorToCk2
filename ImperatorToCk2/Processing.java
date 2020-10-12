import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
/**
 * Write a description of class Processing here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Processing
{

    private int x;


    public Processing()
    {
        x = 0;
    }


    public static String basicProvinceTotal(int totalCKProv, String[] ck2TagTotals, String[][] ck2ProvInfo, int typeToCollect,int aq2)
    {
        
        //typeToCollect 0 = Owner, 1 = Culture, 2 = Religion
    //int aq2 = 0;
    int flag = 0;
    int flag2 = 0;
    int aq5 = 0;
    int aq6 = 0;
    String[] irOwners;
    String[] owners;
    String[] provtags;
    String[] pops;
    int culRelID = 0;
    String[] pastOwners;
    pastOwners = new String[totalCKProv];
    pastOwners[0] = "debug";
    int aq7 = 0;
    
        irOwners = ck2TagTotals[aq2].split("~"); 
        //System.out.println(irOwners[0]+"a"+irOwners.length);
    
        while (aq5 < irOwners.length) {
        owners = irOwners[aq5].split(","); 
        
        int[] ownerTot;
        ownerTot = new int[totalCKProv]; //should redefine each time
        
        while (aq7 <= totalCKProv) {
        if (owners[0].equals (pastOwners[aq7])) {
        culRelID = aq7;    
        }
        
        if (pastOwners[aq7] == null ) {
        culRelID = aq7;
        pastOwners[aq7] = owners[0];
        aq7 = 9999;
        }
        else {
        aq7 = aq7 + 1;    
        }
        }   
        //System.out.println(pastOwners[culRelID]);
        //System.out.println(aq6);
        if (owners[1].equals("null")) {
        ownerTot[culRelID] = 0;    
        }
        else {
        ownerTot[culRelID] = Integer.parseInt(owners[1]);
        }
        
        //    while (aq6 > irOwners.length) {
            //if (owners[aq6]) {
        //System.out.println(owners[0]+owners[1]+"b");    
            //}
            //pops[
        //}
        //System.out.println(ownerTot[aq6]);
        //System.out.println(ownerTot[1]+"_1");
        //System.out.println(ownerTot[299]+"_300");
        //System.out.println(ownerTot[aq6-1]);
        
        aq6 = 1;
        while (aq6 < totalCKProv) {
         if (ownerTot[aq6] > ownerTot[aq6-1]){
         ck2ProvInfo[typeToCollect][aq2] = owners[0];
         //System.out.println(owners[0] + "_Owners0" + ck2ProvInfo[typeToCollect][aq2] + "sentBack");
         //System.out.println((ck2ProvInfo[0][aq2])+"_"+aq2+"c");
         //System.out.println(ck2ProvInfo[typeToCollect][aq2]+"inLoop"+aq2);
         }
         //System.out.println(ownerTot[aq6]);
         //System.out.println(ownerTot[aq6-1]);
         aq6 = aq6 + 1;
         
        }
        aq5 = aq5 + 1;
        //aq6 = 0;
        aq7 = 0;
        culRelID = 0;
    
       }
       
        //System.out.println(ck2ProvInfo[typeToCollect][aq2]+"outofLoop"+aq2);   
        
        //aq2 = aq2 + 1;
        //String Culture 
    
    
    return ck2ProvInfo[typeToCollect][aq2];
    }
    
    public static String[][] basicProvinceTotalOld(int totalCKProv, String[] ck2TagTotals, String[][] ck2ProvInfo, int typeToCollect)
    {
        
        //typeToCollect 0 = Owner, 1 = Culture, 2 = Religion
    int aq2 = 0;
    int flag = 0;
    int flag2 = 0;
    int aq5 = 0;
    int aq6 = 0;
    String[] irOwners;
    String[] owners;
    String[] provtags;
    String[] pops;
    
    while( aq2 < totalCKProv) {
        try {
            //System.out.println(ck2ProvInfo[0][343]);
        if (ck2TagTotals[aq2] == null) {
            
        }
        irOwners = ck2TagTotals[aq2].split("~"); 
        //System.out.println(irOwners[0]+"a"+irOwners.length);
    
        while (aq5 < irOwners.length) {
        owners = irOwners[aq5].split(","); 
        
        int[] ownerTot;
        ownerTot = new int[totalCKProv]; //should redefine each time
        
        ownerTot[Integer.parseInt(owners[0])] = Integer.parseInt(owners[1]);
        //    while (aq6 > irOwners.length) {
            //if (owners[aq6]) {
        //System.out.println(owners[0]+owners[1]+"b");    
            //}
            //pops[
        //}
        aq6 = 1;
        while (aq6 < totalCKProv) {
         if (ownerTot[aq6] > ownerTot[aq6-1]){
         ck2ProvInfo[typeToCollect][aq2] = owners[0];
         //System.out.println((ck2ProvInfo[0][aq2])+"_"+aq2+"c");
         }
         aq6 = aq6 + 1;
         
        }
        aq5 = aq5 + 1;
        //aq6 = 0;
        }
    
       }
       catch (java.lang.NullPointerException exception) {
           
        }
        aq2 = aq2 + 1;
    
    }
    
    return ck2ProvInfo;
    }
    
     public static String[] importNames (String name, int provIDnum, String ck2Dir) throws IOException
   {
    
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
     String provID = Integer.toString(provIDnum);
      
     //System.out.println ("Load 1 done");
     
      
          FileInputStream fileIn= new FileInputStream("provinceNames.txt");
      Scanner scnr= new Scanner(fileIn);
      
      int flag = 0;
      //double q = 0.0;
      String ckName = "debug";
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      int aqq = 0;
      //String qaaa = scnr.nextLine();
      boolean endOrNot = true;
      //String vmm = scnr.nextLine();
      String qaaa = "debug";
      String[] output;   // Owner Culture Religeon PopTotal Buildings
      output = new String[2];
      
      output[0] = "noName"; //default for no owner, uncolonized province
      output[1] = "plains"; //default for no culture, uncolonized province with 0 pops
      //output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
      
      try {
      while (endOrNot = true){
          
          qaaa = scnr.nextLine();
          //System.out.println(qaaa);
          //double qaaa2 = Double.parseDouble(qaaa);
          if (qaaa.split(",")[0].equals(provID)){
          endOrNot = false;
          ckName = qaaa.split(",")[4];
          output[0] = ckName;
          output[1] = importCK2ProvFile(ck2Dir,provIDnum,ckName)[0];
          
          //System.out.println(output[0]+" is 0, "+output[1]+" is 1");
          //System.out.println(output[0]+" is 0, "+output[1]+" is 1");
        
        
          
          //aqq = aqq + 1;
        }
    }
    
        
    }catch (java.util.NoSuchElementException exception){
        endOrNot = false;
        
      
    }   
     //System.out.println (output[0]);
     //System.out.println (output[1]);
     //System.out.println (output[2]);
     //System.out.println (output[3]);
     //System.out.println (output[4]);
     
     fileIn.close();
     
     return output;
    
    
    
    }
    
     public static String[] importCK2ProvFile (String ck2Dir, int provIDnum, String ck2Prov) throws IOException
   {
    
       
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
      String VM = "\\";
      
      VM = VM.substring(0);
      
     String provID = Integer.toString(provIDnum);
     
     String name = ck2Dir+VM+"history"+VM+"provinces"+VM+provID+" - "+ck2Prov+".txt";
     
     String[] output;   // Owner Culture Religeon PopTotal Buildings
      output = new String[1];
      
     //System.out.println ("Load 1 done");
     try {
      
      FileInputStream fileIn= new FileInputStream(name);
      Scanner scnr= new Scanner(fileIn);
      
      int flag = 0;
      //double q = 0.0;
      String ckName = "debug";
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      int aqq = 0;
      //String qaaa = scnr.nextLine();
      boolean endOrNot = true;
      //String vmm = scnr.nextLine();
      String qaaa = "debug";
      //String[] output;   // Owner Culture Religeon PopTotal Buildings
      //output = new String[1];
      
      output[0] = "plains"; //default for no owner, uncolonized province
      //output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
      //output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
      
      try {
      while (endOrNot = true){
          
          qaaa = scnr.nextLine();
          //double qaaa2 = Double.parseDouble(qaaa);
          if (qaaa.split("= ")[0].equals("terrain ")){
          endOrNot = false;
          output[0] = qaaa.split(",")[0];
          //System.out.println (aqq);
          
          
          
          
        
        
          
          //aqq = aqq + 1;
        }
        
        
    }
    
        
    }catch (java.util.NoSuchElementException exception){
        endOrNot = false;
      
    }   
     //System.out.println (output[0]);
     //System.out.println (output[1]);
     //System.out.println (output[2]);
     //System.out.println (output[3]);
     //System.out.println (output[4]);
    }
    
    catch(java.io.FileNotFoundException exception) {
    
     output[0] = "error";
     //output[1] = "error";
      
    }
     
     return output;
    
    
    
    }
    
    
         public static String importBaronyName (String name, int provIDnum, String ck2Dir) throws IOException
   {
    
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
      int aqq = 0;
      String tab = "	";
      String VM = "\\";
      
      VM = VM.substring(0);
      
      //importNames(name,aqq,ck2Dir);
      
     String provID = Integer.toString(provIDnum);
      
     //System.out.println ("Load 1 done");
     //importNames(name, provIDnum, String ck2Dir);
      
          FileInputStream fileIn= new FileInputStream(ck2Dir+VM+"common"+VM+"landed_titles"+VM+"landed_titles.txt");
      Scanner scnr= new Scanner(fileIn);
      
      //int flag = 0;
      //double q = 0.0;
      String ckName = "debug";
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      //int aqq = 0;
      //String qaaa = scnr.nextLine();
      int flag = 0;
      //String vmm = scnr.nextLine();
      String qaaa = "debug";
      String output = qaaa;   // Owner Culture Religeon PopTotal Buildings
      //output = new String[2014];
      String provName = "noProv";
      String tab3 = tab+tab+tab;
      String tab4 = tab+tab+tab+tab;
      
      provName = importNames(name,provIDnum,ck2Dir)[0];
      provName = provName.toLowerCase();
      provName = provName.replace(" ","_");
      provName = provName.replace("-","_");
      
      if (provName.equals("padua")) {
          
          provName = "padova";
        }
        
      if (provName.equals("angseley")) {
          
          provName = "anglesey";
        }
        
      if (provName.equals("padua")) {
          
          provName = "padova";
        }
        
      if (provName.equals("aurilliac")) {
          
          provName = "aurillac";
        }
        
      if (provName.equals("el_arish")) {
          
          provName = "el-arish";
        }
        
      
      
      if (provName.equals ("error")) {
        flag = 3;  
        }
      
      //output[0] = "noName"; //default for no owner, uncolonized province
      //output[1] = "plains"; //default for no culture, uncolonized province with 0 pops
      //output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
      
      try {
      while (flag == 0){
          
          //System.out.println(provName + "_Name");
          qaaa = scnr.nextLine();
          //System.out.println(qaaa);
          //double qaaa2 = Double.parseDouble(qaaa);
          if (qaaa.split("=")[0].equals(tab3 + "c_" + provName + " ") || qaaa.split("=")[0].equals(tab3 + "c_" + provName)){
          flag = 1;
          output = null;
          while (flag == 1){
          //ckName = qaaa.split(",")[4];
          //output[aqq] = ckName;
          System.out.println(qaaa);
          System.out.println(aqq);
          //output[1] = importCK2ProvFile(ck2Dir,provIDnum,ckName)[0];
          try {
          if (qaaa.split("_")[0].equals(tab4+"b")){
          //System.out.println(output[0]+" is 0, "+output[1]+" is 1");
          //System.out.println(output[0]+" is 0, "+output[1]+" is 1");
          qaaa = qaaa.split("b_")[1];
          if (output != null) {
          output = output + "," + qaaa.split(" =")[0];
         } else { output = qaaa.split(" =")[0]; }
          
          aqq = aqq + 1;
        }
        }catch (java.util.NoSuchElementException exception){
        qaaa = scnr.nextLine();
        
      
    }
        qaaa = scnr.nextLine();
        if (aqq == 7) {
        flag = 2;    
        }
        }
        
        
          
          //aqq = aqq + 1;
        }
    }
    
        
    }catch (java.util.NoSuchElementException exception){
        flag = 2;
        
      
    }   
     //System.out.println (output[0]);
     //System.out.println (output[1]);
     //System.out.println (output[2]);
     //System.out.println (output[3]);
     //System.out.println (output[4]);
     
     fileIn.close();
     
     return output;
    
    
    
    }
    
    
         public static String[] importBaronyNameList (String name, int provIDnum, String ck2Dir) throws IOException
   {
    
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
     String provID = Integer.toString(provIDnum);
      
     //System.out.println ("Load 1 done");
     
      
    //      FileInputStream fileIn= new FileInputStream("provinceNames.txt");
    //  Scanner scnr= new Scanner(fileIn);
      
      int flag = 0;
      //double q = 0.0;
      String ckName = "debug";
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      int aqq = 1;
      //String qaaa = scnr.nextLine();
      boolean endOrNot = true;
      //String vmm = scnr.nextLine();
      String qaaa = "debug";
      String[] output;   // Owner Culture Religeon PopTotal Buildings
      output = new String[2015];
      
      output[0] = "noName"; //default for no owner, uncolonized province
      //output[1] = "plains"; //default for no culture, uncolonized province with 0 pops
      //output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
      
      try {
      while (endOrNot = true){
          output[aqq]=importBaronyName(ck2Dir,aqq,ck2Dir);
          System.out.println(aqq+"is for"+output[aqq]);
          aqq = aqq + 1;
          
    }
    
        
    }catch (java.lang.ArrayIndexOutOfBoundsException exception){
        endOrNot = false;
        
      
    }   
     //System.out.println (output[0]);
     //System.out.println (output[1]);
     //System.out.println (output[2]);
     //System.out.println (output[3]);
     //System.out.println (output[4]);
     
     //fileIn.close();
     
     return output;
    
    
    
    }
    
          public static String[] importRegionList (int totProv, String impDir) throws IOException
   {
    
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
     
      
     //String provID = Integer.toString(provIDnum);
      
     //System.out.println ("Load 1 done");
     
     String VM = "\\";
      
      VM = VM.substring(0);
      
       //impDir = "C:"+VM+"Program Files (x86)"+VM+"Steam"+VM+"steamapps"+VM+"common"+VM+"ImperatorRome";
     
      
          FileInputStream fileIn= new FileInputStream("regionConverter.txt");
      Scanner scnr= new Scanner(fileIn);
      
      int flag = 0;
      //double q = 0.0;
      String ckName = "debug";
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      int aqq = 1;
      //String qaaa = scnr.nextLine();
      boolean endOrNot = true;
      //String vmm = scnr.nextLine();
      String qaaa = "debug";
      String[] provList;   // Owner Culture Religeon PopTotal Buildings
      provList = new String[totProv];
      
      //output[0] = "noName"; //default for no owner, uncolonized province
      //output[1] = "plains"; //default for no culture, uncolonized province with 0 pops
      //output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
      
      qaaa = scnr.nextLine();
      
      try {
      while (endOrNot = true){
          //output[aqq]=importBaronyName(ck2Dir,aqq,ck2Dir);
          qaaa = scnr.nextLine();
          
          //qaaa = qaaa.substring(1);
          
          //System.out.println(qaaa);
          provList = importRegion (provList,(impDir+VM+"game"+VM+"setup"+VM+"provinces"+VM+"00_"+qaaa+".txt"),qaaa);
          //System.out.println(qaaa);
          //provList = importRegion (provList,(impDir+VM+"game"+VM+"setup"+VM+"provinces"+VM+"00_"+"africa_region"+".txt"),qaaa);
          aqq = aqq + 1;
          
    }
    
        
    }catch (java.util.NoSuchElementException exception){
        endOrNot = false;
        
      
    }   
     //System.out.println (output[0]);
     //System.out.println (output[1]);
     //System.out.println (output[2]);
     //System.out.println (output[3]);
     //System.out.println (output[4]);
     //System.out.println(qaaa);
     //fileIn.close();
     
     return provList;
    
    
    
    }
    
           public static String[] importRegion (String[] provList, String dir, String region) throws IOException
   {
    
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
     //String provID = Integer.toString(provIDnum);
      
     //System.out.println ("Load 1 done");
     
      
          FileInputStream fileIn= new FileInputStream(dir);
      Scanner scnr= new Scanner(fileIn);
      
      int flag = 0;
      //double q = 0.0;
      String ckName = "debug";
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      int aqq = 0;
      String qaaa = "aa";
      boolean endOrNot = true;
      
      int number = 0;
      
      //String splitter = "={ #";
      
      
      try {
      while (endOrNot = true){
          qaaa = scnr.nextLine();
          
          if (aqq == 0) {
          qaaa = qaaa.substring(1);    
            }
          //qaaa = qaaa.substring(1);
          //System.out.println(qaaa);
          
          try {
          
          
          qaaa = qaaa.split("=")[0];
          number = Integer.parseInt(qaaa);    
          
          provList[number] = region;   
          
          
            
          }catch (java.lang.NumberFormatException exception) {
              
            }
          
          aqq = aqq + 1;    
          
    }
    
        
    }catch (java.util.NoSuchElementException exception){
        endOrNot = false;
        
      
    }   
     //System.out.println (output[0]);
     //System.out.println (output[1]);
     //System.out.println (output[2]);
     //System.out.println (output[3]);
     //System.out.println (output[4]);
     
     //fileIn.close();
     
     return provList;
    
    
    
    }
    
    
    public static String randomizeColor ()
   {
    
      //use 0 = province
      //use 1 = 
      //use 2 = 
      //use 3 =
      
     //String provID = Integer.toString(provIDnum);
      
     //System.out.println ("Load 1 done");
     
      
      
      
      int flag = 0;
      //double q = 0.0;
      
      //String idStr = provID.toString();
      
      //String keyWord = "	"+provID+"={";
      //System.out.println (key");
      
      int aqq = 0;
      String qaaa = "aa";
      int Rng1 = (int) (Math.random() * 255);
      int Rng2 = (int) (Math.random() * 255);
      int Rng3 = (int) (Math.random() * 255);
      String color = Integer.toString(Rng1) + " " + Integer.toString(Rng2) + " " + Integer.toString(Rng3); 
     
     return color;
    
    
    
    }
    
}
