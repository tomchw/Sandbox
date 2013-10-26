package com.tchw.gwt.app.shared;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;

public class ItemStringBuilder {

	private ImmutableList.Builder<String> builder = ImmutableList.builder();
	
	private ItemStringBuilder() {
	}
	
	public ItemStringBuilder append(String s) {
		builder.add(s);
		return this;
	}
	
	public String build() {
		UnmodifiableIterator<String> items = builder.build().iterator();
		StringBuilder stringBuilder = new StringBuilder();
		while(items.hasNext()) {
			appendItem(items, stringBuilder);
			appendSeparatorIfNotTheLastItem(items, stringBuilder);
		}
		return stringBuilder.toString();
	}

	private static void appendSeparatorIfNotTheLastItem(UnmodifiableIterator<String> items, StringBuilder stringBuilder) {
    	if(items.hasNext()) {
			stringBuilder.append("\r\n");
		}
	}

	private static void appendItem(UnmodifiableIterator<String> items, StringBuilder stringBuilder) {
		String next = items.next();
		stringBuilder.append(next);
	}
	
	public static ItemStringBuilder create() {
		return new ItemStringBuilder();
	}
	
}
