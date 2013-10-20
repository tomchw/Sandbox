package com.tchw.gwt.app.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tchw.gwt.app.client.binders.Binder1;

public class LeftPanel {
	
	public static Panel create(final MainPanel mainPanel) {
		VerticalPanel panel = new VerticalPanel();
		
		final Label label = new Label("LeftPanel it is");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
			}
		});
		
		label.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				label.setText(String.valueOf(System.currentTimeMillis()));
			}
		});
		
		panel.add(label);
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
		
		ToggleButton toggleButton = new ToggleButton("Radio");
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
		
		panel.add(toggleButton);
		panel.add(new ToggleButton("Radio"));
		panel.add(new ToggleButton("Radio"));
		panel.add(new ToggleButton("Radio"));
		panel.add(new ToggleButton("Radio"));
		panel.add(new ToggleButton("Radio"));
		
		return panel;
	}
	
}
