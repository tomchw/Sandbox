package com.tchw.gwt.app.client.examples;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;

public class MyPopupPanel {

	public static void show() {
		DecoratedPopupPanel popupPanel = new DecoratedPopupPanel(true, true);
		popupPanel.setAnimationEnabled(true);
		popupPanel.setWidget(new Label("All done?"));
		popupPanel.center();
	}
	
}
