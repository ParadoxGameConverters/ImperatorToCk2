package ImperatorToCK2; 

import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
/**
 * Everything dealing with characters
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Characters
{
    private int x;

    public static String[] importChar (String name, String charID) throws IOException
    {

        String tab = "	";
        char quote = '"';

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String keyWord = charID+"={";

        int aqq = 0;

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[17];

        output[0] = "noName"; //default for no owner, uncolonized province
        output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
        output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[3] = "30"; //default age
        output[4] = "m"; //by default all characters are male, unless specified to be female
        output[7] = "q"; //default for no dynasty
        output[8] = "q"; //default for no traits
        output[14] = "0"; //default for unmarried character
        output[15] = "0"; //default for character with no children
        output[16] = "0"; //default for minor character with no family name

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (qaaa.equals(keyWord)){
                    endOrNot = false;
                    while (flag == 0) {
                        qaaa = scnr.nextLine();
                        if (qaaa.split("=")[0].equals( tab+tab+"name" ) ) {
                            output[0] = qaaa.split("=")[1];
                            output[0] = output[0].substring(1,output[0].length()-1);
                        }
                        else if (qaaa.split("=")[0].equals( tab+"family_name" ) ) {
                            output[16] = qaaa.split("=")[1];
                            output[16] = output[16].substring(1,output[16].length()-1);
                        }
                        else if (qaaa.split("=")[0].equals( tab+"culture" ) ) {
                            output[1] = qaaa.split("=")[1];
                            output[1] = output[1].substring(1,output[1].length()-1);
                        }
                        else if (qaaa.split("=")[0].equals( tab+"religion" ) ) {
                            output[2] = qaaa.split("=")[1];
                            output[2] = output[2].substring(1,output[2].length()-1);
                        }
                        else if (qaaa.split("=")[0].equals( tab+"family" ) ) {
                            output[7] = qaaa.split("=")[1];

                        }
                        else if (qaaa.split("=")[0].equals( tab+"traits" ) ) {
                            Output.logPrint (qaaa); 
                            output[8] = qaaa.split("=")[1];

                            output[8] = output[8].substring(2,output[8].length()-2);
                            Output.logPrint (output[8]);
                        }
                        else if (qaaa.split("=")[0].equals( tab+tab+"martial" ) ) {
                            output[10] = qaaa.split("=")[1];
                        }
                        else if (qaaa.split("=")[0].equals( tab+tab+"finesse" ) ) {
                            output[11] = qaaa.split("=")[1];
                        }
                        else if (qaaa.split("=")[0].equals( tab+tab+"charisma" ) ) {
                            output[12] = qaaa.split("=")[1];
                        }
                        else if (qaaa.split("=")[0].equals( tab+tab+"zeal" ) ) {
                            output[13] = qaaa.split("=")[1];
                        }
                        else if (qaaa.split("=")[0].equals( tab+"spouse" ) ) {
                            output[14] = qaaa.split("=")[1];
                            output[14] = output[14].substring(2,output[14].length()-2); 

                            try {
                                if (output[14].split(" ")[1] != null) {
                                    output[14] = output[14].split(" ")[output[14].split(" ").length-1];   
                                }
                            }catch (java.lang.ArrayIndexOutOfBoundsException exception) {

                            }
                        }
                        else if (qaaa.split("=")[0].equals( tab+"children" ) ) {

                
                            output[15] = qaaa.split("=")[1];
                            output[15] = output[15].substring(2,output[15].length()-2);    

                        }

                        //popTotal
                        else if (qaaa.split("=")[0].equals( tab+"age" ) ) {
                            aqq = aqq + 1;
                            output[3] = qaaa.split("=")[1];
                        }

                        //might be used or ignored
                        else if (qaaa.equals( tab+"female=yes" ) ) {
                            aqq = aqq + 1;
                            output[4] = "f";
                        }

                        else if (qaaa.split("=")[0].equals( tab+"death_date" ) ) {
                            aqq = aqq + 1;
                            flag = 1; //end loop, babies which die don't have any experience field
                            output[4] = output[4]+"_"+qaaa.split("=")[1];
                            output[6] = "0";
                        }

                        else if (qaaa.split("=")[0].equals( tab+"character_experience" ) ) {
                            aqq = aqq + 1;
                            flag = 1; //end loop
                            output[6] = qaaa.split("=")[1];
                        }

                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;
    
    }
    public static String importAndConvDynasty (String dir, String IDnum, String backupName, String file) throws IOException
    {

        String tab = "	";

        FileInputStream fileIn= new FileInputStream(file);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String keyWord = tab+tab+IDnum;

        int aqq = 0;
        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String output = "noName"; //default for no name

      
        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (qaaa.split("=")[0].equals(keyWord)){
                    endOrNot = false;
                    while (flag == 0) {
                        qaaa = scnr.nextLine();
                        if (qaaa.split("=")[0].equals( tab+tab+tab+"key" ) ) {
                            output = qaaa.split("=")[1];
                            output = output.substring(1,output.length()-1);
                            flag = 1;
                        }

                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        if (output.split("_")[0].equals ("minor")) {
            output = backupName;
        }

        IDnum = Integer.toString(Integer.parseInt(IDnum) + 700000000);  

        Output.dynastyCreation(output,IDnum,dir);
        return output;

    
    }
}
