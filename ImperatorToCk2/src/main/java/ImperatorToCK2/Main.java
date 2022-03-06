package ImperatorToCK2;       
   

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main
 *
 * @author The Imperator:Rome to CK II converter was originally developed by Shinymewtwo99
 * 
 */
public class Main
{
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private int x;

    public static void main (String[] args) throws IOException
    {

        try {
            ConverterLogger.setup();
            LOGGER.setLevel(Level.FINEST);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        LOGGER.info("Converter version 0.1A \"Assyrian\" - compatible with Imperator: Rome 1.3-2.0 and Crusader Kings II 3.3");
        LOGGER.finest("0%");

        long startTime = System.nanoTime(); //Starts the converter clock, used to tell how much time has passed

        try {
            Scanner input = new Scanner(System.in);
            String Dir; //Desired user directory, usually located in documents
            String Dir2; //.mod files use reverse slashes (/ instead of \) 
            String modName; //important for creating directories
            String saveName; //The save file (.rome) to read from
            String impDir; //The directory of the save file (.rome) to read from

            Importer importer = new Importer();
            Output output = new Output();
            Directories directories = new Directories();

            //LOGGER.info("Please input your system profile username");

            String[] configDirectories = Importer.importDir("configuration.txt");
            String VM = "\\";
            VM = VM.substring(0);
            String VN = "//";
            VN = VN.substring(0);
            Dir2 = configDirectories[1];
            //Dir = configDirectories[3];
            Dir = "output";

            modName = configDirectories[4].replace(VM,"~~~");//.substring() hates working with \ characters
            modName = modName.replace(VN,"~~~");//.substring() hates working with / characters

            modName = modName.split("~~~")[modName.split("~~~").length-1];

            if (configDirectories[6].equals("")) { //if there is a name or not
                modName = Processing.formatSaveName(modName);
            } else {
                modName = configDirectories[6];
            }
            
            String date = "100.1.1"; //default date in case something goes wrong

            String impGameDir = configDirectories[1];

            String ck2Dir = configDirectories[0];

            String impDirSave = configDirectories[4];
            
            String republicOption = configDirectories[10];

            directories.modFolders (Dir,modName); //Creating the folders to write the mod files
            //along with nessicery sub-folders
            directories.descriptors(Dir,modName,Dir2); //Basic .mod files required for the launcher

            String modDirectory = Dir+VN+modName;

            String[] impProvtoCK;   // Owner Culture Religeon PopTotal Buildings
            impProvtoCK = new String[2];

            String[] impProvInfo;   // Owner Culture Religeon PopTotal Buildings
            impProvInfo = new String[5];

            ArrayList<String[]> impProvInfoList = new ArrayList<String[]>();

            String[][] ck2ProvInfo;   // Array list of array lists...
            ck2ProvInfo = new String[5][8500];

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

            String[] ck2MonumentTotals;   // Province monuments
            ck2MonumentTotals = new String[totalCKProv];

            output.localizationBlankFile(modDirectory); //creates the country localization file

            String[] ck2HasLand;   // If country has land or not in CK II
            ck2HasLand = new String[5000];

            int[] ck2LandTot;   // The ammount of land each country has
            ck2LandTot = new int[5000];

            ArrayList<String> convertedCharacters = new ArrayList<String>(); //characters who have been converted

            convertedCharacters.add("0"); //Debug at id 0 so list will never be empty

            ArrayList<String> impSubjectInfo = new ArrayList<String>(); //Overlord-Subject relations

            ArrayList<String[]> impCharInfoList = new ArrayList<String[]>();

            ArrayList<String> impDynList = new ArrayList<String>();
            
            ArrayList<String> flaggedGovernorships = new ArrayList<String>(); //governorships which have been given flags (GFX)
            
            flaggedGovernorships.add("Glorious__Bingo_region"); //debug so that list starts with 1 item

            String[] impProvRegions = Processing.importRegionList(8500,impGameDir);

            int aqtest = 0;
            while (aqtest < 5000) { //sets the default for all tags as landless in CKII
                ck2HasLand[aqtest] = "no";
                ck2LandTot[aqtest] = 0;
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

            String saveDiplo = "tempDiplo.txt";

            String saveMonuments = "tempMonuments.txt";
            
            ArrayList<String> govMap = Importer.importBasicFile("governmentConversion.txt"); //government mappings
            
            int compressedOrNot = Importer.compressTest(impDirSave); //0 for compressed, 1 for decompressed
            
            if (compressedOrNot == 0) { //compressed save! Initiating Rakaly decompressor
                LOGGER.info("Compressed save detected! Implementing Rakaly Decompressor...");
                
                String newImpDirSave = impDirSave.replace(".rome", "_melted.rome");
                
                String[] rakalyCommand = new String [3];
                rakalyCommand[0] = "rakaly_win.exe";
                rakalyCommand[1] = "melt";
                rakalyCommand[2] = impDirSave;
                Processing.fileExcecute(rakalyCommand);
                impDirSave = newImpDirSave;
                
                LOGGER.info("Decompressed save generated at " + newImpDirSave);
                
            }
            
            String[] saveInfo = Importer.importSaveInfo(impDirSave);
            
            if (saveInfo[0].equals("bad")) {
                LOGGER.warning("Unable to detect save file version! Defaulting to 2.0.3");
                saveInfo[0] = ("2.0.3");
            }
            
            if (saveInfo[1].equals("bad")) {
                LOGGER.warning("Unable to detect save file date! Defaulting to 100.1.1");
                saveInfo[1] = ("100.1.1");
            }
            
            if (configDirectories[7].equals("saveYearAUC")) { //if AUC date is selected
                date = saveInfo[1];
                //System.out.println(date);
            } else if (configDirectories[7].equals("saveYear")){ //if AD date is selected
                //System.out.println(saveInfo[1]);
                String tmpSaveDate = saveInfo[1].replace(".",",");
                int tmpYear = Integer.parseInt(tmpSaveDate.split(",")[0]);
                tmpYear = tmpYear - 754;
                date = date.replace(".",","); //'.' character breaks .split function
                if (tmpYear >= 100) { //use AD version of date
                    date = Integer.toString(tmpYear)+"."+tmpSaveDate.split(",")[1]+"."+tmpSaveDate.split(",")[2];
                } else { //if year is less then 100 AD, game will use 100 AD as year while preserving the day and month
                    date = "100."+tmpSaveDate.split(",")[1]+"."+tmpSaveDate.split(",")[2];
                }
                date = date.replace(",",".");
            } else if (configDirectories[7].equals("customYear")){
                String tmpDate = configDirectories[8].replace(".",","); //'.' character breaks .split function
                int tmpYear = Integer.parseInt(tmpDate.split(",")[0]);
                if (tmpYear >= 100) { //if less then 100 AD, game will use 100 AD
                    date = configDirectories[8];
                }
            }
            
            if (republicOption.equals("bad")) { //If something goes wrong with reading the republic option, default to repMer
                LOGGER.warning("Error with Republic Conversion Option! Defaulting to Merchant Republic");
                republicOption = ("repMer");
            }

            LOGGER.info("Creating temp files...");

            TempFiles.tempCreate(impDirSave, tab+"country_database={", tab+"state_database={", saveCountries);

            LOGGER.info("temp Countries created");

            TempFiles.tempCreate(impDirSave, "provinces={", "}", saveProvinces);

            LOGGER.info("temp Provinces created");   

            TempFiles.tempCreate(impDirSave, "character={", "objectives={", saveCharacters);

            LOGGER.info("temp Characters created");

            TempFiles.tempCreate(impDirSave, tab+"families={", "character={", saveDynasty);

            LOGGER.info("temp Dynasties created");

            TempFiles.tempCreate(impDirSave, "diplomacy={", "jobs={", saveDiplo);

            LOGGER.info("temp Diplo created");

            TempFiles.tempCreate(impDirSave, "great_work_manager={", "country_culture_manager={", saveMonuments);

            LOGGER.info("temp Monuments created");

            long tempTime = System.nanoTime();
            long tempTot = (((tempTime - startTime) / 1000000000)/60) ;

            LOGGER.info("All temp files created after "+ tempTot + " minutes");

            LOGGER.finest("5%");

            LOGGER.info("Importing territory data..."); 

            Processing.combineProvConvList("provinceConversionCore.txt","provinceConversion.txt"); //combines old style mappings and new style mappings

            //processing information

            impProvInfoList = importer.importProv(saveProvinces);
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

                    impProvInfo = impProvInfoList.get(aqq);

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
                    //monuments
                    //if (ck2MonumentTotals[ckProvNum] == (null)) {

                    //    ck2MonumentTotals[ckProvNum] = impProvInfo[5] + "," + impProvInfo[3];
                    //}else {
                    //    ck2MonumentTotals[ckProvNum] = ck2MonumentTotals[ckProvNum] + "~" + impProvInfo[5] + "," + impProvInfo[3];
                    //}

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

            long territoryTime = System.nanoTime();
            long territoryTot = (((territoryTime - startTime) / 1000000000)/60);
            LOGGER.info("Territory data imported after "+ territoryTot + " minutes");

            LOGGER.finest("25%");

            LOGGER.info("Combining territories into provinces...");

            aq2 = 0;
            flag = 0;
            flag2 = 0;
            int aq5 = 0;
            int aq6 = 0;
            String[] irOwners;

            while( aq2 < totalCKProv) { // Calculate province ownership
                if (ck2TagTotals[aq2] != null)  {

                    irOwners = ck2TagTotals[aq2].split("~"); 

                    while (aq5 < irOwners.length) {
                        String[] owners = irOwners[aq5].split(","); 

                        //LOGGER.info(irOwners[aq5]+"_irOwners_"+aq2);  

                        int[] ownerTot;
                        ownerTot = new int[totalCKProv]; //should redefine each time

                        int ownNum = Integer.parseInt(owners[0]);

                        if (ownNum == 9999) {
                            ownNum = 0;

                            //LOGGER.info(aq5);
                        }

                        if (owners[1].equals ("null")) {
                            owners[1] = "0";

                            //LOGGER.info(aq5);
                        }

                        ownerTot[ownNum] = Integer.parseInt(owners[1]);
                        //LOGGER.info(owners[0]+owners[1]+"b_owners");    
                        ck2ProvInfo[0][aq2] = owners[0];
                        aq6 = 1;
                        while (aq6 < totalCKProv) {
                            if (ownerTot[aq6] > ownerTot[aq6-1]){
                                ck2ProvInfo[0][aq2] = owners[0];
                                //LOGGER.info((ck2ProvInfo[0][aq2])+"_"+aq2+"cq");
                            }
                            aq6 = aq6 + 1;

                        }
                        aq5 = aq5 + 1;
                        int tempQ = Integer.parseInt(ck2ProvInfo[0][aq2]);
                        //LOGGER.info(tempQ);
                        if (tempQ != 9999){
                            ck2HasLand[tempQ] = "yes"; //marks country as landed in CK II
                            ck2LandTot[tempQ] = ck2LandTot[tempQ] + 1; //adds tag's CK II province count
                        }

                    }
                    aq5 = 0;

                }
                else if (aq2 < 380) {
                    //LOGGER.info (ck2TagTotals[aq2] + "_" + aq2);    
                }
                aq2 = aq2 + 1;

            }
            long provinceTime = System.nanoTime();
            long provinceTimeTot = (((provinceTime - startTime) / 1000000000)/60);
            LOGGER.info("Province ownership calculated after "+provinceTimeTot+" minutes");
            LOGGER.finest("35%");
            aq2 = 0;
            flag = 0;

            while( aq2 < totalCKProv) { // Combines data based off of majority ownership, 30 Roman pops and 15 Punic'll make CKII prov Roman
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

            ArrayList<String> dejureDuchies = Processing.calculateDuchyNameList(ck2Dir,ck2ProvInfo);

            aq2 = 0;
            LOGGER.info("Province religion and culture calculated");
            LOGGER.info("Province data combined");
            LOGGER.finest("45%");
            LOGGER.info("Importing country data...");

            //LOGGER.config("The region is" + ck2ProvInfo[4][343]);
            int flagCount = 0;

            ArrayList<String[]> impTagInfo = new ArrayList<String[]>();
            //Country processing
            impTagInfo = importer.importCountry(saveCountries);

            long countryTime = System.nanoTime();
            long countryTimeTot = (((countryTime - startTime) / 1000000000)/60);
            LOGGER.info("Country data imported after "+countryTimeTot+" minutes");
            LOGGER.finest("55%");

            LOGGER.config("and the culture is" + ck2ProvInfo[1][343]);

            LOGGER.config("and the culture is" + ck2ProvInfo[1][1574]);
            int aq4 = 0;
            LOGGER.config(ck2TagTotals[343]);

            int totCountries = impTagInfo.size(); //ammount of IR countries in save file

            LOGGER.info("Importing subject data...");

            impSubjectInfo = Importer.importSubjects(saveDiplo);

            long subjectTime = System.nanoTime();
            long subjectTimeTot = (((subjectTime - startTime) / 1000000000)/60);

            LOGGER.info("Subject data imported after "+subjectTimeTot+" minutes");
            LOGGER.finest("65%");

            LOGGER.info("Copying default output...");

            //Default output, will be included in every conversion regardless of what occured in the save file
            Output.copyRaw("defaultOutput"+VM+"cultures"+VM+"00_cultures.txt",modDirectory+VM+"common"+VM+"cultures"+VM+"00_cultures.txt");
            Output.copyRaw("defaultOutput"+VM+"cultures"+VM+"50_convertedCultures.txt",modDirectory+VM+"common"+VM+"cultures"+VM+"50_convertedCultures.txt");
            Output.copyRaw("defaultOutput"+VM+"religions"+VM+"00_religions.txt",modDirectory+VM+"common"+VM+"religions"+VM+"00_religions.txt");
            Output.copyRaw("defaultOutput"+VM+"religions"+VM+"50_convertedReligions.txt",modDirectory+VM+"common"+VM+"religions"+VM+"50_convertedReligions.txt");
            //Output.output("defaultOutput"+VM+"bookmarks"+VM+"50_customBookmark.txt",modDirectory+VM+"common"+VM+"bookmarks"+VM+"50_customBookmark.txt");
            Processing.customDate(date,"defaultOutput"+VM+"bookmarks"+VM+"50_customBookmark.txt",modDirectory+VM+"common"+VM+"bookmarks"+VM+"50_customBookmark.txt");
            Output.output("defaultOutput"+VM+"bookmarks"+VM+"00_bookmarks.txt",modDirectory+VM+"common"+VM+"bookmarks"+VM+"00_bookmarks.txt");
            Output.output("defaultOutput"+VM+"bloodlines"+VM+"50_convertedBloodlines.txt",modDirectory+VM+"common"+VM+"bloodlines"+VM+"50_convertedBloodlines.txt");
            Output.output("defaultOutput"+VM+"bloodlines"+VM+"00_earlyBloodlines.txt",modDirectory+VM+"common"+VM+"bloodlines"+VM+"00_earlyBloodlines.txt");
            Output.output("defaultOutput"+VM+"landed_titles"+VM+"holysites.txt",modDirectory+VM+"common"+VM+"landed_titles"+VM+"holysites.txt");
            Output.output("defaultOutput"+VM+"landed_titles"+VM+"zzz_default_dejure.txt",modDirectory+VM+"common"+VM+"landed_titles"+VM+"zzz_default_dejure.txt");
            Output.output("defaultOutput"+VM+"landed_titles"+VM+"WtWSMS_kingdoms.txt",modDirectory+VM+"common"+VM+"landed_titles"+VM+"WtWSMS_kingdoms.txt");

            //defaultLocalization
            Output.output("defaultOutput"+VM+"localization"+VM+"culture_loc.csv",modDirectory+VM+"localisation"+VM+"culture_loc.csv");
            Output.output("defaultOutput"+VM+"localization"+VM+"religion_loc.csv",modDirectory+VM+"localisation"+VM+"religion_loc.csv");
            Output.output("defaultOutput"+VM+"localization"+VM+"bookmark_loc.csv",modDirectory+VM+"localisation"+VM+"bookmark_loc.csv");
            Output.output("defaultOutput"+VM+"localization"+VM+"zzz_default_dejure_localization.csv",modDirectory+VM+"localisation"+VM+"zzz_default_dejure_localization.csv");
            Output.output("defaultOutput"+VM+"localization"+VM+"irck2_event_loc.csv",modDirectory+VM+"localisation"+VM+"irck2_event_loc.csv");
            Output.output("defaultOutput"+VM+"localization"+VM+"irck2_decision_loc.csv",modDirectory+VM+"localisation"+VM+"irck2_decision_loc.csv");
            Output.output("defaultOutput"+VM+"localization"+VM+"irck2_scope_loc.csv",modDirectory+VM+"localisation"+VM+"irck2_scope_loc.csv");

            //defaultDynasties (Used to dynamically generate random dynasty names for new cultures, otherwise will default to Smith)
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_belgaeDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_belgaeDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_celtic_pannonianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_celtic_pannonianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_gallicDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_gallicDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_leponticDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_leponticDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_celtiberianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_celtiberianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_lusitanianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_lusitanianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_dacianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_dacianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_thracianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_thracianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_carthaginianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_carthaginianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_phoenicianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_phoenicianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_gothicDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_gothicDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_vandalDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_vandalDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_etruscanDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_etruscanDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_rhaetianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_rhaetianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_nuragicDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_nuragicDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_turdetanianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_turdetanianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_ibericDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_ibericDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_old_ligurianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_old_ligurianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_sabellianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_sabellianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_siculianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_siculianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_veneticDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_veneticDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_phrygianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_phrygianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_lycianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_lycianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_isaurianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_isaurianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_paphlagonianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_paphlagonianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_cilicianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_cilicianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_caucasian_albanianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_caucasian_albanianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_babylonianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_babylonianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_manxDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_manxDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_nabateanDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_nabateanDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_hebrewDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_hebrewDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_ancient_egyptianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_ancient_egyptianDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_hellenisticDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_hellenisticDynasties.txt");
            Output.output("defaultOutput"+VM+"dynasties"+VM+"02_aestianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_aestianDynasties.txt");
            
            //Roman split Dynasties
            Output.copyRaw("defaultOutput"+VM+"dynasties"+VM+"02_aromanianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_aromanianDynasties.txt");
            Output.copyRaw("defaultOutput"+VM+"dynasties"+VM+"02_cambrianDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_cambrianDynasties.txt");
            Output.copyRaw("defaultOutput"+VM+"dynasties"+VM+"02_romano_africanDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_romano_africanDynasties.txt");
            Output.copyRaw("defaultOutput"+VM+"dynasties"+VM+"02_palmyreneDynasties.txt",modDirectory+VM+"common"+VM+"dynasties"+VM+"02_palmyreneDynasties.txt");
            
            //defaultDiseases
            Output.output("defaultOutput"+VM+"disease"+VM+"00_disease.txt",modDirectory+VM+"common"+VM+"disease"+VM+"00_disease.txt");
            Output.output("defaultOutput"+VM+"disease"+VM+"01_disease_old.txt",modDirectory+VM+"common"+VM+"disease"+VM+"01_disease_old.txt");
            
            //imperialGovernments
            Output.output("defaultOutput"+VM+"governments"+VM+"imperial_governments.txt",modDirectory+VM+"common"+VM+"governments"+VM+"imperial_governments.txt");
            Output.output("defaultOutput"+VM+"government_flavor"+VM+"50_government_flavor.txt",modDirectory+VM+"common"+VM+"government_flavor"+VM+"50_government_flavor.txt");
            Output.output("defaultOutput"+VM+"laws"+VM+"succession_laws.txt",modDirectory+VM+"common"+VM+"laws"+VM+"succession_laws.txt");
            
            //republicGovernments
            Output.output("defaultOutput"+VM+"governments"+VM+"merchant_republic_governments.txt",modDirectory+VM+"common"+VM+"governments"+VM+"merchant_republic_governments.txt");
            
            //blankedDejureTitles
            Output.output("defaultOutput"+VM+"titles"+VM+"d_angria.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_angria.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_aragon.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_aragon.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_barcelona.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_barcelona.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_bohemia.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_bohemia.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_brandenburg.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_brandenburg.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_bremen.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_bremen.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_burgundy.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_burgundy.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_castilla.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_castilla.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_champagne.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_champagne.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_galicia.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_galicia.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_holstein.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_holstein.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_lausitz.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_lausitz.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_leon.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_leon.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_meissen.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_meissen.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_munster_germany.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_munster_germany.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_navarra.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_navarra.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_poitou.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_poitou.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_saxony.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_saxony.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_asturias.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_asturias.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_porto.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_porto.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_moravia.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_moravia.txt");
            Output.output("defaultOutput"+VM+"titles"+VM+"d_cyrenaica.txt",modDirectory+VM+"history"+VM+"titles"+VM+"d_cyrenaica.txt");
            
            //defaultEvents
            Output.output("defaultOutput"+VM+"events"+VM+"cm_culture_conversion_events.txt",modDirectory+VM+"events"+VM+"cm_culture_conversion_events.txt");
            Output.output("defaultOutput"+VM+"events"+VM+"ir_roman_culture_conversion.txt",modDirectory+VM+"events"+VM+"ir_roman_culture_conversion.txt");
            Output.output("defaultOutput"+VM+"events"+VM+"ir_germanic_culture_conversion.txt",modDirectory+VM+"events"+VM+"ir_germanic_culture_conversion.txt");
            Output.output("defaultOutput"+VM+"events"+VM+"ir_baltic_culture_conversion.txt",modDirectory+VM+"events"+VM+"ir_baltic_culture_conversion.txt");
            Output.output("defaultOutput"+VM+"events"+VM+"ir_christianity_and_schisms.txt",modDirectory+VM+"events"+VM+"ir_christianity_and_schisms.txt");
            
            //defaultDecisions
            Output.output("defaultOutput"+VM+"decisions"+VM+"conversion_decisions.txt",modDirectory+VM+"decisions"+VM+"conversion_decisions.txt");
            Output.output("defaultOutput"+VM+"decisions"+VM+"ir_roman_conversion_decisions.txt",modDirectory+VM+"decisions"+VM+"ir_roman_conversion_decisions.txt");
            Output.output("defaultOutput"+VM+"decisions"+VM+"ir_roman_title_decisions.txt",modDirectory+VM+"decisions"+VM+"ir_roman_title_decisions.txt");
            Output.output("defaultOutput"+VM+"decisions"+VM+"ir_germanic_conversion_decisions.txt",modDirectory+VM+"decisions"+VM+"ir_germanic_conversion_decisions.txt");
            Output.output("defaultOutput"+VM+"decisions"+VM+"ir_baltic_conversion_decisions.txt",modDirectory+VM+"decisions"+VM+"ir_baltic_conversion_decisions.txt");
            Output.output("defaultOutput"+VM+"decisions"+VM+"ir_christianity_and_schisms_decisions.txt",modDirectory+VM+"decisions"+VM+"ir_christianity_and_schisms_decisions.txt");
            
            //defaultMap
            Output.output("defaultOutput"+VM+"map"+VM+"geographical_region.txt",modDirectory+VM+"map"+VM+"geographical_region.txt");
            
            //defaultGFX
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"d_aquileia_church.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+"d_aquileia_church.tga");
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"d_arian.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+"d_arian.tga");
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"d_georgian_orthodox.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+"d_georgian_orthodox.tga");
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"d_insular.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+"d_insular.tga");
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"d_pelagian.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+"d_pelagian.tga");
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"k_nicene.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+"k_nicene.tga");

            long outputTime = System.nanoTime();
            long outputTimeTot = (((outputTime - startTime) / 1000000000)/60);

            LOGGER.info("defaultOutput copied after "+outputTimeTot+" minutes");
            LOGGER.finest("69%");

            flag = 0;
            String[] Character;

            int aq7 = 0;
            String governor;
            String governorID;
            String[] governorships;
            String govReg;
            String govRegID;
            String[] govCharacter;

            int empireRank = 350; //Ammount of holdings to be Empire

            impCharInfoList = Characters.importChar(saveCharacters,compressedOrNot);

            impDynList = Characters.importDynasty(saveDynasty);

            //Array

            try {
                try {
                    while (flag == 0) {

                        if (ck2HasLand[aq4] != null) {
                            if (ck2HasLand[aq4].equals ("yes")) {

                                String tempNum2 = Integer.toString( tempNum + Integer.parseInt(impTagInfo.get(aq4)[16]));
                                String rank = "k";
                                String oldName = impTagInfo.get(aq4)[0]; //used to determine whether or not title is converted

                                int subjectOrNot = Processing.checkSubjectList(aq4,impSubjectInfo);
                                //LOGGER.config("subjectOrNot at " + aq4 + " is " + subjectOrNot);
                                impTagInfo.get(aq4)[17] = Processing.checkGovList(impTagInfo.get(aq4)[17],govMap); //converted government type
                                
                                Character = impCharInfoList.get(Integer.parseInt(impTagInfo.get(aq4)[16]));
                                String rulerDynasty = Characters.searchDynasty(impDynList,Character[7]);

                                if (subjectOrNot == 9999) { //if tag is free or independent
                                    if (ck2LandTot[aq4] >= empireRank || impTagInfo.get(aq4)[17].equals("imperium")) {
                                        rank = "e";
                                    }
                                    impTagInfo.get(aq4)[0] = Processing.convertTitle("titleConversion.txt",rank,impTagInfo.get(aq4)[21],impTagInfo.get(aq4)[0]);
                                    convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],impTagInfo.get(aq4)[17],
                                    impTagInfo.get(aq4)[5],rank,"no_liege",date,republicOption,Character[7],impDynList,impCharInfoList,convertedCharacters,aq4,
                                    impTagInfo.get(aq4)[17],modDirectory);
                                    //LOGGER.info("Free Nation at " + aq4);
                                } else { //if tag is subject
                                    String[] subjectInfo = impSubjectInfo.get(subjectOrNot).split(",");
                                    String overlord = impTagInfo.get(Integer.parseInt(subjectInfo[0]))[0];
                                    String overlordGov = Processing.checkGovList(impTagInfo.get(Integer.parseInt(subjectInfo[0]))[17],govMap);

                                    if (ck2LandTot[Integer.parseInt(subjectInfo[0])] >= empireRank || 
                                        impTagInfo.get(Integer.parseInt(subjectInfo[0]))[17].equals("imperium")) {
                                        //if overlord is empire, make subject kingdom, else make duchy
                                        rank = "k";
                                    } else {
                                        rank = "d";
                                    }

                                    impTagInfo.get(aq4)[0] = Processing.convertTitle("titleConversion.txt",rank,impTagInfo.get(aq4)[21],impTagInfo.get(aq4)[0]);

                                    if (subjectInfo[2].equals ("feudatory") || subjectInfo[2].equals ("satrapy") || subjectInfo[2].equals ("client_state")) { 
                                        //convert as vassal
                                        

                                        convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],impTagInfo.get(aq4)[17],
                                        impTagInfo.get(aq4)[5],rank,overlord,date,republicOption,Character[7],impDynList,impCharInfoList,
                                        convertedCharacters,aq4,overlordGov,modDirectory);
                                        //LOGGER.info("Subject Nation at " + aq4 + " Overlord is " + subjectInfo[0]);
                                    }

                                    else { 
                                        //convert as CK II tributary
                                        //WIP
                                        convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],impTagInfo.get(aq4)[17],
                                        impTagInfo.get(aq4)[5],rank,overlord,date,republicOption,Character[7],impDynList,impCharInfoList,
                                        convertedCharacters,aq4,overlordGov,modDirectory);
                                        //LOGGER.info("Tributary Nation at " + aq4 + " Overlord is " + subjectInfo[0]);
                                    }
                                }

                                //LOGGER.info (impTagInfo.get(aq4)[16] + " rules " + impTagInfo.get(aq4)[0] + "_" + aq4);
                                
                                convertedCharacters = Output.characterCreation(tempNum2, Output.cultureOutput(Character[1]),Output.religionOutput(Character[2]),
                                    Character[3],Character[0],Character[7],Character[4],Character[8],Character[10],Character[11],Character[12],Character[13],Character[14],
                                    Character[15],impTagInfo.get(aq4)[17],"q","q",convertedCharacters,impCharInfoList,date,modDirectory);
                                //LOGGER.config ("c");


                                Output.dynastyCreation(rulerDynasty,Character[7],Character[16],modDirectory);

                                String[] locName = importer.importLocalisation(impGameDir,impTagInfo.get(aq4)[19],rulerDynasty);
                                Output.localizationCreation(locName,impTagInfo.get(aq4)[0],rank,modDirectory);
                                if (oldName.equals(impTagInfo.get(aq4)[0])) {
                                    Output.copyFlag(ck2Dir,modDirectory,rank,impTagInfo.get(aq4)[5],impTagInfo.get(aq4)[0]);
                                }

                                //LOGGER.info(tempTest+impTagInfo.get(aq4)[16] + "_" +Character[3]+Character[0]+Character[7]);
                                //LOGGER.info ("Name is " + locName[0] + " for " +impTagInfo.get(aq4)[0]);
                                //LOGGER.config ("output1");
                                aq7 = 0;
                                String subRank = "d";//rank of governorships, 1 below primary title
                                if (rank.equals("e")) { //Create kingdom tier title of capital region for empire title
                                    subRank = "k";
                                    String capitalColor = Processing.capitalColor(impTagInfo.get(aq4)[3]); //sets the capital region to use different color
                                    
                                    convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,capitalColor,impTagInfo.get(aq4)[17],
                                    impTagInfo.get(aq4)[5],subRank,"no_liege",date,republicOption,Character[7],impDynList,impCharInfoList,convertedCharacters,
                                    aq4,impTagInfo.get(aq4)[17],modDirectory);

                                    
                                    String capitalName = "PROV"+impTagInfo.get(aq4)[5]; //use name of capital for generated kingdom
                                    String[] capitalLoc = Importer.importProvLocalisation(impGameDir,capitalName);
                                    if (capitalLoc[0].equals(capitalName)) { //In the event I:R prov has no name, use CK2 prov name
                                        capitalName = Importer.importConvList("provinceConversion.txt",Integer.parseInt(impTagInfo.get(aq4)[5]))[1];
                                        capitalName = Processing.importNames("a",Integer.parseInt(capitalName),ck2Dir)[0];
                                        capitalLoc = (capitalName+","+capitalName).split(",");
                                    }

                                    Output.localizationCreation(capitalLoc,impTagInfo.get(aq4)[0],subRank,modDirectory);
                                    Output.copyFlag(ck2Dir,modDirectory,subRank,impTagInfo.get(aq4)[5],impTagInfo.get(aq4)[0]); //use flag of empire
                                }
                                
                                impTagInfo.get(aq4)[22] = rank;

                                //governor conversion
                                if (impTagInfo.get(aq4)[20] != "none" && subjectOrNot == 9999) {
                                    governorships = impTagInfo.get(aq4)[20].split(",");
                                    while (aq7 < governorships.length) {
                                        governor = governorships[aq7].split("~")[1]; 
                                        governorID = Integer.toString(tempNum + Integer.parseInt(governor)); 
                                        govReg = governorships[aq7].split("~")[0]; 
                                        govRegID = impTagInfo.get(aq4)[0]+"__"+govReg; 

                                        convertedCharacters = Output.titleCreation(govRegID,governorID,Processing.randomizeColor(),"no","none",subRank,
                                        impTagInfo.get(aq4)[0],date,republicOption,Character[7],impDynList,impCharInfoList,convertedCharacters,aq4,
                                        "govq",modDirectory);
                                        
                                        govCharacter = impCharInfoList.get(Integer.parseInt(governor));
                                        convertedCharacters = Output.characterCreation(governorID, Output.cultureOutput(govCharacter[1]),Output.religionOutput(govCharacter[2]),govCharacter[3],
                                            govCharacter[0],govCharacter[7],govCharacter[4],govCharacter[8],govCharacter[10],govCharacter[11],govCharacter[12],govCharacter[13],
                                            govCharacter[14],govCharacter[15],saveCharacters,"q","q",convertedCharacters,impCharInfoList,date,modDirectory);
                                            
                                        String governorDynasty = Characters.searchDynasty(impDynList,govCharacter[7]);
                                        
                                        Output.dynastyCreation(governorDynasty,govCharacter[7],govCharacter[16],modDirectory);

                                        Output.copyFlag(ck2Dir,modDirectory,subRank,impTagInfo.get(aq4)[5],govRegID); //default flag for governorships

                                        aq7 = aq7 + 1;
                                    }

                                }

                            }
                        }

                        aq4 = aq4 + 1;
                    }

                }catch (java.util.NoSuchElementException exception){
                    flag = 1;
                    //LOGGER.config("NoSuchElementException and flag = 1");
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException exception){
                flag = 1;
                //LOGGER.config("ArrayIndexOutOfBoundsException and flag = 1" + "_" + aq4);
            }
            aq4 = 0;
            aq7 = 0;
            LOGGER.config(ck2HasLand[343]);

            Output.dejureTitleCreation(impTagInfo,empireRank,ck2LandTot,dejureDuchies,impSubjectInfo,modDirectory);

            long titleTime = System.nanoTime();
            long titleTimeTot = (((titleTime - startTime) / 1000000000)/60);
            LOGGER.info("Titles and characters created after "+titleTimeTot+" minutes");
            LOGGER.finest("85%");
            LOGGER.info("Outputting Province info");

            String[] bList;
            bList = Processing.importBaronyNameList(modDirectory,aq4,ck2Dir);

            LOGGER.info("Barony Name List collected");

            try {
                try {
                    while (flag == 1) {

                        if (ck2ProvInfo[1][aq4] != null) {

                            int tempNum2b = 0;

                            String ruler;
                            String gov;

                            String[] importedInfo = Processing.importNames(modDirectory,aq4,ck2Dir);

                            if (ck2ProvInfo[0][aq4].equals ("9999")) {// Dynamically creates a country and character for an uncolonized territory with no owner
                                ruler = Integer.toString((tempNum * 6) + aq4);
                                gov = "tribal";
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
                                } //English adjective endings

                                dynRel = output.religionOutput(dynRel);
                                dynCult = output.cultureOutput(dynCult);

                                String dynCharName = importedInfo[0] + "icus"; //county_name-icus, temporary naming solution instead of Glorious_Debug

                                String dynCharAge = Processing.randomizeAge();

                                LOGGER.info("Uncolonized province at ID "+aq4+", creating chiefdom of "+importedInfo[0]+" led by "+dynCharName);

                                Output.dynastyCreation("of "+importedInfo[0],ruler,"debug",modDirectory);
                                Output.characterCreation(ruler,dynCult,dynRel,dynCharAge,dynCharName,ruler,"69","q","5","5","5","5","0","0",
                                saveCharacters,"q","q",convertedCharacters,impCharInfoList,date,modDirectory);
                                String greyShade = Processing.randomizeColorGrey();

                                convertedCharacters = Output.titleCreation("dynamic"+aq4,ruler,greyShade,"no",Integer.toString(aq4),"d","no_liege",date,republicOption,
                                "noDynasty",impDynList,impCharInfoList,convertedCharacters,aq4,Integer.toString(aq4),modDirectory);
                                Output.localizationCreation(dynLoc,"dynamic"+aq4,"d",modDirectory);
                                Output.copyFlag(ck2Dir,modDirectory,"d",Integer.toString(aq4),"dynamic"+aq4);

                            } else {
                                tempNum2b = Integer.parseInt(ck2ProvInfo[0][aq4]);

                                ruler = impTagInfo.get(tempNum2b)[16];
                                gov = impTagInfo.get(tempNum2b)[17];
                                int tempNum2q = Integer.parseInt(ruler) + tempNum;
                                ruler = Integer.toString(tempNum2q);

                                int subjectOrNot = Processing.checkSubjectList(tempNum2b,impSubjectInfo);

                                if (impTagInfo.get(tempNum2b)[20] != "none" && subjectOrNot == 9999) { //governors without 9999 check, creates hole
                                    governorships = impTagInfo.get(tempNum2b)[20].split(",");
                                    aq7 = 0;
                                    String overlordCapital = impTagInfo.get(tempNum2b)[5];
                                    
                                    overlordCapital = Importer.importConvList("provinceConversion.txt",Integer.parseInt(overlordCapital))[1];
                                    if (overlordCapital.equals("99999")) { //unmapped capital province detected, set 1 as default
                                        LOGGER.info("Warning, I:R province "+impTagInfo.get(tempNum2b)[5]+" is unmapped!");
                                        overlordCapital = "69";
                                    }
                                    String capitalOwner = ck2ProvInfo[0][Integer.parseInt(overlordCapital)].split("__")[0];
                                    int capitalOwnerID = Integer.parseInt(capitalOwner);
                                    if (capitalOwnerID == 9999) { //if capital province is somehow uncolonized, give it to TAG
                                        capitalOwnerID = tempNum2b;
                                    }
                                     capitalOwner = impTagInfo.get(capitalOwnerID)[0];
                                    if (!capitalOwner.equals(impTagInfo.get(tempNum2b)[0])) {
                                        //if capital province is owned by another non-subject TAG, eat all governorships as a precaution
                                        //otherwise, TAG will shatter if all land is not owned directly
                                        int capitalSubjectOrNot = Processing.checkSubjectList(tempNum2b,impSubjectInfo);
                                        if (capitalSubjectOrNot == 9999) { //rival TAG owning capital is free
                                            aq7 = 999;
                                            LOGGER.info(impTagInfo.get(tempNum2b)[0]+" doesn't own it's capital! Initiating anti-shattering proticol");
                                        } else {
                                            String[] capitalTagSubjectInfo = impSubjectInfo.get(subjectOrNot).split(",");
                                            if (impTagInfo.get(Integer.parseInt(capitalTagSubjectInfo[1]))[0] != impTagInfo.get(tempNum2b)[0]) {
                                                //rival tag that is a subject and isn't subject to TAG

                                                aq7 = 999;
                                            }
                                        }
                                    }
                                    while (aq7 < governorships.length) {
                                        govReg = governorships[aq7].split("~")[0];
                                        if (ck2ProvInfo[4][aq4].equals(govReg) && aq4 != Integer.parseInt(overlordCapital)) {
                                            //governorships shall cede capital province of overlord back to overlord
                                            ruler = Integer.toString(tempNum + Integer.parseInt(governorships[aq7].split("~")[1]));
                                            aq7 = aq7 + governorships.length;
                                            int aq8 = 0;
                                            while (aq8 < flaggedGovernorships.size()) { //looks through list to see if flag is assigned
                                                if (flaggedGovernorships.get(aq8).equals(impTagInfo.get(tempNum2b)[0]+"__"+govReg)) {
                                                    aq8 = 99999;
                                                } else {
                                                    aq8 = aq8 + 1;
                                                }
                                            }
                                            if (aq8 != 99999) { //governorship not in list, assign flag for governorship
                                                String rank = "d";
                                                if (impTagInfo.get(tempNum2b)[22].equals("e")) {
                                                    rank = "k";
                                                }
                                                flaggedGovernorships.add(impTagInfo.get(tempNum2b)[0]+"__"+govReg);
                                                String provIDString = (Integer.toString(aq4));
                                                Output.copyFlag(ck2Dir,modDirectory,rank,provIDString,impTagInfo.get(tempNum2b)[0]+"__"+govReg);
                                                
                                                String govLoc = Importer.importConvListR("provinceConversion.txt",aq4)[0];
                                                govLoc = "PROV"+govLoc;
                                                String[] govLocName = Importer.importProvLocalisation(impGameDir,govLoc);
                                                output.localizationCreation(govLocName,impTagInfo.get(tempNum2b)[0]+"__"+govReg,rank,modDirectory);
                                                
                                                
                                            }
                                        } else {
                                            aq7 = aq7 + 1;    
                                        }
                                    }

                                }
                                
                                else if (subjectOrNot != 9999) { //If ruler is subject, check if he/she rules overlord's capital. If yes, give to overlord
                                    String[] subjectInfo = impSubjectInfo.get(subjectOrNot).split(",");
                                    
                                    String overlordCapital = impTagInfo.get(Integer.parseInt(subjectInfo[0]))[5];
                                    overlordCapital = Importer.importConvList("provinceConversion.txt",Integer.parseInt(overlordCapital))[1];
                                    if (overlordCapital.equals(Integer.toString(aq4))) {
                                        ruler = impTagInfo.get(Integer.parseInt(subjectInfo[0]))[16];
                                        gov = impTagInfo.get(Integer.parseInt(subjectInfo[0]))[17];
                                        tempNum2q = Integer.parseInt(ruler) + tempNum;
                                        ruler = Integer.toString(tempNum2q);
                                    } else { //if does not own capital, check if both are merchant republics
                                        String overlordGov = Processing.checkGovList(impTagInfo.get(Integer.parseInt(subjectInfo[0]))[17],govMap);
                                        if (overlordGov.equals("republic") && gov.equals("republic") && republicOption.equals("repMer")) {
                                            gov = "monarchy"; //Merchant republics under merchant republics crash CK2
                                        } //If mrepublic is subject to another mrepublic, become feudal to be playable
                                    }
                                    
                                }

                            }
                            //LOGGER.info("Creating province "+importedInfo[0]+" at ID "+aq4+" ruled by "+ruler);
                            

                            Output.provinceCreation(Integer.toString(aq4),Output.cultureOutput(ck2ProvInfo[1][aq4]),Output.religionOutput(ck2ProvInfo[2][aq4]),
                                modDirectory, importedInfo[1],importedInfo[0],gov,ck2PopTotals[aq4],bList,saveMonuments,republicOption,aq4);

                            Output.ctitleCreation(importedInfo[0],ruler,modDirectory,aq4,date);
                        }

                        aq4 = aq4 + 1;
                    }

                }catch (java.util.NoSuchElementException exception){
                    flag = 1;
                    //LOGGER.config ("Exception1");
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException exception){
                flag = 2;
                //LOGGER.config ("Exception2");
                //LOGGER.config(ck2ProvInfo[1][343] + "_343");
                //LOGGER.config(ck2ProvInfo[1][342] + "_342");
                //LOGGER.config(ck2ProvInfo[1][341] + "_341");
                //LOGGER.config(ck2ProvInfo[1][340] + "_340");
                //LOGGER.config(ck2ProvInfo[1][339] + "_339");

            }
            //LOGGER.config(ck2ProvInfo[1][343] + "_343");
            //LOGGER.config(ck2ProvInfo[1][342] + "_342");
            //LOGGER.config(ck2ProvInfo[1][341] + "_341");
            //LOGGER.config(ck2ProvInfo[1][340] + "_340");
            //LOGGER.config(ck2ProvInfo[1][339] + "_339");
            //LOGGER.config(ck2ProvInfo[1][338] + "_338");

            long endTime = System.nanoTime();
            long elapsedTot = (((endTime - startTime) / 1000000000)/60) ;

            LOGGER.info("Converter successfully finished after " + elapsedTot + " minutes!");

            LOGGER.finest("100%");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.severe(sw.toString());
            throw e;
        }
    }
}

