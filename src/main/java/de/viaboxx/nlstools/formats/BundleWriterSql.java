package de.viaboxx.nlstools.formats;

import de.viaboxx.nlstools.model.MBBundle;
import de.viaboxx.nlstools.model.MBEntry;
import de.viaboxx.nlstools.model.MBText;
import de.viaboxx.nlstools.util.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

/**
 * Description: <br/>
 * User: roman.stumm <br/>
 * Date: 15.06.2007 <br/>
 * Time: 12:20:23 <br/>
 * License: Apache 2.0
 */
public class BundleWriterSql extends BundleWriter {
    public BundleWriterSql(Task task, String configFile, MBBundle currentBundle, String outputPath, FileType fileType,
                           Set<String> allowedLocales) {
        super(task, configFile, currentBundle, outputPath, fileType, allowedLocales);
    }

    private File getSQLFile() {
        return currentBundle.getSqldomain() == null ? null :
            new File(getOutputPath(), currentBundle.getSqldomain() + suffix());
    }

    protected void writeOutputFiles() throws IOException {
        File file = getSQLFile();
        if (file == null) return;
        String domain = currentBundle.getSqldomain();
        Writer fw = FileUtils.openFileWriterUTF8(file);
        task.log("writing statements for SQLDomain " + domain + " to: " + file,
            Project.MSG_INFO);
        try {
            fw.write(
                "DELETE FROM NLSTEXT t WHERE EXISTS (SELECT 1 FROM NLSBUNDLE b WHERE DOMAIN='" +
                    domain + "' AND t.BUNDLEID=b.ID);\n");
            fw.write("DELETE FROM NLSBUNDLE WHERE DOMAIN='" + domain + "';\n");
            fw.write("INSERT INTO NLSBUNDLE (ID, DOMAIN) SELECT SEQ_NLSBundle.NEXTVAL,'" +
                domain + "' FROM DUAL;\n");

            for (MBEntry theEntry : getCurrentBundle().getEntries()) {
                String name = theEntry.getKey();
                Iterator<MBText> texts = theEntry.getTexts().iterator();
                String theKey = name.replace('.', '_');
                while (texts.hasNext()) {
                    MBText theText = texts.next();
                    String lang = theText.getLocale();
                    fw.write(
                        "INSERT INTO NLSTEXT (KEY, TRANSLATED, LOCALE, BundleID) SELECT ");
                    fw.write("'" + theKey + "','" + theText.getValue() + "', '" + lang +
                        "', b.ID  FROM NLSBUNDLE b WHERE b.DOMAIN = '" + domain +
                        "';\n");
                }
            }
            fw.write("COMMIT;\n\n");
        } finally {
            fw.close();
        }
    }

    protected boolean needsNewFiles() throws FileNotFoundException {
        File outfile = getSQLFile();
        if (outfile == null) return false;
        if (!outfile.exists()) {
            return true;
        }
        File infile = new File(configFile);
        if (!infile.exists()) {
            throw new FileNotFoundException(infile + " not found");
        }
        return (infile.lastModified() > outfile.lastModified());
    }

    protected String suffix() {
        return ".sql";
    }
}
