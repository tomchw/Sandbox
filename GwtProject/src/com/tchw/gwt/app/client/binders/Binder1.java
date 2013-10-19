package com.tchw.gwt.app.client.binders;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Binder1 extends Composite {

    interface MyUiBinder extends UiBinder<Widget, Binder1> {}
	  
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField SpanElement nameSpan;

	public Binder1(String p_name) {
	    initWidget(uiBinder.createAndBindUi(this));
        nameSpan.setInnerText(p_name);
	}
}