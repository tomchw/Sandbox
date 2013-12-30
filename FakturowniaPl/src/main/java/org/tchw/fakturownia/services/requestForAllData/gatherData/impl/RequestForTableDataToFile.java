package org.tchw.fakturownia.services.requestForAllData.gatherData.impl;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.requestForAllData.GetRequest.Login.Table;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.gatherData.RequestForTableData;
import org.tchw.fakturownia.services.requestForAllData.impl.WriteToFileContentHandling;
import org.tchw.generic.stream.FileHelper;

import com.google.common.io.Files;

public class RequestForTableDataToFile implements RequestForTableData, InitializingBean {

    private final Logger log = Logger.getLogger(getClass());

    private final String repositoryPath;

    private final RequestExecution requestExecution;

    @Value("${gather.data.max.pages}")
    private Integer maxPages;

    public RequestForTableDataToFile(RepositoryDirectory repositoryDirectory, RequestExecution requestExecution) {
        this.requestExecution = requestExecution;
        this.repositoryPath = repositoryDirectory.repositoryDirectory().getPath();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("This number of retrieving pages for given table type indicates error: " + maxPages);
    }

    @Override
    public void gatherTableData(Table table, String tableType) {
        int counter = 0;
        log.info("Requesting for " + tableType);
        FilePathSupport filePathSupport;
        do {
            throwExceptionIfToMuchFiles(tableType, counter);
            counter++;
            String fullFilePath = FileHelper.joiner().join(repositoryPath, tableType, tableType + "." + counter);
            createParentDirs(fullFilePath);
            table.page(counter).handleContent(requestExecution, new WriteToFileContentHandling(new File(fullFilePath)));
            filePathSupport = new FilePathSupport(fullFilePath);
        } while( filePathSupport.areThereMorePages() );
    }

    private void throwExceptionIfToMuchFiles(String tableType, int counter) {
        if( counter >= maxPages ) {
            throw new RuntimeException("It is already " + counter + " pages gathered for " + tableType + ". Stopped gathering. Are there such many pages to gather or empty JSON file will never come?");
        }
    }

    private void createParentDirs(String fullFilePath) {
        try {
            Files.createParentDirs(new File(fullFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
