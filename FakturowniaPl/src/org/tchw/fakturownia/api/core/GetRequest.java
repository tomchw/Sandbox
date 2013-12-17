package org.tchw.fakturownia.api.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.tchw.fakturownia.api.core.ExecuteRequest.ContentHandlingWithBufferedReader;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class GetRequest {

    public static Login login(String login, String token) {
        return new Login(login, token);
    }

    public static class Login {

        private final String login;
        private final String token;

        public Login(String login, String token) {
            this.login = login;
            this.token = token;
        }

        public Table invoice(int id) {
            return new Table("invoices/" + id + ".json");
        }

        public Table invoices() {
            return new Table("invoices.json");
        }

        public Table products() {
            return new Table("products.json");
        }

        public Table clients() {
            return new Table("clients.json");
        }

        public class Table {

            private final String table;

            public Table(String table) {
                this.table = table;
            }

            public Page page(int pageNumber) {
                return new Page(pageNumber);
            }

            public class Page {

                private final int pageNumber;

                public Page(int pageNumber) {
                    this.pageNumber = pageNumber;
                }

                public Execution writeContentToFile() {
                    return writeContentToFile("{login}.{table}.{page}.txt");
                }

                public Execution writeContentToFile(String filePath) {
                    final File file = new File(filePath
                            .replace("{page}",  String.valueOf(pageNumber))
                            .replace("{table}", table)
                            .replace("{login}", login)
                            );
                    return new Execution(writeToFileContantHandling(file));
                }

                public Execution printContentToSreen() {
                    return new Execution(writeToScreenContantHandling());
                }

                private ContentHandlingWithBufferedReader writeToScreenContantHandling() {
                    ContentHandlingWithBufferedReader contentHandlingWithBufferedReader = new ContentHandlingWithBufferedReader() {
                        @Override
                        public void handleContent(final BufferedReader reader) {
                            try {
                                for (String line : CharStreams.readLines(reader)) {
                                    System.out.println(line);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                    return contentHandlingWithBufferedReader;
                }


                private ContentHandlingWithBufferedReader writeToFileContantHandling(final File file) {
                    ContentHandlingWithBufferedReader contentHandlingWithBufferedReader = new ContentHandlingWithBufferedReader() {
                        @Override
                        public void handleContent(final BufferedReader reader) {
                            try {
                                CharStreams
                                .asCharSource(asInputSupplier(reader))
                                .copyTo(asCharSink(file));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        private CharSink asCharSink(File file) {
                            return Files.asCharSink(file, Charsets.UTF_8);
                        }

                        private InputSupplier<Reader> asInputSupplier(final BufferedReader reader) {
                            return new InputSupplier<Reader>() {
                                @Override
                                public Reader getInput() throws IOException {
                                    return reader;
                                }
                            };
                        }
                    };
                    return contentHandlingWithBufferedReader;
                }

                public class Execution {

                    private final ContentHandlingWithBufferedReader contentHandlingWithBufferedReader;

                    public Execution(ContentHandlingWithBufferedReader contentHandlingWithBufferedReader) {
                        this.contentHandlingWithBufferedReader = contentHandlingWithBufferedReader;
                    }

                    public void executeSync() {
                        String url = "https://" + login + ".fakturownia.pl/" + table + "?page=" + pageNumber + "&api_token=" + token;
                        new ExecuteRequest(url, contentHandlingWithBufferedReader).executeSync();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Login tcc1 = GetRequest.login("tcc1", "sMEuDnemiZIPcbEL5g");
        tcc1.invoices().page(1).writeContentToFile("{login}.{table}.{page}.txt").executeSync();
        tcc1.products().page(1).writeContentToFile("{login}.{table}.{page}.txt").executeSync();
        tcc1.clients().page(1).writeContentToFile("{login}.{table}.{page}.txt").executeSync();
    }

}
