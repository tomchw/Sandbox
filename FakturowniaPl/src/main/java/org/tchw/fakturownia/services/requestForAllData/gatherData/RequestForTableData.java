package org.tchw.fakturownia.services.requestForAllData.gatherData;

import org.tchw.fakturownia.services.requestForAllData.GetRequest.Login.Table;

public interface RequestForTableData {

    public void gatherTableData(Table table, String tableType);

}
