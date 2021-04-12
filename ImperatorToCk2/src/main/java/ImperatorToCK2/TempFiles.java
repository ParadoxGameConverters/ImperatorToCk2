 

import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * This will create temp files at specified points in the save files, used to significantly decrease time spent reading.
 *
 * @author Shinymewtwo99
 * @version
 */
public class TempFiles
{



    public static void tempCreate(String save, String keyword, String endword, String type) throws IOException
    {
        int flag = 0;

        FileInputStream fileIn= new FileInputStream(save);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(type);
        PrintWriter out = new PrintWriter(fileOut);   

        String vmm = scnr.nextLine();

        try {

            while (flag == 0) {
                if (vmm.equals (keyword)) {   
                    flag = 1;    
                }
                else {
                    vmm = scnr.nextLine();    
                }
            }

            while (flag == 1) {
                out.println (vmm);
                if (vmm.equals(endword)) {
                    flag = 2;    

                }
                else {
                    vmm = scnr.nextLine();     
                }
            }
            //developed by Shinymewtwo99
        }
        catch (java.util.NoSuchElementException exception){
            flag = 2;

        }  
        out.flush();
        fileIn.close();
    }
}
