package ImperatorToCK2;  
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;
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
                ownerTot[culRelID] = Integer.parseInt(owners[1]);
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

    public static String[] importRegionList (int totProv, String impDir) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);

        FileInputStream fileIn= new FileInputStream("regionConverter.txt");
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";

        int aqq = 1;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] provList;   // Owner Culture Religeon PopTotal Buildings
        provList = new String[totProv];

        qaaa = scnr.nextLine();

        try {
            while (endOrNot = true){
                qaaa = scnr.nextLine();
                provList = importRegion (provList,(impDir+VM+"game"+VM+"setup"+VM+"provinces"+VM+"00_"+qaaa+".txt"),qaaa);
                aqq = aqq + 1;

            }

        }catch (java.util.NoSuchElementException exception){
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
        String color = Integer.toString(Rng); 

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

    public static int combineProvConvList(String source, String destination) throws IOException //combines the two province mapping files into one
    {

        String[] toAdd = convertProvConvList("provinceConversion2.txt","provinceConversion2Formatted.txt");
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

            if (countryStr.equals(subCountry)) {
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

    public static String convertTitle(String name, String rank, String title, String defaultTitle) throws IOException
    {// Converts dynamically generated title to vanilla counterpart
        String tmpTitle = Importer.importCultList(name,rank+"_"+title)[1];//converts title
        //Output.logPrint(tmpTitle);
        tmpTitle = tmpTitle.replace((rank+"_"),"");
        //Output.logPrint(tmpTitle);

        if (tmpTitle.equals("99999") || tmpTitle.equals("peq")) {//if there is no vanilla match
            return defaultTitle;
        } else {
            title = tmpTitle;
        }

        return title;
    }

    public static ArrayList<String> calculateDuchyNameList (String ck2Dir, String[][] ck2ProvInfo) throws IOException
    {

        ArrayList<String> output = new ArrayList<String>(); //owner,culture,duchy

        int aqq = 0;

        int aq2 = 0;

        ArrayList<String> duchies = Importer.importDuchyNameList(ck2Dir);

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

                            provCultTotal = provCultTotal + "," + Output.cultureOutput(ck2ProvInfo[1][aq4]);
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

        String VM = "\\"; 
        VM = VM.substring(0);

        String ck2CultureInfo; //e_title,k_title

        Importer importer = new Importer();

        String[] dejureTitles = importer.importDejureList("dejureConversion.txt",cult);

        return dejureTitles;
    }

    public static String[] calculateUsedTitles(String[] cultureTitles, ArrayList<String[]> impTagInfo) throws IOException //calculates if titles exist
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String ck2CultureInfo; //e_title,k_title

        int aqq = 0;
        
        int flag = 0; //Sets it so that only the first correct tag becomes cultureTitle. 
        
        int flag2 = 0; //Defeated civil war tags will still exist in save, causing calc to set non-existant civil war faction as cultureTitle without flags

        while (aqq < impTagInfo.size()) {
            if (("e_"+impTagInfo.get(aqq)[21]).equals(cultureTitles[1]) && flag == 0) {
                cultureTitles[1] = "e_"+impTagInfo.get(aqq)[0];
                flag = 1;
            }

            if (("k_"+impTagInfo.get(aqq)[21]).equals(cultureTitles[2]) && flag2 == 0) {
                cultureTitles[2] = "k_"+impTagInfo.get(aqq)[0];
                flag2 = 1;
            }
            aqq = aqq + 1;

        }

        return cultureTitles;
    }

}
