package com.tchw.gwt.app.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tchw.gwt.app.client.examples.CellTableExample;
import com.tchw.gwt.app.client.tools.Buttons;
import com.tchw.gwt.app.client.visualizations.VGauge;

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
		Button warning = Buttons.builder("E").warning().small().emptyClick();
		
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
		panel.add(warning);
		panel.add(Buttons.builder("E").link().large().emptyClick());
		panel.add(CellTableExample.cellTable());
		VerticalPanel verticalPanel = new VerticalPanel();
		panel.add(verticalPanel);
		final VGauge gauge = VGauge.builder().buildAndAddTo(verticalPanel);
		panel.add(Buttons.builder("<-").success().small().click(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gauge.addValue(-10);
			}
		}));
		panel.add(Buttons.builder("->").success().small().click(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gauge.addValue(15);
			}
		}));
	}
	

}
