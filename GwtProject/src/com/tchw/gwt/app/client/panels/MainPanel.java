package com.tchw.gwt.app.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
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
		addToPanel();
	}
	
	public void clear() {
		panel.clear();
	}
	
	public void rebuild() {
		panel.clear();
		addToPanel();
	}
	
	public Panel asPanel() {
		return panel;
	}
	
	public static MainPanel create() {
		final VerticalPanel panel = new VerticalPanel();
		return new MainPanel(panel);
	}

	private void addToPanel() {
		playingWithButtonAndPopupPanel();
		playingWithButtons();
		playingWithCellTable();
		playingWithGauge();
	}

	private void playingWithCellTable() {
		panel.add(CellTableExample.cellTable());
	}

	private void playingWithGauge() {
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
		//ChangeGaugeEveryFewSeconds.install(gauge);
	}

	private void playingWithButtons() {
		panel.add(Buttons.builder("Mini default").defaul().mini().emptyClick());
		panel.add(Buttons.builder("Small success, click me").success().small().click(new ClickHandler() {
			private int counter;
			@Override
			public void onClick(ClickEvent event) {
				panel.add(new Label("Clicked " + ++counter));
			}
		}));
		panel.add(Buttons.builder("Large danger").danger().large().emptyClick());
		panel.add(Buttons.builder("Small warning").warning().small().emptyClick());
		panel.add(Buttons.builder("Button as link").link().large().emptyClick());
	}

	private void playingWithButtonAndPopupPanel() {
		final Button buttonWithPopupPanel = Buttons.builder("Having popup panel").primary().large().emptyClick();
		panel.add(buttonWithPopupPanel);
		
		final DecoratedPopupPanel popupPanel = new DecoratedPopupPanel();
		popupPanel.setAutoHideEnabled(true);
		popupPanel.setAnimationEnabled(true);
		popupPanel.setWidget(new Label("Hello"));
		buttonWithPopupPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				popupPanel.showRelativeTo(buttonWithPopupPanel);
			}
		});
		
		buttonWithPopupPanel.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				popupPanel.hide();
			}
		});
	}
	

}
