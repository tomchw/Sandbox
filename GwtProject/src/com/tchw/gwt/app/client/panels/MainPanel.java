package com.tchw.gwt.app.client.panels;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainPanel {

	public final Panel panel;
	
	private MainPanel(Panel p_panel) {
		panel = p_panel;
	}
	
	public void clear() {
		panel.clear();
	}
	
	public void rebuild() {
		panel.clear();
	}
	
	public static MainPanel create() {
		final VerticalPanel panel = new VerticalPanel();
		return new MainPanel(panel);
	}
}
