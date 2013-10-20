package com.tchw.gwt.app.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tchw.gwt.app.client.tools.Buttons;

public class MainPanel {

	private final Panel panel;
	
	private MainPanel(Panel p_panel) {
		panel = p_panel;
	}
	
	public void clear() {
		panel.clear();
	}
	
	public void rebuild() {
		panel.clear();
		addToPanel(panel);
	}
	
	public Panel asPanel() {
		return panel;
	}
	
	public static MainPanel create() {
		final VerticalPanel panel = new VerticalPanel();
		addToPanel(panel);
		return new MainPanel(panel);
	}

	private static void addToPanel(final Panel panel) {
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
	}
}
