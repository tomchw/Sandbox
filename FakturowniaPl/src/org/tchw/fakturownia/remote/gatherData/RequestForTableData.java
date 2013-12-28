package org.tchw.fakturownia.remote.gatherData;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.remote.GetRequest.Login.Table;
import org.tchw.fakturownia.remote.impl.WriteToFileContentHandling;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class RequestForTableData {

    private final Logger log = Logger.getLogger(getClass());

    private final File directory;
    private final Table table;
    private final String tableType;

    private int counter;

    public RequestForTableData(File directory, Table table, String tableType) {
        this.directory = directory;
        this.table = table;
        this.tableType = tableType;
    }

    public File execute() {
        log.info("Requesting for " + tableType);
        FilePathSupport filePathSupport;
        File todayDirectoryFile = new File(Joiner.on("/").join(directory.getPath(), todayDirectoryName()));
        do {
            counter++;
            String fullFilePath = Joiner.on("/").join(todayDirectoryFile, tableType, tableType + "." + counter);
            createParentDirs(fullFilePath);
            table.page(counter).handleContent(new WriteToFileContentHandling(new File(fullFilePath)));
            filePathSupport = new FilePathSupport(fullFilePath);
        } while( filePathSupport.areThereMorePages() );
        return todayDirectoryFile;
    }

    private void createParentDirs(String fullFilePath) {
        try {
            Files.createParentDirs(new File(fullFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String todayDirectoryName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
