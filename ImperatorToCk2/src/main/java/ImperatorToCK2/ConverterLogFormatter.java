package ImperatorToCK2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConverterLogFormatter  extends Formatter {
    // this method is called for every log records
    public String format(LogRecord rec) {
        StringBuffer buf = new StringBuffer(1000);

        buf.append(calcDate(rec.getMillis()));

        if (rec.getLevel().equals(Level.SEVERE))
        {
            buf.append("   [ERROR] ");
        }
        else if (rec.getLevel().equals(Level.WARNING))
        {
            buf.append(" [WARNING] ");
        }
        else if (rec.getLevel().equals(Level.INFO))
        {
            buf.append("    [INFO] ");
        }
        else if (rec.getLevel().equals(Level.CONFIG))
        {
            buf.append("   [DEBUG]     ");
        }
        else if (rec.getLevel().equals(Level.FINEST))
        {
            buf.append("[PROGRESS] ");
        }

        buf.append(formatMessage(rec));
        buf.append('\n');
        
        System.out.println(buf.toString());

        return buf.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}
