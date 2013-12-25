package org.tchw.fakturownia.api.model;

import org.tchw.generic.data.model.Finder;

import com.google.common.collect.ImmutableList;

public interface InvoiceFinder extends Finder<Invoice> {

    ImmutableList<Invoice> byClientId(String clientId);

}
