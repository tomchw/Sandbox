package org.tchw.fakturownia.services;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.google.common.collect.ImmutableList;
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
        ImmutableList.Builder<Callable<Object>> fetchInvoiceBuilder = ImmutableList.builder();
        JsonArray jsonArray = Stream.from(file).passTo(Json.takeFromReader()).asJsonArray();
        Iterator<JsonObject> iterator = jsonArray.getObjects().iterator();
        while(iterator.hasNext()) {
            String id = iterator.next().getString("id");
            fetchInvoiceBuilder.add(new FetchInvoice(id));
        }

        fetchInvoicesUsingMultiThreading(fetchInvoiceBuilder.build());
    }

    private void fetchInvoicesUsingMultiThreading(ImmutableList<Callable<Object>> fetchInvoices) {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
        try {
            newFixedThreadPool.invokeAll(fetchInvoices);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            newFixedThreadPool.shutdown();
        }
    }

    private class FetchInvoice implements Callable<Object> {

        private final String id;

        public FetchInvoice(String id) {
            this.id = id;
        }

        @Override
        public Object call() throws Exception {
            requestInvoiceAndSaveToFile(id);
            return null;
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
