package de.viaboxx.nlstools.formats;

import de.viaboxx.nlstools.model.MBBundle;
import de.viaboxx.nlstools.model.MBEntry;
import de.viaboxx.nlstools.model.MBText;
import de.viaboxx.nlstools.util.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

/**
 * Description: <br/>
 * User: roman.stumm <br/>
 * Date: 12.10.2010 <br/>
 * Time: 12:14:13 <br/>
 * License: Apache 2.0
 */
public class BundleWriterFlexClass extends BundleWriter {
    private String exampleLocale;

    public BundleWriterFlexClass(Task task, String configFile, MBBundle currentBundle, String outputPath,
                                 FileType fileType, Set<String> allowedLocales) {
        super(task, configFile, currentBundle, outputPath, fileType, allowedLocales);
    }

    public String getExampleLocale() {
        return exampleLocale;
    }

    public void setExampleLocale(String exampleLocale) {
        this.exampleLocale = exampleLocale;
    }

    protected String suffix() {
        return ".as";
    }

    /**
     * generate the current bundle's interface
     *
     * @throws Exception
     */
    public void writeOutputFiles() throws Exception {
        // now write the flex class
        if(currentBundle.getInterfaceName() == null) return;
        String iffile = getInterfaceFileName();
        task.log("writing flex class to: " + iffile, Project.MSG_INFO);
        mkdirs(iffile);
        Writer out = FileUtils.openFileWriterUTF8(new File(iffile));
        PrintWriter pw = new PrintWriter(out);
        try {
            writeStaticIntro(pw);
            if (FileType.FLEX_SMALL == fileType || FileType.JAVA_ENUM_KEYS == fileType) {
                pw.println(
                        "// keys not contained (small interface). see .xml source file for possible keys");
            } else if (FileType.FLEX_FULL == fileType) {
                writeConstants(pw, getCurrentBundle());
            }
            writeStaticOutro(pw);
        } finally {
            pw.close();
        }
    }

    String getIPackage() {
        String inf = currentBundle.getInterfaceName();
        int pidx = inf.lastIndexOf('.');
        if (pidx < 0) {
            return "";
        }
        return inf.substring(0, pidx);
    }

    String getIClass() {
        String inf = currentBundle.getInterfaceName();
        int pidx = inf.lastIndexOf('.');
        if (pidx < 0) {
            return inf;
        }
        return inf.substring(pidx + 1);
    }

    private String getInterfaceFileName() {
        StringBuilder fileName = new StringBuilder(getInterfacePathName());
        fileName.append("/");
        fileName.append(getIClass());
        fileName.append(suffix());
        return fileName.toString();
    }

    private String getInterfacePathName() {
        String fileName = getOutputPath();
        char lastChar = fileName.charAt(fileName.length() - 1);
        if (lastChar != '\\' && lastChar != '/') {
            fileName += "/";
        }
        fileName += getIPackage().replace('.', '/');
        return fileName;
    }

    /**
     * Write the static beginning of the interface file.
     *
     * @param pw writer to write to
     */
    void writeStaticIntro(PrintWriter pw) {
        String str = getIPackage();
        if (str != null && str.length() > 0) {
            pw.print("package ");
            pw.print(str);
            pw.println("{");
        }
        pw.println();
        writeDoNotAlter(pw);
        pw.print("public class ");
        pw.print(getIClass());
        pw.println(" {");
        pw.print("  public static const _BUNDLE_NAME:String = \"");
        pw.print(currentBundle.getBaseName());
        pw.println("\";");
        pw.println();
    }

    void writeDoNotAlter(PrintWriter pw) {
        pw.println("/**");
        pw.print(" * contains keys of resource bundle ");
        pw.print(currentBundle.getBaseName());
        pw.println('.');
        pw.println(" * THIS FILE HAS BEEN GENERATED AUTOMATICALLY - DO NOT ALTER!");
        pw.println(" */");
    }

    /**
     * Write the staic end of the interface file.
     *
     * @param pw writer to write to
     */
    private void writeStaticOutro(PrintWriter pw) {
        pw.println("}");
        writeDoNotAlter(pw);
        pw.println("}");
    }

    /**
     * Write the constants to the interface file.
     *
     * @param pw      writer to write to
     * @param aBundle to read from
     */
    void writeConstants(PrintWriter pw, MBBundle aBundle) {
        for (MBEntry mbEntry : aBundle.getEntries()) {
            String keyName = mbEntry.getKey();
            Iterator<MBText> texts = mbEntry.getTexts().iterator();
            pw.print("  /** ");
            while (texts.hasNext()) {
                MBText theText = texts.next();
                String lang = theText.getLocale();
                pw.print("{");
                pw.print(lang);
                pw.print("} ");
            }
            MBText xmpl = mbEntry.findExampleText(getExampleLocale());
            if (xmpl != null) {
                pw.print(" | ");
                pw.print(xmpl.getLocale());
                pw.print(" = ");
                pw.print(StringEscapeUtils.escapeXml(xmpl.getValue()));
            }
            pw.println(" */");
            pw.print("  public static const ");
            String theKey = keyName.replace('.', '_');
            pw.print(theKey);
            pw.print(":String = \"");
            pw.print(keyName);
            pw.println("\";");
        }
    }

    /**
     * true when generation is neccessary, false when up-to-date
     *
     * @throws java.io.FileNotFoundException
     */
    @Override
    protected boolean needsNewFiles() throws FileNotFoundException {
        if(currentBundle.getInterfaceName() == null) return false;
        File outfile = new File(getInterfaceFileName());
        if (!outfile.exists()) {
            return true;
        }
        File infile = new File(configFile);
        if (!infile.exists()) {
            throw new FileNotFoundException(infile + " not found");
        }
        return (infile.lastModified() > outfile.lastModified());
    }

}
