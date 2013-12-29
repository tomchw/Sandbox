package org.tchw.fakturownia.model.file.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.generic.stream.FileHelper;
import org.tchw.specific.werbum.Werbum;

public class RepositoryDirectoryImpl implements RepositoryDirectory {

    @Override
    public File repositoryDirectory() {
        return new File( FileHelper.joiner().join( Werbum.directory, todayDirectoryName() ));
    }

    private String todayDirectoryName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
