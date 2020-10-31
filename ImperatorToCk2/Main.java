import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
/**
 * Main
 *
 * @author The Imperator:Rome to CK II converter was originally developed by Shinymewtwo99
 * 
 */
public class Main
{

    private int x;

    public static void main (String[] args) throws IOException
    {

        
        Scanner input = new Scanner(System.in);
        String Dir; //Desired user directory, usually located in documents
        String Dir2; //.mod files use reverse slashes (/ instead of \) 
        String modName; //important for creating directories
        String saveName; //The save file (.eu4) to read from
        String impDir; //The directory of the save file (.eu4) to read from

        Importer importer = new Importer();
        Output output = new Output();
        Directories directories = new Directories();

        System.out.println("Please input your system profile username");
        Dir = input.next();
        String VM = "\\";
        VM = VM.substring(0);
        String VN = "//";
        VN = VN.substring(0);
        Dir2 = "C:"+VN+"Users"+VN+Dir+VN+"Documents"+VN+"Paradox Interactive"+VN+"Crusader Kings II"+VN+"mod";
        String User = Dir;
        Dir = "C:"+VM+"Users"+VM+Dir+VM+"Documents"+VM+"Paradox Interactive"+VM+"Crusader Kings II"+VM+"mod";

        System.out.println("Please input the name for your mod");
        modName = input.next();

        System.out.println("Please input the name of your Imperator Rome save (with.rome)");
        saveName = input.next();

        impDir = "C:"+VM+"Users"+VM+User+VM+"Documents"+VM+"Paradox Interactive"+VM+"Imperator"+VM+"save games";

        String impGameDir = "C:"+VM+"Program Files (x86)"+VM+"Steam"+VM+"steamapps"+VM+"common"+VM+"ImperatorRome";

        String ck2Dir = "C:"+VM+"Program Files (x86)"+VM+"Steam"+VM+"steamapps"+VM+"common"+VM+"Crusader Kings II";

        String impDirSave = impDir+VM+saveName;

        directories.modFolders (Dir,modName); //Creating the folders to write the mod files
        //along with nessicery sub-folders
        directories.descriptors(Dir,modName,Dir2); //Basic .mod files required for the launcher

        String modDirectory = Dir+VN+modName;

        String[] impProvtoCK;   // Owner Culture Religeon PopTotal Buildings
        impProvtoCK = new String[2];

        String[] impNationInfo;   // Owner Culture Religeon PopTotal Buildings
        impNationInfo = new String[21];

        String[] impProvInfo;   // Owner Culture Religeon PopTotal Buildings
        impProvInfo = new String[5];

        String[][] ck2ProvInfo;   // Array list of array lists...
        ck2ProvInfo = new String[5][8000];

        String[] impProvRegions = Processing.importRegionList(8000,impGameDir);
        //[0] is owner, [1] is culture, [2] is religion, [3] is calculated from pop
        int totalPop = 0;//pop total
        int totalCKProv = 2050;

        String[] ck2PopTotals;   // Owner Culture Religeon PopTotal Buildings
        ck2PopTotals = new String[totalCKProv];
        /////////////////////////////////////////
        String[] ck2TagTotals;   // Owner Culture Religeon PopTotal Buildings
        ck2TagTotals = new String[totalCKProv];

        //TAG1,0~TAG2,0,~TAG3,0

        String[] ck2CultureTotals;   // Owner Culture Religeon PopTotal Buildings
        ck2CultureTotals = new String[totalCKProv];

        String[] ck2RelTotals;   // Owner Culture Religeon PopTotal Buildings
        ck2RelTotals = new String[totalCKProv];

        String[] ck2RegionTotals;   // Regions for governorship
        ck2RegionTotals = new String[totalCKProv];

        output.localizationBlankFile(modDirectory); //creates the country localization file

        String[] ck2HasLand;   // Owner Culture Religeon PopTotal Buildings
        ck2HasLand = new String[5000];
        int aqtest = 0;
        while (aqtest < 5000) {
            ck2HasLand[aqtest] = "no";
            aqtest = aqtest + 1;
        }

        int tagNum = 0;
        int cultNum = 0;
        int relNum = 0;

        String tempTest = "1000000";
        int tempNum = 1000000;

        int aqq = 0;

        int aq2 = 340;

        int aq3 = 0;

        int ckProvNum = 0;

        int temp;
        int temp2;

        int flag = 0;

        int flag2 = 0;

        String tab = "	";

        String saveCountries = "tempCountries.txt";

        String saveProvinces = "tempProvinces.txt";

        String saveCharacters = "tempCharacters.txt";

        String saveDynasty = "tempDynasty.txt";

        System.out.println("Creating temp files...");

        TempFiles.tempCreate(impDirSave, tab+"country_database={", tab+"state_database={", saveCountries);

        System.out.println("temp Countries created");

        TempFiles.tempCreate(impDirSave, "provinces={", "road_network={", saveProvinces);

        System.out.println("temp Provinces created");   

        TempFiles.tempCreate(impDirSave, "character={", "objectives={", saveCharacters);

        System.out.println("temp Characters created");

        TempFiles.tempCreate(impDirSave, tab+"families={", "character={", saveDynasty);

        System.out.println("temp Dynasties created");

        System.out.println("All temp files created");

        System.out.println("Importing territory data..."); 

        Processing.combineProvConvList("provinceConversionCore.txt","provinceConversion.txt");
           
        //processing information
        totalPop = 0;
        while (flag == 0) {
            impProvtoCK = importer.importConvList("provinceConversion.txt",aqq); 

            if (impProvtoCK[0].equals ("peq")) {
            }
       
            else {
                if (ckProvNum != Integer.parseInt(impProvtoCK[1])) {
                    ckProvNum = Integer.parseInt(impProvtoCK[1]);
                    totalPop = 0;
                    tagNum = 0;
                    cultNum = 0;
                    relNum = 0;
                }

                impProvInfo = importer.importProv(saveProvinces,aqq);

                temp = 0;
                temp2 = 0;
                //nation
                if (ck2TagTotals[ckProvNum] == (null)) {

                    ck2TagTotals[ckProvNum] = impProvInfo[0] + "," + impProvInfo[3];
                }else {
                    ck2TagTotals[ckProvNum] = ck2TagTotals[ckProvNum] + "~" + impProvInfo[0] + "," + impProvInfo[3];
                }
                //culture
                if (ck2CultureTotals[ckProvNum] == (null)) {

                    ck2CultureTotals[ckProvNum] = impProvInfo[1] + "," + impProvInfo[3];
                }else {
                    ck2CultureTotals[ckProvNum] = ck2CultureTotals[ckProvNum] + "~" + impProvInfo[1] + "," + impProvInfo[3];
                }
                //religeon
                if (ck2RelTotals[ckProvNum] == (null)) {

                    ck2RelTotals[ckProvNum] = impProvInfo[2] + "," + impProvInfo[3];
                }else {
                    ck2RelTotals[ckProvNum] = ck2RelTotals[ckProvNum] + "~" + impProvInfo[2] + "," + impProvInfo[3];
                }
                //region for governor conversion
                if (ck2RegionTotals[ckProvNum] == (null)) {

                    ck2RegionTotals[ckProvNum] = impProvRegions[aqq] + "," + impProvInfo[3];
                }else {
                    ck2RegionTotals[ckProvNum] = ck2RegionTotals[ckProvNum] + "~" + impProvRegions[aqq] + "," + impProvInfo[3];
                }

                try {
                    totalPop = Integer.parseInt(ck2PopTotals[ckProvNum]);

                }catch (java.lang.NumberFormatException exception) {
                    totalPop = 0;  
                }

                if (impProvInfo[3] == null) {
                    impProvInfo[3] = "0";  
                }
                totalPop = Integer.parseInt(impProvInfo[3]) + totalPop;
                ck2ProvInfo[3][ckProvNum] = Integer.toString(totalPop);

                ck2PopTotals[ckProvNum] = Integer.toString(totalPop);

            }

            if (aqq == 7843) {
                flag = 1;   
            }

            aqq = aqq + 1;
        }

        //Culture, rel, tag Info, and pop total returned
        System.out.println("Territory data imported");
        System.out.println("Combining territories into provinces...");

        aq2 = 0;
        flag = 0;
        flag2 = 0;
        int aq5 = 0;
        int aq6 = 0;
        String[] irOwners;

        while( aq2 < totalCKProv) {
            if (ck2TagTotals[aq2] != null)  {

                irOwners = ck2TagTotals[aq2].split("~"); 

       
                while (aq5 < irOwners.length) {
                    String[] owners = irOwners[aq5].split(","); 

                    System.out.println(irOwners[aq5]+"_irOwners_"+aq2);  

                    int[] ownerTot;
                    ownerTot = new int[totalCKProv]; //should redefine each time

                    int ownNum = Integer.parseInt(owners[0]);

                    if (ownNum == 9999) {
                        ownNum = 0;

                        System.out.println(aq5);
                    }

                    if (owners[1].equals ("null")) {
                        owners[1] = "0";

                        System.out.println(aq5);
                    }

                    ownerTot[ownNum] = Integer.parseInt(owners[1]);
                    System.out.println(owners[0]+owners[1]+"b_owners");    
                    ck2ProvInfo[0][aq2] = owners[0];
                    aq6 = 1;
                    while (aq6 < totalCKProv) {
                        if (ownerTot[aq6] > ownerTot[aq6-1]){
                            ck2ProvInfo[0][aq2] = owners[0];
                            System.out.println((ck2ProvInfo[0][aq2])+"_"+aq2+"cq");
                        }
                        aq6 = aq6 + 1;

                    }
                    aq5 = aq5 + 1;
                    int tempQ = Integer.parseInt(ck2ProvInfo[0][aq2]);
                    System.out.println(tempQ);
                    if (tempQ != 9999){
                        ck2HasLand[tempQ] = "yes";
                    }

                }
                aq5 = 0;

            }
            else if (aq2 < 380) {
                System.out.println (ck2TagTotals[aq2] + "_" + aq2);    
            }
            aq2 = aq2 + 1;

        }
        System.out.println("Province ownership calculated");
        aq2 = 0;
        flag = 0;

        while( aq2 < totalCKProv) {
            try {

                if (ck2TagTotals[aq2] == null) {

                }
                ck2ProvInfo[1][aq2] = Processing.basicProvinceTotal(totalCKProv,ck2CultureTotals,ck2ProvInfo,1,aq2);
                ck2ProvInfo[2][aq2] = Processing.basicProvinceTotal(totalCKProv,ck2RelTotals,ck2ProvInfo,2,aq2);
                ck2ProvInfo[4][aq2] = Processing.basicProvinceTotal(totalCKProv,ck2RegionTotals,ck2ProvInfo,4,aq2);
            }
            catch (java.lang.NullPointerException exception) {

            }
            aq2 = aq2 + 1;
        }
        aq2 = 0;
        System.out.println("Province religion and culture calculated");
        System.out.println("Province data combined");
        System.out.println("Importing country data...");

        System.out.println("The region is" + ck2ProvInfo[4][343]);
        int flagCount = 0;

        ArrayList<String[]> impTagInfo = new ArrayList<String[]>();
        //Country processing
        try{
            while (flag == 0) {
                impTagInfo.add(importer.importCountry(saveCountries,aq2));

                System.out.println (impTagInfo.get(aq2)[0] + " " +  impTagInfo.get(aq2)[6] + " " + impTagInfo.get(aq2)[4]);

                aq2 = aq2 + 1;

                if (aq2 > 1) {

                    if ( impTagInfo.get(aq2-1)[0].equals("9999")) {
                        flagCount = flagCount + 1; //temp testing, to be removed later
                    }
                    else {
                        flagCount = 0;    
                    }
                }

                if (flagCount == 5) {
                    flag = 1; //temp testing, to be removed later
                }
            }
        }catch (java.util.NoSuchElementException exception){
            flag = 1;

        }  
        System.out.println(impTagInfo.get(Integer.parseInt(ck2ProvInfo[0][343]))[0] + "is the owner of Province 343, the religion is" + ck2ProvInfo[2][343]);
        System.out.println("and the culture is" + ck2ProvInfo[1][343]);

        System.out.println(impTagInfo.get(Integer.parseInt(ck2ProvInfo[0][1574]))[0] + "is the owner of Province 1574, the religion is" + ck2ProvInfo[2][1574]);
        System.out.println("and the culture is" + ck2ProvInfo[1][1574]);
        int aq4 = 0;
        System.out.println(ck2TagTotals[343]);
        //output
        //DebugChar
        Output.output("CharTest.txt",modDirectory+VM+"history"+VM+"characters"+VM+"CharTest.txt");
        Output.output("defaultOutput"+VM+"cultures"+VM+"00_cultures.txt",modDirectory+VM+"common"+VM+"cultures"+VM+"00_cultures.txt");
        Output.output("defaultOutput"+VM+"cultures"+VM+"50_convertedCultures.txt",modDirectory+VM+"common"+VM+"cultures"+VM+"50_convertedCultures.txt");
        Output.output("defaultOutput"+VM+"religions"+VM+"50_convertedReligions.txt",modDirectory+VM+"common"+VM+"religions"+VM+"50_convertedReligions.txt");
        Output.output("defaultOutput"+VM+"bookmarks"+VM+"50_customBookmark.txt",modDirectory+VM+"common"+VM+"bookmarks"+VM+"50_customBookmark.txt");
        Output.output("defaultOutput"+VM+"bookmarks"+VM+"00_bookmarks.txt",modDirectory+VM+"common"+VM+"bookmarks"+VM+"00_bookmarks.txt");
        Output.output("defaultOutput"+VM+"bloodlines"+VM+"50_convertedBloodlines.txt",modDirectory+VM+"common"+VM+"bloodlines"+VM+"50_convertedBloodlines.txt");

        //eu4Converter
        Output.output("defaultOutput"+VM+"eu4Converter"+VM+"culture_table.csv",modDirectory+VM+"eu4_converter"+VM+"culture_table.csv");
        Output.output("defaultOutput"+VM+"eu4Converter"+VM+"religion_table.csv",modDirectory+VM+"eu4_converter"+VM+"religion_table.csv");
        Output.output("defaultOutput"+VM+"eu4Converter"+VM+"50_romeCultures.txt",modDirectory+VM+"eu4_converter"+VM+"copy"+VM+"common"+VM+"cultures"+VM+"50_romeCultures.txt");
        Output.output("defaultOutput"+VM+"eu4Converter"+VM+"50_romeReligions.txt",modDirectory+VM+"eu4_converter"+VM+"copy"+VM+"common"+VM+"religions"+VM+"50_romeReligions.txt");
        flag = 0;
        String[] Character;

        int aq7 = 0;
        String governor;
        String governorID;
        String[] governorships;
        String govReg;
        String govRegID;
        String[] govCharacter;
        try {
            try {
                while (flag == 0) {

                    if (ck2HasLand[aq4] != null) {
                        if (ck2HasLand[aq4].equals ("yes")) {
                            String tempNum2 = Integer.toString( tempNum + Integer.parseInt(impTagInfo.get(aq4)[16]));

                            Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],"no",impTagInfo.get(aq4)[5],"k","no_liege",modDirectory);

        
                            System.out.println (impTagInfo.get(aq4)[16] + "rules" + impTagInfo.get(aq4)[0] + "_" + aq4);

                            Character = Characters.importChar(saveCharacters,impTagInfo.get(aq4)[16]);
                            Output.characterCreation(tempNum2, Output.cultureOutput(Character[1]),Output.religionOutput(Character[2]),Character[3],Character[0],
                                Character[7],Character[4],Character[8],Character[10],Character[11],Character[12],Character[13],Character[14],Character[15],saveCharacters,
                                "q","q",modDirectory);
                            System.out.println ("c");

                            Characters.importAndConvDynasty(modDirectory,Character[7],Character[16],saveDynasty);

                            String[] locName = importer.importLocalisation(impGameDir,impTagInfo.get(aq4)[19]);
                            output.localizationCreation(locName,impTagInfo.get(aq4)[0],modDirectory);

                            System.out.println(tempTest+impTagInfo.get(aq4)[16] + "_" +Character[3]+Character[0]+Character[7]);
                            System.out.println ("Name is " + locName[0] + " for " +impTagInfo.get(aq4)[0]);
                            System.out.println ("output1");
                            aq7 = 0;
                            //governor conversion
                            if (impTagInfo.get(aq4)[20] != "none") {
                                governorships = impTagInfo.get(aq4)[20].split(",");
                                while (aq7 < governorships.length) {
                                    governor = governorships[aq7].split("~")[1]; 
                                    governorID = Integer.toString(tempNum + Integer.parseInt(governor)); 
                                    govReg = governorships[aq7].split("~")[0]; 
                                    govRegID = impTagInfo.get(aq4)[0]+"__"+govReg; 

                                    Output.titleCreation(govRegID,governorID,Processing.randomizeColor(),"no","none","d",impTagInfo.get(aq4)[0],modDirectory);

                                    govCharacter = Characters.importChar(saveCharacters,governor);

                                    Output.characterCreation(governorID, Output.cultureOutput(govCharacter[1]),Output.religionOutput(govCharacter[2]),govCharacter[3],
                                        govCharacter[0],govCharacter[7],govCharacter[4],govCharacter[8],govCharacter[10],govCharacter[11],govCharacter[12],govCharacter[13],
                                        govCharacter[14],govCharacter[15],saveCharacters,"q","q",modDirectory);

                                    aq7 = aq7 + 1;
                                }

                            }

        
                        }
                    }

                    aq4 = aq4 + 1;
                }

            }catch (java.util.NoSuchElementException exception){
                flag = 1;
                System.out.println("NoSuchElementException and flag = 1");
            }
        }catch (java.lang.ArrayIndexOutOfBoundsException exception){
            flag = 1;
            System.out.println("ArrayIndexOutOfBoundsException and flag = 1" + "_" + aq4);
        }
        aq4 = 0;
        aq7 = 0;
        System.out.println(ck2HasLand[343]);

        String[] bList;
        bList = Processing.importBaronyNameList(modDirectory,aq4,ck2Dir);

        try {
            try {
                while (flag == 1) {

                    if (ck2ProvInfo[1][aq4] != null) {

                        int tempNum2b = 0;

                        String ruler;
                        String gov;

                        String[] importedInfo = Processing.importNames(modDirectory,aq4,ck2Dir);

                        if (ck2ProvInfo[0][aq4].equals ("9999")) { // Dynamically creates a country and character for an uncolonized territory with no owner
                            ruler = Integer.toString((tempNum * 6) + aq4);
                            gov = "tribal_federation";
                            String [] dynLoc = new String[2];
                            dynLoc[0] = importedInfo[0];
                            dynLoc[1] = importedInfo[0]+"ian";
                            String dynRel = ck2ProvInfo[2][aq4];
                            String dynCult = ck2ProvInfo[1][aq4];

                            if (dynRel.charAt(0) == '"') {
                                dynRel = dynRel.substring(1,dynRel.length()-1);    
                            }

                            if (dynCult.charAt(0) == '"') {
                                dynCult = dynCult.substring(1,dynCult.length()-1);    
                            }

                            if (importedInfo[0].charAt(importedInfo[0].length()-1) == 'a' || importedInfo[0].charAt(importedInfo[0].length()-1) == 'e'){
                                dynLoc[1] = importedInfo[0]+"n";    
                            }

                            dynRel = output.religionOutput(dynRel);
                            dynCult = output.cultureOutput(dynCult);

          
                            Output.dynastyCreation("of "+importedInfo[0],ruler,modDirectory);
                            Output.characterCreation(ruler,dynCult,dynRel,"30","Glorious_Debug",ruler,"69","q","5","5","5","5","0","0",saveCharacters,"q","q",modDirectory);
                            Output.titleCreation("dynamic"+aq4,ruler,"40 40 40","no",Integer.toString(aq4),"k","no_liege",modDirectory);
                            output.localizationCreation(dynLoc,"dynamic"+aq4,modDirectory);
                        } else {
                            tempNum2b = Integer.parseInt(ck2ProvInfo[0][aq4]);

                            ruler = impTagInfo.get(tempNum2b)[16];
                            gov = impTagInfo.get(tempNum2b)[17];
                            int tempNum2q = Integer.parseInt(ruler) + tempNum;
                            ruler = Integer.toString(tempNum2q);

                            if (impTagInfo.get(tempNum2b)[20] != "none") { //governors
                                governorships = impTagInfo.get(tempNum2b)[20].split(",");
                                aq7 = 0;
                                while (aq7 < governorships.length) {
                                    govReg = governorships[aq7].split("~")[0];
                                    if (ck2ProvInfo[4][aq4].equals(govReg)) {
                                        ruler = Integer.toString(tempNum + Integer.parseInt(governorships[aq7].split("~")[1]));
                                        aq7 = aq7 + governorships.length;
                                    } else {
                                        aq7 = aq7 + 1;    
                                    }
                                }

                            }

                        }
                        Output.provinceCreation(Integer.toString(aq4),Output.cultureOutput(ck2ProvInfo[1][aq4]),Output.religionOutput(ck2ProvInfo[2][aq4]),
                            modDirectory, importedInfo[1],importedInfo[0],gov,ck2PopTotals[aq4],bList,aq4);

                        Output.ctitleCreation(importedInfo[0],ruler,modDirectory,aq4);
                    }
        
                    aq4 = aq4 + 1;
                }

            }catch (java.util.NoSuchElementException exception){
                flag = 1;
                System.out.println ("Exception1");
            }
        }catch (java.lang.ArrayIndexOutOfBoundsException exception){
            flag = 2;
            System.out.println ("Exception2");
            System.out.println(ck2ProvInfo[1][343] + "_343");
            System.out.println(ck2ProvInfo[1][342] + "_342");
            System.out.println(ck2ProvInfo[1][341] + "_341");
            System.out.println(ck2ProvInfo[1][340] + "_340");
            System.out.println(ck2ProvInfo[1][339] + "_339");

        }
        System.out.println(ck2ProvInfo[1][343] + "_343");
        System.out.println(ck2ProvInfo[1][342] + "_342");
        System.out.println(ck2ProvInfo[1][341] + "_341");
        System.out.println(ck2ProvInfo[1][340] + "_340");
        System.out.println(ck2ProvInfo[1][339] + "_339");
        System.out.println(ck2ProvInfo[1][338] + "_338");

    }

}
