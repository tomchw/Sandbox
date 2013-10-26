package com.tchw.gwt.app.client.binders;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class DynamicListBinder extends Composite {

	private static DynamicListBinderUiBinder uiBinder = GWT
			.create(DynamicListBinderUiBinder.class);

	@UiField UListElement list;
	
	interface DynamicListBinderUiBinder extends
			UiBinder<Widget, DynamicListBinder> {
	}

	public DynamicListBinder() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public DynamicListBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public DynamicListBinder add(String name) {
		Anchor anchor = new Anchor();
		anchor.setStyleName(null);
		anchor.setText("XXX");
		anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("Fuck you!");
			}
		});
		System.out.println("Anchor: " + anchor.getElement().toString());
		
		HTML element = new HTML();
		element.setHTML("<li><a tabindex=\"-1\" href=\"#\">ABC</a></li>");
		System.out.println(element.toString());
		list.appendChild(element.getElement().getChild(0));
		list.appendChild(anchor.getElement());
		return this;
	}
}
