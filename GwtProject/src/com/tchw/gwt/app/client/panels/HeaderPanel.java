package com.tchw.gwt.app.client.panels;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HeaderPanel {

	public static Panel create() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label("HeaderPanel it is"));
		return panel;
	}
	
}
