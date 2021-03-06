package org.tchw.fakturownia.services;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.RequestLogin;
import org.tchw.fakturownia.services.requestForAllData.gatherData.RequestForTableData;
import org.tchw.fakturownia.services.requestForAllData.impl.WriteToFileContentHandling;
import org.tchw.generic.stream.FileHelper;
import org.tchw.generic.stream.json.Json;
import org.tchw.generic.stream.json.JsonArray;
import org.tchw.generic.stream.json.JsonObject;
import org.tchw.generic.stream.stream.Stream;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

public class RequestForAllData {

    private final Logger log = Logger.getLogger(getClass());

    private final RequestLogin requestLogin;

    private final RepositoryDirectory repositoryDirectory;

    private final RequestForTableData requestForTableData;

    private final RequestExecution requestExecution;

    public RequestForAllData(RequestLogin requestLogin, RepositoryDirectory repositoryDirectory,
            RequestForTableData requestForTableData, RequestExecution requestExecution) {
        this.requestLogin = requestLogin;
        this.repositoryDirectory = repositoryDirectory;
        this.requestForTableData = requestForTableData;
        this.requestExecution = requestExecution;
    }

    public void requestForAllData() {
        log.info("Requesting for all data. Files will be saved to " + repositoryDirectory.repositoryDirectory().getPath());
        requestForClients();
        requestForProducts();
        requestForInvoices();
        requestForFullInvoices();
        log.info("End of requesting for all data");
    }

    private void requestForInvoices() {
        requestForTableData.gatherTableData(requestLogin.login().invoices(), "tempInvoices");
    }

    private void requestForProducts() {
        requestForTableData.gatherTableData(requestLogin.login().products(), "products");
    }

    private void requestForClients() {
        requestForTableData.gatherTableData(requestLogin.login().clients(), "clients");
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
            List<Future<Object>> futures = newFixedThreadPool.invokeAll(fetchInvoices);
            for (Future<Object> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
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
        requestLogin.login().invoice(id).page(1).handleContent(requestExecution, new WriteToFileContentHandling(targetFile));
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
