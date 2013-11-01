package com.tchw.gwt.app.client.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.tchw.gwt.app.client.common.button.Buttons;

public class GroupOfButtons extends Composite {

	public static Builder builder() {
		return new Builder();
	}

	private GroupOfButtons(Panel panel) {
		initWidget(panel);
	}
	
	public static class Builder {
		
		private Panel panel = new HTMLPanel("");
		
		private Builder() {
			panel.setStyleName("btn-group");
		}
		
		public Builder vertical() {
			panel.setStyleName("btn-group btn-group-vertical");
			return this;
		}
		
		public Builder addButton(Button button) {
			panel.add(button);
			return this;
		}
		
		public Builder addClickable(String text) {
			addButton( Buttons.builder(text).defaul().defaul().emptyClick() );
			return this;
		}
		
		public GroupOfButtons build() {
			return new GroupOfButtons(panel);
		}
	}
}