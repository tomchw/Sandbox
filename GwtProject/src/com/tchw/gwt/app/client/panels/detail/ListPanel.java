package com.tchw.gwt.app.client.panels.detail;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tchw.gwt.app.client.binders.DynamicListBinder;

public class ListPanel {

	public final Panel panel;
	
	private ListPanel(Builder builder) {
		panel = new VerticalPanel();
		DynamicListBinder dynamicListBinder = new DynamicListBinder();
		dynamicListBinder.add("ABC");
		panel.add(dynamicListBinder);
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		public ListPanel build() {
			return new ListPanel(this);
		}
		
	}
}
