package org.tchw.fakturownia.remote;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class GetRequest {

    private final static Logger log = Logger.getLogger(GetRequest.class);

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

        public Table invoice(String id) {
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

                public void handleContent(ContentHandling contentHandling) {
                    new Execution(contentHandling).executeSync();
                }

                public void saveContentToFile() {
                    saveContentToFile("{login}.{table}.{page}.txt");
                }

                public void saveContentToFile(String filePath) {
                    final File file = new File(filePath
                            .replace("{page}",  String.valueOf(pageNumber))
                            .replace("{table}", table)
                            .replace("{login}", login)
                            );
                    new Execution(writeToFileContantHandling(file)).executeSync();
                }

                public Execution printContentToSreen() {
                    return new Execution(writeToScreenContantHandling());
                }

                private ContentHandling writeToScreenContantHandling() {
                    ContentHandling contentHandlingWithBufferedReader = new ContentHandling() {
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


                private ContentHandling writeToFileContantHandling(final File file) {
                    ContentHandling contentHandlingWithBufferedReader = new ContentHandling() {
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

                    private final ContentHandling contentHandlingWithBufferedReader;

                    public Execution(ContentHandling contentHandlingWithBufferedReader) {
                        this.contentHandlingWithBufferedReader = contentHandlingWithBufferedReader;
                    }

                    public void executeSync() {
                        log.debug("Getting " + table + " page " + pageNumber );
                        String url = "https://" + login + ".fakturownia.pl/" + table + "?page=" + pageNumber + "&api_token=" + token;
                        new ExecuteRequest(url, contentHandlingWithBufferedReader).executeSync();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Login tcc1 = GetRequest.login("tcc1", "sMEuDnemiZIPcbEL5g");
        tcc1.invoices().page(1).saveContentToFile("{login}.{table}.{page}.txt");
        tcc1.products().page(1).saveContentToFile("{login}.{table}.{page}.txt");
        tcc1.clients().page(1).saveContentToFile("{login}.{table}.{page}.txt");
    }

}
