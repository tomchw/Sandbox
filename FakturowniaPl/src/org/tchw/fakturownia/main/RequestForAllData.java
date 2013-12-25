package org.tchw.fakturownia.main;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.tchw.fakturownia.remote.GetRequest.Login;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.generic.stream.json.Json;
import org.tchw.generic.stream.json.JsonArray;
import org.tchw.generic.stream.json.JsonObject;
import org.tchw.generic.stream.stream.Stream;
import org.tchw.specific.werbum.Werbum;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class RequestForAllData {

    private final Login login;

    public RequestForAllData(Login login) {
        this.login = login;
    }

    public void execute() {
        File directory = Werbum.directory;
        new RequestForTableData(directory, login.clients(), "clients").execute();
        new RequestForTableData(directory, login.products(), "products").execute();
        File todayDirectoryPath = new RequestForTableData(directory, login.invoices(), "tempInvoices").execute();
        loadFullInvoices(todayDirectoryPath);
    }

    private void loadFullInvoices(File todayDirectoryPath) {
        File[] listFiles = tempInvoicesDir(todayDirectoryPath).listFiles();
        for (File file : listFiles) {
            loadForEachTempInvoiceFile(todayDirectoryPath, file);
        }

    }

    private void loadForEachTempInvoiceFile(File todayDirectoryPath, File file) {
        JsonArray jsonArray = Stream.from(file).passTo(Json.takeFromReader()).asJsonArray();
        Iterator<JsonObject> iterator = jsonArray.getObjects().iterator();
        while(iterator.hasNext()) {
            String id = iterator.next().getString("id");
            loadInvoiceAndSaveToFile(todayDirectoryPath, id);
        }
    }

    private void loadInvoiceAndSaveToFile(File todayDirectoryPath, String id) {
        File targetFile = new File(Joiner.on("/").join(todayDirectoryPath.getPath(), "invoices", "invoice." + id ));
        createParentDirs(targetFile);
        login.invoice(id).page(1).saveContentToFile(targetFile);
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

    public static void main(String[] args) {
        new RequestForAllData(Werbum.login).execute();
    }

}
