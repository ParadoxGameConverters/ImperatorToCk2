package ImperatorToCK2;  
   
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;
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

    public static String cultureOutput(String irCulture) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String ck2CultureInfo;   // Owner Culture Religeon PopTotal Buildings

        Importer importer = new Importer();

        ck2CultureInfo = importer.importCultList("cultureConversion.txt",irCulture)[1];

        return ck2CultureInfo;
    }

    public static String religionOutput(String irRel) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String ck2CultureInfo;   // Owner Culture Religeon PopTotal Buildings

        Importer importer = new Importer();

        ck2CultureInfo = importer.importCultList("religionConversion.txt",irRel)[1];

        return ck2CultureInfo;
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
        out.println (tab+"color={ "+irColor+" }");
        out.println (tab+"color2={ "+irColor+" }");

        if (capital != "none") { //governorships don't have set capitals

            capital = Importer.importConvList("provinceConversion.txt",Integer.parseInt(capital))[1];

            out.println (tab+"capital = "+capital);

        }
        if ( government.equals("yes") ) {
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

    public static String titleCreation(String irTAG, String irKING, String irCOLOR, String government, String capital,String rank,String liege,String date1,
    String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String irKING100 = "9"+irKING;

        String tab = "	";

        titleCreationCommon(irTAG,irCOLOR,government,capital,rank,Directory);
        String oldDirectory = Directory;
        Directory = Directory + VM + "history" + VM + "titles";
        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        Importer importer = new Importer();

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + rank+"_" + irTAG + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        //String date1 = "100.1.1";
        String date2 = "1066.9.15";

        String overlordRank = "k";
        if (rank.equals("k")){
            overlordRank = "e";
        }

        out.println (date1+"={");
        if (liege != "no_liege") { //If country is a subject

            out.println (tab+"liege="+overlordRank+"_"+liege);
            out.println (tab+"de_jure_liege="+overlordRank+"_"+liege);
        }
        out.println("\tholder="+irKING100);
        if (government.equals("imperium") && rank.equals("e")) { //If I:R government is imperial, set government to CK II imperial (roman_imperial_government)
            out.println (tab+"law = crown_authority_2");
            out.println (tab+"law = succ_byzantine_elective");
            out.println (tab+"law = centralization_3");
            out.println (tab+"law = imperial_administration");
            out.println (tab+"law = ze_administration_laws_2");
            out.println (tab+"law = vice_royalty_2");
            out.println (tab+"law = revoke_title_law_1");
            imperialCreation(irTAG,rank,oldDirectory);
            imperialSuccession(irTAG,rank,oldDirectory);
        }
        out.println ("}");
        out.println ();

        out.println (date2+"={");
        if (liege != "no_liege") {
            out.println (tab+"liege="+overlordRank+"_"+liege);
            out.println (tab+"de_jure_liege="+overlordRank+"_"+liege);
        }
        out.println ("\tholder="+irKING);
        out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static String provinceCreation(String ckProv, String ckCult, String ckRel, String Directory, String landType, 
    String name, String gov, String pops, String[] bList, String saveMonuments, int id) throws IOException
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
        boolean convRepublic = true; //To be done later

        if (gov.equals ("tribal_federation") || gov.equals ("tribal_kingdom") || gov.equals ("tribal_chiefdom")) {
            holding1 = "tribal";
            holding2 = "tribal";
            holding3 = "tribal";
        }
        else if (gov.split("_").equals ("republicQ") || convRepublic == true) { //Currently unused, need to figure out how to implement republics
            holding2 = "city";   
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

    public static String ctitleCreation(String name, String irKING, String Directory, int id,String date1) throws IOException
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

        //String date1 = "100.1.1";
        String date2 = "1066.9.15";

        out.println (date1+"={");
        out.println ("    holder="+irKING100);
        out.println ("}");
        out.println ();

        out.println (date2+"={");
        out.println ("    holder="+irKING);
        out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static ArrayList<String> characterCreation(String irKING, String cult, String rel, String age, String name, String dynasty,
    String sex, String traits, String martial, String zeal, String charisma, String finesse, String spouse, String children,String government,String father,
    String mother,ArrayList<String> convertedList,ArrayList<String[]> charList,String date1,String Directory) throws IOException
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

            characterCreation( spouse1066,  cultureOutput(spouseInfo[1]),  religionOutput(spouseInfo[2]),  spouseInfo[3],  spouseInfo[0],  spouseInfo[7],
                spouseInfo[4],  spouseInfo[8],  martial,  zeal,  charisma,  finesse,  "0",  "0", "no","q",  "q",convertedList,charList,date1, Directory);
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
                //oldlogPrint ("Child " + aq4 + " out of " + childCount);
                child1066 = Integer.toString( 1000000 + Integer.parseInt(children.split(" ")[aq4]) );

                characterCreation( child1066,  cultureOutput(childInfo[1]),  religionOutput(childInfo[2]),  childInfo[3],  childInfo[0],  childInfo[7],
                    childInfo[4],  childInfo[8],  martial,  zeal,  charisma,  finesse,  childInfo[14],  childInfo[15], isPurple,irKING,spouse1066,
                    convertedList,charList,date1,Directory);

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
            dynasty = Integer.toString(Integer.parseInt(dynasty) + 700000000);
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

        //String date1 = "100.1.1";
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

        if (name.split("_")[0].equals ("minor")) {
            name = backupName;
        }

        if (backupName.equals("debug")) { //gives all IR character dynasties + 700000000 to prevent conflict, generated ones (debug) use + 6000000
        } else {

            id = Integer.toString(Integer.parseInt(id) + 700000000);
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

        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "b_" + name + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        out.println ("c_"+name+"={");
        out.println (tab+"b_"+name+"={");
        out.println (tab+"}");
        out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
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

    public static void dejureTitleCreation(ArrayList<String[]> impTagInfo, int empireRank, int[] ck2LandTot, ArrayList<String> dejureDuchies,
    ArrayList<String> impSubjectInfo, String Directory) throws IOException
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

                if (ck2LandTot[tagID] >= empireRank || impTagInfo.get(tagID)[17].equals("imperium")) {
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
                    int subjectOrNot = Processing.checkSubjectList(tagID,impSubjectInfo);
                    int flag2 = 0;

                    if (subjectOrNot != 9999) { //if tag is not free
                        String[] subjectInfo = impSubjectInfo.get(subjectOrNot).split(",");
                        tagID = Integer.parseInt(subjectInfo[0]);

                        if (ck2LandTot[tagID] >= empireRank) { //if overlord is e tier, give subject dejure land with overlord as liege
                            out.println (tagID+" = {");
                            tagID = Integer.parseInt(tag);
                            flag2 = 1;
                        }
                    } else { //if tag is independent k tier, assign appropriate dejure culture empire liege
                        String[] cultureTitles = Processing.defaultDejureConversion(cultureOutput(impTagInfo.get(tagID)[6]));
                        cultureTitles = Processing.calculateUsedTitles(cultureTitles,impTagInfo,empireRank,ck2LandTot); //determines if tag exists in I:R
                        out.println (cultureTitles[1]+" = {");
                    }

                    out.println (tab+rank+"_"+impTagInfo.get(tagID)[0]+" = {");
                    out.println (tab+tab+duchy+" = {");
                    out.println (tab+tab+"}");
                    out.println (tab+"}");

                    if (subjectOrNot == 9999 || flag2 == 1) {
                        out.println ("}");
                    }
                }
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

    }

    public static String imperialCreation(String title, String rank, String Directory) throws IOException //needed to allow TAGs imperial government
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        Directory = Directory + VM + "common" + VM + "governments" + VM + "imperial_governments.txt";

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

        String VM = "\\";
        VM = VM.substring(0);
        if (!tag.contains("dynamic") && !tag.contains("__")) { //if the tag is dynamically generated or is governorship, already uses CK II province ID
            prov = Importer.importConvList("provinceConversion.txt",Integer.parseInt(prov))[1];
        }

        prov = Processing.importNames("a",Integer.parseInt(prov),ck2Dir)[0];
        prov = Processing.formatProvName(prov);
        try {
            Output.copyRaw(ck2Dir+VM+"gfx"+VM+"flags"+VM+"c_"+prov+".tga",modDirectory+VM+"gfx"+VM+"flags"+VM+rank+"_"+tag+".tga");
        }catch (java.io.FileNotFoundException exception) { //if flag cannot be found, will use default one
            Output.copyRaw("defaultOutput"+VM+"gfx"+VM+"flags"+VM+"c_default.tga",modDirectory+VM+"gfx"+VM+"flags"+VM+rank+"_"+tag+".tga");
        }

    }
}
