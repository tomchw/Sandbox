package org.tchw.fakturownia.services;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.tchw.fakturownia.model.Repository;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.calculateClientsProfits.AllClientsProfitCalculator;
import org.tchw.fakturownia.services.calculateClientsProfits.ClientProfitCalculator;
import org.tchw.fakturownia.services.calculateClientsProfits.ClientsProfitsToExcelCsv;
import org.tchw.generic.stream.FileHelper;

public class CalculateClientsProfits {

    private final Logger log = Logger.getLogger(getClass());
    private final Repository repository;
    private final RepositoryDirectory repositoryDirectory;

    public CalculateClientsProfits(Repository repository, RepositoryDirectory repositoryDirectory) {
        this.repository = repository;
        this.repositoryDirectory = repositoryDirectory;
    }

    public void calculateClientsProfits() {
        String repositoryPath = repositoryDirectory.repositoryDirectory().getPath();
        log.info("Calculating clients profits takig data from " + repositoryPath);

        AllClientsProfitCalculator allClientsProfitCalculator = new AllClientsProfitCalculator(repository, new ClientProfitCalculator(repository));

        BufferedWriter writer = bufferedWriter( FileHelper.joiner().join( repositoryDirectory.repositoryDirectory(), "clientsProfits.csv") );
        CsvListWriter csvListWriter = new CsvListWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
        try {
            allClientsProfitCalculator.calculate(new ClientsProfitsToExcelCsv(csvListWriter));
        } finally {
            closeQuietly(csvListWriter);
            closeQuietly(writer);
        }
        log.info("End of calculating clients profits");
    }

    private BufferedWriter bufferedWriter(String fileName) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(fileName), Charset.forName("windows-1250")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    private void closeQuietly(Closeable writer) {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
