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
 * Information which is output
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Output
{

    private int x;

    public static int output(String source, String destination) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        FileInputStream fileIn= new FileInputStream(source);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(destination);
        PrintWriter out = new PrintWriter(fileOut);

        String qaaa = scnr.nextLine();
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

    public static String cultureOutput(ArrayList<String> mappings,String irCulture) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String ck2CultureInfo;   // Owner Culture Religeon PopTotal Buildings

        Importer importer = new Importer();

        //ck2CultureInfo = importer.importCultList("cultureConversion.txt",irCulture)[1];
        ck2CultureInfo = Importer.importMappingFromArray(mappings,irCulture)[1];

        return ck2CultureInfo;
    }

    public static String religionOutput(ArrayList<String> mappings,String ck2Culture,String tagCulture,String date,String irRel,
    String ck2County, ArrayList<String[]> ck2Geo,String tagTag, ArrayList<String> converterSettings) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        int count = 0;

        String[] ck2ReligionInfo;
        String ck2Religion = "unknown";

        Importer importer = new Importer();

        ArrayList<String[]> validMappings = Importer.importMappingFromArrayArgs(mappings,irRel);
        
        while (count < validMappings.size()) {
            ck2ReligionInfo = validMappings.get(count);
            
            if (ck2ReligionInfo.length == 2) {
                //System.out.println(ck2ReligionInfo[0]+" "+ck2ReligionInfo[1]);
                ck2Religion = ck2ReligionInfo[1];
                return ck2Religion;
            }
            else {
                int numArgs = ck2ReligionInfo.length-2;
                int count2 = 0;
                boolean passedCheck = true;
                while (count2 < numArgs) { //check all arguments, if all are true, return culture
                    int argBeingChecked = count2+2;
                    String[] relArgument = ck2ReligionInfo[argBeingChecked].split("=");
                    if (relArgument[0].equals("culture")) {
                        //System.out.println(relArgument[1]+" "+ck2Culture);
                        if (!ck2Culture.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("tagCulture")) {
                        //System.out.println(relArgument[1]+" "+tagCulture);
                        if (!tagCulture.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("year")) {
                        //System.out.println(relArgument[1]+" "+date);
                        String tmpDate = date.replace(".",",");
                        int saveYearInt = Integer.parseInt(tmpDate.split(",")[0]);
                        int argumentYear = Integer.parseInt(relArgument[1]);
                        if (saveYearInt < argumentYear) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("tagTag")) {
                        //System.out.println(relArgument[1]+" "+tagTag);
                        if (!tagTag.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("setting")) {
                        //System.out.println(relArgument[1]+" "+tagTag);
                        int settingCount = 0;
                        passedCheck = false;
                        while (settingCount < converterSettings.size()) {
                            String selectedSetting = converterSettings.get(settingCount);
                            if (selectedSetting.equals(relArgument[1])) {
                                count2 = count2 + numArgs;
                                passedCheck = true;
                            }
                            settingCount = settingCount + 1;
                        }
                        count2 = count2 + numArgs;
                        
                    }
                    else if (relArgument[0].equals("ck2Region")) {
                        //System.out.println(ck2County + " religion is");
                        if (!Processing.checkGeo("c_"+ck2County, ck2Geo,relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                        //System.out.println(relArgument[1]+" "+ck2County);
                    }
                    else { //if argument is invalid, nullify whole mapping
                       count2 = count2 + numArgs;
                       passedCheck = false; 
                    }
                    
                    count2 = count2+1;
                }
                if (passedCheck == true) {
                    ck2Religion = ck2ReligionInfo[1];
                    return ck2Religion;
                }
                
            }
            count = count + 1;
            
        }
        
        System.out.println("Something went wrong, "+irRel+" will convert as noculture");
        return ck2Religion;
    }

    public static String titleCreationCommon(String irTAG, String irColor, String government,String capital,String rank, String Directory) throws IOException
    {

        String tab = "	";
        String VM = "\\"; 
        VM = VM.substring(0);
        Directory = Directory + VM + "common" + VM + "landed_titles";
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + rank+"_" + irTAG + "_LandedTitle.txt");
        PrintWriter out = new PrintWriter(fileOut);

        out.println (rank+"_"+irTAG+" = {");
        if (!irColor.equals("none")) {
            out.println (tab+"color={ "+irColor+" }");
            out.println (tab+"color2={ "+irColor+" }");
        }

        if (!capital.equals("none")) { //governorships don't have set capitals

            out.println (tab+"capital = "+capital);

        }
        if ( government.equals("republic") ) {
            out.println (tab+tab+tab+"is_republic = yes"); //if it is a republic and republics are enabled  
        } else if (government.equals("imperium") && rank.equals("e")) {
            out.println (tab+"purple_born_heirs = yes"); //if government is imperial, enable born in purple mechanic
            out.println (tab+"has_top_de_jure_capital = yes");
        }
        out.println ("}");

        out.flush();
        fileOut.close();

        return irColor;
    }

    public static ArrayList<String> titleCreation(String irTAG, String irKING, String irCOLOR, String government, String capital,String rank,String liege,
    String date1,String republicOption,String irDynasty,ArrayList<String> dynList,ArrayList<String[]> impCharInfoList,ArrayList<String> convertedCharacters,
    int tagIDNum,String liegeGov,ArrayList<String> cultureMappings,ArrayList<String> religionMappings,ArrayList<String[]> impTagInfo,String[][] ck2ProvInfo,
    ArrayList<String[]> ck2Geo,ArrayList<String> converterSettings,String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String irKING100 = "9"+irKING;

        String tab = "	";

        String oldDynasty = irDynasty;

        if (!government.equals("palace")) { //if palace, don't recalculate dynasty and recreate title
            titleCreationCommon(irTAG,irCOLOR,government,capital,rank,Directory);
            irDynasty = Processing.calcDynID(irDynasty);
        }
        String oldDirectory = Directory;
        Directory = Directory + VM + "history" + VM + "titles";
        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        Importer importer = new Importer();

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + rank+"_" + irTAG + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        String date2 = "1066.9.15";
        out.println (date1+"={");

        String overlordRank = "k";
        if (rank.equals("k")){
            overlordRank = "e";
        } else if (rank.equals("b") && government.equals("palace")) {
            overlordRank = liege.split(",")[0];
            liege = liege.split(",")[1];
            out.println (tab+"holding_dynasty = "+irDynasty);
        }

        if (!liege.equals("no_liege")) { //If country is a subject
            out.println (tab+"liege="+overlordRank+"_"+liege);
            if (!rank.equals("b") && !government.equals("palace")) {
                out.println (tab+"de_jure_liege="+overlordRank+"_"+liege);
            }
            if (liegeGov.equals("republic") && republicOption.equals("repMer")) { //If subject, don't convert as merchant republic
                republicOption = "repFeu";
            }
        }
        if (!irKING.equals("0")) { //if title has no valid character, make it have no holder
            out.println("\tholder="+irKING100);
        }
        if (government.equals("imperium") && rank.equals("e")) { //If I:R government is imperial, set government to CK II imperial (roman_imperial_government)
            out.println (tab+"law = crown_authority_2");
            out.println (tab+"law = succ_byzantine_elective");
            out.println (tab+"law = centralization_3");
            out.println (tab+"law = imperial_administration");
            out.println (tab+"law = ze_administration_laws_2");
            out.println (tab+"law = vice_royalty_2");
            out.println (tab+"law = revoke_title_law_1");
            govCreation(irTAG,rank,"i",oldDirectory);
            imperialSuccession(irTAG,rank,oldDirectory);
        }
        if (government.equals("republic") && republicOption.equals("repMer")) { 
            //If I:R government is republic and option is enabled, set to CK2 merchant republic (regardless of coastline requirements)

            String palace = irDynasty+"_"+irTAG;
            titleCreationCommon(palace,"none","none","none","b",oldDirectory); //creates merchant palace for ruler's family
            convertedCharacters = titleCreation(palace,irKING,irCOLOR,"palace",capital,"b",rank+","+irTAG,date1,republicOption,irDynasty,
                dynList,impCharInfoList,convertedCharacters,tagIDNum,liegeGov,cultureMappings,religionMappings,impTagInfo,ck2ProvInfo,ck2Geo,converterSettings,oldDirectory);
            convertedCharacters = createFamilies(dynList,irTAG,oldDynasty,rank,impCharInfoList,convertedCharacters,date1,republicOption,tagIDNum,
                liegeGov,cultureMappings,religionMappings,impTagInfo,ck2ProvInfo,ck2Geo,converterSettings,oldDirectory);

            govCreation(irTAG,rank,"m",oldDirectory);
        }
        if (government.equals("nomad")) { 
            //If I:R government is a steppe nomad, set to CK2 steppe nomad
            out.println (tab+"historical_nomad = yes");

        }
        out.println ("}");
        out.println ();
        out.flush();
        fileOut.close();

        return convertedCharacters;
    }

    public static String provinceCreation(String ckProv, String ckCult, String ckRel, String Directory, String landType, 
    String name, String gov, String pops, String[] bList, String saveMonuments, String republicOption, int id) throws IOException
    {

        String tab = "	";
        String VM = "\\"; 
        VM = VM.substring(0);
        String bracket1 = "{{"; 
        bracket1 = bracket1.substring(0);
        Directory = Directory + VM + "history" + VM + "provinces";

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + ckProv + " - " + name + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        String[] barony = bList[id].split(",");

        name = name.toLowerCase();

        name = name.replace(' ','_');

        if (id == 103) { //Leon in Brittany and Spain have the same name in definition.csv
            name = "french_leon";  
        } else if (id == 1955) {
            name = "hy_many";  
        } else if (id == 1966) {
            name = "aurillac";  
        } else if (id == 1781) {
            name = "alqusair";  
        } else if (id == 722) {
            name = "al_aqabah";  
        } else if (id == 1379) {
            name = "asayita";  
        } else if (id == 1234) {
            name = "damin_i_koh";  
        } else if (id == 254) {
            name = "wurzburg";  
        } else if (id == 446) {
            name = "znojmo";  
        } else if (id == 715) {
            name = "zanjan_abhar";  
        } else if (id == 242) {
            name = "aargau";  
        } else if (id == 355) {
            name = "padova";  
        } else if (id == 1949) {
            name = "anglesey";  
        } else if (id == 935) {
            //name = "amalfi";  
        }

        String holding1 = "castle";
        String holding2 = "city";
        String holding3 = "temple";
        boolean convRepublic = false;
        if (republicOption.equals("repMer") || republicOption.equals("repRep")) {
            convRepublic = true;
        }

        if (gov.equals ("tribal") || gov.equals ("nomad")) {
            holding1 = "tribal";
            holding2 = "tribal";
            holding3 = "tribal";
        }
        else if (gov.equals ("republic") && convRepublic == true) { //If republic, primary holding becomes city instead of castle
            holding1 = "city";   
            holding2 = "castle";   
        }


        int popNum = Integer.parseInt(pops);
        int holdingTot = 1;

        if (popNum <= 15) {
            holdingTot = 1;  
        } 
        else if (popNum <= 40) {
            holdingTot = 2;    
        }
        else if (popNum <= 60) {
            holdingTot = 3;    
        }
        else if (popNum <= 85) {
            holdingTot = 4;   
        }
        else if (popNum <= 100) {
            holdingTot = 5;   
        }
        else if (popNum <= 300) {
            holdingTot = 6;   
        }
        else if (popNum >= 500) {
            holdingTot = 7;  
        }

        out.println ("# County Title");
        out.println ("title = c_"+name);
        out.println ("");

        out.println ("# Settlements");
        out.println ("max_settlements = "+holdingTot);
        out.println ("b_"+barony[0]+" = "+holding1);

        if (popNum >= 30) {
            out.println ("b_"+barony[1]+" = "+holding2);  
        } 
        if (popNum >= 80) {
            out.println ("b_"+barony[2]+" = "+holding3);    
        }
        if (popNum >= 120) {
            out.println ("b_"+barony[3]+" = "+holding2);  
        }
        if (popNum >= 170) {
            out.println ("b_"+barony[4]+" = "+holding2);
        }
        if (popNum >= 600) {
            out.println ("b_"+barony[5]+" = "+holding2);
        }
        if (popNum <= 1000) {
            out.println ("b_"+barony[6]+" = "+holding2);  
        }
        out.println ("");

        out.println ("# Misc");
        out.println ("culture = "+ckCult);
        out.println ("religion = "+ckRel);
        if (landType.equals ("plains")){ 
        }
        else {
            out.println (landType);
        }

        if (id == 23) { //Stonehenge in pre-2.0 saves
            if (Processing.checkMonumentList(saveMonuments) == 0 || 1 == 1) {//if 0, it is an old save before dynamic/custom monuments
                out.println ("");
                out.println ("# History");
                out.println ("1.1.1 = {");
                out.println (tab+"build_wonder = wonder_pagan_stones_stonehenge");
                out.println (tab+"set_wonder_stage = 3");
                out.println (tab+"set_wonder_damaged = yes");
                out.println ("}");
            }
        }

        if (id == 800) { //The great pyramids of Giza in pre-2.0 saves
            if (Processing.checkMonumentList(saveMonuments) == 0 || 1 == 1) {//if 0, it is an old save before dynamic/custom monuments
                out.println ("");
                out.println ("# History");
                out.println ("1.1.1 = {");
                out.println (tab+"build_wonder = wonder_pyramid_giza");
                out.println (tab+"set_wonder_stage = 3");
                out.println (tab+"build_wonder_upgrade = upgrade_mythological_beast");
                out.println ("}");
            }
        }

        out.flush();
        fileOut.close();

        return ckProv;
    }

    public static String ctitleCreation(String name, String irKING, String Directory, int id,String date1,String overlord) throws IOException
    {

        btitleCreation(name,Directory,id);
        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';

        String irKING100 = "9"+irKING;

        name = name.toLowerCase();

        name = name.replace(' ','_');

        if (id == 103) { //Leon in Brittany and Spain have the same name in definition.csv
            name = "french_leon";  
        }  else if (id == 1955) {
            name = "hy_many";  
        } else if (id == 1966) {
            name = "aurillac";  
        } else if (id == 1781) {
            name = "alqusair";  
        } else if (id == 722) {
            name = "al_aqabah";  
        } else if (id == 1379) {
            name = "asayita";  
        } else if (id == 1234) {
            name = "damin_i_koh";  
        } else if (id == 254) {
            name = "wurzburg";  
        } else if (id == 446) {
            name = "znojmo";  
        } else if (id == 715) {
            name = "zanjan_abhar";  
        } else if (id == 242) {
            name = "aargau";  
        } else if (id == 355) {
            name = "padova";  
        } else if (id == 1949) {
            name = "anglesey";  
        } else if (id == 935) {
            //name = "amalfi";  
        }

        Directory = Directory + VM + "history" + VM + "titles";
        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "c_" + name + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        String date2 = "1066.9.15";

        out.println (date1+"={");
        if (!overlord.equals("none")) {
            out.println ("    liege="+overlord);
        }
        out.println ("    holder="+irKING100);
        out.println ("}");
        out.println ();

        //No longer needed due to using one start date
        //out.println (date2+"={");
        //out.println ("    holder="+irKING);
        //out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static ArrayList<String> characterCreation(String irKING, String cult, String rel, String age, String name, String dynasty,
    String sex, String traits, String martial, String zeal, String charisma, String finesse, String spouse, String children,String government,String father,
    String mother,ArrayList<String> convertedList,ArrayList<String[]> charList,String date1,ArrayList<String> cultureMappings,
    ArrayList<String> religionMappings,ArrayList<String[]> impTagInfo,String[][] ck2ProvInfo,ArrayList<String[]> ck2Geo,ArrayList<String> converterSettings,
    String Directory)
    throws IOException
    {

        int characterCount = 0;

        while (characterCount < convertedList.size()) { //checks if a character has been converted or not
            if (irKING.equals(convertedList.get(characterCount))) {
                return convertedList; //If a character has already been converted, no point to repeat and avoids children with jobs not having fathers
            } else {
                characterCount = characterCount + 1;    
            }

        }

        String VM = "\\"; 
        VM = VM.substring(0);
        String tab = "	";
        char quote = '"';
        String[] spouseInfo;
        String[] childInfo;
        int childCount;
        String spouse1066 = Integer.toString( 1000000 + Integer.parseInt(spouse));
        String child1066;

        int hasFather = 0;
        int hasMother = 0;
        String father100 = "9" + father;
        String mother100 = "9" + mother;

        String dead = "no";
        
        if (government.equals("nomad")) { //set all nomadic characters to follow state culture
            int charID = (Integer.parseInt(irKING)-1000000);
            String[] extendedCharInfo = charList.get(charID);
            int charCountry = Integer.parseInt(extendedCharInfo[17]);
            cult = impTagInfo.get(charCountry)[6];
        }

        if (sex.length() > 1) {
            if (sex.charAt(1) == '_') {
                dead = "yes";

                sex = sex.split("_")[0];  
            }
        }

        if (father != "q") {
            hasFather = 1;

        }

        if (mother != "q") {
            hasMother = 1;

        }

        int aq4 = 0;

        if (spouse != "0") {//Recursively calls to get rest of family
            int spouseID = Integer.parseInt(spouse);
            spouseInfo = charList.get(spouseID);
            String spouseCulture = cultureOutput(cultureMappings,spouseInfo[1]);
            int spouseCountry = Integer.parseInt(spouseInfo[17]);
            String spouseCountryCulture = impTagInfo.get(spouseCountry)[6];
            String spouseCountryTag = impTagInfo.get(spouseCountry)[0];
            int spouseCountryCapital = Integer.parseInt(impTagInfo.get(spouseCountry)[5]);
            String spouseCountryCapitalCounty = ck2ProvInfo[5][spouseCountryCapital];
            String spouseReligion = religionOutput(religionMappings,spouseCulture,spouseCountryCulture,date1,spouseInfo[2],spouseCountryCapitalCounty,ck2Geo,spouseCountryTag,converterSettings);

            characterCreation( spouse1066,spouseCulture,  spouseReligion,  spouseInfo[3],  spouseInfo[0],
            spouseInfo[7],spouseInfo[4],  spouseInfo[8],  martial,  zeal,  charisma,  finesse,  "0",  "0", "no","q",  "q",convertedList,charList,date1,
            cultureMappings,religionMappings,impTagInfo,ck2ProvInfo,ck2Geo,converterSettings,Directory);
        }

        if (children != "0") {
            childCount = 1;
            try {
                if (children.split(" ")[1] != null) {
                    childCount = children.split(" ").length-1;   
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException exception) {

            }
            String isPurple = "no";
            if (government.equals("imperium") || government.equals("purple")) {
                isPurple = "purple";
            }

            while (aq4 < childCount) {//Recursively calls to get rest of family

                int childID = Integer.parseInt(children.split(" ")[aq4]);

                childInfo = charList.get(childID);
                child1066 = Integer.toString( 1000000 + Integer.parseInt(children.split(" ")[aq4]) );
                String childCulture = cultureOutput(cultureMappings,childInfo[1]);
                int childCountry = Integer.parseInt(childInfo[17]);
                String childCountryCulture = impTagInfo.get(childCountry)[6];
                String childCountryTag = impTagInfo.get(childCountry)[0];
                int childCountryCapital = Integer.parseInt(impTagInfo.get(childCountry)[5]);
                String childCountryCapitalCounty = ck2ProvInfo[5][childCountryCapital];
                String childReligion = religionOutput(religionMappings,childCulture,childCountryCulture,date1,childInfo[2],childCountryCapitalCounty,ck2Geo,childCountryTag,converterSettings);

                characterCreation( child1066, childCulture, childReligion,  childInfo[3],  childInfo[0],  childInfo[7],
                    childInfo[4],  childInfo[8],  martial,  zeal,  charisma,  finesse,  childInfo[14],  childInfo[15], isPurple,irKING,spouse1066,
                    convertedList,charList,date1,cultureMappings,religionMappings,impTagInfo,ck2ProvInfo,ck2Geo,converterSettings,Directory);

                aq4 = aq4 + 1;
            }
            aq4 = 0;
        }

        String irKING100 = "9" + irKING;

        String spouse100 = "9" + spouse1066;

        int numAge = Integer.parseInt(age);

        Directory = Directory + VM + "history" + VM + "characters";
        if (sex != "69") {
            //for all non-generated characters
            //dynasty = Integer.toString(Integer.parseInt(dynasty) + 700000000);
            dynasty = Processing.calcDynID(dynasty);
        }

        String tempTrait = "a";
        int aqq = 0;
        int aq2 = 0;

        ArrayList<String> convTraitList = new ArrayList<String>();

        if (traits != "q") {
            //oldlogPrint("traitsGood"); 
            String[] traitList = traits.split(" ");

            try {
                while (aqq < 99) {
                    if (traitList[aqq].charAt(0) == quote) {
                        tempTrait = traitList[aqq].substring(1,traitList[aqq].length()-1);    
                    } else {
                        tempTrait = traitList[aqq];
                    }

                    tempTrait = Importer.importCultList("charTraitConverter.txt",tempTrait)[1];   
                    if (tempTrait != "99999") {
                        convTraitList.add (tempTrait);
                        aq2 = aq2 + 1;
                    }
                    aqq = aqq + 1;
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException exception) {
                aqq = 999;    
            }

        }

        aqq = 0;

        Importer importer = new Importer();

        //any filename with non-asci characters won't render in CK II, changed file output name to use GloriousCharacter instead of character name
        //in case player created interesting names in I:R

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "GloriousCharacter" + "_" + irKING + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        String tmpDate = date1.replace(".",","); //'.' character breaks .split function
        String monthDay = "." + tmpDate.split(",")[1] + "." + tmpDate.split(",")[2];
        monthDay = monthDay.replace(",",".");
        String date2 = "1066.9.15";
        int yearNum = Integer.parseInt(tmpDate.split(",")[0]);
        int birthdayNum2 = 1066 - numAge;
        int birthdayNum = yearNum - numAge;
        String birthday2 = Integer.toString(birthdayNum2)+".9.15";
        String birthday = Integer.toString(birthdayNum)+monthDay;

        //Military/Martial Charisma/Stewardship Zeal/Learning

        aqq = 0;
        //100 Start date
        out.println ();
        out.println (irKING100+"={");
        out.println (tab+"name="+quote+name+quote);
        if (sex.equals("f")) {
            out.println (tab+"female = yes");    
        }
        if (government.equals("purple")) {
            out.println (tab+"trait="+quote+"born_in_the_purple"+quote);    
        }
        out.println (tab+"dynasty="+dynasty);
        out.println (tab+"martial="+martial);
        out.println (tab+"diplomacy="+zeal);
        out.println (tab+"intrigue="+finesse);
        out.println (tab+"stewardship="+charisma);
        out.println (tab+"religion="+quote+rel+quote);
        out.println (tab+"culture="+quote+cult+quote);
        if (rel.equals("hindu") || rel.equals("buddhist") || rel.equals("jain")) { //Caste system defaults to merchant unless specified, sets to ruler
            out.println (tab+"trait=kshatriya");
        }

        if (traits != "q") {
            while (aqq < aq2) {
                if (convTraitList.get(aqq).charAt(convTraitList.get(aqq).length()-1) == 'B' && hasFather == 0) { //bloodline
                    out.println (tab+date1+"={");
                    out.println (tab+tab+"create_bloodline = {");
                    out.println (tab+tab+tab+"type = "+convTraitList.get(aqq).substring(0,convTraitList.get(aqq).length()-1));
                    out.println (tab+tab+tab+"has_dlc = "+quote+"Holy Fury"+quote);
                    out.println (tab+tab+"}");
                    out.println (tab+"}");    
                }else { //regular trait

                    out.println (tab+"trait="+convTraitList.get(aqq));

                }
                aqq = aqq + 1;
            }

        }

        if (sex != "69") { //if a character is dynamically generated or not
            out.println (tab+"disallow_random_traits = yes");    
        }

        if (hasFather == 1) {
            out.println (tab+"father="+father100);

        }

        if (hasMother == 1) {
            out.println (tab+"mother="+mother100);

        }

        out.println (tab+birthday+"={");
        out.println (tab+tab+"birth="+quote+birthday+quote);
        out.println (tab+"}");

        if (spouse != "0") {
            out.println (tab+date1+"={");
            out.println (tab+tab+"add_spouse="+spouse100);
            out.println (tab+"}");
        }

        //default death date so the character will be dead in the 1066 start date
        if (dead.equals("yes")) {
            out.println (tab+date1+" ={");   
        } else {
            out.println (tab+(yearNum+250)+".1.1 ={");    
        }
        out.println (tab+tab+"death= yes");
        out.println (tab+"}");
        out.println ("}");

        out.flush();
        fileOut.close();

        convertedList.add(irKING);

        return convertedList;
    }

    public static String dynastyCreation(String name, String id, String backupName, String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        if (name.split(" ")[0].equals ("minor")) {
            name = backupName;
        }

        if (backupName.equals("debug")) { //gives all IR character dynasties + 700000000 to prevent conflict, generated ones (debug) use + 6000000
        } else {

            id = Processing.calcDynID(id);
        }

        Directory = Directory + VM + "common" + VM + "dynasties";
        String ck2CultureInfo ="a";   // debug output
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "c_" + id + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        String date1 = "100.1.1";
        String date2 = "1066.9.15";

        out.println (id+"=");
        out.println ("{");
        out.println (tab+"name="+VMq+name+VMq);
        out.println (tab+"used_for_random=no");
        out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static String btitleCreation(String name, String Directory, int id) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";
        String oldName = name;
        name = name.toLowerCase();
        name = name.replace(' ','_');

        if (id == 103) { //Leon in Brittany and Spain have the same name in definition.csv
            name = "french_leon";  
        } else if (id == 1955) {
            name = "hy_many";  
        } else if (id == 1966) {
            name = "aurillac";  
        } else if (id == 1781) {
            name = "alqusair";  
        } else if (id == 722) {
            name = "al_aqabah";  
        } else if (id == 1379) {
            name = "asayita";  
        } else if (id == 1234) {
            name = "damin_i_koh";  
        } else if (id == 254) {
            name = "wurzburg";  
        } else if (id == 446) {
            name = "znojmo";  
        } else if (id == 715) {
            name = "zanjan_abhar";  
        } else if (id == 242) {
            name = "aargau";  
        } else if (id == 355) {
            name = "padova";  
        } else if (id == 1949) {
            name = "anglesey";  
        } else if (id == 935) {
            //name = "amalfi";  
        }

        Directory = Directory + VM + "common" + VM + "landed_titles";

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "b_" + name + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        out.println ("c_"+name+"={");
        out.println (tab+"b_"+name+"={");
        out.println (tab+"}");
        out.println ("}");
        out.flush();
        fileOut.close();

        return "a";
    }

    public static String localizationCreation(String[] name, String title, String rank, String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importModLocalisation(Directory);

        Directory = Directory + VM + "localisation";
        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "converted_title_localisation.csv");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        int aqq = 0;

        try {

            while (flag == 0) {
                out.println (oldFile.get(aqq));
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            flag = 1;

        } 

        out.println (rank+"_"+title+";"+name[0]+";"+name[0]+";"+name[0]+";;"+name[0]+";;;;;;;;;x");
        out.println (rank+"_"+title+"_adj"+";"+name[1]+";"+name[1]+";"+name[1]+";;"+name[1]+";;;;;;;;;x");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static String localizationBlankFile(String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';

        Directory = Directory + VM + "localisation";
        String ck2CultureInfo ="a";   // blank default
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "converted_title_localisation.csv");
        PrintWriter out = new PrintWriter(fileOut);

        out.println ("#Localization for all kingdom titles");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static void logPrint(String name) throws IOException //outputs to log.txt
    {

        String logFile = "debugTest.txt";

        ArrayList<String> oldFile = new ArrayList<String>();
        oldFile = Importer.importBasicFile(logFile);

        FileOutputStream fileOut= new FileOutputStream(logFile);
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        int aqq = 0;

        try {

            while (flag == 0) {
                out.println (oldFile.get(aqq));
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            flag = 1;

        } 

        out.println(name);
        out.flush();
        fileOut.close();

    }

    public static void logBlank() throws IOException //creates/replaces new log file
    {

        String logFile = "log.txt";

        FileOutputStream fileOut= new FileOutputStream(logFile);
        PrintWriter out = new PrintWriter(fileOut);

        out.println ("Log File for I:R to CK II converter");
        out.println ("If I:R to CK II crashes or has problems, send this log file and your I:R save game to the Paradox Converter team ASAP!");
        out.println ("");
        out.flush();
        fileOut.close();

    }

    public static void dejureTitleCreation(ArrayList<String[]> impTagInfo, int empireRank,int duchyRank, int[] ck2LandTot, ArrayList<String> dejureDuchies,
    ArrayList<String> impSubjectInfo,ArrayList<String> cultureMappings, String Directory) throws IOException
    {

        String tab = "	";
        String VM = "\\"; 
        VM = VM.substring(0);
        Directory = Directory + VM + "common" + VM + "landed_titles";
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "titlesDejure.txt"); //CK II's engine is picky with the file name
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < dejureDuchies.size()) {

            String duchy = dejureDuchies.get(aqq).split(",")[2];
            String tag = dejureDuchies.get(aqq).split(",")[0];
            String region = dejureDuchies.get(aqq).split(",")[3];
            String culture = dejureDuchies.get(aqq).split(",")[1];

            if (tag.equals("99999") || tag.equals("none") || tag.equals("null")) {

            } 

            else if (tag.equals("9999")) { //uncolonized province, creates dejure provinces based off of culture

                String[] cultureTitles = Processing.defaultDejureConversion(culture);
                cultureTitles = Processing.calculateUsedTitles(cultureTitles,impTagInfo,empireRank,ck2LandTot); //determines if tag exists in I:R

                out.println (cultureTitles[1]+" = {");
                out.println (tab+cultureTitles[2]+" = {");
                out.println (tab+tab+duchy+" = {");
                out.println (tab+tab+"}");
                out.println (tab+"}");
                out.println ("}");

            } else {

                int tagID = Integer.parseInt(tag);

                String rank = "k";
                
                int subjectOrNot = Processing.checkSubjectList(tagID,impSubjectInfo);

                if ((ck2LandTot[tagID] >= empireRank && subjectOrNot == 9999) || (impTagInfo.get(tagID)[17].equals("imperium") && subjectOrNot == 9999)) {
                    rank = "e";

                    int aq2 = 0;

                    int flag = 0;

                    out.println (rank+"_"+impTagInfo.get(tagID)[0]+" = {");

                    if (impTagInfo.get(tagID)[20] != "none") {
                        String[] governorships = impTagInfo.get(tagID)[20].split(",");
                        aq2 = 0;
                        while (aq2 < governorships.length) {
                            String govReg = governorships[aq2].split("~")[0];
                            if (region.equals(govReg)) {
                                tag = impTagInfo.get(tagID)[0]+"__"+govReg;
                                aq2 = aq2 + governorships.length;
                                flag = 1;

                            } else {
                                aq2 = aq2 + 1;    
                            }
                        }

                    }

                    if (flag == 0) {
                        out.println (tab+"k"+"_"+impTagInfo.get(tagID)[0]+" = {");
                    } else {
                        out.println (tab+"k"+"_"+tag+" = {");
                    }

                    out.println (tab+tab+duchy+" = {");
                    out.println (tab+tab+"}");
                    out.println (tab+"}");

                    out.println ("}");

                }
                else {
                    //int subjectOrNot = Processing.checkSubjectList(tagID,impSubjectInfo);
                    int flag2 = 0;
                    int hasDejureEmpire = 0;

                    if (subjectOrNot != 9999) { //if tag is not free
                        String[] subjectInfo = impSubjectInfo.get(subjectOrNot).split(",");
                        tagID = Integer.parseInt(subjectInfo[0]);

                        if (ck2LandTot[tagID] >= empireRank) { //if overlord is e tier, give subject dejure land with overlord as liege
                            out.println ("e_"+impTagInfo.get(tagID)[0]+" = {");//+"#3_"+tagID
                            tagID = Integer.parseInt(tag);
                            flag2 = 1;
                            hasDejureEmpire = 1;
                        } else { //if overlord is k tier, calculate liege
                            //String[] cultureTitles = Processing.defaultDejureConversion(cultureOutput(cultureMappings,impTagInfo.get(tagID)[6]));
                            String[] cultureTitles = Processing.defaultDejureConversion(impTagInfo.get(tagID)[6]);
                            cultureTitles = Processing.calculateUsedTitles(cultureTitles,impTagInfo,empireRank,ck2LandTot); //determines if tag exists in I:R
                            //print e_liege
                            out.println (cultureTitles[1]+" = {");
                            hasDejureEmpire = 1;
                        }
                    } else if (ck2LandTot[tagID] > duchyRank) { //if tag is independent k tier, assign appropriate dejure culture empire liege
                        //String[] cultureTitles = Processing.defaultDejureConversion(cultureOutput(cultureMappings,impTagInfo.get(tagID)[6]));
                        String[] cultureTitles = Processing.defaultDejureConversion(impTagInfo.get(tagID)[6]);
                        cultureTitles = Processing.calculateUsedTitles(cultureTitles,impTagInfo,empireRank,ck2LandTot); //determines if tag exists in I:R
                        //print e_liege
                        out.println (cultureTitles[1]+" = {#q1");
                        flag2 = 1;
                        hasDejureEmpire = 1;
                    }
                    
                    if (ck2LandTot[tagID] <= duchyRank) { //if duchy, set rank, set kindom liege, and set dejure liege
                            //String[] cultureTitles = Processing.defaultDejureConversion(cultureOutput(cultureMappings,impTagInfo.get(tagID)[6]));
                            String[] cultureTitles = Processing.defaultDejureConversion(impTagInfo.get(tagID)[6]);
                            cultureTitles = Processing.calculateUsedTitles(cultureTitles,impTagInfo,empireRank,ck2LandTot); //determines if tag exists in I:R
                            if (flag2 != 1) {
                                if (hasDejureEmpire == 0) {
                                    out.println (cultureTitles[1]+" = {");//emperor
                                    hasDejureEmpire = 1;
                                }
                                rank = "d";
                                out.println (tab+cultureTitles[2]+" = {"); //king
                                out.println (tab+tab+rank+"_"+impTagInfo.get(tagID)[0]+" = {");
                                out.println (tab+tab+"}");
                            } else {
                                out.println (tab+rank+"_"+impTagInfo.get(tagID)[0]+" = {");
                            }
                            flag2 = 1;
                        }
                    else {
                        out.println (tab+rank+"_"+impTagInfo.get(tagID)[0]+" = {");
                    }
                    out.println (tab+tab+duchy+" = {");
                    out.println (tab+tab+"}");
                    out.println (tab+"}");
                    out.println ("}");
                }
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

    }

    public static String govCreation(String title, String rank, String govFile, String Directory) throws IOException 
    //needed to allow TAGs imperial government and merchant republic government
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        if (govFile.equals("i")) { //for convienience
            govFile = "imperial_governments.txt";
        } else if (govFile.equals("m")) {
            govFile = "merchant_republic_governments.txt";
        }

        Directory = Directory + VM + "common" + VM + "governments" + VM + govFile;

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(Directory);

        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < oldFile.size()) {
            out.println (oldFile.get(aqq));
            String key = oldFile.get(aqq).replace(tab,"");
            key = key.replace("#","");
            if (key.equals("title = e_TAG")) {
                String imperialTag = oldFile.get(aqq).replace("#","");
                imperialTag = imperialTag.replace("e_TAG",rank+"_"+title);
                out.println (imperialTag);
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static String imperialSuccession(String title, String rank, String Directory) throws IOException //needed to allow TAGs imperial laws
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        Directory = Directory + VM + "common" + VM + "laws" + VM + "succession_laws.txt";

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(Directory);

        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < oldFile.size()) {
            out.println (oldFile.get(aqq));
            if (oldFile.get(aqq).contains("e_TAG")) {
                String imperialTag = oldFile.get(aqq).replace("e_TAG",rank+"_"+title);
                imperialTag = imperialTag.replace("#","");
                out.println (imperialTag);
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static void copyRaw(String dir1, String dir2) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);

        FileInputStream fileIn= new FileInputStream(dir1);
        FileOutputStream fileOut= new FileOutputStream(dir2);

        boolean endOrNot = true;
        int aqq = 0;

        try {
            while (endOrNot = true && aqq != -1){
                if (aqq != -1) {
                    aqq = fileIn.read();
                    fileOut.write(aqq);
                }

            }
        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;
            fileIn.close();
            fileOut.close();

        }   

    }

    public static void copyFlag(String ck2Dir, String modDirectory, String rank, String prov, String tag) throws IOException //copies flag files
    {

        prov = Processing.importNames("a",Integer.parseInt(prov),ck2Dir)[0];
        prov = Processing.formatProvName(prov);
        try {
            Output.copyRaw(ck2Dir+"/gfx/flags/c_"+prov+".tga",modDirectory+"/gfx/flags/"+rank+"_"+tag+".tga");
        }catch (java.io.FileNotFoundException exception) { //if flag cannot be found, will use default one
            Output.copyRaw("defaultOutput/flagDev/c_default.tga",modDirectory+"/gfx/flags/"+rank+"_"+tag+".tga");
        }

    }

    //creates I:R flag
    public static int generateFlag(String ck2Dir, String irDirectory, String rank, ArrayList<String[]> flagList, String tag, String flagID,
    ArrayList<String[]> colorList, ArrayList<String> flagGFXList, String modDirectory) throws IOException
    {
        int flagCreated = 0; //if flag is successfully created, change to 1
        //output[2] format = hsvOrRgb,r g b
        //output[3] format = hsvOrRgb,r g b
        // output[4] format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)
        //System.out.println("Attempting to create "+tag+" + "+flagID);
        int aqq = 1;
        int flag = 0;
        while (aqq < flagList.size() && flag == 0) {
            if (flagList.get(aqq)[0].equals(flagID)) {
                flag = 1; //end loop
                String[] flagSource = flagList.get(aqq);
                String ck2Tag = rank+"_"+tag;
                if (flagSource[1].equals("pattern_horizontal_split_02.tga")) {
                    flagSource[1] = "pattern_horizontal_split_01.tga";
                } else if (flagSource[1].equals("pattern_horizontal_split_01.tga")) {
                    flagSource[1] = "pattern_horizontal_split_02.tga";
                }
                String pattern = irDirectory + "/game/gfx/coat_of_arms/patterns/" + flagSource[1];
                String color1 = flagSource[2];
                String color2 = flagSource[3];
                String emblems = flagSource[4];

                int aq3 = 0;
                while (aq3 < flagGFXList.size()) {
                    if (flagGFXList.get(aq3).contains(flagSource[1])) {
                        //System.out.println("AQ3");
                        pattern = flagGFXList.get(aq3);
                        aq3 = flagGFXList.size();
                    }
                    //System.out.println(flagSource[1] + " "+aq3 + " " + flagGFXList.size());
                    aq3 = aq3 + 1;
                }

                color1 = getColor(color1,colorList);
                color2 = getColor(color2,colorList);

                String devFlagName = "defaultOutput/flagDev/"+ck2Tag+"Dev.gif";
                String flagName = modDirectory+"/gfx/flags/"+ck2Tag+".tga";

                irFlagBackground(pattern,devFlagName,color1,color2);

                String[] emblemList = emblems.split("~~");
                int aq2 = 0;
                while (aq2 < emblemList.length) {
                    String[] emblem = emblemList[aq2].split("~_~");
                    String eTexture = irDirectory+"/game/gfx/coat_of_arms/colored_emblems/"+emblem[0];
                    String eColor1 = emblem[1];
                    String eColor2 = emblem[2];
                    String eScale = emblem[3];
                    String ePos = emblem[4];
                    String eRot= emblem[5];
                    String eNameOld = "defaultOutput/flagDev/emblem"+aq2+"Old"+".gif";
                    String eName = "defaultOutput/flagDev/emblem"+aq2+".gif";
                    
                    //System.out.println(emblemList[aq2] + " "+aq2 + " " + emblemList.length);

                    int aq4 = 0;
                    while (aq4 < flagGFXList.size()) {
                        if (flagGFXList.get(aq4).contains(emblem[0])) {
                            eTexture = flagGFXList.get(aq4);
                            aq4 = flagGFXList.size();
                        }
                        aq4 = aq4 + 1;
                    }

                    eColor1 = getColor(eColor1,colorList);
                    if (!eColor2.equals("none")) {
                        eColor2 = getColor(eColor2,colorList);
                    }

                    irFlagEmblem(eTexture,eNameOld,eColor1,eColor2,eName,eScale,eRot,ePos,devFlagName);
                    aq2 = aq2+1;
                }
                irFlagScaleExact(devFlagName,flagName,"128","128"); //create final .tga flag, scale to CK2 128x128
                File flagCheck = new File (flagName);//Check if flag exists
                flagCreated = 1; //Flag has been created
            }

            aqq = aqq + 1;

        }
        return flagCreated;

    }

    public static ArrayList<String> createFamilies (ArrayList<String> dynList, String tag, String rulerFamily, String rank,
    ArrayList<String[]> impCharInfoList,ArrayList<String> convertedCharacters,String date,String republicOption, int tagIDNum, 
    String liegeGov,ArrayList<String> cultureMappings,ArrayList<String> religionMappings,ArrayList<String[]> impTagInfo,String[][] ck2ProvInfo,
    ArrayList<String[]> ck2Geo,ArrayList<String> converterSettings,String directory) throws IOException
    //creates families for Merchant Republics
    {
        int aqq = 1;
        String VM = "\\"; 
        VM = VM.substring(0);
        String tagID = Integer.toString(tagIDNum);
        String families = Characters.getMajorFamilies(dynList,tagID);
        String[] familyList = families.split(",");
        String rulerFamilyOld = rulerFamily;
        rulerFamily = Processing.calcDynID(rulerFamily);
        String bDirectory = directory;
        while (aqq < familyList.length && aqq <= 5) {
            String[] dynasty = Characters.searchWholeDynasty(dynList,familyList[aqq]);
            String newDynasty = Processing.calcDynID(dynasty[1]);
            if (!newDynasty.equals(rulerFamilyOld)) { //if not of ruler's family
                String palace = newDynasty + "_" + tag;
                String palaceDir = bDirectory + VM + "b_" + palace + ".txt";
                if (Processing.checkFile(palaceDir) == false) { //if barony doesn't already exist
                    String head = Processing.calcHead(impCharInfoList,dynasty[4]);
                    String headNum = Processing.calcCharID(head);
                    String[] headCharacter = impCharInfoList.get(Integer.parseInt(head));
                    String headCulture = cultureOutput(cultureMappings,headCharacter[1]);
                    //ArrayList<String[]> impTagInfo
                    int headCountry = Integer.parseInt(headCharacter[17]);
                    String headCountryCulture = impTagInfo.get(headCountry)[6];
                    String headCountryTag = impTagInfo.get(headCountry)[0];
                    int headCountryCapital = Integer.parseInt(impTagInfo.get(headCountry)[5]);
                    String headCountryCapitalCounty = ck2ProvInfo[5][headCountryCapital];
                    String headReligion = religionOutput(religionMappings,headCulture,headCountryCulture,date,headCharacter[2],headCountryCapitalCounty,
                    ck2Geo,headCountryTag,converterSettings);

                    convertedCharacters = characterCreation(headNum, headCulture,headReligion,
                        headCharacter[3],headCharacter[0],headCharacter[7],headCharacter[4],headCharacter[8],headCharacter[10],headCharacter[11],
                        headCharacter[12],headCharacter[13],headCharacter[14],
                        headCharacter[15],"palace","q","q",convertedCharacters,impCharInfoList,date,cultureMappings,religionMappings,impTagInfo,
                        ck2ProvInfo,ck2Geo,converterSettings,directory);

                    dynastyCreation(dynasty[0],headCharacter[7],headCharacter[16],directory);

                    titleCreationCommon(palace,"none","none","none","b",directory); //creates merchant palace for ruler's family
                    convertedCharacters = titleCreation(palace,headNum,"none","palace","none","b",rank+","+tag,date,republicOption,newDynasty,dynList,
                        impCharInfoList,convertedCharacters,tagIDNum,liegeGov,cultureMappings,religionMappings,impTagInfo,ck2ProvInfo,ck2Geo,converterSettings,directory);

                }
            }
            aqq = aqq + 1;

        }

        return convertedCharacters;
    }

    public static void irFlagEmblem(String eTexture,String eNameOld,String eColor1,String eColor2,String eName,String eScale,
    String eRot,String ePos,String devFlagName) throws IOException //generates and applies emblem to flag
    {
        //eTexture is source emblem gfx file
        //eNameOld is modified gfx file potentially not 256x256 (needs to be a separate image from eName due to IM quirks)
        //eName is modified gfx file set to 256x256, ready to be scaled, rotated, or positioned
        //devFlagName is the name of the combined development .gif flag
        if (!eTexture.contains("gfx/coat_of_arms/textured_emblems/")) { //if not a textured emblem, colorize emblem
            irFlagBackground(eTexture,eNameOld,eColor1,eColor2);
            irFlagScaleExact(eNameOld,eName,"256","256"); //set's size to 256 x 256
        } else { //if a textured emblem, don't colorize
            irFlagScaleExact(eTexture,eName,"256","256"); //set's size to 256 x 256
        }

        if (!eScale.equals("none")) {
            irFlagScale(eName,eScale);
        }

        if (!eRot.equals("none")) {
            irFlagRotate(eName,eRot);
        }
        if (!ePos.equals("none")) {
            irFlagPos(eName,ePos);
        }

        irFlagCombine(devFlagName,eName,devFlagName); //merge emblem into dev flag
    }

    public static void irFlagBackground(String oldName, String name, String color, String color2) throws IOException
    {
        irFlagColor(name,oldName,color,"1");
        if (!color2.equals("none") && !oldName.contains("pattern_solid.tga")) {
            String layer2Name = name.replace(".gif","layer2.gif");
            layer2Name = layer2Name.replace(".tga","layer2.gif");
            layer2Name = layer2Name.replace(".dds","layer2.gif");
            irFlagColor(layer2Name,oldName,"none","1");
            irFlagColor(layer2Name,layer2Name,color2,"2");
            irFlagCombine(name,layer2Name,name);
        }
    }

    public static void irFlagColor(String name, String oldName, String color, String oneOrTwo) throws IOException
    {
        String replaceColor = oneOrTwo;
        if (oneOrTwo.equals("1")) {
            replaceColor = "red";
        }
        else if (oneOrTwo.equals("2")) {
            replaceColor = "yellow";
        }
        String[] rakalyCommand = new String [10];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = oldName;
        rakalyCommand[3] = "-fuzz";
        rakalyCommand[4] = "40%";
        rakalyCommand[5] = "-fill";
        rakalyCommand[6] = color;
        rakalyCommand[7] = "-opaque";
        rakalyCommand[8] = replaceColor;
        rakalyCommand[9] = name;
        Processing.fileExcecute(rakalyCommand);
        irFlagColorBlueGreen(name,name,color,oneOrTwo);
    }
    
    public static void irFlagColorBlueGreen(String name, String oldName, String color, String oneOrTwo) throws IOException //I:R supports blue/green gfx
    {
        String replaceColor = oneOrTwo;
        if (oneOrTwo.equals("1")) {
            replaceColor = "blue";
        }
        else if (oneOrTwo.equals("2")) {
            replaceColor = "lime";
        }
        String[] rakalyCommand = new String [10];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = oldName;
        rakalyCommand[3] = "-fuzz";
        rakalyCommand[4] = "40%";
        rakalyCommand[5] = "-fill";
        rakalyCommand[6] = color;
        rakalyCommand[7] = "-opaque";
        rakalyCommand[8] = replaceColor;
        rakalyCommand[9] = name;
        Processing.fileExcecute(rakalyCommand);
    }

    public static String irFlagScale(String name, String percent) throws IOException
    {

        percent = percent.replace("  "," ");
        String[] numbers = percent.split(" ");
        double scaleNum1 = Double.parseDouble(numbers[0]) * 256;
        double scaleNum2 = Double.parseDouble(numbers[1]) * 256;
        if (scaleNum1 < 0) {
            scaleNum1 = scaleNum1 * -1;
            irFlagFlip(name,name,"x");
        }
        if (scaleNum2 < 0) {
            scaleNum2 = scaleNum2 * -1;
            irFlagFlip(name,name,"y");
        }

        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-resize";
        rakalyCommand[4] = scaleNum1 + "x" + scaleNum2 + "!";
        rakalyCommand[5] = "-quality";
        rakalyCommand[6] = "92";
        rakalyCommand[7] = name;
        Processing.fileExcecute(rakalyCommand);
        irFlagCanvas(name,"256","256");
        return scaleNum1 + "x" + scaleNum2;
    }

    public static void irFlagScaleExact(String oldName,String newName, String dim1, String dim2) throws IOException //scales based on exact dimensions
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = oldName;
        rakalyCommand[3] = "-resize";
        rakalyCommand[4] = dim1 + "x" + dim2 + "!";
        rakalyCommand[5] = "-quality";
        rakalyCommand[6] = "92";
        rakalyCommand[7] = newName;
        Processing.fileExcecute(rakalyCommand);
    }

    public static void irFlagCanvas(String name,String dim1,String dim2) throws IOException //set's the canvas
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-gravity";
        rakalyCommand[4] = "center";
        rakalyCommand[5] = "-extent";
        rakalyCommand[6] = dim1+"x"+dim2;
        rakalyCommand[7] = name;
        Processing.fileExcecute(rakalyCommand);

    }

    public static void irFlagRotate(String name, String degrees) throws IOException
    {
        String[] rakalyCommand = new String [11];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-background";
        rakalyCommand[4] = "none";
        rakalyCommand[5] = "-virtual-pixel";
        rakalyCommand[6] = "background";
        rakalyCommand[7] = "-distort";
        rakalyCommand[8] = "ScaleRotateTranslate";
        rakalyCommand[9] = degrees;
        rakalyCommand[10] = name;
        Processing.fileExcecute(rakalyCommand);
    }

    public static String irFlagPos(String name, String position) throws IOException
    {
        String[] numbers = position.split(" ");
        if (numbers.length < 2) { //in case flag has broken formatting (OEO's 0.5340.5, for example)
            //numbers[0] = position.split(".")[0] + "." + position.split(".")[1];
            return "Malformed position data " + position;
        }
        int posNumX = (int)(Double.parseDouble(numbers[0]) * 256);
        int posNumY = (int)(Double.parseDouble(numbers[1]) * 256);
        String posXY = posNumX+","+posNumY;
        String test = "'128,128 "+posXY+"'";

        String[] rakalyCommand = new String [9];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-virtual-pixel";
        rakalyCommand[4] = "none";
        rakalyCommand[5] = "-distort";
        rakalyCommand[6] = "Affine";
        rakalyCommand[7] = "128,128 "+posXY;
        rakalyCommand[8] = name;
        Processing.fileExcecute(rakalyCommand);

        return test;
    }

    public static void irFlagCombine(String background, String emblem, String product) throws IOException //combined test
    {
        String[] rakalyCommand = new String [7];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "composite";
        rakalyCommand[2] = "-gravity";
        rakalyCommand[3] = "center";
        rakalyCommand[4] = emblem;
        rakalyCommand[5] = background;
        rakalyCommand[6] = product;
        Processing.fileExcecute(rakalyCommand);
    }

    public static void irFlagFlip(String background, String product, String dim) throws IOException //combined test
    {
        String flipOrFlop = "-flop"; //flop for x, flip for y
        if (dim.equals("y")) {
            flipOrFlop = "-flip";
        }
        String[] rakalyCommand = new String [4];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = background;
        rakalyCommand[2] = flipOrFlop;
        rakalyCommand[3] = product;
        Processing.fileExcecute(rakalyCommand);
    }

    public static void irFlagHue(String background, String hue, String product) throws IOException //combined test
    {
        String[] rakalyCommand = new String [6];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = background;
        rakalyCommand[3] = "-modulate";
        rakalyCommand[4] = hue;
        rakalyCommand[5] = product;
        Processing.fileExcecute(rakalyCommand);
    }
    
    public static void irFlagShadow(String shadow, String devFlagName) throws IOException //gives image a shadow, doesn't work with .gif files!
    {
        String[] rakalyCommand = new String [6];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = devFlagName;
        rakalyCommand[3] = "-shadow";
        rakalyCommand[4] = "70x15+60+60";
        rakalyCommand[5] = shadow;
        Processing.fileExcecute(rakalyCommand);
        irFlagCombine(shadow,devFlagName,shadow);
    }
    
    public static void irFlagCombineCutout(String devFlagName,String mask,String overlay,String output) throws IOException
    //Cuts out parts of the flag based on the mask, then fills those parts back in with the overlay
    {
        String[] rakalyCommand = new String [18];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = devFlagName;
        rakalyCommand[3] = "-alpha";
        rakalyCommand[4] = "set";
        rakalyCommand[5] = "-gravity";
        rakalyCommand[6] = "center";
        rakalyCommand[7] = "-extent";
        rakalyCommand[8] = "256x256";
        rakalyCommand[9] = mask;
        rakalyCommand[10] = "-compose";
        rakalyCommand[11] = "DstIn";
        rakalyCommand[12] = "-composite";
        rakalyCommand[13] = overlay;
        rakalyCommand[14] = "-compose";
        rakalyCommand[15] = "Over";
        rakalyCommand[16] = "-composite";
        rakalyCommand[17] = output;
        
        Processing.fileExcecute(rakalyCommand);
    }
    
    public static void irFlagTexture(String devFlagName, String texture, String output) throws IOException //applies texture to image
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "composite";
        rakalyCommand[2] = texture;
        rakalyCommand[3] = devFlagName;
        rakalyCommand[4] = "-tile";
        rakalyCommand[5] = "-compose";
        rakalyCommand[6] = "Hardlight";
        rakalyCommand[7] = output;
        
        Processing.fileExcecute(rakalyCommand);
    }
    
    public static void irFlagGlow(String shadow, String devFlagName, String color, String intensity) throws IOException //combined test
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = devFlagName;
        rakalyCommand[3] = "-background";
        rakalyCommand[4] = color;
        rakalyCommand[5] = "-shadow";
        rakalyCommand[6] = "1000x"+intensity+"+60+60";
        rakalyCommand[7] = shadow;
        Processing.fileExcecute(rakalyCommand);
        Output.irFlagCombine(shadow,devFlagName,shadow);
    }

    public static String getColor(String colorName,ArrayList<String[]> colorList) throws IOException
    //get's and converts I:R color to correct format
    {

        int aqq = 0;
        int flag = 0;
        while (aqq < colorList.size() && flag == 0) {
            String color = colorName;
            if (colorList.get(aqq)[0].equals(colorName)) {
                flag = 1; //end loop
                color = colorList.get(aqq)[1];
                color = color.replace("  "," ");
            }
            if (color.split(",")[0].equals("rgb")) {
                color = color.split(",")[1];
                color = Processing.fixWhite(color);
                //Pure white(255,255,255) messes with image scaling, turning the entire emblem white. Changing it to 254,254,254 fixes the issue
                color = "rgb(" + color.replace(" ",",") + ")";
                return color;
            }
            if (color.split(",")[0].equals("hsv")) {
                color = color.split(",")[1];
                color = Processing.deriveRgbFromHsv(color);
                color = Processing.fixWhite(color);
                //Pure white(255,255,255) messes with image scaling, turning the entire emblem white. Changing it to 254,254,254 fixes the issue
                color = "rgb(" + color.replace(" ",",") + ")";
                return color;
            }
            aqq = aqq + 1;
        }
        //colorName = "252 99 225"; //default in case there is no color
        return colorName;

    }

    public static void copyDefaultOutput (String defDir, String outputDir) throws IOException
    {
        ArrayList<String[]> allColors = new ArrayList<String[]>();
        ArrayList<String[]> vanillaColors = new ArrayList<String[]>();
        File fileInfo = new File (defDir);
        String[] fileList = fileInfo.list();

        if (fileList != null) {
            int aqq = 0;
            while (aqq < fileList.length) {
                copyDefaultOutput(defDir+"/"+fileList[aqq],outputDir);
                aqq = aqq + 1;
            }

        } else {
            String fileName = fileInfo.getPath();
            String newFileName = fileName.substring(21,fileName.length());
            newFileName = outputDir + newFileName;
            String folder = fileInfo.getParent();
            folder = outputDir + folder.substring(21,folder.length());
            File folderFile = new File (folder);
            if (!folderFile.exists()) { //if folders don't exist, make them
                folderFile.mkdirs();
            }
            if (defDir.contains ("common/cultures") || defDir.contains ("common/religions") || defDir.contains ("common/dynasties")
            || defDir.contains ("/gfx") || defDir.contains ("/localisation"))  {
                copyRaw(fileName,newFileName);
            }
            else {
                output(fileName,newFileName);
            }
        }
    }

    public static void dynamicSplitTemplateFill(String country, String[] loc, String[] easternEmpire, String[] westernEmpire, String eastTitle,
    String westTitle, String capital, String tagCulture, String outputDirectory, String templateDirectory) throws IOException
    //dynamic east/west empire split
    {

        ArrayList<String> oldFile = new ArrayList<String>();
        oldFile = Importer.importBasicFile(templateDirectory);
        FileOutputStream fileOut= new FileOutputStream(outputDirectory);
        PrintWriter out = new PrintWriter(fileOut);

        String empName = loc[0];
        if (!empName.contains("Empire")) {
            empName = loc[1] + " Empire";
        }
        String adjName = loc[1];

        int aqq = 0;

        while (aqq < oldFile.size()) {
            if (oldFile.get(aqq).contains("e_TAG") || oldFile.get(aqq).contains("tagEmpireName") || oldFile.get(aqq).contains("eastEmpireName")
            || oldFile.get(aqq).contains("westEmpireName") || oldFile.get(aqq).contains("TAG_capital") || oldFile.get(aqq).contains("TAGEmpireAdj")
            || oldFile.get(aqq).contains("TAGCulture")) {
                String imperialTag = oldFile.get(aqq).replace("e_TAG",country);
                imperialTag = imperialTag.replace("tagEmpireName",empName);
                imperialTag = imperialTag.replace("eastEmpireName",easternEmpire[0]);
                imperialTag = imperialTag.replace("westEmpireName",westernEmpire[0]);
                imperialTag = imperialTag.replace(country+"_east","e_"+eastTitle);
                imperialTag = imperialTag.replace(country+"_west","e_"+westTitle);
                imperialTag = imperialTag.replace("TAG_capital",capital);
                imperialTag = imperialTag.replace("TAGEmpireAdj",adjName);
                imperialTag = imperialTag.replace("TAGCulture",tagCulture);
                out.println (imperialTag);
            } else {
                out.println (oldFile.get(aqq));
            }

            aqq = aqq + 1;
        }
        out.flush();
        fileOut.close();
    }

    public static void eastWestTitle(String irTAG, String government, String capital,String rank,
    String date1,String Directory) throws IOException
    {

        String tab = "	";

        String oldDirectory = Directory;
        Directory = Directory+"/history/titles";
        Importer importer = new Importer();

        FileOutputStream fileOut= new FileOutputStream(Directory + "/" + rank+"_" + irTAG + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        out.println (date1+"={");
        out.println (tab+"active = no");
        if (government.equals("imperium") && rank.equals("e")) { //If I:R government is imperial, set government to CK II imperial (roman_imperial_government)
            out.println (tab+"law = crown_authority_2");
            out.println (tab+"law = succ_byzantine_elective");
            out.println (tab+"law = centralization_3");
            out.println (tab+"law = imperial_administration");
            out.println (tab+"law = ze_administration_laws_2");
            out.println (tab+"law = vice_royalty_2");
            out.println (tab+"law = revoke_title_law_1");
            govCreation(irTAG,rank,"i",oldDirectory);
            imperialSuccession(irTAG,rank,oldDirectory);
        }
        out.println ("}");
        out.println ();
        out.flush();
        fileOut.close();
    }

    public static void eastWestFlagGen(String irFlag, String title, String color, String eastColor, String eastTitle,ArrayList<String[]> flagList,
    ArrayList<String[]> colorList,String rank,String capital,ArrayList<String> modFlagGFX,String useRatio,String ck2Dir,String impGameDir,String modDirectory)
    throws IOException
    {

        int genFlag = 0;
        int aqq = 0;
        int flag = 0;

        String irFlagSource = "none";
        boolean usesPNG = false;

        String baseFlagDir = modDirectory+"/gfx/flags/"+rank+"_"+title+".tga";
        String eastFlagDir = modDirectory+"/gfx/flags/"+rank+"_"+eastTitle+".tga";

        while (aqq < flagList.size() && flag == 0) {
            if (flagList.get(aqq)[0].equals(irFlag)) {
                flag = 1;
                String[] flagSource = flagList.get(aqq);
                String[] flagSourceEast = flagSource;
                String irFlagOriginal = flagSourceEast[0];
                flagSourceEast[0] = eastTitle;
                int aq2 = 0;
                String emblems = flagSource[4];
                String eastEmblems = flagSource[4];
                String[] emblemList = emblems.split("~~");
                String eastColorFormatted = "rgb("+eastColor+")";
                String colorFormatted = ("rgb("+color+")");
                int range = 15;
                boolean changedColor = false;
                boolean changedColorR = false;
                ArrayList<String[]> rangeFlag = new ArrayList<String[]>();
                ArrayList<String[]> ratioFlag = new ArrayList<String[]>(); //Used when building flag using color ratio
                ArrayList<String[]> backgroundFlag = new ArrayList<String[]>();
                String rangeC1 = flagSourceEast[2];
                String rangeC2 = flagSourceEast[3];
                String ratioC1 = flagSourceEast[2];
                String ratioC2 = flagSourceEast[3];
                String backgroundColor1F = Output.getColor(flagSourceEast[2],colorList);
                String backgroundColor2F = Output.getColor(flagSourceEast[3],colorList);
                boolean bC1IsWhite = Processing.isWithinColorRatio(backgroundColor1F,"128,128,128",range);
                boolean bC2IsWhite = Processing.isWithinColorRatio(backgroundColor2F,"128,128,128",range);
                if (Processing.isWithinColorRange(backgroundColor1F,("rgb("+color+")"),range)) {
                    rangeC1 = eastColorFormatted;
                    changedColor = true;
                }
                else if (Processing.isWithinColorRatio(backgroundColor1F,("rgb("+color+")"),range) && !bC1IsWhite) {
                    ratioC1 = eastColorFormatted;
                    changedColorR = true;
                }
                if (Processing.isWithinColorRange(backgroundColor2F,("rgb("+color+")"),range)) {
                    rangeC2 = eastColorFormatted;
                    changedColor = true;
                }
                else if (Processing.isWithinColorRatio(backgroundColor2F,("rgb("+color+")"),range) && !bC2IsWhite) {
                    ratioC2 = eastColorFormatted;
                    changedColorR = true;
                }
                String[] rangeFlagString = new String[5];
                rangeFlagString[0] = eastTitle;
                rangeFlagString[1] = flagSourceEast[1];
                rangeFlagString[2] = rangeC1;
                rangeFlagString[3] = rangeC2;
                rangeFlagString[4] = eastEmblems;
                
                String[] ratioFlagString = new String[5];
                ratioFlagString[0] = eastTitle;
                ratioFlagString[1] = flagSourceEast[1];
                ratioFlagString[2] = ratioC1;
                ratioFlagString[3] = ratioC2;
                ratioFlagString[4] = eastEmblems;
                
                String[] backgroundFlagString = new String[5];
                backgroundFlagString[0] = eastTitle;
                backgroundFlagString[1] = flagSourceEast[1];
                backgroundFlagString[2] = flagSourceEast[2];
                backgroundFlagString[3] = flagSourceEast[3];
                backgroundFlagString[4] = eastEmblems;
                
                String rangeEmblems =  rangeFlagString[4];
                String ratioEmblems =  ratioFlagString[4];
                while (aq2 < emblemList.length) {
                    String emblem = emblemList[aq2];
                    if (emblem.split("~_~")[0].contains(".png")) {
                        usesPNG = true;
                        aq2 = aq2 + emblemList.length;
                    }
                    String embColor1 = emblem.split("~_~")[1];
                    String embColor2 = emblem.split("~_~")[2];
                    String embColor1F = Output.getColor(embColor1,colorList);
                    String embColor2F = Output.getColor(embColor2,colorList);
                    boolean c1IsWhite = Processing.isWithinColorRatio(embColor1F,"128,128,128",range);
                    boolean c2IsWhite = Processing.isWithinColorRatio(embColor2F,"128,128,128",range);
                    if (Processing.isWithinColorRange(embColor1F,colorFormatted,range)) {
                        rangeEmblems = rangeEmblems.replace(emblem,emblem.replace(embColor1,eastColorFormatted));
                        changedColor = true;
                    }
                    else if (Processing.isWithinColorRatio(embColor1F,colorFormatted,range) && !c1IsWhite) {
                        ratioEmblems = ratioEmblems.replace(emblem,emblem.replace(embColor1,eastColorFormatted));
                        changedColorR = true;
                    }
                    if (Processing.isWithinColorRange(embColor2F,colorFormatted,range)) {
                        rangeEmblems = rangeEmblems.replace(emblem,emblem.replace(embColor2,eastColorFormatted));
                        changedColor = true;
                    }
                    else if (Processing.isWithinColorRatio(embColor2F,colorFormatted,range) && !c2IsWhite) {
                        ratioEmblems = ratioEmblems.replace(emblem,emblem.replace(embColor2,eastColorFormatted));
                        changedColorR = true;
                    }
                    aq2 = aq2 + 1;
                }
                rangeFlagString[4] = rangeEmblems;
                rangeFlag.add(rangeFlagString);
                rangeFlag.add(rangeFlagString);
                ratioFlagString[4] = ratioEmblems;
                ratioFlag.add(ratioFlagString);
                ratioFlag.add(ratioFlagString);
                if (!changedColor) {
                    if (changedColorR && !usesPNG) { //Try rebuilding flag using color ratio rather then color range
                        genFlag = generateFlag(ck2Dir,impGameDir,rank,ratioFlag,eastTitle,eastTitle,colorList,modFlagGFX,modDirectory);
                    } else {
                        backgroundFlagString[2] = eastColorFormatted;
                        backgroundFlagString[4] = eastEmblems;
                        backgroundFlag.add(backgroundFlagString);
                        backgroundFlag.add(backgroundFlagString);
                        changedColorR = false;
                    }
                }

                if (changedColor) {
                    genFlag = generateFlag(ck2Dir,impGameDir,rank,rangeFlag,eastTitle,eastTitle,colorList,modFlagGFX,modDirectory);
                } else {
                    genFlag = generateFlag(ck2Dir,impGameDir,rank,backgroundFlag,eastTitle,eastTitle,colorList,modFlagGFX,modDirectory);
                }
                flagSource[0] = irFlagOriginal;

            }
            aqq = aqq + 1;
        }

        if (genFlag == 0) {
            Output.copyFlag(ck2Dir,modDirectory,rank,capital,eastTitle);
        }
        if (usesPNG || genFlag == 0) {
            if (eastTitle.contains("east")) {
                Output.irFlagHue(eastFlagDir,"100,100,33.3",eastFlagDir);
            }
        }
    }
    
    public static void splitBloodlineEmbGen(String ck2Dir, String irDirectory, String rank, ArrayList<String[]> flagList, String tag, String flagID,
    ArrayList<String[]> colorList, ArrayList<String> flagGFXList, String modDirectory)
    throws IOException
    {

        int flagCreated = 0; //if flag is successfully created, change to 1
        //output[2] format = hsvOrRgb,r g b
        //output[3] format = hsvOrRgb,r g b
        // output[4] format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)
        int aqq = 1;
        int flag = 0;
        boolean usesPNG = false;
        boolean hasOnlyBorders = true;
        boolean isBrightBackground = false;
        String ck2Tag = rank+"_"+tag;
        String devFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_splitDev.gif";
        String shadowFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_splitDevShadow.tga";
        String flagName = modDirectory+"/gfx/interface/bloodlines/bloodlines_symbol_"+ck2Tag+"_split.tga";
        String lineGFX = "defaultOutput/templates/gfx/interface/SplitLine04.tga";
        
        String glowFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow.tga";
        String glowFlagName2 = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow2.tga";
        String glowFlagName3 = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow3.tga";
        String glowFlagName4 = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow4.tga";
        String flagNameR = modDirectory+"/gfx/interface/bloodlines/bloodlines_symbol_"+ck2Tag+"_restored.tga";
        
        while (aqq < flagList.size() && flag == 0) {
            if (flagList.get(aqq)[0].equals(flagID)) {
                flag = 1; //end loop
                String[] flagSource = flagList.get(aqq);
                if (flagSource[1].equals("pattern_horizontal_split_02.tga")) {
                    flagSource[1] = "pattern_horizontal_split_01.tga";
                } else if (flagSource[1].equals("pattern_horizontal_split_01.tga")) {
                    flagSource[1] = "pattern_horizontal_split_02.tga";
                }
                String pattern = irDirectory + "/game/gfx/coat_of_arms/patterns/" + "pattern_solid.tga";
                String color1 = flagSource[2];
                String color2 = flagSource[3];
                String emblems = flagSource[4];
                
                isBrightBackground = Processing.isBright(getColor(color1,colorList),200);

                int aq3 = 0;
                while (aq3 < flagGFXList.size()) {
                    if (flagGFXList.get(aq3).contains(flagSource[1])) {
                        pattern = flagGFXList.get(aq3);
                        aq3 = flagGFXList.size();
                    }
                    aq3 = aq3 + 1;
                }

                irFlagBackground(pattern,devFlagName,"None","None");

                String[] emblemList = emblems.split("~~");
                int aq2 = 0;
                while (aq2 < emblemList.length) {
                    String[] emblem = emblemList[aq2].split("~_~");
                    String eTexture = irDirectory+"/game/gfx/coat_of_arms/colored_emblems/"+emblem[0];
                    String eColor1 = emblem[1];
                    String eColor2 = emblem[2];
                    String eScale = emblem[3];
                    String ePos = emblem[4];
                    String eRot= emblem[5];
                    String eNameOld = "defaultOutput/flagDev/emblem"+aq2+"Old"+".gif";
                    String eName = "defaultOutput/flagDev/emblem"+aq2+".gif";

                    int aq4 = 0;
                    while (aq4 < flagGFXList.size()) {
                        if (flagGFXList.get(aq4).contains(emblem[0])) {
                            eTexture = flagGFXList.get(aq4);
                            aq4 = flagGFXList.size();
                        }
                        aq4 = aq4 + 1;
                    }
                    
                    if (eTexture.contains(".png")) {
                        usesPNG = true;
                        aq2 = aq2 + emblemList.length;
                    }

                    eColor1 = getColor(eColor1,colorList);
                    if (!eColor2.equals("none")) {
                        eColor2 = getColor(eColor2,colorList);
                    }

                    if (!emblem[0].contains("ce_border_")) { //Don't include borders onto flag GFX
                        irFlagEmblem(eTexture,eNameOld,eColor1,eColor2,eName,eScale,eRot,ePos,devFlagName);
                        hasOnlyBorders = false;
                    }
                    aq2 = aq2+1;
                }
                if (aq2 != 0 && !usesPNG && !hasOnlyBorders) {
                    flagCreated = 1; //Flag has been created
                }
            }

            aqq = aqq + 1;

        }
        
        if (flagCreated != 1) {
            String oldFlagName = modDirectory+"/gfx/flags/"+ck2Tag+".tga";
            File oldFlagFile = new File(oldFlagName);
            boolean oldFlag = true;
            if (!oldFlagFile.exists()) { //TAGs converted to existing titles don't copy that title's flag to the mod files
                oldFlagName = ck2Dir+"/gfx/flags/"+ck2Tag+".tga";
                oldFlag = false;
            }
            String circleMaskName = "defaultOutput/templates/gfx/interface/CircleMask.gif";
            String greyMaskName = "defaultOutput/templates/gfx/interface/greyMask.gif";
            String transparantOverlay256_256 = "defaultOutput/templates/gfx/interface/transparant256_256.gif";
            lineGFX = "defaultOutput/templates/gfx/interface/SplitLine05.tga";
            irFlagScaleExact(oldFlagName,devFlagName,"256","256");
            if (!usesPNG || !oldFlag) {
                irFlagFlip(devFlagName,devFlagName,"y");
            }
            irFlagTexture(devFlagName,greyMaskName,devFlagName);
            irFlagCombineCutout(devFlagName,circleMaskName,transparantOverlay256_256,devFlagName);
        }
        
        irFlagGlow(glowFlagName,devFlagName,"yellow","15");
        irFlagGlow(glowFlagName2,devFlagName,"rgb(254,254,180)","5");
        irFlagGlow(glowFlagName3,devFlagName,"gold","5");
        String glow4 = "black";
        if (isBrightBackground) {
            glow4 = "yellow";
        }
        irFlagGlow(glowFlagName4,devFlagName,glow4,"5");
        irFlagCombine(glowFlagName,glowFlagName2,glowFlagName);
        irFlagCombine(glowFlagName,glowFlagName3,glowFlagName);
        irFlagCombine(glowFlagName,glowFlagName4,glowFlagName);
        
        irFlagScaleExact(glowFlagName,flagNameR,"96","93"); //create final .tga flag, scale to bloodline icon size 96x93
        
        irFlagCombine(devFlagName,lineGFX,devFlagName);
        irFlagShadow(shadowFlagName,devFlagName);
        irFlagScaleExact(shadowFlagName,flagName,"96","93"); //create final .tga flag, scale to bloodline icon size 96x93

    }
    
    public static void eastWestDecisionIcon(String country,String modDirectory) throws IOException
    {
        String iconDevName = "defaultOutput/flagDev/iconDev"+country+".gif";
        String iconTemplateName = "defaultOutput/templates/gfx/interface/decision_icon_e_TAG_split.gif";
        String shadowFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+country+"_splitDevShadow.tga";
        String iconName = modDirectory+"/gfx/interface/decision_icon_"+country+"_split.tga";
        irFlagScaleExact(shadowFlagName,iconDevName,"26","26");
        irFlagCombine(iconTemplateName,iconDevName,iconName);
        
    }
    
    public static void eastWestRestorationIcon(String country,String irFlag,ArrayList<String[]> flagList,String ck2Dir,String modDirectory)
    throws IOException
    {
        String iconDevName = "defaultOutput/flagDev/iconRestorationDev"+country+".gif";
        String iconTemplateName = "defaultOutput/templates/gfx/interface/decision_icon_e_TAG_restoration.gif";
        String devFlagName = modDirectory+"/gfx/flags/"+country+".tga";
        File devFlagFile = new File(devFlagName);
        String iconName = modDirectory+"/gfx/interface/decision_icon_"+country+"_restoration.tga";
        if (!devFlagFile.exists()) { //TAGs converted to existing titles don't copy that title's flag to the mod files
            String sourceFlagName = ck2Dir+"/gfx/flags/"+country+".tga";
            irFlagFlip(sourceFlagName,iconDevName,"y"); //CK2-sourced flags get flipped, unflips flag
            devFlagName = iconDevName;
        } else { //Check to see if flag was converted from I:R. If not, unflip
            int aqq = 0;
            boolean hasFlag = false;
            while (aqq < flagList.size()) {
                if (flagList.get(aqq)[0].equals(irFlag)) {
                    hasFlag = true;
                    aqq = flagList.size() +1;
                }
                aqq = aqq + 1;
            }
            if (!hasFlag) { //CK2-sourced flags get flipped, unflips flag
                irFlagFlip(devFlagName,iconDevName,"y");
            }
        }
        irFlagScaleExact(devFlagName,iconDevName,"24","20");
        irFlagCombine(iconTemplateName,iconDevName,iconName);
        
    }
    
    public static void replaceInFile(String textToReplace, String testToReplaceWith, String Directory) throws IOException // Replace A with B
    {

        String tab = "	";

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(Directory);

        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < oldFile.size()) {
            //out.println (oldFile.get(aqq));
            if (oldFile.get(aqq).contains(textToReplace)) {
                String replacedLine = oldFile.get(aqq).replace(textToReplace,testToReplaceWith);
                replacedLine = replacedLine.replace("#","");
                out.println (replacedLine);
            } else {
                out.println (oldFile.get(aqq));
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();
    }

}
