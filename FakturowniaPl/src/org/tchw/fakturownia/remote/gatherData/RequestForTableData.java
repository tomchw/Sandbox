package org.tchw.fakturownia.remote.gatherData;

import org.tchw.fakturownia.remote.GetRequest.Login.Table;

public interface RequestForTableData {

    public void gatherTableData(Table table, String tableType);

}
