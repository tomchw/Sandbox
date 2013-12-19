package org.tchw.fakturownia.api.model;

import com.google.common.collect.ImmutableList;

public interface InvoiceFinder {

    ImmutableList<Invoice> byClientId(String clientId);

}
