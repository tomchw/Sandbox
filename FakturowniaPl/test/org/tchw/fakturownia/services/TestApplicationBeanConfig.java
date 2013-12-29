package org.tchw.fakturownia.services;

import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.ResponseContentHandling;
import org.tchw.generic.stream.stream.Stream;

import com.google.common.io.Files;

public class TestApplicationBeanConfig {

    @Bean
    public RepositoryDirectory repositoryDirectory() {
        return new RepositoryDirectory() {

            private final File file = Files.createTempDir();

            @Override
            public File repositoryDirectory() {
                return file;
            }
        };
    }

    @Bean
    public RequestExecution requestExecution() {
        return new RequestExecution() {

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
        };
    }
}
