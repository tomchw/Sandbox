package org.tchw.fakturownia.model.file.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.generic.stream.FileHelper;

public class RepositoryDirectoryImpl implements RepositoryDirectory, InitializingBean {

    private final Logger log = Logger.getLogger(getClass());

    @Value("${local.repository.directory}")
    private String localDirectory;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Local repository directory: " + localDirectory);
    }

    @Override
    public File repositoryDirectory() {
        return new File( FileHelper.joiner().join( localDirectory, todayDirectoryName() ));
    }

    private String todayDirectoryName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
