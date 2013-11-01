package com.tchw.gwt.app.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;

public class Dropdowns extends Composite {

	private Dropdowns(Panel panel) {
		initWidget(panel);
		System.out.println(panel.toString());
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private final Panel ulPanel = new HTMLPanel("ul", "");
		
		private Builder() {
			ulPanel.setStyleName("dropdown-menu");
			ulPanel.getElement().setAttribute("role", "menu");
			ulPanel.getElement().setAttribute("aria-labelledby", "dropdownMenu");
			ulPanel.getElement().setAttribute("style", "display: block; position: static; margin-bottom: 5px; *width: 180px;");
		}

		public Builder divider() {
			Panel li = li();
			li.setStyleName("divider");
			ulPanel.add(li);
			return this;
		}
		
		public Builder add(String text) {
			Panel li = li();
			li.add(anchor(text));
			ulPanel.add(li);
			return this;
		}

		public Builder add(String text, ClickHandler clickHandler) {
			Panel li = li();
			Anchor anchor = anchor(text);
			anchor.addClickHandler(clickHandler);
			li.add(anchor);
			ulPanel.add(li);
			return this;
		}
		
		
		private static Anchor anchor(String text) {
			Anchor anchor = new Anchor(text);
			anchor.setTabIndex(-1);
			return anchor;
		}

		public Dropdowns build() {
			HTMLPanel divPanel = new HTMLPanel("");
			divPanel.setStyleName("dropdown clearfix");
			divPanel.add(ulPanel);
			return new Dropdowns(divPanel);
		}
		
		private static Panel li() {
			return new HTMLPanel("li", "");
		}
	}
	
}
