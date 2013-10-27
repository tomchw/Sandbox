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
import com.tchw.gwt.app.client.common.button.Buttons;
import com.tchw.gwt.app.client.common.visualizations.VGauge;
import com.tchw.gwt.app.client.examples.CellTableExample;
import com.tchw.gwt.app.client.examples.ChangeGaugeEveryFewSeconds;

public class RightPanel {

	public final Panel panel = new VerticalPanel();
	
	private RightPanel() {
		initWidgets();
	}

	public static RightPanel create() {
		return new RightPanel();
	}
	
	private void initWidgets() {
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
		ChangeGaugeEveryFewSeconds.install(gauge);
	}

	private void playingWithButtons() {
		final Panel innerPanel = new VerticalPanel();
		playingWithButtonAndPopupPanel(innerPanel);
		
		innerPanel.add(Buttons.builder("Mini default").defaul().mini().emptyClick());
		innerPanel.add(Buttons.builder("Small success, click me").success().small().click(new ClickHandler() {
			private int counter;
			@Override
			public void onClick(ClickEvent event) {
				innerPanel.add(new Label("Clicked " + ++counter));
			}
		}));
		innerPanel.add(Buttons.builder("Large danger").danger().large().emptyClick());
		innerPanel.add(Buttons.builder("Small warning").warning().small().emptyClick());
		innerPanel.add(Buttons.builder("Button as link").link().large().emptyClick());

		panel.add(innerPanel);
	}

	private void playingWithButtonAndPopupPanel(Panel panel) {
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
