package com.tchw.gwt.app.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainPanel {

	public static Panel create() {
		final VerticalPanel panel = new VerticalPanel();
		panel.add(Buttons.builder("A").primary().large().emptyClick());
		panel.add(Buttons.builder("B").defaul().mini().emptyClick());
		panel.add(Buttons.builder("C").success().small().click(new ClickHandler() {
			private int counter;
			@Override
			public void onClick(ClickEvent event) {
				panel.add(new Label("Clicked " + ++counter));
			}
		}));
		panel.add(Buttons.builder("D").danger().large().emptyClick());
		panel.add(Buttons.builder("E").warning().small().emptyClick());
		panel.add(Buttons.builder("E").link().large().emptyClick());
		return panel;
	}
}
