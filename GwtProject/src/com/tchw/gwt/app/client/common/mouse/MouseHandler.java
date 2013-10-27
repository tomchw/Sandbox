package com.tchw.gwt.app.client.common.mouse;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Label;

public class MouseHandler {

	private final LabelMoveHandler labelMoveHandler = new LabelMoveHandler();
	
	public static MouseHandler create() {
		return new MouseHandler();
	}
	
	public MouseHandler listenMouseFor(HasAllMouseHandlers hasAllMouseHandlers) {
		hasAllMouseHandlers.addMouseMoveHandler(labelMoveHandler);
		return this;
	}
	
	public Label asLabel() {
		return labelMoveHandler.label;
	}

	private static class LabelMoveHandler implements MouseMoveHandler {
		
		private final Label label = new Label("<Mouse handler label>");
		
		@Override
		public void onMouseMove(MouseMoveEvent event) {
			String info = "[X = " + event.getX() + ", Y = " +  event.getY() + "]";
			label.setText(info);
		}
	};
	
	
}
