package org.tchw.fakturownia.services;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.remote.GetRequest.Login;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.fakturownia.remote.impl.WriteToFileContentHandling;
import org.tchw.generic.stream.json.Json;
import org.tchw.generic.stream.json.JsonArray;
import org.tchw.generic.stream.json.JsonObject;
import org.tchw.generic.stream.stream.Stream;
import org.tchw.specific.werbum.Werbum;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class RequestForAllData {

    private final Logger log = Logger.getLogger(getClass());

    private final Login login;

    public RequestForAllData(Login login) {
        this.login = login;
    }

    public void execute() {
        File directory = Werbum.directory;
        log.info("Requesting for all data. Files will be saved to " + directory.getPath());
        requestForClients(directory);
        requestForProducts(directory);
        File todayDirectoryPath = requestForInvoices(directory);
        requestForFullInvoices(todayDirectoryPath);
        log.info("End of requesting for all data");
    }

    private File requestForInvoices(File directory) {
        return new RequestForTableData(directory, login.invoices(), "tempInvoices").execute();
    }

    private void requestForProducts(File directory) {
        new RequestForTableData(directory, login.products(), "products").execute();
    }

    private void requestForClients(File directory) {
        new RequestForTableData(directory, login.clients(), "clients").execute();
    }

    private void requestForFullInvoices(File todayDirectoryPath) {
        log.info("Request for full invoices");
        File[] listFiles = tempInvoicesDir(todayDirectoryPath).listFiles();
        for (File file : listFiles) {
            requestForEachTempInvoiceFile(todayDirectoryPath, file);
        }
    }

    private void requestForEachTempInvoiceFile(File todayDirectoryPath, File file) {
        JsonArray jsonArray = Stream.from(file).passTo(Json.takeFromReader()).asJsonArray();
        Iterator<JsonObject> iterator = jsonArray.getObjects().iterator();
        while(iterator.hasNext()) {
            String id = iterator.next().getString("id");
            requestInvoiceAndSaveToFile(todayDirectoryPath, id);
        }
    }

    private void requestInvoiceAndSaveToFile(File todayDirectoryPath, String id) {
        File targetFile = new File(Joiner.on("/").join(todayDirectoryPath.getPath(), "invoices", "invoice." + id ));
        createParentDirs(targetFile);
        login.invoice(id).page(1).handleContent(new WriteToFileContentHandling(targetFile));
    }

    private File tempInvoicesDir(File todayDirectoryPath) {
        return new File(Joiner.on("/").join(todayDirectoryPath, "tempInvoices"));
    }

    private void createParentDirs(File targetFile) {
        try {
            Files.createParentDirs(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
