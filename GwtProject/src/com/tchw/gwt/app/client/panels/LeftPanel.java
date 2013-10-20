package com.tchw.gwt.app.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tchw.gwt.app.client.binders.Binder1;
import com.tchw.gwt.app.client.tools.Buttons;
import com.tchw.gwt.app.client.tools.MouseHandler;

public class LeftPanel {
	
	public static Panel create(final MainPanel mainPanel) {
		VerticalPanel panel = new VerticalPanel();
		
		final Label label = new Label("LeftPanel it is");

		MouseHandler mouseHandler = MouseHandler.create()
				.listenMouseFor(label);
		
		panel.add(label);
		panel.add(mouseHandler.asLabel());
		panel.add(new Binder1("Added b1"));		

		Button clearMainPanelButton = clearMainPanel(mainPanel);
		mouseHandler.listenMouseFor(clearMainPanelButton);
		
		panel.add(clearMainPanelButton);
		panel.add(rebiuldMainPanel(mainPanel));
		
		ToggleButton toggleButton = toggleButtonChangingLabel(label);
		panel.add(toggleButton);

		panel.add(new ToggleButton("Radio 2"));
		panel.add(new ToggleButton("Radio 3"));
		return panel;
	}

	private static ToggleButton toggleButtonChangingLabel(final Label label) {
		ToggleButton toggleButton = new ToggleButton("Radio 1");
		toggleButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if( event.getValue() ) {
						label.setText("ON");
					} else {
						label.setText("OFF");
					}
				}
			});
		return toggleButton;
	}

	private static Button rebiuldMainPanel(final MainPanel mainPanel) {
		return Buttons.builder("Rebuild main panel").success().defaul().click(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.rebuild();
			}
		});
	}

	private static Button clearMainPanel(final MainPanel mainPanel) {
		return Buttons.builder("Clear main panel").danger().large().click(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
			}
		});
	}
	
}
