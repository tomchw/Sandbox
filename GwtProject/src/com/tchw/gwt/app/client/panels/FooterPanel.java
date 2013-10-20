package com.tchw.gwt.app.client.panels;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FooterPanel {

	public static Panel create() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label("FooterPanel it is"));
		return panel;
	}
	
}
