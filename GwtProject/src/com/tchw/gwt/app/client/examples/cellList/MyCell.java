package com.tchw.gwt.app.client.examples.cellList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.tchw.gwt.app.shared.ItemStringBuilder;

public class MyCell extends AbstractCell<String> {

	interface Templates extends SafeHtmlTemplates {
	      
		@SafeHtmlTemplates.Template("<div class=\"{0}\">{1}</div>")
	    SafeHtml cell(String clazz, SafeHtml value);		
	}
	
	private final static Templates templates = GWT.create(Templates.class);
	
	public MyCell() {
		super("click");
	}
	
	@Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
		if( value == null ) {
			return;
		}
		
		//SafeStyles safeStyles = new SafeStylesBuilder().appendTrustedString("label").toSafeStyles();
		
		SafeHtml html = templates.cell("label label-warning", SafeHtmlUtils.fromString(value));
		sb.append(html);
	}

	@Override
    public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		String info = ItemStringBuilder.create()
			.append("EventTarget: " + event.getEventTarget())
			.build();
		System.out.println(info);
		valueUpdater.update("Dupa");
	}
	
}
