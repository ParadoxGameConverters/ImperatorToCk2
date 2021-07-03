package ImperatorToCK2;      

import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;

/**
 * Importer deals with importing most information from a save file.
 *
 * @author Shinymewtwo99
 * @version
 */
public class Importer
{

    public static ArrayList<String[]> importProv (String name) throws IOException
    {

        //String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String[]> impProvList= new ArrayList<String[]>();

        String tab = "	";

        int flag = 0;

        String keyWord = tab+1+"={";

        int aqq = 0;

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[6];

        output[0] = "9999"; //default for no owner, uncolonized province
        output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
        output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[3] = "0"; //default for no pops, uncolonized or uninhabitible province
        output[4] = "{ 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 }"; //default for no buildinga
        output[5] = "9999"; //default for no monument

        impProvList.add(output); //default at ID 0

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.equals(keyWord)){
                    endOrNot = false;

                    while (flag == 0) {
                        qaaa = scnr.nextLine();
                        if (qaaa.split("=")[0].equals( tab+tab+"owner" ) ) {
                            output[0] = qaaa.split("=")[1];
                        }
                        if (qaaa.split("=")[0].equals( tab+tab+"culture" ) ) {
                            output[1] = qaaa.split("=")[1];
                            output[1] = output[1].substring(1,output[1].length()-1);
                        }
                        if (qaaa.split("=")[0].equals( tab+tab+"religion" ) ) {
                            output[2] = qaaa.split("=")[1];
                            output[2] = output[2].substring(1,output[2].length()-1);
                        }

                        //popTotal
                        if (qaaa.split("=")[0].equals( tab+tab+"pop" ) ) {
                            aqq = aqq + 1; //count pop
                            output[3] = Integer.toString(aqq);
                        }

                        //might be used or ignored
                        if (qaaa.split("=")[0].equals( tab+tab+"buildings" ) ) {
                            output[4] = qaaa.split("=")[1];

                        }

                        if (qaaa.split("=")[0].equals( tab+tab+"great_work" ) ) {
                            output[5] = qaaa.split("=")[1];

                        }

                        if (qaaa.split("=")[0].equals( tab+"}" ) ) { //ends here

                            String[] tmpOutput = new String[output.length];
                            int aq2 = 0;
                            while (aq2 < output.length) {
                                tmpOutput[aq2] = output[aq2];
                                aq2 = aq2 + 1;
                            }

                            impProvList.add(tmpOutput);

                            output[0] = "9999"; //default for no owner, uncolonized province
                            output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
                            output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
                            output[3] = "0"; //default for no pops, uncolonized or uninhabitible province
                            output[4] = "{ 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 }"; //default for no buildinga
                            output[5] = "9999"; //default for no monument

                            aqq = 0; //reset pop count

                        }

                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return impProvList;

    }

    public static ArrayList<String[]> importCountry (String name) throws IOException
    {

        String tab = "	";

        String VQ2 = "{}q";

        String bracket1 = VQ2.substring(0,1);
        String bracket2 = VQ2.substring(1,2);

        //System.out.println ("Load 1 done");
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        ArrayList<String[]> impTagInfo = new ArrayList<String[]>();

        //double q = 0.0;

        //String idStr = provID.toString();

        String startWord = tab+"country_database={";
        String endWord = tab+"state_database={";

        String keyWord = tab+tab+"}";

        int aqq = 0;
        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[22];

        output[0] = "9999"; //default for no tag
        output[1] = "6969"; //default for no flag seed
        output[2] = "no"; //default for no gender equality laws
        output[3] = "0 0 0"; //default for no color
        output[4] = "0"; //default for no gold
        output[5] = "4529"; //default for no capital (Province of Olbia)
        output[6] = "noCulture"; //default for no culture, uncolonized province with 0 pops";
        output[7] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[8] = "0"; //default for no technology
        output[9] = "0"; //default for no technology
        output[10] = "0"; //default for no technology
        output[11] = "0"; //default for no technology
        output[12] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[13] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[14] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[15] = "0"; //default for no researcher, will either be a random character in CKII or no character
        output[16] = "0"; //default for no leader, will either be a random character in CKII or no character
        output[20] = "none"; //default for no governors/governorships, land will be directly held by the ruler
        output[21] = "9999"; //default for no historical tag used in nation formation

        impTagInfo.add(output); //default entry at ID 0

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (vmm.equals(startWord)){

                    while (flag == 0) {
                        qaaa = scnr.nextLine(); 

                        if (qaaa.equals(keyWord)){
                            flag = 1;

                            while (flag == 1) {
                                qaaa = scnr.nextLine();
                                if (qaaa.split("=")[0].equals( tab+tab+tab+"tag" ) ) {
                                    output[0] = qaaa.split("=")[1];
                                    output[0] = output[0].substring(1,output[0].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"historical" ) ) {
                                    output[21] = qaaa.split("=")[1];
                                    output[21] = output[21].substring(1,output[21].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"country_name" ) ) {
                                    qaaa = scnr.nextLine(); 
                                    output[19] = qaaa.split("=")[1];
                                    output[19] = output[19].substring(1,output[19].length()-1);
                                    if (output[19].equals("CIVILWAR_FACTION_NAME")) {
                                        qaaa = scnr.nextLine();
                                        if (qaaa.split("=")[0].equals(tab+tab+tab+tab+"adjective")) {
                                            qaaa = scnr.nextLine();    
                                        }
                                        if (qaaa.split("=")[0].equals(tab+tab+tab+tab+"base")) {

                                            qaaa = scnr.nextLine();
                                            qaaa = qaaa.split("=")[1];
                                            qaaa = qaaa.substring(1,qaaa.length()-1);
                                            output[19] = "CIVILWAR-"+qaaa;
                                        }
                                    }

                                    //System.out.println ("Load 3 done");
                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"seed" ) ) {
                                    output[1] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"gender_equality" ) ) {
                                    output[2] = qaaa.split("=")[1]; //used for determining inheiratance laws
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"color" ) ) {

                                    output[3] = qaaa.split(tab+tab+tab+"color2")[0];
                                    output[3] = qaaa.substring(15,output[3].length()-2);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"gold" ) ) {
                                    output[4] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"capital" ) ) {
                                    output[5] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"primary_culture" ) ) {
                                    output[6] = qaaa.split("=")[1];
                                    output[6] = output[6].substring(1,output[6].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"religion" ) ) {
                                    output[7] = qaaa.split("=")[1];
                                    output[7] = output[7].substring(1,output[7].length()-1);
                                }
                                //Technology
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"military_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[12] = qaaa.substring(33,qaaa.length()); //researcher
                                    }
                                    qaaa = scnr.nextLine();
                                    output[8] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"civic_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[13] = qaaa.substring(30,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[9] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"oratory_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[14] = qaaa.substring(32,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[10] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"religious_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[15] = qaaa.substring(34,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[11] = qaaa.split("=")[1]; //technology
                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"monarch" ) ) {
                                    output[16] = qaaa.split("=")[1];

                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"government_key" ) ) {
                                    output[17] = qaaa.split("=")[1];
                                    output[17] = output[17].substring(1,output[17].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"succession_law" ) ) {
                                    output[18] = qaaa.split("=")[1];

                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"governorship" ) ) {
                                    qaaa = scnr.nextLine();
                                    String tempReg = qaaa.split("=")[1].substring(1,qaaa.split("=")[1].length()-1);
                                    qaaa = scnr.nextLine();
                                    if (qaaa.split("=")[0].equals (tab+tab+tab+tab+"governor")) {
                                        if (output[20].equals ("none") ) {
                                            output[20] = tempReg + "~" + qaaa.split("=")[1];
                                        }
                                        else {
                                            output[20] =  output[20] + "," + tempReg + "~" + qaaa.split("=")[1];   
                                        }
                                    }

                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"budget_dates" ) ) {

                                    if (output[21].equals("9999")) { //failsafe if somehow there is no historical tag
                                        output[21] = output[0];
                                    }

                                    String[] tmpOutput = new String[output.length];

                                    int aq2 = 0;

                                    while (aq2 < output.length) {
                                        tmpOutput[aq2] = output[aq2];
                                        aq2 = aq2 + 1;
                                    }

                                    impTagInfo.add(tmpOutput);

                                    aqq = aqq + 1;

                                    output[0] = "9999"; //default for no tag
                                    output[1] = "6969"; //default for no flag seed
                                    output[2] = "no"; //default for no gender equality laws
                                    output[3] = "0 0 0"; //default for no color
                                    output[4] = "0"; //default for no gold
                                    output[5] = "4529"; //default for no capital (Province of Olbia)
                                    output[6] = "noCulture"; //default for no culture, uncolonized province with 0 pops";
                                    output[7] = "noReligion"; //default for no religion, uncolonized province with 0 pops
                                    output[8] = "0"; //default for no technology
                                    output[9] = "0"; //default for no technology
                                    output[10] = "0"; //default for no technology
                                    output[11] = "0"; //default for no technology
                                    output[12] = "0"; //default for no researcher, will either be a random character in CKII or no character 
                                    output[13] = "0"; //default for no researcher, will either be a random character in CKII or no character 
                                    output[14] = "0"; //default for no researcher, will either be a random character in CKII or no character 
                                    output[15] = "0"; //default for no researcher, will either be a random character in CKII or no character
                                    output[16] = "0"; //default for no leader, will either be a random character in CKII or no character
                                    output[20] = "none"; //default for no governors/governorships, land will be directly held by the ruler
                                    output[21] = "9999"; //default for no historical tag used in nation formation

                                }
                            }
                        }
                    }   

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return impTagInfo;
    }

    public static ArrayList<String> importSubjects (String name) throws IOException
    {

        //Primarily used for subjects/vassals in IR

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String tab = "	";

        int aqq = 0;
        
        ArrayList<String> currentList = new ArrayList<String>();

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;
        output = new String[5];

        output[0] = "9999"; //default for no overlord, overlord has no subjects
        output[1] = "9999"; //default for no subject
        output[2] = "9999"; //default for no subject relation

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                //overlord
                if (qaaa.split("=")[0].equals( tab+tab+"first" ) ) {
                    output[0] = qaaa.split("=")[1];
                }

                //subject
                if (qaaa.split("=")[0].equals( tab+tab+"second" ) ) {
                    output[1] = qaaa.split("=")[1];
                }
                //subject type
                if (qaaa.split("=")[0].equals( tab+tab+"subject_type" ) ) {
                    output[2] = qaaa.split("=")[1];
                    output[2] = output[2].substring(1,output[2].length()-1);
                    flag = 1; //end loop
                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }

                    currentList.add(tmpOutput[0]+","+tmpOutput[1]+","+tmpOutput[2]);
                    
                    output[0] = "9999";
                    output[1] = "9999";
                    output[2] = "9999";
                }


                flag = 0;
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }

        if (output[1].equals("9999") || output[2].equals("9999")) { //Fallback in case something goes wrong, subject relation won't be converted
            output[0] = "9999"; //default for no overlord, overlord has no subjects
            output[1] = "9999"; //default for no subject
            output[2] = "9999"; //default for no subject relation    
        }

        return currentList;

    }

    public static String[] importDir (String name) throws IOException //Imports directories from configuration.txt
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[7];

        output[0] = "bad"; //default for no CK II game directory
        output[1] = "bad"; //default for no IR game directory
        output[2] = "bad"; //default for no IR mod directory
        output[3] = "bad"; //default for no CK II mod directory
        output[4] = "bad"; //default for no save game directory
        output[5] = "bad"; //default for no selected IR mods
        output[6] = "bad"; //default for no custom CK II mod name

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(" = ")[0].equals("CK2Directory")){
                    output[0] = qaaa.split(" = ")[1];
                    output[0] = output[0].substring(1,output[0].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("ImperatorDirectory")){
                    output[1] = qaaa.split(" = ")[1];
                    output[1] = output[1].substring(1,output[1].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("ImperatorModPath")){
                    output[2] = qaaa.split(" = ")[1];
                    output[2] = output[2].substring(1,output[2].length()-1);
                }
                else if (qaaa.split(" = ")[0].equals("targetGameModPath")){
                    output[3] = qaaa.split(" = ")[1];
                    output[3] = output[3].substring(1,output[3].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("SaveGame")){
                    output[4] = qaaa.split(" = ")[1];
                    output[4] = output[4].substring(1,output[4].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("selectedMods")){
                    output[5] = qaaa.split(" = ")[1];

                }
                else if (qaaa.split(" = ")[0].equals("output_name")){
                    output[6] = qaaa.split(" = ")[1];
                    output[6] = output[6].substring(1,output[6].length()-1);

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importConvList (String name, int provIDnum) throws IOException //Checks old format first, then new format
    {

        String provID = Integer.toString(provIDnum);
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provID)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importCultList (String name, String provIDnum) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provIDnum)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importLocalisation (String directory, String tag, String dynasty) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String[] output;
        output = new String[2];
        String provOrName = "debug";
        String revoltName = "no";
        int usesDynasty = 0;

        try { 
            if (tag.split("-")[0].equals("CIVILWAR")) { //For the opposing side of a country undergoing civil war
                revoltName = tag.split("-")[1];
            }
        }catch (java.lang.NullPointerException exception){
            revoltName = "no";
        }

        if (dynasty.equals("00Region00")) {

            output = importRegionLocalisation(directory,tag);    

        }

        else if (revoltName.equals("no")) {

            try { 
                provOrName = tag.split("OV")[0];

            }catch (java.lang.NullPointerException exception){
                provOrName = "debug2";

            }

            if (provOrName.equals ("PR")) {
                output = importProvLocalisation(directory,tag);
            }
            else {  

                String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "countries_l_english.yml";

                FileInputStream fileIn= new FileInputStream(name);
                Scanner scnr= new Scanner(fileIn);

                int flag = 0;

                boolean endOrNot = true;

                String qaaa;
                qaaa = scnr.nextLine();

                output[0] = tag; //default for no name, will just use tag ID
                output[1] = tag; //default for no adjective, will just use tag ID

                try {
                    while (endOrNot = true){

                        qaaa = scnr.nextLine();

                        if (qaaa.split(":")[0].equals(" "+tag)){
                            output[0] = qaaa.split(":")[1];
                            output[0] = output[0].substring(3,output[0].length()-1);

                        }

                        if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE")){
                            endOrNot = false;
                            output[1] = qaaa.split(":")[1];
                            output[1] = output[1].substring(3,output[1].length()-1);

                        }
                    }

                }catch (java.util.NoSuchElementException exception){
                    endOrNot = false;

                }   

            }

            if (output[0].equals (tag) && usesDynasty != 1) {
                output = importFormableLocalisation(directory,tag);   
            }

        }
        else {

            String[] revoltNames = importLocalisation (directory, revoltName, dynasty);
            output[0] = revoltNames[1] + " Revolt";
            output[1] = revoltNames[1] + " Revolter";
        }

        if (output[0].charAt(0) == '[') { //For countries which use a dynasty name for their country, like the Seleukid Empire
            output[1] = dynasty;
            if (output[1].charAt(output[1].length()-1) == 'a') {
                output[1] = output[1] + "n";    
            }
            output[0] = output[1] + " Empire"; //may change out Empire for country rank
        } else if (output[0].equals(tag)) {
            output = importAreaLocalisation(directory,tag);    
        } 

        if (output[0].equals(tag)) { //Mod support
            output = importCustCountryLocalisation (tag);    
        }

        if (output[0].equals(tag)) {
            output = importRegionLocalisation(directory,tag);    
        }

        return output;

    }

    public static String[] importProvLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "provincenames_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importAreaLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "regionnames_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importCustCountryLocalisation (String tag) throws IOException //Supported Loc for popular IR mods
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = "supportedModLoc.txt";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importRegionLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "macroregions_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importFormableLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];
        String provOrName = "debug";

        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "nation_formation_l_english.yml";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String tag2 = tag;

        try {
            tag2 = tag.split("_NAM")[0];  
        }

        catch (java.lang.NullPointerException exception){

        }

        output[0] = tag; //default for no name, will just use tag ID
        output[1] = tag; //default for no adjective, will just use tag ID

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);

                }

                if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE") || 
                qaaa.split(":")[0].equals(" "+tag2+"_ADJ") || qaaa.split(":")[0].equals(" "+tag2+"_ADJECTIVE")){
                    endOrNot = false;
                    output[1] = qaaa.split(":")[1];
                    output[1] = output[1].substring(3,output[1].length()-1);
                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static ArrayList<String> importModLocalisation (String directory) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        ArrayList<String> oldFile = new ArrayList<String>();
        char quote = '"';

        String name = directory + VM + "localisation" + VM + "converted_title_localisation.csv";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                oldFile.add(qaaa);

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return oldFile;

    }

    public static ArrayList<String> importBasicFile (String directory) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        ArrayList<String> oldFile = new ArrayList<String>();
        char quote = '"';

        FileInputStream fileIn= new FileInputStream(directory);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                oldFile.add(qaaa);

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return oldFile;

    }
    
    public static String[] importDejureList (String name, String provIDnum) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // culture e_title k_title
        output = new String[3];

        output[0] = "nomap"; //default for no mapping
        output[1] = "nomap"; //default for no mapping
        output[2] = "nomap"; //default for no mapping

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provIDnum)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];
                    output[2] = qaaa.split(",")[2];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }
    
    public static ArrayList<String> importDuchyNameList (String ck2Dir) throws IOException //imports dejure duchies, along with the counties they go to
    {

        String VM = "\\";
        VM = VM.substring(0);
        String tab = "	";

        FileInputStream fileIn= new FileInputStream(ck2Dir+VM+"common"+VM+"landed_titles"+VM+"landed_titles.txt");
        Scanner scnr= new Scanner(fileIn);

        String ckName = "debug";

        String qaaa = scnr.nextLine();
        String provName = "noProv";
        String tab3 = tab+tab+tab;

        String duchyWord = tab+tab+"d"; //Word used to identify duchies
        String duchyWord2 = "        d"; //Bohemia and Moravia use different formatting
        String countyWord = tab3+"c";
        String countyWord2 = "            "+tab+"c";//Bohemia and Moravia use different formatting
        String countyWord3 = "            c";
        String countyWord4 = tab3+tab+"c";

        ArrayList<String> duchies = new ArrayList<String>();

        int aqq = 1;
        int endOrNot = 0;

        int flag2 = 0;

        String duchyList = " ";

        try {
            while (endOrNot == 0){

                if (qaaa.split("_")[0].equals(duchyWord) || qaaa.split("_")[0].equals(duchyWord2)) {

                    duchies.add(duchyList);

                    duchyList = qaaa.split(" = ")[0].replace(tab,"");
                    aqq = aqq + 1;
                }

                if (qaaa.split("_")[0].equals(countyWord) || qaaa.split("_")[0].equals(countyWord2) || qaaa.split("_")[0].equals(countyWord3) ||
                    qaaa.split("_")[0].equals(countyWord4)) {
                    String county = qaaa.split(" = ")[0].replace(tab,"");
                    duchyList = duchyList + "," + county;
                    qaaa = scnr.nextLine();
                }

                qaaa = scnr.nextLine();

                aqq = aqq + 1;

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = 1;

        }   
        return duchies;

    }
    
    
    //developed originally by Shinymewtwo99
}
