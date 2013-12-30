package org.tchw.fakturownia.app.requestForAllDataMainTest;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.ResponseContentHandling;
import org.tchw.generic.stream.stream.Stream;

public class TestRequestExecution implements RequestExecution {

    @Override
    public void doGet(String url, ResponseContentHandling contentHandling) {
        String urlPath = url.replaceFirst("https://", "");
        String resourceName;
        if( askingForFullInvoices(urlPath) ) {
            resourceName = retrieveFullInvoicesResourceName(urlPath);
        } else {
            resourceName = retrieveResourceName(urlPath);
        }
        contentHandling.handleContent( Stream.fromResource(TestApplicationBeanConfig.class, resourceName).asBufferedReader() );
    }

    private String retrieveFullInvoicesResourceName(String urlPath) {
        try {
            Matcher matcher = Pattern.compile("/(\\d+)\\.json").matcher(urlPath);
            matcher.find();
            String invoiceNumber = matcher.group(1);

            return MessageFormat.format("invoice.{0}.txt", invoiceNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException("Problems while trying parse URL: " + urlPath, e);
        }
    }

    private boolean askingForFullInvoices(String urlPath) {
        return urlPath.matches(".*/invoices/.*");
    }

    private String retrieveResourceName(String urlPath) {
        try {
            Matcher matcher = Pattern.compile("/(.+?)\\.json.+?page=(\\d+)").matcher(urlPath);
            matcher.find();
            String tableType = matcher.group(1);
            String pageNumber = matcher.group(2);

            return MessageFormat.format("{0}.{1}.txt", tableType, pageNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException("Problems while trying parse URL: " + urlPath, e);
        }
    }
}
