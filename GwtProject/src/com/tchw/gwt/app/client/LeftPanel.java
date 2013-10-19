package com.tchw.gwt.app.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LeftPanel {
	
	public static Panel create() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label("LeftPanel it is"));
		panel.add(new Label("Something new"));
		return panel;
	}
	
}
