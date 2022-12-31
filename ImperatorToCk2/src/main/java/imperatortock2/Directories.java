package imperatortock2;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;

/**
 * Creates the basic directories for the CK II mod
 */
public class Directories {
    private int x;

    public static int modFolders(String Dir, String modName) throws IOException {
        //Necessary to create all of the folders required for the mod
        File output = new File(Dir);
        output.mkdir();

        int aqv = 0;
        boolean endOrNot2 = true;
        String VM = "\\";
        VM = VM.substring(0);
        String mainModFolder = Dir + VM + modName;
        File f = new File(mainModFolder);
        f.mkdir();

        File f2 = new File(mainModFolder + VM + "common");
        f2.mkdir();

        File f3 = new File(mainModFolder + VM + "events");
        f3.mkdir();

        File f4 = new File(mainModFolder + VM + "gfx");
        f4.mkdir();

        File f5 = new File(mainModFolder + VM + "history");
        f5.mkdir();

        File f6 = new File(mainModFolder + VM + "localisation");
        f6.mkdir();
        // Sub folders

        File f7 = new File(mainModFolder + VM + "history" + VM + "characters");
        f7.mkdir();

        File f8 = new File(mainModFolder + VM + "history" + VM + "provinces");
        f8.mkdir();

        File f9 = new File(mainModFolder + VM + "history" + VM + "technology");
        f9.mkdir();

        File f10 = new File(mainModFolder + VM + "history" + VM + "titles");
        f10.mkdir();

        File f11 = new File(mainModFolder + VM + "common" + VM + "governments");
        f11.mkdir();

        File f12 = new File(mainModFolder + VM + "common" + VM + "cultures");
        f12.mkdir();

        File f13 = new File(mainModFolder + VM + "common" + VM + "dynasties");
        f13.mkdir();

        File f14 = new File(mainModFolder + VM + "common" + VM + "landed_titles");
        f14.mkdir();

        File f15 = new File(mainModFolder + VM + "common" + VM + "religions");
        f15.mkdir();

        File f16 = new File(mainModFolder + VM + "common" + VM + "bookmarks");
        f16.mkdir();

        File f17 = new File(mainModFolder + VM + "eu4_converter");
        f17.mkdir();

        File f18 = new File(mainModFolder + VM + "gfx" + VM + "interface");
        f18.mkdir();

        File f23 = new File(mainModFolder + VM + "common" + VM + "bloodlines");
        f23.mkdir();

        File f24 = new File(mainModFolder + VM + "common" + VM + "disease");
        f24.mkdir();

        File f25 = new File(mainModFolder + VM + "common" + VM + "government_flavor");
        f25.mkdir();

        File f26 = new File(mainModFolder + VM + "common" + VM + "laws");
        f26.mkdir();

        File f27 = new File(mainModFolder + VM + "gfx" + VM + "flags");
        f27.mkdir();

        File f28 = new File(mainModFolder + VM + "decisions");
        f28.mkdir();

        File f29 = new File(mainModFolder + VM + "map");
        f29.mkdir();

        File f30 = new File(mainModFolder + "/common/event_modifiers");
        f30.mkdir();

        File f31 = new File(mainModFolder + "/interface");
        f31.mkdir();

        File f32 = new File(mainModFolder + "/gfx/event_pictures");
        f32.mkdir();

        File f33 = new File(mainModFolder + "/gfx/interface/bloodlines");
        f33.mkdir();

        File f34 = new File(mainModFolder + "/common/cb_types");
        f34.mkdir();

        return aqv;
    }

    public static String descriptors(String Dir, String modName, String Dir2) throws IOException {
        //Each mod requires 2 .mod "descriptor" files so the game launcher can
        //read the mod files as a mod
        String VM = "\\";
        char VMq = '"';
        String VN = "//";
        char VN2 = '/';
        VN = VN.substring(0);
        VM = VM.substring(0);
        int aqq = 1;
        String mainModFolder = Dir + VM + modName;
        String dotModFolder = Dir2 + VN + modName;

        FileOutputStream fileOut = new FileOutputStream(mainModFolder + ".mod");
        PrintWriter out = new PrintWriter(fileOut);

        out.println("name=" + VMq + modName + VMq);

        out.println("path=" + VMq + "mod" + VN2 + modName + VMq);

        out.println("user_dir=" + VMq + modName + VMq);

        out.flush();

        fileOut.close();
        return "q";
    }

}
