package org.tchw.fakturownia.api.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.tchw.fakturownia.api.core.ExecuteRequest.ContentHandlingWithBufferedReader;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.CharStreams;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class GetRequest {
    
    private static Table domain(String login, String token) {
        return new Table(login, token);
    }

    public static class Table {

        private final String login;
        private final String token;

        private String table;
        
        public Table(String login, String token) {
            this.login = login;
            this.token = token;
        }

        public Page invoices() {
            table = "invoices.json";
            return new Page();
        }

        public class Page {
            
            private int pageNumber;

            public ContentHandling page(int pageNumber) {
                this.pageNumber = pageNumber;
                return new ContentHandling();
            }
            
            public class ContentHandling {
                
                private ContentHandlingWithBufferedReader contentHandlingWithBufferedReader;
                
                public Execution toFile(final String filePath) {
                    contentHandlingWithBufferedReader = new ContentHandlingWithBufferedReader() {
                        @Override
                        public void handleContent(final BufferedReader reader) {
                            try {
                                CharStreams
                                    .asCharSource(asInputSupplier(reader))
                                    .copyTo(asCharSink(filePath));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        private CharSink asCharSink(final String filePath) {
                            return Files.asCharSink(new File(filePath), Charsets.UTF_8, FileWriteMode.APPEND);
                        }

                        private InputSupplier<Reader> asInputSupplier(
                                final BufferedReader reader) {
                            return new InputSupplier<Reader>() {
                                @Override
                                public Reader getInput() throws IOException {
                                    return reader;
                                }
                            };
                        }
                    };
                    
                    return new Execution();
                }
                
                public class Execution {
                    public void executeSync() {
                        String url = "https:/" + login + ".fakturownia.pl/" + table + "?page=" + pageNumber + "?api_token=" + token; 
                        new ExecuteRequest(url, contentHandlingWithBufferedReader).executeSync();
                    }
                }                
            }
            
        }
    }

    
    public static void main(String[] args) {
        
        GetRequest.domain("tcc1", "sMEuDnemiZIPcbEL5g").invoices().page(1).toFile("xxx.xxx").executeSync();
        
    }
    
}
