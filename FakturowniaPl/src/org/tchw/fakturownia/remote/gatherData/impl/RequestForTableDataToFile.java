package org.tchw.fakturownia.remote.gatherData.impl;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.data.model.file.RepositoryDirectory;
import org.tchw.fakturownia.remote.GetRequest.Login.Table;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.fakturownia.remote.impl.WriteToFileContentHandling;
import org.tchw.generic.stream.FileHelper;

import com.google.common.io.Files;

public class RequestForTableDataToFile implements RequestForTableData {

    private final Logger log = Logger.getLogger(getClass());

    private final String repositoryPath;

    public RequestForTableDataToFile(RepositoryDirectory repositoryDirectory) {
        this.repositoryPath = repositoryDirectory.repositoryDirectory().getPath();
    }

    public void gatherTableData(Table table, String tableType) {
        int counter = 0;
        log.info("Requesting for " + tableType);
        FilePathSupport filePathSupport;
        do {
            counter++;
            String fullFilePath = FileHelper.joiner().join(repositoryPath, tableType, tableType + "." + counter);
            createParentDirs(fullFilePath);
            table.page(counter).handleContent(new WriteToFileContentHandling(new File(fullFilePath)));
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



}
