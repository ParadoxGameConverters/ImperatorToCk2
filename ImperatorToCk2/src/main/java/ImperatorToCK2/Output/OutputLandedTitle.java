package ImperatorToCK2.Output;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import ImperatorToCK2.CK2.LandedTitle;

public class OutputLandedTitle {
    public static void outputLandedTitle(LandedTitle title, String Directory) throws IOException {
        Directory = Directory + "\\common\\landed_titles";
        FileOutputStream fileOut = new FileOutputStream(Directory + '\\' + title.getName() + "_LandedTitle.txt");
        PrintWriter out = new PrintWriter(fileOut);

        out.println(title.getName() + " = {");

        if (title.getColor().isPresent()) {
            String color = title.getColor().get();
            out.println("\tcolor={ " + color + " }");
            out.println("\tcolor2={ " + color + " }");
        }

        if (title.getCapital().isPresent()) {
            out.println("\tcapital = " + title.getCapital().get());
        }

        switch (title.getGovernment()) {
            case REPUBLIC:
                out.println("\tis_republic = yes");
                break;
            case EMPIRE:
                out.println("\tpurple_born_heirs = yes");
                out.println("\thas_top_de_jure_capital = yes");
                break;
            default:
                // Do nothing for palaces and monarchies
        }

        out.println("}");

        out.flush();
        fileOut.close();
    }
}
