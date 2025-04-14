package com.paradoxgameconverters.irtockii;

import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;

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
        output = new String[25];

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
        output[22] = "k"; //default for no rank, ranks are not stored in the save file, will be calculated during output
        output[23] = "noFlag"; //default for no flag
        output[24] = "4529"; //default for no original capital (Province of Olbia)

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
                                            if (qaaa.equals("CIVILWAR_FACTION_NAME")) {
                                                int civilWarFlag = 0;
                                                while (civilWarFlag == 0) {
                                                    qaaa = scnr.nextLine();
                                                    qaaa = qaaa.replace(tab,"");
                                                    if (qaaa.split("=")[0].equals("name")) {
                                                        qaaa = qaaa.split("=")[1];
                                                        qaaa = qaaa.substring(1,qaaa.length()-1);
                                                        if (!qaaa.equals ("CIVILWAR_FACTION_NAME")) {
                                                            civilWarFlag = 1;
                                                        }
                                                    }
                                                }

                                            }
                                            output[19] = "CIVILWAR-"+qaaa;
                                        }
                                    }

                                    //System.out.println ("Load 3 done");
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"flag" ) ) {
                                    output[23] = qaaa.split("=")[1];
                                    output[23] = output[23].substring(1,output[23].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"seed" ) ) {
                                    output[1] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"gender_equality" ) ) {
                                    output[2] = qaaa.split("=")[1]; //will be used for determining inheiratance laws
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"color" ) ) {

                                    if (qaaa.length() == 14) { //Rakaly save format
                                        qaaa = scnr.nextLine();
                                        output[3] = qaaa.replace(tab,"");
                                    } else { //regular decompressed save format

                                        output[3] = qaaa.split(tab+tab+tab+"color2")[0];
                                        output[3] = qaaa.substring(15,output[3].length()-2);
                                    }
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
                                    
                                    output[24] = output[5]; //set original capital not to be changed

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
                                    output[22] = "k"; //default for no rank, ranks are not stored in the save file, will be calculated during output
                                    output[23] = "noFlag"; //default for no flag
                                    output[24] = "4529"; //default for no original capital (Province of Olbia)

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
        output = new String[13];

        output[0] = "bad"; //default for no CK II game directory
        output[1] = "bad"; //default for no IR game directory
        output[2] = "bad"; //default for no IR mod directory
        output[3] = "bad"; //default for no CK II mod directory
        output[4] = "bad"; //default for no save game directory
        output[5] = "bad"; //default for no selected IR mods
        output[6] = "bad"; //default for no custom CK II mod name
        output[7] = "bad"; //default for no year choice
        output[8] = "bad"; //default for no custom year date
        output[9] = "bad"; //default for no dejure conversion
        output[10] = "bad"; //default for no republic conversion
        output[11] = "bad"; //default for no nomad conversion
        output[12] = "bad"; //default for no big mod selection

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
                else if (qaaa.split(" = ")[0].equals("year")){
                    output[7] = qaaa.split(" = ")[1];
                    output[7] = output[7].substring(1,output[7].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("customYearDate")){
                    output[8] = qaaa.split(" = ")[1];
                    output[8] = output[8].substring(1,output[8].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("dejure")){
                    output[9] = qaaa.split(" = ")[1];
                    output[9] = output[9].substring(1,output[9].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("republic")){
                    output[10] = qaaa.split(" = ")[1];
                    output[10] = output[10].substring(1,output[10].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("nomad")){
                    output[11] = qaaa.split(" = ")[1];
                    output[11] = output[11].substring(1,output[11].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("bigMods")){
                    output[12] = qaaa.split(" = ")[1];
                    output[12] = output[12].substring(1,output[12].length()-1);

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

    public static String[] importConvListR (String name, int provIDnum) throws IOException //Reverse of importConvList
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

                try {

                    if (qaaa.split(",")[1].equals(provID)){
                        endOrNot = false;
                        output[0] = qaaa.split(",")[0];
                        output[1] = qaaa.split(",")[1];

                    }

                } catch (java.lang.ArrayIndexOutOfBoundsException exception) {

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

    public static String[] importLocalisation (ArrayList<String> locList, String tag, String dynasty) throws IOException
    {
        char quote = '"';
        String[] output;
        output = new String[2];
        String revoltName = "no";
        int usesDynasty = 0;

        try { 
            if (tag.split("-")[0].equals("CIVILWAR")) { //For the opposing side of a country undergoing civil war
                revoltName = tag.split("-")[1];
            }
        }catch (java.lang.NullPointerException exception){
            revoltName = "no";
        }

        if (revoltName.equals("no")) {

            output[0] = tag; //default for no name, will just use tag ID
            output[1] = tag; //default for no adjective, will just use tag ID
            int aqq = 0;
            boolean name = false;

            try {
                while (aqq < locList.size()){

                    String qaaa = locList.get(aqq);
                    qaaa = qaaa.replace(": ",":0 ");
                    try {
                        if (qaaa.charAt(0) != ' ') {//If loc lacks leading space, add a space
                            qaaa = " " + qaaa;
                        }
                    } catch (java.lang.StringIndexOutOfBoundsException exception) {
                        
                    }

                    if (qaaa.split(":")[0].equals(" "+tag)){
                        output[0] = qaaa.split(":")[1];
                        output[0] = output[0].substring(3,output[0].length()-1);
                        name = true;

                    }

                    String adjName = tag.replace("_FEUDATORY_NAME","_FEUDATORY_ADJECTIVE"); //for certain mission tags which have different formatting
                    adjName = adjName.replace("_NAME","");

                    if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE")
                    || qaaa.split(":")[0].equals(" "+adjName+"_ADJ") || qaaa.split(":")[0].equals(" "+adjName+"_ADJECTIVE")){
                        if (name) {
                          aqq = locList.size() + 1;  
                        }
                        output[1] = qaaa.split(":")[1];
                        output[1] = output[1].substring(3,output[1].length()-1);

                    } else {
                        if (output[1].charAt(output[1].length()-1) == 'a') {
                            output[1] = output[0] + "n";    
                        } else {
                            output[1] = output[0];
                        }
                    }

                    aqq = aqq + 1;
                }

            }catch (java.util.NoSuchElementException exception){
                aqq = locList.size() + 1;

            }   

        }
        else {

            String[] revoltNames = importLocalisation (locList, revoltName, dynasty);
            output[0] = revoltNames[1] + " Revolt";
            output[1] = revoltNames[1] + " Revolter";
        }
        
        output[0] = output[0].split(quote+" #")[0];
        output[1] = output[1].split(quote+" #")[0];

        if (output[0].charAt(0) == '[') { //For countries which use a dynasty name for their country, like the Seleukid Empire
            output[1] = dynasty;
            if (output[1].charAt(output[1].length()-1) == 'a') {
                output[1] = output[1] + "n";    
            }
            output[0] = output[1] + " Empire"; //may change out Empire for country rank
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
        output[1] = "e_nomap"; //default for no mapping
        output[2] = "k_nomap"; //default for no mapping

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

        String qaaa = scnr.nextLine();
        String tab3 = tab+tab+tab;

        String duchyWord = tab+tab+"d"; //Word used to identify duchies
        String duchyWord2 = "        d"; //Bohemia and Moravia use different formatting
        String countyWord = tab3+"c";
        String countyWord2 = "            "+tab+"c";//Bohemia and Moravia use different formatting
        String countyWord3 = "            c";
        String countyWord4 = tab3+tab+"c";
        String countyWord5 = "           "+tab+tab+"c";//Bohemia again
        String countyWord6 = "           "+tab+"c";//Bohemia

        ArrayList<String> duchies = new ArrayList<String>();

        int aqq = 1;
        int endOrNot = 0;

        String duchyList = " ";

        try {
            while (endOrNot == 0){

                if (qaaa.split("_")[0].equals(duchyWord) || qaaa.split("_")[0].equals(duchyWord2)) {

                    duchies.add(duchyList);

                    duchyList = qaaa.split(" = ")[0].replace(tab,"");
                    duchyList = duchyList.replace(" ",""); //Bohemia
                    duchyList = duchyList.split("=")[0]; //Gondar
                    aqq = aqq + 1;
                }

                if (qaaa.split("_")[0].equals(countyWord) || qaaa.split("_")[0].equals(countyWord2) || qaaa.split("_")[0].equals(countyWord3) ||
                qaaa.split("_")[0].equals(countyWord4) || qaaa.split("_")[0].equals(countyWord5) || qaaa.split("_")[0].equals(countyWord6)) {
                    String county = qaaa.split(" = ")[0].replace(tab,"");
                    county = county.replace(" ","");//Bohemia
                    county = county.split("=")[0]; //Pecs
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

    public static int compressTest (String saveDir) throws IOException //Checks if the file is compressed or not
    {

        String VM = "\\";
        VM = VM.substring(0);
        String tab = "	";

        FileInputStream fileIn= new FileInputStream(saveDir);
        Scanner scnr= new Scanner(fileIn);

        String qaaa = scnr.nextLine();

        int compressedOrNot = 0; //0 for compressed, 1 for decompressed

        int aqq = 1;

        try {
            while (aqq != 10){ //loop for the first 10 lines (potential futureproofing), if the key is there, decompressed. Else, compressed
                String testParameter = qaaa.split("=")[0];
                if (testParameter.equals("save_game_version") || testParameter.equals("version")) {

                    compressedOrNot = 1;
                }

                qaaa = scnr.nextLine();
                aqq = aqq + 1;
            }
        }catch (java.util.NoSuchElementException exception){
            aqq = 10;

        }   
        return compressedOrNot;

    }

    public static String[] importSaveInfo (String directory) throws IOException //imports basic save info, like version
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        FileInputStream fileIn= new FileInputStream(directory);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "bad"; //default for no version
        output[1] = "bad"; //default for no date
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split("=")[0].equals("version")){
                    output[0] = qaaa.split("=")[1];
                }

                if (qaaa.split("=")[0].equals("date")){
                    output[1] = qaaa.split("=")[1];
                    endOrNot = false;
                }

                if (qaaa.split("=")[0].equals("variables")){ //end of save has been found without finding save date
                    endOrNot = false;
                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static ArrayList<String[]> importFlag (String name) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String[]> impFlagList= new ArrayList<String[]>();

        String tab = "	";
        char quote = '"';
        String strQuote = quote+"_";
        strQuote = strQuote.split("_")[0];
        int flag = 0;

        String qaaa;
        String[] output;
        output = new String[5];

        output[0] = "unnamedFlag"; //default for no tag/name
        output[1] = "pattern_solid.tgaq"; //default for no pattern
        output[2] = "none"; //default for color1, format = hsvOrRgb,r g b
        output[3] = "none"; //default for color2, format = hsvOrRgb,r g b
        output[4] = "0"; //default for no emblems, format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)
        //~_~ divides aspects of an emblem, ~~ divides emblems

        impFlagList.add(output); //default at ID 0

        try {

            while (flag == 0) {
                qaaa = scnr.nextLine();
                qaaa = qaaa.replace(" = ","=");
                qaaa = qaaa.replace("= ","=");
                qaaa = qaaa.replace(" =","=");
                qaaa = qaaa.replace(quote+" ",quote+"");
                qaaa = qaaa.replace("    ",tab); //Some flags have strange formatting
                qaaa = qaaa.replace(" color2=",tab+"color2="); //Some flags have strange formatting
                if (qaaa.contains("#") && !qaaa.equals("#") && qaaa.charAt(0)!= '#') {
                    qaaa = qaaa.split("#")[0];
                }
                if (qaaa.contains( "=" ) && output[0].equals("unnamedFlag")) {
                    output[0] = qaaa.split("=")[0];
                }
                if (qaaa.split("=")[0].equals(tab+"pattern") ) {
                    output[1] = qaaa.split("=")[1];
                    output[1] = output[1].replace(" ","");
                    output[1] = output[1].substring(1,output[1].length()-1);
                }
                if (qaaa.split("=").length != 2) { //if using unusual formatting
                    if (qaaa.contains("color1=")) {
                        output[2] = qaaa.split(tab+"color1=")[1];
                        output[2] = output[2].split(tab+"color2=")[0];
                        output[2] = output[2].replace(tab,"");
                        output[2] = output[2].replace("  "," ");
                    }
                    if (qaaa.contains("color2=")) {
                        output[3] = qaaa.split("color2=")[1];
                        output[3] = output[3].split(tab+"color3=")[0];
                        output[3] = output[3].split("   "+"color3=")[0];
                        output[3] = output[3].replace(tab,"");
                        output[3] = output[3].replace("  "," ");
                    }
                } else { //if using regular formatting
                    if (qaaa.split("=")[0].equals(tab+"color1") ) {
                        output[2] = qaaa.split("=")[1];
                        output[2] = output[2].replace("  "," ");
                        if (qaaa.contains("rgb ")) {
                            output[2] = output[2].split("rgb ")[1];
                            output[2] = output[2].split(tab)[0]; //Invictus formatting
                            output[2] = "rgb," + output[2].substring(2,output[2].length()-2);
                        }
                        else if (qaaa.contains("hsv ")) {
                            output[2] = output[2].split("hsv ")[1];
                            output[2] = output[2].split(tab)[0]; //Invictus formatting
                            output[2] = "hsv," + output[2].substring(2,output[2].length()-2);
                        } else {
                            if (output[2].contains(strQuote)) {
                                output[2] = output[2].substring(1,output[2].length()-1);
                            }
                        }
                        output[2] = output[2].replace(tab,"");
                    }
                    if (qaaa.split("=")[0].equals(tab+"color2") ) {
                        output[3] = qaaa.split("=")[1];
                        output[3] = output[3].replace("  "," ");
                        if (qaaa.contains("rgb ")) {
                            output[3] = output[3].split("rgb ")[1];
                            output[3] = output[3].split(tab)[0]; //Invictus formatting
                            output[3] = "rgb," + output[3].substring(2,output[3].length()-2);
                        }
                        else if (qaaa.contains("hsv ")) {
                            output[3] = output[3].split("hsv ")[1];
                            output[3] = output[3].split(tab)[0]; //Invictus formatting
                            output[3] = "hsv," + output[3].substring(2,output[3].length()-2);
                        } else {
                            if (output[3].contains(strQuote)) {
                                output[3] = output[3].substring(1,output[3].length()-1);
                            }
                        }
                        output[3] = output[3].replace(tab,"");
                    }
                }

                //emblem
                if (qaaa.contains("colored_emblem") || qaaa.contains("textured_emblem")) {
                    String embTexture = "noTexture";
                    String embColor1 = "noColor1";
                    String embColor2 = "noColor2";
                    String embScale = "none";
                    String embPos = "none";
                    String embRot = "none";
                    int instanceYes = 0; //If emblem has no intances, use colored emblem as instance, 0 for no 1 for yes
                    String tmpOutput = "0";
                    int endLoop = 0;
                    while (!qaaa.equals(tab+"}") && endLoop == 0) {
                        qaaa = scnr.nextLine();
                        qaaa = qaaa.replace(" = ","=");
                        qaaa = qaaa.replace("= ","=");
                        qaaa = qaaa.replace(" =","=");
                        qaaa = qaaa.replace(quote+" ",quote+"");
                        qaaa = qaaa.replace("    ",tab); // to fix Invictus Judea Countries file formatting
                        
                        qaaa = qaaa.replace(" color2=",tab+"color2="); // to fix Invictus Barbaricum Countries file formatting
                        qaaa = qaaa.replace(" texture=",tab+"texture="); // to fix Invictus Barbaricum Countries file formatting
                        if (qaaa.contains("#") && !qaaa.equals("#")) {
                            qaaa = qaaa.split("#")[0];
                        }
                        if (qaaa.split("=").length != 2) { //if using unusual formatting
                            if (qaaa.contains("texture=")) {
                                embTexture = qaaa.split("texture=")[1];
                                embTexture = embTexture.split(" ")[0];
                                embTexture = embTexture.split(tab)[0];
                                embTexture = embTexture.substring(1,embTexture.length()-1);
                            }
                            if (qaaa.contains("color1=")) {
                                embColor1 = qaaa.split("color1=")[1];
                                embColor1 = embColor1.split(" ")[0];
                                embColor1 = embColor1.split(tab)[0];
                                embColor1 = embColor1.replace(tab,"");
                                embColor1 = embColor1.replace("  "," ");
                                if (embColor1.contains("rgb ")) {
                                    embColor1 = embColor1.split("rgb ")[1];
                                    embColor1 = embColor1.split(tab)[0]; //Invictus formatting
                                    embColor1 = "rgb," + embColor1.substring(2,embColor1.length()-2);
                                }
                                else if (qaaa.contains("hsv ")) {
                                    embColor1 = embColor1.split("hsv ")[1];
                                    embColor1 = embColor1.split(tab)[0]; //Invictus formatting
                                    embColor1 = "hsv," + output[2].substring(2,output[2].length()-2);
                                }
                            }
                            if (qaaa.contains("color2=")) {
                                embColor2 = qaaa.split("color2=")[1];
                                embColor2 = embColor2.split(tab+"color3=")[0];
                                embColor2 = embColor2.split(" ")[0];
                                embColor2 = embColor2.split(tab)[0];
                                embColor2 = embColor2.replace(tab,"");
                                embColor2 = embColor2.replace("  "," ");
                                if (embColor2.contains("rgb ")) {
                                    embColor2 = embColor2.split("rgb ")[1];
                                    embColor2 = embColor2.split(tab)[0]; //Invictus formatting
                                    embColor2 = "rgb," + embColor2.substring(2,embColor2.length()-2);
                                }
                                else if (qaaa.contains("hsv ")) {
                                    embColor2 = embColor2.split("hsv ")[1];
                                    embColor2 = embColor2.split(tab)[0]; //Invictus formatting
                                    embColor2 = "hsv," + embColor2.substring(2,embColor2.length()-2);
                                }
                            }
                        } else { //regular formatting

                            if (qaaa.contains( "texture=" ) || qaaa.contains( "texture =" ) ) {
                                embTexture = qaaa.split("=")[1];
                                embTexture = embTexture.split(tab)[0];
                                embTexture = embTexture.substring(1,embTexture.length()-1);
                            }
                            else if (qaaa.contains( "color1=" ) || qaaa.contains("color1 =") ) {
                                embColor1 = qaaa.split("=")[1];
                                if (embColor1.contains(strQuote)) {
                                    embColor1 = embColor1.substring(1,embColor1.length()-1);
                                }
                                embColor1 = embColor1.replace(tab,"");
                                embColor1 = embColor1.replace("  "," ");
                                if (embColor1.contains("rgb ")) {
                                    embColor1 = embColor1.split("rgb ")[1];
                                    embColor1 = embColor1.split(tab)[0]; //Invictus formatting
                                    embColor1 = "rgb," + embColor1.substring(2,embColor1.length()-2);
                                }
                                else if (qaaa.contains("hsv ")) {
                                    embColor1 = embColor1.split("hsv ")[1];
                                    embColor1 = embColor1.split(tab)[0]; //Invictus formatting
                                    embColor1 = "hsv," + embColor1.substring(2,embColor1.length()-2);
                                }
                            }
                            else if (qaaa.contains( "color2=" ) || qaaa.contains("color2 =") ) {
                                embColor2 = qaaa.split("=")[1];
                                if (embColor2.contains(strQuote)) {
                                    embColor2 = embColor2.substring(1,embColor2.length()-1);
                                }
                                embColor2 = embColor2.replace(tab,"");
                                embColor2 = embColor2.replace("  "," ");
                                if (embColor2.contains("rgb ")) {
                                    embColor2 = embColor2.split("rgb ")[1];
                                    embColor2 = embColor2.split(tab)[0]; //Invictus formatting
                                    embColor2 = "rgb," + embColor2.substring(2,embColor2.length()-2);
                                }
                                else if (qaaa.contains("hsv ")) {
                                    embColor2 = embColor2.split("hsv ")[1];
                                    embColor2 = embColor2.split(tab)[0]; //Invictus formatting
                                    embColor2 = "hsv," + embColor2.substring(2,output[2].length()-2);
                                }
                            }
                        }
                        if (qaaa.contains("instance=") || qaaa.contains("instance =") || qaaa.equals("}")) { //get instances
                            instanceYes = 1;
                            if (qaaa.equals("}")) {
                                endLoop = 1;
                            }
                            if (qaaa.split("=").length != 2) { //unusual formatting
                                if (qaaa.contains("scale=")) {
                                    embScale = qaaa.split("scale=")[1];
                                    embScale = embScale.replace("  "," ");
                                    embScale = embScale.split(" }")[0];
                                    embScale = embScale.split(tab)[0];
                                    embScale = embScale.substring(2,embScale.length());
                                }
                                if (qaaa.contains("position=")) {
                                    embPos = qaaa.split("position=")[1];
                                    embPos = embPos.split(" }")[0];
                                    embPos = embPos.split(tab)[0];
                                    embPos = embPos.substring(2,embPos.length());
                                }
                                if (qaaa.contains("rotation=")) {
                                    embRot = qaaa.split("rotation=")[1];
                                    embRot = embRot.split(" ")[0];
                                    embRot = embRot.split(tab)[0];
                                }
                                if (tmpOutput.equals("0")) { //build emblem
                                    tmpOutput = embTexture+"~_~"+embColor1+"~_~"+embColor2+"~_~"+embScale+"~_~"+embPos+"~_~"+embRot;
                                } else {
                                    tmpOutput = tmpOutput+"~~"+embTexture+"~_~"+embColor1+"~_~"+embColor2+"~_~"+embScale+"~_~"+embPos+"~_~"+embRot;
                                }
                                embScale = "none";
                                embPos = "none";
                                embRot = "none";
                            }
                            else { //normal formatting

                                while (!qaaa.equals(tab+"}") ) {
                                    qaaa = scnr.nextLine();
                                    qaaa = qaaa.replace(" = ","=");
                                    qaaa = qaaa.replace("= ","=");
                                    qaaa = qaaa.replace(" =","=");
                                    qaaa = qaaa.replace("    ",tab);
                                    String qaaaNoTab = qaaa.replace(tab,"");
                                    if (qaaa.contains("scale=")) {
                                        embScale = qaaa.split("=")[1];
                                        embScale = embScale.split(tab)[0];
                                        if (qaaaNoTab.equals("scale={")) { //very rare edge case where scale is split across lines
                                            qaaa = scnr.nextLine();
                                            embScale = qaaa.replace(tab,"");
                                            embScale = embScale.replace("    ","");
                                            //System.out.println("Edge case at "+qaaa);
                                            
                                        } else { //normal
                                            embScale = embScale.substring(2,embScale.length()-2);
                                        }
                                    }
                                    if (qaaa.contains("position=")) {
                                        embPos = qaaa.split("=")[1];
                                        embPos = embPos.split(tab)[0];
                                        if (qaaaNoTab.equals("position={")) { //very rare edge case where position is split across lines
                                            qaaa = scnr.nextLine();
                                            embPos = qaaa.replace(tab,"");
                                            embPos = embPos.replace("    ","");
                                            //System.out.println("Edge case at "+qaaa);
                                        } else { //normal
                                            embPos = embPos.substring(2,embPos.length()-2);
                                        }
                                    }
                                    if (qaaa.contains("rotation=")) {
                                        if (!qaaa.split("=")[0].contains("#")) {
                                            embRot = qaaa.split("=")[1];
                                            embRot = embRot.split(tab)[0];
                                            embRot = embRot.replace(" ","");
                                        }
                                    }
                                    if (qaaa.contains("texture=")) {
                                        embTexture = qaaa.split("=")[1];
                                        embTexture = embTexture.split(tab)[0];
                                        embTexture = embRot.replace(" ","");
                                    }
                                    if (qaaa.equals(tab+tab+"}")) {
                                        if (tmpOutput.equals("0")) {//build emblem
                                            tmpOutput = embTexture+"~_~"+embColor1+"~_~"+embColor2+"~_~"+embScale+"~_~"+embPos+"~_~"+embRot;
                                        } else {
                                            tmpOutput = tmpOutput+"~~"+embTexture+"~_~"+embColor1+"~_~"+embColor2+"~_~"+embScale+"~_~"+embPos+"~_~"+embRot;
                                        }
                                        embScale = "none";
                                        embPos = "none";
                                        embRot = "none";
                                    }
                                }
                            }
                        }

                    }
                    if (embColor1.equals("noColor1")) {
                        embColor1 = "none";
                    }
                    if (embColor2.equals("noColor2")) {
                        embColor2 = "none";
                    }
                    if (embTexture.equals("noTexture")) {
                        embTexture = "none";
                    }
                    if (instanceYes == 0) { //if emblem has no instances specified, generate instance
                        if (output[4].equals("0")) {//build emblem
                            output[4] = embTexture+"~_~"+embColor1+"~_~"+embColor2+"~_~"+embScale+"~_~"+embPos+"~_~"+embRot;
                        } else {
                            output[4] = output[4]+"~~"+embTexture+"~_~"+embColor1+"~_~"+embColor2+"~_~"+embScale+"~_~"+embPos+"~_~"+embRot;
                        }
                    } else {
                        tmpOutput = tmpOutput.replace("noColor1",embColor1);
                        tmpOutput = tmpOutput.replace("noColor2",embColor2);
                        tmpOutput = tmpOutput.replace("noTexture",embTexture);
                        if (output[4].equals("0")) { //build emblem
                            output[4] = tmpOutput;
                        } else {
                            output[4] = output[4]+"~~"+tmpOutput;
                        }
                    }
                }

                if (qaaa.equals( "}" ) ) { //ends here

                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }

                    impFlagList.add(tmpOutput);

                    output[0] = "unnamedFlag"; //default for no tag/name
                    output[1] = "pattern_solid.tga"; //default for no pattern
                    output[2] = "none"; //default for color1, format = hsvOrRgb~r,g,b
                    output[3] = "none"; //default for color2, format = hsvOrRgb~r,g,b
                    output[4] = "0"; //default for no emblems, format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)

                }

            }

        }catch (java.util.NoSuchElementException exception){

        }   

        return impFlagList;

    }

    public static ArrayList<String[]> importAllFlags (String name, ArrayList<String> modDirs) throws IOException
    {
        ArrayList<String[]> allFlags = new ArrayList<String[]>();
        ArrayList<String[]> vanillaFlags = importFlag(name+"/game/common/coat_of_arms/coat_of_arms/00_pre_scripted_countries.txt");
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/common/coat_of_arms/coat_of_arms";
                File flagInfo = new File (modDir);
                String[] flagList = flagInfo.list();

                if (flagList != null) {
                    int aq2 = 0;
                    while (aq2 < flagList.length) {
                        ArrayList<String[]> modFlags = importFlag(modDir+"/"+flagList[aq2]);
                        allFlags.addAll(modFlags);
                        aq2 = aq2 + 1;
                    }

                }
            }
            aqq = aqq + 1;
        }

        allFlags.addAll(vanillaFlags);

        return allFlags;
    }

    public static ArrayList<String[]> importColors (String name) throws IOException //named_colors for flags
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String[]> impColorList= new ArrayList<String[]>();

        String tab = "	";

        int flag = 0;

        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "none"; //default for no name
        output[1] = "none"; //default for no color

        impColorList.add(output); //default at ID 0

        try {
            while (flag == 0){
                qaaa = scnr.nextLine();
                qaaa = qaaa.replace(" "+tab,"");
                qaaa = qaaa.replace(tab,"");
                qaaa = qaaa.replace("    ","");
                qaaa = qaaa.replace(" = ","=");
                qaaa = qaaa.replace("= ","=");
                qaaa = qaaa.replace(" =","=");
                if (!qaaa.equals( "colors={" ) && qaaa.contains("=") ) {
                    output[0] = qaaa.split("=")[0];
                    output[1] = qaaa.split("=")[1];
                    if (qaaa.contains("rgb ") || qaaa.contains("rgb ")) {
                        output[1] = output[1].split("rgb ")[1];
                        output[1] = output[1].split(" }")[0];
                        output[1] = "rgb," + output[1].substring(2,output[1].length());
                    }
                    else if (qaaa.contains("hsv ")) {
                        output[1] = output[1].split("hsv ")[1];
                        output[1] = output[1].split(" }")[0];
                        output[1] = "hsv," + output[1].substring(2,output[1].length());
                    }
                    else if (qaaa.contains("={")) { //if colors do not specify rgb/hsv, default to rgb
                        output[1] = output[1].split(" }")[0];
                        output[1] = "rgb," + output[1].substring(2,output[1].length());
                    }
                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }

                    impColorList.add(tmpOutput);

                    output[0] = "none"; //default for no name
                    output[1] = "none"; //default for no color
                }

            }
        }catch (java.util.NoSuchElementException exception){

        }   

        return impColorList;

    }

    public static ArrayList<String[]> importAllColors (String name, ArrayList<String> modDirs) throws IOException
    {
        ArrayList<String[]> allColors = new ArrayList<String[]>();
        ArrayList<String[]> vanillaColors = importColors(name+"/game/common/named_colors/default_colors.txt");
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/common/named_colors";
                File colorInfo = new File (modDir);
                String[] colorList = colorInfo.list();

                if (colorList != null) {
                    int aq2 = 0;
                    while (aq2 < colorList.length) {
                        ArrayList<String[]> modColors = importColors(modDir+"/"+colorList[aq2]);
                        allColors.addAll(modColors);
                        aq2 = aq2 + 1;
                    }

                }
            }
            aqq = aqq + 1;
        }

        allColors.addAll(vanillaColors);

        return allColors;
    }
    
    public static ArrayList<String> importRegions (String name) throws IOException //list of all regions
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String> impRegionList= new ArrayList<String>();

        String tab = "	";

        int flag = 0;

        String qaaa = "";
        String output = "none";
        String endBracket = " }".replace(" ","");

        impRegionList.add(output); //default at ID 0

        try {
            while (flag == 0){
                qaaa = scnr.nextLine();
                qaaa = qaaa.replace ("_region {","_region = {"); //special edge case for vanilla madhyadesa_region
                if (qaaa.contains( "_region = {" ) || qaaa.contains("_area = {") ) { //_area is special edge case for vanilla bohemia_area region
                    output = qaaa.split(" =")[0];
                    while (!qaaa.equals(endBracket)){
                        qaaa = scnr.nextLine();
                        if (qaaa.contains(tab+tab) && !qaaa.equals(tab+tab)) {
                            String area = qaaa.split(tab+tab)[1];
                            area = area.split(tab)[0]; //formatting cleanup
                            area = area.split(" ")[0];
                            area = area.split("#")[0];
                            output = output + "," + area;
                        }
                    }

                    String tmpOutput = output;

                    impRegionList.add(tmpOutput);

                    output = "none"; //default for no name
                }

            }
        }catch (java.util.NoSuchElementException exception){

        }   

        return impRegionList;

    }
    
    public static String getRegionDir (String name, ArrayList<String> regionList, ArrayList<String> modDirs) throws IOException
    {
        String regionDir = name+"//game//";
        int aqq = 0;
        while (regionList.size() > aqq) {
            //if (!regionList.get(aqq).equals("none")) {
            if (!regionList.get(aqq).equals("none")) {
                //regionDir = modDirs.get(aqq)+"//";
                //regionDir = modDirs.get(aqq)+"//";
                regionDir = regionList.get(aqq).split("map_data")[0];
            }
            aqq = aqq + 1;
        }

        return regionDir;
    }

    public static ArrayList<String> importAllLoc (String name, ArrayList<String> modDirs) throws IOException //imports all localization files
    {
        ArrayList<String> allLoc = new ArrayList<String>();
        ArrayList<String> moddedLoc = new ArrayList<String>();
        ArrayList<String> regionLoc = importBasicFile(name+"/game/localization/english/macroregions_l_english.yml");
        ArrayList<String> formableLoc = importBasicFile(name+"/game/localization/english/nation_formation_l_english.yml");
        ArrayList<String> countryLoc = importBasicFile(name+"/game/localization/english/countries_l_english.yml");
        ArrayList<String> provLoc = importBasicFile(name+"/game/localization/english/provincenames_l_english.yml");
        ArrayList<String> areaLoc = importBasicFile(name+"/game/localization/english/regionnames_l_english.yml");
        ArrayList<String> supportedModLoc = importBasicFile("supportedModLoc.txt"); //default loc for some mods in case mod files are missing
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/localization/english";
                try {
                    importModLoc(modDir,modDirs,moddedLoc);
                } catch (java.lang.OutOfMemoryError exception) { //User has many mods with too many lines of localization to handle in memory
                    aqq = 1 + modDirs.size(); 
                }
            }
            aqq = aqq + 1;
        }

        allLoc.addAll(regionLoc);
        allLoc.addAll(formableLoc);
        allLoc.addAll(countryLoc);
        allLoc.addAll(provLoc);
        allLoc.addAll(areaLoc);
        allLoc.addAll(supportedModLoc);
        allLoc.addAll(moddedLoc);

        return allLoc;
    }

    public static ArrayList<String> importModLoc (String modDir, ArrayList<String> modDirs, ArrayList<String> allModLoc) throws IOException
    //imports all modded localization files
    {
        int aqq = 0;
        File locInfo = new File (modDir);
        String[] locList = locInfo.list();

        try {
            if (locList != null) {
                while (aqq < locList.length) {
                    importModLoc(modDir+"/"+locList[aqq],modDirs,allModLoc);
                    aqq = aqq + 1;
                }

            }
            else {
                ArrayList<String> modLoc = importBasicFile(modDir);
                allModLoc.addAll(modLoc);
            }
        } catch (Exception e){ //if a non-existant file is accessed, cancel so that converter doesn't crash
            
        }

        return allModLoc;
    }

    public static ArrayList<String> importModDirs (String name, String irModDir) throws IOException //imports all directories for mods used in save file
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String> impModList= new ArrayList<String>();

        irModDir = irModDir.substring(0,irModDir.length()-4);

        String tab = "	";
        
        char quoteMark = '"';

        int flag = 0;

        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String output;   // Owner Culture Religeon PopTotal Buildings

        output = "none"; //default for no name

        impModList.add(output); //default at ID 0

        try {
            while (flag == 0){
                qaaa = scnr.nextLine();
                if (qaaa.contains( "enabled_mods" ) ) {
                    flag = 1;
                    qaaa = scnr.nextLine();
                    String tmpOutput;
                    while (!qaaa.contains("}") && !qaaa.contains("speed=")) {
                        qaaa = qaaa.replace(tab,"");
                        String[] mods = qaaa.split(".mod"+quoteMark);
                        int aqq = 0;
                        while (aqq < mods.length) {
                            //System.out.println("Mod="+mods[aqq]);
                            mods[aqq] = mods[aqq]+".mod";
                            mods[aqq] = mods[aqq].replace(" "+quoteMark,"Q");
                            mods[aqq] = mods[aqq].substring(1,mods[aqq].length());
                            //System.out.println("Qhd="+mods[aqq]);
                            try { //get real mod dir
                                output = importModDirInfo(irModDir+"/"+mods[aqq]);
                                impModList.add(output);
                            } catch (Exception e){ //if something goes wrong in finding the mod dir, ignore mod to prevent entire converter from crashing

                            }
                            aqq = aqq + 1;
                        }
                        qaaa = scnr.nextLine();
                    }
                }

            }
        }catch (java.util.NoSuchElementException exception){
            return impModList;
        }   

        return impModList;

    }

    public static String importModDirInfo (String name) throws IOException //imports directory info for a specific mod from the .mod file
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String output = "none"; //default for no name

        try {
            //try
            while (flag == 0){
                String qaaa = scnr.nextLine();
                if (qaaa.contains( "path=" ) ) {
                    flag = 1;
                    output = qaaa.split("=")[1];
                    output = output.substring(1,output.length()-1);
                }

            }
        }catch (Exception e){ //if anything goes wrong, return to avoid stopping converter
            return output;
        }   

        return output;

    }

    public static ArrayList<String> importModFlagDirs (ArrayList<String> modDirs) throws IOException //imports modded flag gfx locations/names
    {

        String coloredEmblems = "/gfx/coat_of_arms/colored_emblems";
        String patterns = "/gfx/coat_of_arms/patterns";
        String texturedEmblems = "/gfx/coat_of_arms/textured_emblems";

        ArrayList<String> gfxList= new ArrayList<String>();

        String tab = "	";

        int flag = 0;
        int aqq = 0;
        String qaaa = "";
        String output;

        output = "none"; //default for no name

        gfxList.add(output); //default at ID 0

        try {
            while (aqq < modDirs.size()){
                if (!modDirs.get(aqq).equals ("none")) {
                    File embDir = new File(modDirs.get(aqq)+coloredEmblems);
                    String[] emblemFiles = embDir.list();
                    if (emblemFiles != null) {
                        int aq2 = 0;
                        while (aq2 < emblemFiles.length) {
                            output = emblemFiles[aq2];
                            output = modDirs.get(aqq)+coloredEmblems+"/"+output;
                            gfxList.add(output);
                            aq2 = aq2 + 1;

                        }
                    }
                    File patDir = new File(modDirs.get(aqq)+patterns);
                    String[] patternFiles = patDir.list();
                    if (patternFiles != null) {
                        int aq3 = 0;
                        while (aq3 < patternFiles.length) {
                            output = patternFiles[aq3];
                            output = modDirs.get(aqq)+patterns+"/"+output;
                            gfxList.add(output);
                            aq3 = aq3 + 1;

                        }
                    }
                    File texDir = new File(modDirs.get(aqq)+texturedEmblems);
                    String[] textureFiles = texDir.list();
                    if (textureFiles != null) {
                        int aq4 = 0;
                        while (aq4 < textureFiles.length) {
                            output = textureFiles[aq4];
                            output = modDirs.get(aqq)+texturedEmblems+"/"+output;
                            gfxList.add(output);
                            aq4 = aq4 + 1;

                        }
                    }
                }

                aqq = aqq + 1;
            }
        }catch (java.util.NoSuchElementException exception){
            flag = 1;
        }   

        return gfxList;

    }
    
    public static ArrayList<String> importModRegionDirs (ArrayList<String> modDirs) throws IOException //imports modded region ID's
    {

        String regionFile = "/map_data";

        ArrayList<String> gfxList= new ArrayList<String>();

        String tab = "	";

        int flag = 0;
        int aqq = 0;
        String qaaa = "";
        String output;

        output = "none"; //default for no name

        gfxList.add(output); //default at ID 0

        try {
            while (aqq < modDirs.size()){
                if (!modDirs.get(aqq).equals ("none")) {
                    File regionDir = new File(modDirs.get(aqq)+regionFile);
                    String[] emblemFiles = regionDir.list();
                    if (emblemFiles != null) {
                        int aq2 = 0;
                        while (aq2 < emblemFiles.length) {
                            output = emblemFiles[aq2];
                            if (output.equals("regions.txt")) {
                                output = modDirs.get(aqq)+regionFile+"/"+output;
                                gfxList.add(output);
                            }
                            aq2 = aq2 + 1;

                        }
                    }
                }

                aqq = aqq + 1;
            }
        }catch (java.util.NoSuchElementException exception){
            flag = 1;
        }   

        return gfxList;

    }
    
    public static String[] importMappingFromArray (ArrayList<String> source, String provIDnum) throws IOException
    //Simpler mapper that doesn't check for arguments and is faster, returning as soon as it finds a valid match
    {
        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops
        
        int count = 0;

        try {
            while (count < source.size()){

                qaaa = source.get(count);

                if (qaaa.split(",")[0].equals(provIDnum)){
                    count = 1 + source.size();
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }
                count = count + 1;
            }

        }catch (java.util.NoSuchElementException exception){
            count = 1 + source.size();

        }   

        return output;

    }
    
    public static ArrayList<String[]> importMappingFromArrayArgs (ArrayList<String> source, String provIDnum) throws IOException
    //Should only be used on mapping files that support arguments
    {
        String line;
        ArrayList<String[]> allMatches = new ArrayList<String[]>();
        String[] match;
        match = new String[2];

        match[0] = "peq"; //default for no ir input
        match[1] = "99999"; //default for no ck2 output
        
        int count = 0;

        try {
            while (count < source.size()){

                line = source.get(count);

                if (line.split(",")[0].equals(provIDnum) && !line.contains("#")){
                    //count = 1 + source.size();
                    if (line.split(",").length >= 3) {
                        //return qaaa.split(","); //if mapping file has arguments, return those as well
                        allMatches.add(line.split(","));
                    } else {
                        match[0] = line.split(",")[0];
                        match[1] = line.split(",")[1];
                        allMatches.add(match);
                    }

                }
                count = count + 1;
            }

        }catch (java.util.NoSuchElementException exception){
            count = 1 + source.size();

        }   

        return allMatches;

    }
    
    public static ArrayList<String[]> importCK2Geo () throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        String tab = "	";
        char quote = '"';
        String name = "defaultOutput/default/map/geographical_region.txt";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        ArrayList<String[]> geography = new ArrayList<String[]>();
        
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                qaaa = Processing.removeComments(qaaa);
                qaaa = qaaa.replace(tab,"");

                if (qaaa.contains(" = {")){
                    String geoName = qaaa.split(" = ")[0];
                    String compositeGeo = "Q";
                    qaaa = scnr.nextLine();
                    while (!qaaa.equals("}")) {
                        qaaa = Processing.removeComments(qaaa);
                        if (qaaa.contains("c_") || qaaa.contains("d_")) {
                            qaaa = qaaa.replace(tab,"");
                            qaaa = qaaa.replace("  "," ");
                            if (compositeGeo.equals("Q")) {
                                compositeGeo = qaaa.replace(" ",",");
                            } else {
                                compositeGeo = compositeGeo + "," + qaaa.replace(" ",",");
                            }
                            
                        }
                        qaaa = scnr.nextLine();
                    }
                    String endingChar = compositeGeo.substring(compositeGeo.length()-1,compositeGeo.length());
                    if (endingChar.equals(",")) {
                        compositeGeo = compositeGeo.substring(0,compositeGeo.length()-1);
                    }
                    String[] output = new String[2];
                    output[0] = geoName;
                    output[1] = compositeGeo;
                    //System.out.println(geoName + ": " + compositeGeo);
                    geography.add(output);
                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return geography;

    }

    //developed originally by Shinymewtwo99
}
