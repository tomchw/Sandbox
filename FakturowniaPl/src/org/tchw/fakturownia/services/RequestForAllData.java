package org.tchw.fakturownia.services;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.data.model.file.RepositoryDirectory;
import org.tchw.fakturownia.remote.GetRequest.Login;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.fakturownia.remote.impl.WriteToFileContentHandling;
import org.tchw.generic.stream.FileHelper;
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

    private final RepositoryDirectory repositoryDirectory;

    private final RequestForTableData requestForTableData;

    public RequestForAllData(Login login, RepositoryDirectory repositoryDirectory, RequestForTableData requestForTableData) {
        this.login = login;
        this.repositoryDirectory = repositoryDirectory;
        this.requestForTableData = requestForTableData;
    }

    public void execute() {
        File directory = Werbum.directory;
        log.info("Requesting for all data. Files will be saved to " + directory.getPath());
        requestForClients();
        requestForProducts();
        requestForInvoices();
        requestForFullInvoices();
        log.info("End of requesting for all data");
    }

    private void requestForInvoices() {
        requestForTableData.gatherTableData(login.invoices(), "tempInvoices");
    }

    private void requestForProducts() {
        requestForTableData.gatherTableData(login.products(), "products");
    }

    private void requestForClients() {
        requestForTableData.gatherTableData(login.clients(), "clients");
    }

    private void requestForFullInvoices() {
        log.info("Request for full invoices");
        File[] listFiles = tempInvoicesDir().listFiles();
        for (File file : listFiles) {
            requestForEachTempInvoiceFile(file);
        }
    }

    private void requestForEachTempInvoiceFile(File file) {
        JsonArray jsonArray = Stream.from(file).passTo(Json.takeFromReader()).asJsonArray();
        Iterator<JsonObject> iterator = jsonArray.getObjects().iterator();
        while(iterator.hasNext()) {
            String id = iterator.next().getString("id");
            requestInvoiceAndSaveToFile(id);
        }
    }

    private void requestInvoiceAndSaveToFile(String id) {
        File targetFile = new File(Joiner.on("/").join(repositoryDirectory.repositoryDirectory().getPath(), "invoices", "invoice." + id ));
        createParentDirs(targetFile);
        login.invoice(id).page(1).handleContent(new WriteToFileContentHandling(targetFile));
    }

    private File tempInvoicesDir() {
        return new File(FileHelper.joiner().join(repositoryDirectory.repositoryDirectory().getPath(), "tempInvoices")) ;
    }

    private void createParentDirs(File targetFile) {
        try {
            Files.createParentDirs(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
