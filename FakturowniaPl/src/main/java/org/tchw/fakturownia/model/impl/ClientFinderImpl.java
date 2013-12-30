package org.tchw.fakturownia.model.impl;

import java.io.Reader;

import org.tchw.fakturownia.model.Client;
import org.tchw.fakturownia.model.ClientFinder;
import org.tchw.generic.stream.json.JsonToPojo;
import org.tchw.generic.stream.model.AbstractFinder;
import org.tchw.generic.stream.stream.Stream;
import org.tchw.generic.stream.stream.Streams;
import org.tchw.generic.stream.stream.Stream.From.ReaderPasser;
import org.tchw.generic.stream.stream.Streams.From.ReadersPasser;

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
