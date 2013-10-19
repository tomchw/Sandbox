package com.tchw.gwt.app.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tchw.gwt.app.client.binders.Binder1;

public class LeftPanel {
	
	public static Panel create(final MainPanel mainPanel) {
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label("LeftPanel it is"));
		panel.add(new Label("Something new f"));
		panel.add(new Binder1("Added b1"));		
		panel.add(Buttons.builder("Clear main panel").danger().large().click(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
			}
		}));
		panel.add(Buttons.builder("Rebuild main panel").success().defaul().click(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.rebuild();
			}
		}));		
		return panel;
	}
	
}
