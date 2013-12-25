package org.tchw.fakturownia.data.model;

import org.tchw.generic.stream.model.Finder;

import com.google.common.collect.ImmutableList;

public interface InvoiceFinder extends Finder<Invoice> {

    ImmutableList<Invoice> byClientId(String clientId);

}
