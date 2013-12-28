package org.tchw.fakturownia.remote;

import org.apache.log4j.Logger;
import org.tchw.fakturownia.remote.impl.RequestExecutionImpl;

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

                public void handleContent(ResponseContentHandling contentHandling) {
                    new Execution(contentHandling).executeSync();
                }

                public class Execution {

                    private final ResponseContentHandling contentHandlingWithBufferedReader;

                    public Execution(ResponseContentHandling contentHandlingWithBufferedReader) {
                        this.contentHandlingWithBufferedReader = contentHandlingWithBufferedReader;
                    }

                    public void executeSync() {
                        log.debug("Getting " + table + " page " + pageNumber );
                        String url = "https://" + login + ".fakturownia.pl/" + table + "?page=" + pageNumber + "&api_token=" + token;
                        new RequestExecutionImpl().execute(url, contentHandlingWithBufferedReader);
                    }
                }
            }
        }
    }

}
