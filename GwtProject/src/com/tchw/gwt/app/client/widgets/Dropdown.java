package com.tchw.gwt.app.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class Dropdown extends Composite {

	private Dropdown(Panel panel) {
		initWidget(panel);
		System.out.println(panel.toString());
	}
	
	public static Builder builder() {
		return Builder.create();
	}

	public static Builder builderSubmenu() {
		return Builder.createForSubmenu();
	}

	public static class Builder {
		
		private final Panel ulPanel = new HTMLPanel("ul", "");

		private static Builder createForSubmenu() {
			return new Builder();
		}
		
		private static Builder create() {
			Builder builder = new Builder();
			builder.ulPanel.getElement().setAttribute("role", "menu");
			builder.ulPanel.getElement().setAttribute("aria-labelledby", "dropdownMenu");
			builder.ulPanel.getElement().setAttribute("style", "display: block; position: static; margin-bottom: 5px; *width: 180px;");
			return builder;
		}
		
		private Builder() {
			ulPanel.setStyleName("dropdown-menu");
		}

		public Builder divider() {
			ulPanel.add(li().styleName("divider").build());
			return this;
		}
		
		public Builder add(String text) {
			ulPanel.add(li().add(anchor(text)).build());
			return this;
		}

		public Builder addSubmenu(String text, Dropdown dropdowns) {
			ulPanel.add(li().styleName("dropdown-submenu").add(anchor(text)).add(dropdowns).build());
			return this;
		}
		
		public Builder add(String text, ClickHandler clickHandler) {
			Anchor anchor = anchor(text);
			anchor.addClickHandler(clickHandler);
			ulPanel.add(li().add(anchor).build());
			return this;
		}
		
		
		private static Anchor anchor(String text) {
			Anchor anchor = new Anchor(text);
			anchor.setTabIndex(-1);
			return anchor;
		}

		public Dropdown build() {
			return new Dropdown(ulPanel);
		}
		
		private static LiBuilder li() {
			return new LiBuilder();
		}
		
		private static class LiBuilder {
			
			private final Panel li = new HTMLPanel("li", "");
			
			public LiBuilder styleName(String styleName) {
				li.setStyleName(styleName);
				return this;
			}

			public LiBuilder add(Widget widget) {
				li.add(widget);
				return this;
			}

			public Panel build() {
				return li;
			}
		}
	}
	
}
