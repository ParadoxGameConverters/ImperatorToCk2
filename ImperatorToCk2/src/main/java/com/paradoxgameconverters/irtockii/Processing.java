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
 * Write a description of class Processing here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Processing
{

    public static String basicProvinceTotal(int totalCKProv, String[] ck2TagTotals, String[][] ck2ProvInfo, int typeToCollect,int aq2)
    {

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

            if (owners[1].equals("null")) {
                ownerTot[culRelID] = 0;    
            }
            else {
                ownerTot[culRelID] = Integer.parseInt(owners[1])+1;
            }

            aq6 = 1;
            while (aq6 < totalCKProv) {
                if (ownerTot[aq6] > ownerTot[aq6-1]){
                    ck2ProvInfo[typeToCollect][aq2] = owners[0];

                }

                aq6 = aq6 + 1;

            }
            aq5 = aq5 + 1;

            aq7 = 0;
            culRelID = 0;

        }

        return ck2ProvInfo[typeToCollect][aq2];
    }

    public static String[] importNames (String name, int provIDnum, String ck2Dir) throws IOException //Imports CK II prov name
    {

        String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream("provinceNames.txt");
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";

        int aqq = 0;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "noName"; //default for no owner, uncolonized province
        output[1] = "plains"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (qaaa.split(",")[0].equals(provID)){
                    endOrNot = false;
                    ckName = qaaa.split(",")[4];
                    if (provIDnum == 103) { //Leon in Brittany and Spain have the same name in definition.csv
                        ckName = "french_leon";  
                    }
                    output[0] = ckName;
                    output[1] = importCK2ProvFile(ck2Dir,provIDnum,ckName)[0];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   
        fileIn.close();
        return output;

    }

    public static String[] importCK2ProvFile (String ck2Dir, int provIDnum, String ck2Prov) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        String provID = Integer.toString(provIDnum);
        String name = ck2Dir+VM+"history"+VM+"provinces"+VM+provID+" - "+ck2Prov+".txt";
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[1];

        try {

            FileInputStream fileIn= new FileInputStream(name);
            Scanner scnr= new Scanner(fileIn);

            int flag = 0;
            String ckName = "debug";

            int aqq = 0;
            boolean endOrNot = true;
            String qaaa = "debug";

            output[0] = "plains"; //default terrain type

            try {
                while (endOrNot = true){

                    qaaa = scnr.nextLine();
                    if (qaaa.split("= ")[0].equals("terrain ")){
                        endOrNot = false;
                        output[0] = qaaa.split(",")[0];

                    }

                }

            }catch (java.util.NoSuchElementException exception){
                endOrNot = false;

            }   

        }

        catch(java.io.FileNotFoundException exception) {

            output[0] = "error";

        }

        return output;

    }

    public static String importBaronyName (String name, int provIDnum, String ck2Dir) throws IOException
    {

        int aqq = 0;
        String tab = "	";
        String VM = "\\";

        VM = VM.substring(0);

        String bracket1 = "a{";
        String bracket2 = "a}";

        bracket1 = bracket1.substring(1);
        bracket2 = bracket2.substring(1);

        String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream(ck2Dir+VM+"common"+VM+"landed_titles"+VM+"landed_titles.txt");
        Scanner scnr= new Scanner(fileIn);

        String ckName = "debug";

        int flag = 0;
        String qaaa = "debug";
        String output = qaaa;   // Owner Culture Religeon PopTotal Buildings
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

        else if (provName.equals("angseley")) {

            provName = "anglesey";
        }

        else if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("aurilliac")) {

            provName = "aurillac";
        }

        else if (provName.equals("el_arish")) {

            provName = "el-arish";
        }

        else if (provName.equals("augstgau")) {

            provName = "aargau";
        }

        else if (provName.equals("franken")) {

            provName = "wurzburg";
        }

        else if (provName.equals("nidaros")) {

            provName = "trondelag";
        }

        else if (provName.equals("jarnbaraland")) {

            provName = "dalarna";
        } 

        else if (provName.equals("trans_portage")) {

            provName = "trans-portage";
        }  

        else if (provName.equals("morava")) {

            provName = "znojmo";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("desht_i_kipchak")) {

            provName = "desht-i-kipchak";
        }

        else if (provName.equals("kara_kum")) {

            provName = "kara-kum";
        }

        else if (provName.equals("petra") || provName.equals("al'_aqabah")|| provName.equals("al_'aqabah")) {

            provName = "al_aqabah";
        }

        else if (provName.equals("anti_atlas")) {

            provName = "anti-atlas";
        }

        else if (provName.equals("asaita")) {

            provName = "asayita";
        }

        else if (provName.equals("al_qusair")) {

            provName = "alqusair";
        }

        else if (provName.equals("kyzyl_su")) {

            provName = "kyzyl-su";
        }
        
        else if (provName.equals("naro_fominsk")) {

            provName = "naro-fominsk";
        }
        
        else if (provName.equals("orekhovo_zouievo")) {

            provName = "orekhovo-zouievo";
        }
        
        else if (provName.equals("monkhkhairkhan")) {

            provName = "monkh_khairkhan";
        }
        
        else if (provName.equals("suvraga_khairkan")) {

            provName = "suvraga_khairkhan";
        }
        

        if (provName.equals ("error")) {
            flag = 3;  
        }

        try {
            while (flag == 0){

                qaaa = scnr.nextLine();

                if (qaaa.split("=")[0].equals(tab3 + "c_" + provName + " ") || qaaa.split("=")[0].equals(tab3 + "c_" + provName)
                || qaaa.split("=")[0].equals("            "+tab+ "c_" + provName+" ") || qaaa.split("=")[0].equals(tab4 + "c_" + provName + " ") ||
                qaaa.split("=")[0].equals("            " + "c_" + provName + " ") || qaaa.split("=")[0].equals("           "+tab+tab+ "c_" + provName+" ") ||
                qaaa.split("=")[0].equals("           " + tab + "c_" + provName + " ")){
                    flag = 1;
                    output = null;
                    while (flag == 1){

                        try {
                            if (qaaa.split("_")[0].equals(tab4+"b") || qaaa.split("_")[0].equals("                "+tab+"b") ||
                            qaaa.split("_")[0].equals(tab4+tab+"b") || qaaa.split("_")[0].equals("                   "+tab+"b") || 
                            qaaa.split("_")[0].equals("                "+"b")){
                                qaaa = qaaa.split("b_")[1];
                                qaaa = qaaa.replace(bracket1,"");
                                qaaa = qaaa.replace(bracket2,"");
                                qaaa = qaaa.replace("=","");
                                qaaa = qaaa.replace(" ","");
                                qaaa = qaaa.replace(tab,"");
                                qaaa = qaaa.split("#")[0]; //purge comments
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

                }
            }

        }catch (java.util.NoSuchElementException exception){
            flag = 2;

        }   

        fileIn.close();

        return output;

    }

    public static String[] importBaronyNameList (String name, int provIDnum, String ck2Dir) throws IOException
    {

        String provID = Integer.toString(provIDnum);

        int flag = 0;
        String ckName = "debug";
        int aqq = 1;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2015];

        output[0] = "noName"; //default for no owner, uncolonized province

        try {
            while (endOrNot = true){
                output[aqq]=importBaronyName(ck2Dir,aqq,ck2Dir);
                if (output[aqq].equals("debug")) {
                    //Output.logPrint(aqq+"is for"+output[aqq]);

                }
                aqq = aqq + 1;

            }

        }catch (java.lang.ArrayIndexOutOfBoundsException exception){
            endOrNot = false;

        }   
        return output;

    }

    public static String[] importRegionList (int totProv, String regionDir) throws IOException
    {

        int flag = 0;
        String ckName = "debug";

        int aqq = 1;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] provList;
        provList = new String[totProv];
        
        ArrayList<String> regions = Importer.importRegions(regionDir+"map_data/regions.txt");
        
        String[] provAreas = importAreas(regionDir+"map_data/areas.txt",totProv);

        

        try {
            while (endOrNot = true){ //Goes through and get's each province for each region
                int regionID = 1;
                while (regionID < regions.size()) {
                qaaa = regions.get(regionID);
                String[] regionList = qaaa.split(",");
                String provArea = provAreas[aqq];
                int regionCount = 1;
                while (regionCount < regionList.length){
                    String selectedArea = regionList[regionCount];
                    if (selectedArea.equals(provArea)) {
                        //System.out.println(regionList[0]+"_QA " + "Area: "+selectedArea);
                        provList[aqq] = regionList[0];
                        //System.out.println(provList[aqq]);
                        regionCount = 1 + regionList.length;
                    }
                    regionCount = regionCount + 1;
                }
                
                regionID = regionID + 1;
                }
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            endOrNot = false;

        }   
        return provList;

    }

    public static String[] importRegion (String[] provList, String dir, String region) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(dir);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";
        int aqq = 0;
        String qaaa = "aa";
        boolean endOrNot = true;

        int number = 0;

        try {
            while (endOrNot = true){
                qaaa = scnr.nextLine();

                if (aqq == 0) {
                    qaaa = qaaa.substring(1);    
                }

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

        return provList;

    }
    
    public static String[] importAreas (String dir,int num) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(dir);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";
        int aqq = 0;
        String qaaa = "aa";
        boolean endOrNot = true;

        int number = 0;
        
        String tab = "	";
        String endBracket = " }".replace(" ","");
        String endBracket2 = endBracket + " ";
        
        String[] provList = new String [num];

        try {
            while (endOrNot = true){
                qaaa = scnr.nextLine();

                if (qaaa.contains(" = {") && !qaaa.contains("provinces")) {
                    String provinceName = qaaa.split(" = ")[0];
                    while (!qaaa.equals(endBracket) && !qaaa.equals(endBracket2)) {
                        qaaa = scnr.nextLine();
                        qaaa = qaaa.replace("  }"," }");
                        qaaa = qaaa.replace(tab,"");
                        if (qaaa.contains("provinces = { ")) {
                            qaaa = qaaa.split(" = ")[1];
                            qaaa = qaaa.replace("  "," ");
                            qaaa = qaaa.replace("{","");
                        }
                        qaaa = qaaa.replace("   ","");
                        qaaa = qaaa.replace("  ","");
                        qaaa = qaaa.replace("	","");
                        qaaa = qaaa.replace("provinces = { ","");
                        
                        if (!qaaa.equals(endBracket) && !qaaa.contains("color") && !qaaa.equals(endBracket2)){
                            String[] numList = qaaa.split(" ");
                            int numCount = 0;
                            while (numCount < numList.length){
                                try {
                                    int provID = Integer.parseInt(numList[numCount]);
                                    provList[provID] = provinceName;
                                }
                                
                                catch (Exception e) {
                                    
                                }
                                numCount = numCount + 1;
                            }
                        }

                    }
                    
                    qaaa = qaaa.substring(1);    
                }

                aqq = aqq + 1;    
            }
        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return provList;

    }

    public static String randomizeColor ()
    {

        int flag = 0;
        int aqq = 0;
        String qaaa = "aa";
        int Rng1 = (int) (Math.random() * 255);
        int Rng2 = (int) (Math.random() * 255);
        int Rng3 = (int) (Math.random() * 255);
        String color = Integer.toString(Rng1) + " " + Integer.toString(Rng2) + " " + Integer.toString(Rng3); 

        return color;

    }

    public static String randomizeColorGrey ()
    {

        int flag = 0;
        int aqq = 0;
        String qaaa = "aa";
        int Rng = (int) (Math.random() * 70);
        String color = Integer.toString(Rng) + " " + Integer.toString(Rng) + " " + Integer.toString(Rng); 

        return color;

    }

    public static String randomizeAge ()
    {

        int flag = 0;
        int aqq = 0;
        String qaaa = "aa";
        int Rng = (int) (Math.random() * 34);
        Rng = Rng + 16;
        String age = Integer.toString(Rng); 

        return age;

    }

    public static String capitalColor (String overlordColor) //Generates color for kingdom tier capital region, as to be different from main country
    {

        String[] overlordColorSplit = overlordColor.split(" ");
        //int c1 = (int) (Integer.parseInt(overlordColorSplit[0]) * 0.97);
        //int c2 = (int) (Integer.parseInt(overlordColorSplit[1]) / 0.75);
        //int c3 = (int) (Integer.parseInt(overlordColorSplit[2]) * 0.13);
        int c1 = (int) (Integer.parseInt(overlordColorSplit[0]) + 60);
        int c2 = (int) (Integer.parseInt(overlordColorSplit[1]) + 60);
        int c3 = (int) (Integer.parseInt(overlordColorSplit[2]) + 60);
        if (c1 < 0) {
            c1 = 0;
        }
        if (c2 < 0) {
            c2 = 0;
        }
        if (c3 < 0) {
            c3 = 0;
        }
        if (c1 > 255) {
            c1 = 255;
        }
        if (c2 > 255) {
            c2 = 255;
        }
        if (c3 > 255) {
            c3 = 255;
        }
        String color = Integer.toString(c1) + " " + Integer.toString(c2) + " " + Integer.toString(c3); 

        return color;

    }

    public static String[] convertProvConvList (String name, String outputDest) throws IOException //Converts standard mapper tool format into regular format
    {

        String tab = "	";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(outputDest);
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[25000];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        char bracket1 = 123;
        char bracket2 = 125;

        String bracket1Str = bracket1+" ";

        bracket1Str = bracket1Str.replace(" ","");

        String bracket2Str = bracket2+" ";

        bracket2Str = bracket2Str.replace(" ","");

        int aq2 = 0;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split("ink = ")[0].equals(tab+"l")){
                    qaaa = qaaa.split("ink = ")[1];
                    qaaa = qaaa.split(tab)[0];
                    qaaa = qaaa.substring(2,qaaa.length()-2);;
                    qaaa = qaaa.replace(" = ",",");

                    String[] provinceMappings = qaaa.split(" ");

                    String impProv = "a";

                    int aqq = 0;

                    while (aqq < provinceMappings.length) {

                        if (provinceMappings[aqq].split(",")[0].equals("ck2")){
                            impProv = provinceMappings[aqq].split(",")[1];
                            aqq = 99999;

                        }
                        else {aqq = aqq + 1;}  

                    }
                    aqq = 0;   
                    if (impProv != "a") {
                        while (aqq < provinceMappings.length) {
                            if (provinceMappings[aqq].split(",")[0].equals("imperator")){
                                output[aq2] = (provinceMappings[aqq].split(",")[1]+","+impProv);

                            }
                            aqq = aqq + 1;
                            aq2 = aq2 + 1;
                        }

                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        out.flush();
        fileOut.close();

        fileIn.close();
        return output;

    }
    
    public static String[] convertProvConvListR (String name, String outputDest) throws IOException //Converts regular format into mapper tool format
    {

        String tab = "	";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(outputDest);
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[15000];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        char bracket1 = 123;
        char bracket2 = 125;

        String bracket1Str = bracket1+" ";

        bracket1Str = bracket1Str.replace(" ","");

        String bracket2Str = bracket2+" ";

        bracket2Str = bracket2Str.replace(" ","");

        int aq2 = 0;
        
        ArrayList<String> oldMappingList = Importer.importBasicFile(name);
        
        //ArrayList<String> newMappingList;
        
        int ck2Prov = 0;

        try {
            while (ck2Prov < 3000){
                int line = 0;
                String newMap = "link = {";
                while (line < oldMappingList.size()){
                    qaaa = oldMappingList.get(line);
                    //System.out.println(qaaa);
                    if (!qaaa.contains("#") && !qaaa.equals("")) {
                        if (qaaa.split(",")[1].equals(Integer.toString(ck2Prov))) {
                            newMap = newMap + " imperator = "+qaaa.split(",")[0];
                        }
                    }
                    
                    line = line + 1;
                    
                }
                if (!newMap.equals("link = {")) {
                    newMap = newMap + " ck2 = " + ck2Prov + " }";
                    out.println(newMap);
                }
                ck2Prov = ck2Prov + 1;

                

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        out.flush();
        fileOut.close();

        fileIn.close();
        return output;

    }

    public static int combineProvConvList(String source, String source2, String destination) throws IOException
    //combines the two province mapping files into one
    //original functionality for the most part depreciated, primarily used to convert source2 into usable format stored at destination
    {

        //String[] toAdd = convertProvConvList("provinceConversion2.txt","provinceConversion2Formatted.txt");
        String[] toAdd = convertProvConvList(source2,"provinceConversion2Formatted.txt");
        String VM = "\\"; 
        VM = VM.substring(0);

        FileInputStream fileIn= new FileInputStream(source);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(destination);
        PrintWriter out = new PrintWriter(fileOut);

        String qaaa = scnr.nextLine();

        int aqq = 0;

        while (aqq < toAdd.length) {
            if (toAdd[aqq] != null) {
                out.println(toAdd[aqq]); 
            }
            aqq = aqq + 1; 
        }

        int flag = 0;
        try {
            while (flag == 0) {
                out.println(qaaa);
                qaaa = scnr.nextLine();

            }

        }catch (java.util.NoSuchElementException exception){
            flag = 1; 
            out.flush();
            fileOut.close();
        }

        return 0;
    }

    public static ArrayList<String> generateSubjectList(int tot, String source) throws IOException
    {

        ArrayList<String> subjects = new ArrayList<String>();

        subjects.add("0,0,feudatory"); //Debug at id 0 so list will never be empty

        int aqq = 0;

        while (aqq < tot) {

            //String[] relation = Importer.importSubjects(source,aqq,subjects);
            //subjects = Importer.importSubjects(source,aqq,subjects);
            //if (relation[0] != "9999") {
            //subjects.add(relation[0]+","+relation[1]+","+relation[2]);

            //}
            aqq = aqq + 1; 
        }

        return subjects;
    }

    public static int checkSubjectList(int country, ArrayList<String> subjects) throws IOException
    {

        String countryStr = Integer.toString(country);
        int aqq = 0;

        int output = 9999;//default, if 9999, country remains free

        while (aqq < subjects.size()) {

            String subCountry = subjects.get(aqq).split(",")[1];

            if (countryStr.equals(subCountry)) { //if match, return id of overlord
                output = aqq;
                aqq = subjects.size() + 1000; //end loop
            }
            aqq = aqq + 1; 
        }

        return output;
    }

    public static int checkMonumentList(String name) throws IOException //Checks if save is from 2.0+ or before
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);
        int flag = 0;
        try {
            String qaaa = scnr.nextLine();

            if (qaaa.equals ("great_work_manager={")) {
                flag = 1;

            }
        } catch (java.util.NoSuchElementException exception) {

        }
        return flag;
    }

    public static String formatSaveName(String name) {// replaces /:*?"<>|- and space with _, while removing tabs and .rome
        String quote = '"'+""; // " character, Java doesn't like isoated " characters
        String tab = "	";

        name = name.replace("/","_");
        name = name.replace(":","_");
        name = name.replace("*","_");
        name = name.replace("?","_");
        name = name.replace(quote,"_");
        name = name.replace("<","_");
        name = name.replace(">","_");
        name = name.replace("|","_");
        name = name.replace("-","_");
        name = name.replace(" ","_");
        name = name.replace(tab,"");

        name = name.replace(".","~~~"); //Java's .split() method does not like period characters, temporarily switches "." to "~~~"

        String[] extensionSplit = name.split("~~~");

        if (extensionSplit[extensionSplit.length-1] != name) { //removes .rome or any other extension. If no extension at all, is left untouched

            name = name.replace(extensionSplit[extensionSplit.length-1],"");

            name = name.substring(0,name.length()-3);

        }

        name = name.replace("~~~",".");

        //Output.logPrint(name+"Q");

        return name;
    }

    public static String convertTitle(ArrayList<String> mappings, String rank, String title, String defaultTitle) throws IOException
    {// Converts dynamically generated title to vanilla counterpart
        String tmpTitle = Importer.importMappingFromArray(mappings,rank+"_"+title)[1]; //converts title

        if (tmpTitle.equals("99999") || tmpTitle.equals("peq")) {//if there is no vanilla match
            return defaultTitle;
        } else {
            tmpTitle = tmpTitle.substring(2,tmpTitle.length());
            title = tmpTitle;
        }

        return title;
    }

    public static ArrayList<String> calculateDuchyNameList (String ck2Dir, String[][] ck2ProvInfo,ArrayList<String> cultureMappings,
    ArrayList<String> duchies) throws IOException
    {

        ArrayList<String> output = new ArrayList<String>(); //owner,culture,duchy

        int aqq = 0;

        int aq2 = 0;

        ArrayList<String> provNameList = new ArrayList<String>();

        while (aqq < 3000) {

            String provName = importNames("a",aqq,ck2Dir)[0];

            provName = formatProvName(provName);

            if (aqq == 103) { //Leon in Brittany and Spain have the same name in definition.csv
                provName = "french_leon";  
            }

            provNameList.add(provName);

            aqq = aqq + 1;

        }

        while (aq2 < duchies.size()) {

            String[] countyList = duchies.get(aq2).split(",");

            String duchy = countyList[0];

            if (countyList.length == 1) {

            } else {

                int aq4 = 0;

                String provCultTotal = "QQQ";
                String provTagTotal = "QQQ";
                String provRegTotal = "QQQ";

                while (aq4 < provNameList.size()) {

                    String ckProvName = provNameList.get(aq4);

                    int aq3 = 1;

                    while (aq3 < countyList.length) {
                        if (countyList[aq3].split("c_")[1].equals(ckProvName)) {

                            provCultTotal = provCultTotal + "," + Output.cultureOutput(cultureMappings,ck2ProvInfo[1][aq4]);
                            provCultTotal = provCultTotal.replace("QQQ,","");
                            provTagTotal = provTagTotal + "," + ck2ProvInfo[0][aq4];
                            provTagTotal = provTagTotal.replace("QQQ,","");
                            provRegTotal = provRegTotal + "," + ck2ProvInfo[4][aq4];
                            provRegTotal = provRegTotal.replace("QQQ,","");

                        }
                        aq3 = aq3 + 1;
                    }

                    aq4 = aq4 + 1;

                }

                String culture = calcDuchyMajority(provCultTotal);
                String tag = calcDuchyMajority(provTagTotal);
                String region = calcDuchyMajority(provRegTotal);

                output.add(tag+","+culture+","+duchy+","+region);

            }

            aq2 = aq2 + 1;

        }

        return output;
    }

    public static String calcDuchyMajority (String duchy) throws IOException //calculates majority dejure ownership of duchy
    {

        String[] counties = duchy.split(",");

        int aqq = 0;

        ArrayList<String> count = new ArrayList<String>();

        while (aqq < counties.length) {
            if (aqq == 0) {
                count.add("debug");
            }
            int aq2 = 0;
            while (aq2 < count.size()) {
                if (counties[aqq] == count.get(aq2)) {
                    count.set(aq2,count.get(aq2)+","+counties[aqq]);
                } else if (counties[aqq] != "null" && counties[aqq] != "99999") {
                    count.add(counties[aqq]);
                }
                aq2 = aq2 + 1;
            }

            aqq = aqq + 1;

        }

        int aq3 = 0;

        int countNum = 0;

        int id = 0;

        while (aq3 < count.size()) {
            if (count.get(aq3).split(",").length > countNum) {
                countNum = count.get(aq3).split(",").length;
                id = aq3;
            }
            aq3 = aq3 + 1;
        }

        id = id - 1;

        duchy = counties[id];

        return duchy;
    }

    public static String formatProvName (String provName) throws IOException //formats province name to internal format
    {

        provName = provName.toLowerCase();
        provName = provName.replace(" ","_");
        provName = provName.replace("-","_");

        if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("angseley")) {

            provName = "anglesey";
        }

        else if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("aurilliac")) {

            provName = "aurillac";
        }

        else if (provName.equals("el_arish")) {

            provName = "el-arish";
        }

        else if (provName.equals("augstgau")) {

            provName = "aargau";
        }

        else if (provName.equals("franken")) {

            provName = "wurzburg";
        }

        else if (provName.equals("nidaros")) {

            provName = "trondelag";
        }

        else if (provName.equals("jarnbaraland")) {

            provName = "dalarna";
        } 

        else if (provName.equals("trans_portage")) {

            provName = "trans-portage";
        }  

        else if (provName.equals("morava")) {

            provName = "znojmo";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("desht_i_kipchak")) {

            provName = "desht-i-kipchak";
        }

        else if (provName.equals("kara_kum")) {

            provName = "kara-kum";
        }

        else if (provName.equals("petra") || provName.equals("al'_aqabah")|| provName.equals("al_'aqabah")) {

            provName = "al_aqabah";
        }

        else if (provName.equals("anti_atlas")) {

            provName = "anti-atlas";
        }

        else if (provName.equals("asaita")) {

            provName = "asayita";
        }

        else if (provName.equals("al_qusair")) {

            provName = "alqusair";
        }

        else if (provName.equals("kyzyl_su")) {

            provName = "kyzyl-su";
        }

        return provName;

    }

    public static String[] defaultDejureConversion(String cult) throws IOException
    {

        Importer importer = new Importer();

        String[] dejureTitles = importer.importDejureList("dejureConversion.txt",cult);

        return dejureTitles;
    }

    public static String[] calculateUsedTitles(String[] cultureTitles, ArrayList<String[]> impTagInfo,int empireRank,int[] ck2LandTot) throws IOException
    //calculates if titles exist
    {

        int aqq = 0;

        int flag = 0; //Sets it so that only the first correct tag becomes cultureTitle. 

        int flag2 = 0; //Defeated civil war tags will still exist in save, causing calc to set non-existant civil war faction as cultureTitle without flags

        while (aqq < impTagInfo.size()) {
            if (("e_"+impTagInfo.get(aqq)[21]).equals(cultureTitles[1]) && flag == 0 && ck2LandTot[aqq] >= empireRank) {
                cultureTitles[1] = "e_"+impTagInfo.get(aqq)[0];
                flag = 1;
            }

            if (("k_"+impTagInfo.get(aqq)[21]).equals(cultureTitles[2]) && flag2 == 0 && ck2LandTot[aqq] > 0) {
                cultureTitles[2] = "k_"+impTagInfo.get(aqq)[0];
                flag2 = 1;
            }
            aqq = aqq + 1;

        }

        return cultureTitles;
    }

    public static boolean checkFile(String file) throws IOException //checks if file exists or not
    {

        try {

            FileInputStream fileIn= new FileInputStream(file);

        }catch (java.io.FileNotFoundException exception){
            return false;

        }

        return true;
    }

    public static void fileExcecute(String[] file) throws IOException //checks if file exists or not
    {
        try {
            Runtime runTime = Runtime.getRuntime();
            Process process = runTime.exec(file);
            process.waitFor();
        } catch (Exception q) {

        }

    }

    public static String customDate(String date, String oldDirectory, String newDirectory) throws IOException //needed to allow TAGs imperial laws
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(oldDirectory);

        FileOutputStream fileOut= new FileOutputStream(newDirectory);
        PrintWriter out = new PrintWriter(fileOut);

        String tmpDate = date.replace(".",",");

        int year = Integer.parseInt(tmpDate.split(",")[0]);
        int month = Integer.parseInt(tmpDate.split(",")[1]);
        int day = Integer.parseInt(tmpDate.split(",")[2]);

        String bookmark = "converted";
        String bookmarkName = "converted_bookmark_name";
        String bookmarkDesc = "converted_bookmark_info";
        String image = "GFX_pick_era_image_4";

        if (year >= 769) {
            bookmark = "bm_charlemagne";
            bookmarkName = "BM_CHARLEMAGNE_ERA";
            bookmarkDesc = "BM_CHARLEMAGNE_ERA_INFO";
            image = "GFX_pick_era_image_1";
        }

        if (year >= 867) {
            bookmark = "bm_the_old_gods";
            bookmarkName = "BM_THE_OLD_GODS_ERA";
            bookmarkDesc = "BM_THE_OLD_GODS_ERA_INFO";
            image = "GFX_pick_era_image_2";
        }

        if (date.equals("936.8.7")) {
            bookmark = "bm_otto_the_first";
            bookmarkName = "OTTO_THE_FIRST_ERA";
            bookmarkDesc = "OTTO_THE_FIRST_ERA_INFO";
            image = "GFX_pick_era_image_6";
        }

        if ((year >= 1066 && month >= 9 && day >= 15) || year >= 1067) {
            bookmark = "bm_fate_of_england";
            bookmarkName = "BM_FATE_OF_ENGLAND_ERA";
            bookmarkDesc = "BM_FATE_OF_ENGLAND_ERA_INFO";
            image = "GFX_pick_era_image_3";
        }

        if (year >= 1337) {
            bookmark = "bm_100_years_war";
            bookmarkName = "BM_100_YEARS_WAR_ERA";
            bookmarkDesc = "BM_100_YEARS_WAR_ERA_INFO";
            image = "GFX_pick_era_image_5";
        }

        int aqq = 0;

        while (aqq < oldFile.size()) {
            if (oldFile.get(aqq).contains("converted = ")) {
                String newTitle = oldFile.get(aqq).replace("converted = ",bookmark);
                out.println (newTitle);
            }

            else if (oldFile.get(aqq).contains("converted_bookmark_name")) {
                String newName = oldFile.get(aqq).replace("converted_bookmark_name",bookmarkName);
                out.println (newName);
            }

            else if (oldFile.get(aqq).contains("converted_bookmark_info")) {
                String newInfo = oldFile.get(aqq).replace("converted_bookmark_info",bookmarkDesc);
                out.println (newInfo);
            }

            else if (oldFile.get(aqq).contains("100.1.1")) {
                String newDate = oldFile.get(aqq).replace("100.1.1",date);
                out.println (newDate);
            } 

            else if (oldFile.get(aqq).contains("GFX_pick_era_image_4")) {
                String newDate = oldFile.get(aqq).replace("GFX_pick_era_image_4",image);
                out.println (newDate);
            } else {
                out.println (oldFile.get(aqq));
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

        return "a";
    }

    public static String checkGovListOLD (String gov, ArrayList<String> govMap) throws IOException //Imports government mappings
    {

        int flag = 0;

        boolean endOrNot = true;

        int aqq = 0;

        String qaaa;
        String output = gov; //backup in case there is no mapping

        String rank = "k"; //may be implemented in the future

        try {
            while (endOrNot = true){

                qaaa = govMap.get(aqq);

                if (qaaa.split(",")[0].equals(gov)){
                    if (qaaa.split(",").length == 2) { //if no rank requirement, convert
                        endOrNot = false;
                        output = qaaa.split(",")[1];
                    } else if (qaaa.split(",").length == 3) {
                        if (qaaa.split(",")[2].equals(rank)) { //if ranks match, convert
                            endOrNot = false;
                            output = qaaa.split(",")[1];
                        }
                    }

                }
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            endOrNot = false;

        }   

        return output;

    }
    
    public static String checkGovList (String gov, ArrayList<String> govMap, String tagCulture, String tagTag, ArrayList<String> settings) throws IOException 
    //Imports government mappings
    {

        //, String tagTag, String nomadSetting
        
        String ck2Culture = "a";
        String date = "100.1.1";
        
        String ck2County = "none";
        ArrayList<String[]> ck2Geo = new ArrayList<String[]>();

        String govOutput = Output.religionOutput(govMap,ck2Culture,tagCulture,date,gov,ck2County,ck2Geo,tagTag,settings);

        return govOutput;

    }

    public static String calcDynID (String id) //gives all IR character dynasties + 700000000 to prevent conflict
    {
        if (!id.equals("noDynasty")) {
            if (id.length() >= 10) {
                id = id.substring(0,9);
            }
            id = Integer.toString(Integer.parseInt(id) + 700000000);
        }

        return id;
    }

    public static String calcCharID (String id) //gives all IR character dynasties + 700000000 to prevent conflict
    {
        id = Integer.toString(1000000 + Integer.parseInt(id));

        return id;

    }

    public static String calcHead (ArrayList<String[]> impCharInfoList, String dynasty) //calculates which character is the head of a dynasty
    {
        int aqq = 1;
        String[] dynastyList = dynasty.split("~");
        String head = dynastyList[0];
        int age = Integer.parseInt(impCharInfoList.get(Integer.parseInt(head))[3]);
        String sex = impCharInfoList.get(Integer.parseInt(head))[4];
        while (aqq > dynastyList.length) {
            String newHead = dynastyList[aqq];
            int newAge = Integer.parseInt(impCharInfoList.get(Integer.parseInt(newHead))[3]);
            if (newAge < age) { //if newHead is older then the current head and is male, newHead becomes the head
                String newSex = impCharInfoList.get(Integer.parseInt(newHead))[4];
                if (newSex.equals("m")) {
                    head = newHead;
                }
            }
            aqq = aqq + 1;

        }

        return head;

    }

    public static String deriveRgbFromHsv (String color) //Converts HSV to RGB, adapted from commonItems
    {

        {
            float h = Float.parseFloat(color.split(" ")[0]);
            float s = Float.parseFloat(color.split(" ")[1]);
            float v = Float.parseFloat(color.split(" ")[2]);

            float r = 0;
            float g = 0;
            float b = 0;
            if (s == 0.0f) // achromatic (grey)
            {
                r = g = b = v;
            }
            else
            {
                if (h >= 1.0f)
                    h = 0.0f;
                int sector = (int)((h * 6.0f));
                float fraction = h * 6.0f - (float)(sector);
                float p = v * (1 - s);
                float q = v * (1 - s * fraction);
                float t = v * (1 - s * (1 - fraction));
                switch (sector)
                {
                    case 0:
                    r = v;
                    g = t;
                    b = p;
                    break;
                    case 1:
                    r = q;
                    g = v;
                    b = p;
                    break;
                    case 2:
                    r = p;
                    g = v;
                    b = t;
                    break;
                    case 3:
                    r = p;
                    g = q;
                    b = v;
                    break;
                    case 4:
                    r = t;
                    g = p;
                    b = v;
                    break;
                    case 5:
                    r = v;
                    g = p;
                    b = q;
                    break;
                    default:
                }
            }
            r *= 255;
            g *= 255;
            b *= 255;
            color = r+" "+g+" "+b;
        }

        return color;

    }

    public static String fixWhite (String color) //prevents colors from being 255 255 255
    {
        if (color.split(" ")[0].contains("255") || color.split(" ")[0].contains("254")) {
            if (color.split(" ")[1].contains("255") || color.split(" ")[1].contains("254")) {
                if (color.split(" ")[2].contains("255") || color.split(" ")[2].contains("254")) {
                    color = color.replace("255","254");
                }
            }
        }
        return color;

    }

    public static String[] eastWestNames (String eastWest, String[] loc) throws IOException
    {
        String[] empire = new String[2];

        empire[0] = eastWest+" "+loc[0];
        empire[1] = eastWest+" "+loc[1];
        if (empire[0].contains("Republic")) {
            empire[0] = empire[0].replace("Republic","Empire");
            empire[1] = empire[0].replace("Republic","Empire");
        }
        else if (empire[0].contains("Kingdom")) {
            empire[0] = empire[0].replace("Kingdom","Empire");
            empire[1] = empire[0].replace("Kingdom","Empire");
        }
        else if (!empire[0].contains("Empire")) {
            empire[0] = eastWest+" "+loc[1];
            empire[0] = empire[0] + " Empire";
        }

        return empire;

    }

    public static void dynamicSplit (String title,String rank,String color,String[] loc,String irFlag,String impGameDir,String capital,
    ArrayList<String[]> flagList,ArrayList<String[]> colorList,ArrayList<String> modFlagGFX,String government,String tagCulture,
    String ck2Dir, ArrayList<String> cultureMappings,String modDirectory) throws IOException
    {
        String country = rank+"_"+title;
        String[] easternEmpire = eastWestNames("Eastern",loc);
        String[] westernEmpire = eastWestNames("Western",loc);
        String eastColor = eastColor(color);
        String westColor = westColor(color);
        String eastTitle = title+"_east";
        if (title.equals("roman_empire")) { //for Eastern Roman Empire, convert to Byzantium
            eastTitle = "byzantium";
        }
        String westTitle = title+"_west";
        String ck2Capital = capital;
        //events
        String eventDir = modDirectory+"/events/dynamic_empire_split_"+country+".txt";
        String eventTemplateDirectory = "defaultOutput/templates/events/dynamic_empire_split.txt";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,eventDir,eventTemplateDirectory);

        String eventRestorationDir = modDirectory+"/events/dynamic_empire_reunification_"+country+".txt";
        String eventRestorationTemplateDirectory = "defaultOutput/templates/events/dynamic_empire_reunification.txt";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,eventRestorationDir,
            eventRestorationTemplateDirectory);

        //decisions
        String decisionDir = modDirectory+"/decisions/dynamic_empire_split_decision_"+country+".txt";
        String decisionTemplateDirectory = "defaultOutput/templates/decisions/dynamic_empire_split_decision.txt";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,
            decisionDir,decisionTemplateDirectory);

        String decisionRestorationDir = modDirectory+"/decisions/dynamic_empire_restoration_decision_"+country+".txt";
        String decisionRestorationTemplateDirectory = "defaultOutput/templates/decisions/dynamic_empire_restoration_decision.txt";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,decisionRestorationDir,
            decisionRestorationTemplateDirectory);

        String decisionGFXDir = modDirectory+"/interface/irck2_empire_split_decision_icons_"+country+".gfx";
        String decisionGFXTemplateDirectory = "defaultOutput/templates/interface/irck2_empire_split_decision_icons_e_TAG.gfx";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,
            decisionGFXDir,decisionGFXTemplateDirectory);

        //bloodlines 
        String bloodlineDir = modDirectory+"/common/bloodlines/50_empireSplitBloodline_"+country+".txt";
        String bloodlineTemplateDirectory = "defaultOutput/templates/common/bloodlines/50_empireSplitBloodline.txt";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,bloodlineDir,
            bloodlineTemplateDirectory);

        String bloodlineGFXDir = modDirectory+"/interface/irck2_bloodlines_"+country+".gfx";
        String bloodlineGFXTemplateDirectory = "defaultOutput/templates/interface/irck2_bloodlines_e_TAG.gfx";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,bloodlineGFXDir,
            bloodlineGFXTemplateDirectory);

        //loc
        String locDir = modDirectory+"/localisation/dynamic_empire_split_loc_"+country+".csv";
        String locTemplateDirectory = "defaultOutput/templates/localisation/dynamic_empire_split_loc.csv";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,
            locDir,locTemplateDirectory);

        String bloodDescDir = modDirectory+"/localisation/dynamic_empire_split_bloodline_loc_"+country+".csv";
        String bloodDescTemplateDirectory = "defaultOutput/templates/localisation/dynamic_empire_split_bloodline_loc.csv";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,bloodDescDir,
            bloodDescTemplateDirectory);

        Output.localizationCreation(easternEmpire,eastTitle,rank,modDirectory);
        Output.localizationCreation(westernEmpire,westTitle,rank,modDirectory);

        //cbs
        String cbDir = modDirectory+"/common/cb_types/50_irck2_cb_types_"+country+".txt";
        String cbTemplateDirectory = "defaultOutput/templates/common/cb_types/50_irck2_cb_types_e_TAG.txt";
        Output.dynamicSplitTemplateFill(country,loc,easternEmpire,westernEmpire,eastTitle,westTitle,ck2Capital,tagCulture,
            cbDir,cbTemplateDirectory);

        //gfx
        String eastFlagDir = modDirectory+"/gfx/flags/"+rank+"_"+eastTitle+".tga";
        String westFlagDir = modDirectory+"/gfx/flags/"+rank+"_"+westTitle+".tga";
        File eastFlag = new File (eastFlagDir);
        File westFlag = new File (westFlagDir);

        String irFlagSource = "none";

        if (!eastTitle.equals("byzantium")) { //For Byzantium, use vanilla flag
            if (!eastFlag.exists()) { //For custom-made flags, don't generate new one
                Output.eastWestFlagGen(irFlag,title,color,eastColor,eastTitle,flagList,colorList,rank,capital,modFlagGFX,"no",ck2Dir,impGameDir,modDirectory);
            }
            Output.titleCreationCommon(eastTitle,eastColor,government,capital,rank,modDirectory);
        }
        if (!westFlag.exists()) { //For custom-made flags, don't generate new one
            Output.eastWestFlagGen(irFlag,title,color,color,westTitle,flagList,colorList,rank,capital,modFlagGFX,"no",ck2Dir,impGameDir,modDirectory);
        }
        Output.titleCreationCommon(westTitle,westColor,government,capital,rank,modDirectory);

        Output.splitBloodlineEmbGen(ck2Dir,impGameDir,rank,flagList,title,irFlag,colorList,modFlagGFX,modDirectory);
        Output.eastWestDecisionIcon(country,modDirectory);
        Output.eastWestRestorationIcon(country,irFlag,flagList,ck2Dir,modDirectory);

        Output.eastWestTitle(eastTitle,government,capital,rank,"100.1.1",modDirectory);
        Output.eastWestTitle(westTitle,government,capital,rank,"100.1.1",modDirectory);

    }

    public static String eastColor (String overlordColor) //Generates color for easternEmpire
    {

        String[] overlordColorSplit = overlordColor.split(" ");
        int c1 = (int) (Integer.parseInt(overlordColorSplit[0])); //Red
        int c2 = (int) (Integer.parseInt(overlordColorSplit[1])); //Green
        int c3 = (int) (Integer.parseInt(overlordColorSplit[2])); //Blue

        double cTot = c1 + c2 + c3;
        double rPercent = (c1 / cTot) * 100;
        double bPercent = (c3 / cTot) * 100;

        c2 = c2 - 50;
        if (rPercent <= 25 && bPercent >= 50){ //if base country is blue, make purple rather then dark blue
            c1 = c1 + 50;
            c3 = c3 - 50;
        } else {
            c1 = c1 - 50;
            c3 = c3 + 50;
        }
        if (c1 < 0) {
            c1 = 0;
        }
        if (c2 < 0) {
            c2 = 0;
        }
        if (c3 < 0) {
            c3 = 0;
        }
        if (c1 > 255) {
            c1 = 255;
        }
        if (c2 > 255) {
            c2 = 255;
        }
        if (c3 > 255) {
            c3 = 255;
        }
        String color = Integer.toString(c1) + " " + Integer.toString(c2) + " " + Integer.toString(c3); 

        return color;

    }

    public static String westColor (String overlordColor) //Generates color for westernEmpire
    {

        String[] overlordColorSplit = overlordColor.split(" ");
        int c1 = (int) (Integer.parseInt(overlordColorSplit[0]) + 50);
        int c2 = (int) (Integer.parseInt(overlordColorSplit[1]) + 50);
        int c3 = (int) (Integer.parseInt(overlordColorSplit[2]) + 50);
        if (c1 < 0) {
            c1 = 0;
        }
        if (c2 < 0) {
            c2 = 0;
        }
        if (c3 < 0) {
            c3 = 0;
        }
        if (c1 > 255) {
            c1 = 255;
        }
        if (c2 > 255) {
            c2 = 255;
        }
        if (c3 > 255) {
            c3 = 255;
        }
        String color = Integer.toString(c1) + " " + Integer.toString(c2) + " " + Integer.toString(c3); 

        return color;

    }

    public static boolean isWithinColorRange (String color1, String color2, int range) //Checks if RGB color falls within the specified range
    {

        boolean yn = false;
        try {
            if (color1.equals("none") || color2.equals("none")) {
                return yn;
            }
            color1 = color1.replace(" ",",");
            color2 = color2.replace(" ",",");
            color1 = color1.replace("rgb(","");
            color2 = color2.replace("rgb(","");
            color1 = color1.replace(")","");
            color2 = color2.replace(")","");
            String[] color1Split = color1.split(",");
            double c1a = (double) (Double.parseDouble(color1Split[0]));
            double c2a = (double) (Double.parseDouble(color1Split[1]));
            double c3a = (double) (Double.parseDouble(color1Split[2]));

            String[] color2Split = color2.split(",");
            double c1b = (double) (Double.parseDouble(color2Split[0]));
            double c2b = (double) (Double.parseDouble(color2Split[1]));
            double c3b = (double) (Double.parseDouble(color1Split[2]));

            double range1 = c1a - c1b;
            if (range1 < 0) {
                range1 = range1 * -1;
            }
            double range2 = c2a - c2b;
            if (range2 < 0) {
                range2 = range2 * -1;
            }
            double range3 = c3a - c3b;
            if (range3 < 0) {
                range3 = range3 * -1;
            }

            if (range1 <= range && range2 <= range && range3 <= range) {
                yn = true;
            }
        } catch(Exception e) {

        }

        return yn;

    }

    public static boolean isWithinColorRatio (String color1, String color2, int range) //Checks if RGB color falls within the specified ratio
    {

        boolean yn = false;
        try {
            if (color1.equals("none") || color2.equals("none")) {
                return yn;
            }
            color1 = color1.replace(" ",",");
            color2 = color2.replace(" ",",");
            color1 = color1.replace("rgb(","");
            color2 = color2.replace("rgb(","");
            color1 = color1.replace(")","");
            color2 = color2.replace(")","");

            String[] color1Split = color1.split(",");
            double c1a = (double) (Double.parseDouble(color1Split[0]));
            double c2a = (double) (Double.parseDouble(color1Split[1]));
            double c3a = (double) (Double.parseDouble(color1Split[2]));

            String[] color2Split = color2.split(",");
            double c1b = (double) (Double.parseDouble(color2Split[0]));
            double c2b = (double) (Double.parseDouble(color2Split[1]));
            double c3b = (double) (Double.parseDouble(color2Split[2]));

            double tot1 = c1a + c2a + c3a;
            double tot2 = c1b + c2b + c3b;

            double r1Ratio = c1a / tot1 * 100;
            double r2Ratio = c1b / tot2 * 100;

            double g1Ratio = c2a / tot1 * 100;
            double g2Ratio = c2b / tot2 * 100;

            double b1Ratio = c3a / tot1 * 100;
            double b2Ratio = c3b / tot2 * 100;

            double range1 = r1Ratio - r2Ratio;
            if (range1 < 0) {
                range1 = range1 * -1;
            }
            double range2 = g1Ratio - g2Ratio;
            if (range2 < 0) {
                range2 = range2 * -1;
            }
            double range3 = b1Ratio - b2Ratio;
            if (range3 < 0) {
                range3 = range3 * -1;
            }

            if (range1 <= range && range2 <= range && range3 <= range) {
                yn = true;
            }
        } catch(Exception e) {

        }

        return yn;

    }

    public static boolean isBright (String color1, int range) //Checks if RGB color is at least x bright
    {

        boolean yn = false;
        if (color1.equals("none")) {
            return yn;
        }
        color1 = color1.replace(" ",",");
        color1 = color1.replace("rgb(","");
        color1 = color1.replace(")","");
        String[] color1Split = color1.split(",");
        double c1 = (double) (Double.parseDouble(color1Split[0]));
        double c2 = (double) (Double.parseDouble(color1Split[1]));
        double c3 = (double) (Double.parseDouble(color1Split[2]));

        if (c1 >= range) {
            yn = true;
        }
        else if (c2 >= range) {
            yn = true;
        }
        else if (c3 >= range) {
            yn = true;
        }

        return yn;

    }

    public static int[] addSubjectsToSize (ArrayList<String> impSubjectInfo, int[] ck2LandTot) throws IOException 
    //Recalculates sizes of all tags to accomodate for owned subjects
    {

        int count = 1;

        while (count<impSubjectInfo.size()) {
            int overlord = Integer.parseInt(impSubjectInfo.get(count).split(",")[0]);
            int subject = Integer.parseInt(impSubjectInfo.get(count).split(",")[1]);
            String overlordGov = impSubjectInfo.get(count).split(",")[2];
            if (overlordGov.equals ("feudatory") || overlordGov.equals ("satrapy") || overlordGov.equals ("client_state")) {
                if (overlord != 9999) { //if there's an overlord, add subject's size to overlord's size
                    ck2LandTot[overlord] = ck2LandTot[overlord]+ck2LandTot[subject];
                }
            }
            count = count + 1;
        }

        return ck2LandTot;

    }

    public static String[][] annexSubjects(int country,int totCountries,String[][] ck2ProvInfo,ArrayList<String> impSubjectInfo) throws IOException
    //Annexes all subjects into the overlord
    {
        int count = 1;

        while (count<impSubjectInfo.size()) {

            String overlord = impSubjectInfo.get(count).split(",")[0];
            int overlordID = Integer.parseInt(overlord);
            //System.out.println("Overlord " + overlordID + " and Subject " + impSubjectInfo.get(count).split(",")[1] + " looking for " + country);
            if (overlordID == country) {
                String subject = impSubjectInfo.get(count).split(",")[1];
                int provCount = 1;
                while (provCount < ck2ProvInfo[0].length) {
                    if (ck2ProvInfo[0][provCount] != null) {

                        if (ck2ProvInfo[0][provCount].equals(subject)) {
                            //System.out.println(ck2ProvInfo[0][provCount] + " set to " + overlord);
                            ck2ProvInfo[0][provCount] = overlord;
                        }
                    }
                    provCount = provCount + 1;
                }
            }
            count = count + 1; 
        }

        return ck2ProvInfo;
    }
    
    public static boolean checkForModID(ArrayList<String> modDirs,ArrayList<String> modIDMappings, String modMappingID) throws IOException
    //Checks whether or not user is using Imperator Invictus
    {
        int count = 0;
        while (count < modDirs.size()) {
            String currentModDir = modDirs.get(count);
            String currentModDescDir = currentModDir+"/descriptor.mod";
            //System.out.println(currentModDir);
            //System.out.println(checkForInvictusID(currentModDir));
            if (checkForModID(currentModDir,modIDMappings,modMappingID)) { //check for Invictus or HMOv2 Steam ID
                return true;
            } else { //Check for manual Invictus installations
                File currentModDescFile = new File(currentModDescDir);
                if (currentModDescFile.exists()) {
                    FileInputStream fileIn= new FileInputStream(currentModDescDir);
                    Scanner scnr= new Scanner(fileIn);
                    String line = scnr.nextLine();
                    boolean endOrNot = true;
                    try {
                        while (endOrNot = true) {
                            if (checkForModID(line,modIDMappings,modMappingID)) {
                                return true;
                            }
                            else {
                               line = scnr.nextLine(); 
                            }
                        }
                    }
                    catch (Exception e) {
                        endOrNot = false;
                    }
                
                }
                
            }
            count = count + 1;
        }

        return false;
    }
    
    public static boolean checkForModID(String modDir,ArrayList<String> modIDMappings, String modMappingID) throws IOException
    //Check given directory to see whether or not it has an Invictus-based mod ID in it.
    //Will create false positive if a user has a file with invModID in name, but shouldn't be an issue due to how long invModID is.
    {
        ArrayList<String> invModIDList = new ArrayList(); //List of all known Invictus-based mod ID's
        
        int modMappingCount = 0;
        while (modMappingCount < modIDMappings.size()) {
            String rawImport = modIDMappings.get(modMappingCount);
            if (rawImport.contains(",")) {
                rawImport = rawImport.split("#")[0]; //ignores comments
                String[] currentModID = rawImport.split(",");
                if (currentModID[1].equals(modMappingID)) {
                    invModIDList.add(currentModID[0]);
                }
            }
            
            modMappingCount = modMappingCount + 1;
        }
        
        
        int count = 0;
        while (count < invModIDList.size()) {
            if (modDir.contains(invModIDList.get(count))) {
               return true;
            }
            count = count + 1;
        }

        return false;
    }
    
    public static void setTechYear(String startDate, String directory) throws IOException
    //Sets the start year of all technology to align with startDate
    {
        String textToReplace = "[startdate]";
        String techDir = directory+"/history/technology/";
        String techDir2 = directory+"/common/defines/tech_defines.lua";
        startDate = startDate.replace(".",",");
        String startYear = startDate.split(",")[0];
        File techFileInfo = new File (techDir);
        String[] techFileList = techFileInfo.list();
                if (techFileList != null) {
                    int aq2 = 0;
                    while (aq2 < techFileList.length) {
                        String techFile = techFileList[aq2];
                        Output.replaceInFile(textToReplace,startYear,techDir+techFile);
                        System.out.println("Replaced "+textToReplace+" with "+startYear+" in "+techDir+techFile);
                        aq2 = aq2 + 1;
                    }

                }
        Output.replaceInFile(textToReplace,startYear,techDir2);

    }
    
    public static ArrayList<String> condenseArrayStr(String[] longArray)
    //Condenses a tag,number array into an ArrayList such that there are no repeat tags
    {
        //longArray;
        int count = 0;
        ArrayList<Integer> checkedCount = new ArrayList<Integer>();
        ArrayList<String> shortArray = new ArrayList<String>();
        //System.out.println("__________ Start __________");
        while (count < longArray.length) {
            String element = longArray[count];
            String identifier = element.split(",")[0];
            String numStr = element.split(",")[1];
            if (numStr.equals("null")) {
                numStr = "0";
            }
            int elementNum = Integer.parseInt(numStr);
            //System.out.println("Checking ID "+identifier+" with "+elementNum+" elements");
            
            int count2 = count+1;
            while (count2 < longArray.length) {
                String element2 = longArray[count2];
                String identifier2 = element2.split(",")[0];
                //System.out.println("Checking ID "+identifier2+" with "+element2.split(",")[1]+" elements");
                if (identifier.equals(identifier2)) {
                    String numStr2 = element2.split(",")[1];
                    if (numStr2.equals("null")) {
                        numStr2 = "0";
                    }
                    int elementNum2 = Integer.parseInt(numStr2);
                    elementNum = elementNum + elementNum2;
                    checkedCount.add(count2);
                    //System.out.println(identifier+" and "+identifier2+" match, adding "+element+" and "+element2);
                }
                count2 = count2 + 1;
            }
            checkedCount.add(count);
            String shortArrayItem = identifier+","+Integer.toString(elementNum);
            shortArray.add(shortArrayItem);
            count = count + 1;
            if (checkedCount.contains(count)) {
                while (checkedCount.contains(count)) {
                    count = count + 1;
                }
            }
            //System.out.println("__________ NewLine __________");
        }
        
        return shortArray;

    }
    
    public static String removeComments(String comment)
    //Sets the start year of all technology to align with startDate
    {
        try {
            comment = comment.split("#")[0];
        } catch (java.lang.ArrayIndexOutOfBoundsException exception) {
            comment = "";
        }
        
        return comment;

    }
    
    public static String cutQuotes(String word)
    //Cut off quotes ("Bob" becomes Bob)
    {
        String newWord = word.substring(1,word.length()-1);

        return newWord; //if no pop found, return null
    }
    
    public static ArrayList<String[]> getConstituentCounties(ArrayList<String> duchyMappings,ArrayList<String[]> ck2Geo)
    {
        ArrayList<String[]> newCk2Geo = new ArrayList<String[]>();
        int count = 0;
        while (count < ck2Geo.size()) {
            String[] ck2Region = ck2Geo.get(count);
            String[] regComponents = ck2Region[1].split(",");
            String ck2RegionName = ck2Region[0];
            int count2 = 0;
            while (count2 < regComponents.length) {
                String selectedComponent = regComponents[count2];
                int count3 = 0;
                while (count3 < duchyMappings.size()) {
                    String[] duchy = duchyMappings.get(count3).split(",");
                    if (duchy[0].equals(selectedComponent) && duchy.length > 1) {
                        //System.out.println(duchy[0]+" Matches up with "+selectedComponent+" in "+ck2RegionName);
                        count3 = count3 + duchyMappings.size();
                        int count4 = 2;
                        selectedComponent = duchy[1];
                        while (count4 < duchy.length) {
                            selectedComponent = selectedComponent + "," + duchy[count4];
                            count4 = count4 + 1;
                        }
                    }
                    
                    count3 = count3 + 1;
                }
                regComponents[count2] = selectedComponent;
                
                count2 = count2 + 1;
            }
            int count5 = 1;
            String tmpNewRegComponent = regComponents[0];
            while (count5 < regComponents.length) {
                tmpNewRegComponent = tmpNewRegComponent + "," + regComponents[count5];
                count5 = count5 + 1;
            }
            ck2Region[1] = tmpNewRegComponent;
            //System.out.println(ck2Region[0] + ": " + ck2Region[1]);
            newCk2Geo.add(ck2Region);
            count = count + 1;
        }

        return newCk2Geo;
    }
    
    public static boolean checkGeo(String ck2County, ArrayList<String[]> ck2Geo, String geoName)
    {
        int count = 0;
        
        while (count < ck2Geo.size()) {
            String[] ck2Region = ck2Geo.get(count);
            String regionName = ck2Region[0];
            if (regionName.equals(geoName)) {
                String[] regionCounties = ck2Region[1].split(",");
                int count2 = 0;
                while (count2 < regionCounties.length) {
                    String regionCounty = regionCounties[count2];
                    if (regionCounty.equals(ck2County)) {
                        return true;
                    }
                    
                    count2 = count2 + 1;
                }
            }
            
            count = count + 1;
            
        }

        return false;
    }
    
    public static String[][] assignNamesToProvs(String[][] ck2ProvInfo, int totalCKProv, String dir) throws IOException
    {
        int count = 0;
        
        while (count < totalCKProv) {
            String provinceName = "";
            provinceName = importNames("a",count,dir)[0];
            
            provinceName = formatProvName(provinceName);

            if (count == 103) { //Leon in Brittany and Spain have the same name in definition.csv
                provinceName = "french_leon";  
            }
            
            ck2ProvInfo[5][count] = provinceName;
            
            count = count + 1;
            
        }

        return ck2ProvInfo;
    }
    
    public static ArrayList<String[]> convertAllCapitals(ArrayList<String[]> impTagInfo) throws IOException
    {
        int count = 0;
        
        while (count < impTagInfo.size()) {
            String[] impTag = impTagInfo.get(count);
            String oldCapital = impTag[5];
            int oldCapitalInt = Integer.parseInt(oldCapital);
            String newCapital = Importer.importConvList("provinceConversion.txt",oldCapitalInt)[1];
            if (newCapital.equals("99999")) {
                System.out.println("Warning, I:R province "+oldCapital+" is unmapped!");
                newCapital = "69";
            }
            impTag[5] = newCapital;
            impTagInfo.set(count,impTag);
            
            count = count + 1;
            
        }

        return impTagInfo;
    }
    
    public static String modifyGovernor(String governorships, String region, String newGovernor) throws IOException
    {
        int count = 0;
        String[] governorshipList = governorships.split(",");
        
        while (count < governorshipList.length) {
            String govInfo = governorshipList[count];
            String selectedRegion = govInfo.split("~")[0];
            if (selectedRegion.equals(region)) {
                String repackagedGovernorship = selectedRegion+"~"+newGovernor;
                governorships = governorships.replace(govInfo,repackagedGovernorship);
                count = 1 + governorshipList.length;
            }
            
            count = count + 1;
            
        }

        return governorships;
    }

}
