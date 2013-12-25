package org.tchw.fakturownia.api.model.impl;

import java.io.Reader;

import org.tchw.data.json.JsonToPojo;
import org.tchw.data.model.AbstractFinder;
import org.tchw.data.stream.Stream;
import org.tchw.data.stream.Stream.From.ReaderPasser;
import org.tchw.data.stream.Streams;
import org.tchw.data.stream.Streams.From.ReadersPasser;
import org.tchw.fakturownia.api.model.Client;
import org.tchw.fakturownia.api.model.ClientFinder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ClientFinderImpl extends AbstractFinder<Client> implements ClientFinder {

    public ClientFinderImpl(ImmutableMap<String, Client> items) {
        super(items);
    }

    public static ReaderPasser<ClientFinder> takeFromReader() {
        return new Stream.From.ReaderPasser<ClientFinder>() {

            @Override
            public ClientFinder pass(Reader reader) {
                ImmutableMap<String, Client> map = JsonToPojo.create().add(reader).buildAsMap(Client.fromJson);
                return new ClientFinderImpl(map);
            }

        };
    }

    public static ReadersPasser<ClientFinder> takeFromReaders() {
        return new Streams.From.ReadersPasser<ClientFinder>() {
            @Override
            public ClientFinder pass(ImmutableList<? extends Reader> readers) {
                ImmutableMap<String, Client> map = JsonToPojo.create().addAll(readers).buildAsMap(Client.fromJson);
                return new ClientFinderImpl(map);
            }
        };
    }
}