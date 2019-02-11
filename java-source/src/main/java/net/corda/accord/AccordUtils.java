package net.corda.accord;

import net.corda.core.crypto.SecureHash;
import net.corda.core.node.ServiceHub;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AccordUtils {


    /**
     * This function returns an input stream after compressing the file that is read from disk.
    */
    public static InputStream getCompressed(InputStream is )
            throws IOException
    {
        byte data[] = new byte[2048];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream( bos );
        BufferedInputStream entryStream = new BufferedInputStream( is, 2048);
        ZipEntry entry = new ZipEntry( "" );
        zos.putNextEntry( entry );
        int count;
        while ( ( count = entryStream.read( data, 0, 2048) ) != -1 )
        {
            zos.write( data, 0, count );
        }
        entryStream.close();
        zos.closeEntry();
        zos.close();

        return new ByteArrayInputStream( bos.toByteArray() );
    }

    /**
     * This function parses a legal document using a Cicero template and returns an input stream with the output from the terminal.
     * The script 'cicero-parse.sh writes the output of Cicero-parse to a temporary file, suppresses standard system-out messaging and then
     * logs out the JSON (which is captured in the input stream.
     */

//    // Unit Test Config
//    public static InputStream getStateFromContract() throws IOException {
//        String[] command = {"./resources/cicero-parse.sh", "../../../node_modules/promissory-note", "../../.././contract.txt"};
//        ProcessBuilder ciceroParse = new ProcessBuilder(command);
//        ciceroParse.directory(new File("./src/main"));
//        return ciceroParse.start().getInputStream();
//    }

    // Production Config
    public static InputStream getStateFromContract() throws IOException {
        String[] command = {"./resources/cicero-parse.sh", "../../../node_modules/promissory-note", "../../.././contract.txt"};
        ProcessBuilder ciceroParse = new ProcessBuilder(command);
        ciceroParse.directory(new File("../../.././src/main"));
        return ciceroParse.start().getInputStream();
    }
}
