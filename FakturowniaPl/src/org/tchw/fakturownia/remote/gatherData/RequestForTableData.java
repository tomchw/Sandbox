package org.tchw.fakturownia.remote.gatherData;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tchw.fakturownia.remote.GetRequest.Login.Table;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class RequestForTableData {

    private final File directory;
    private final Table table;
    private final String tableType;

    private int counter;

    public RequestForTableData(File directory, Table table, String tableType) {
        this.directory = directory;
        this.table = table;
        this.tableType = tableType;
    }

    public void execute() {
        FilePathSupport filePathSupport;
        do {
            counter++;
            String fullFilePath = Joiner.on("/").join(directory.getPath(), todayDirectoryName(), tableType, tableType + "." + counter);
            createParentDirs(fullFilePath);
            table.page(counter).saveContentToFile(fullFilePath);
            filePathSupport = new FilePathSupport(fullFilePath);
        } while( filePathSupport.areThereMorePages() );
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
