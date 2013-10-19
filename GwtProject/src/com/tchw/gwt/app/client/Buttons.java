package com.tchw.gwt.app.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;

public class Buttons {

	public static ColorBuilder builder(String label) {
		Button button = new Button(label);
		button.getElement().setClassName("btn");
		return new ColorBuilder(button);
	}
	
	public static class ColorBuilder {

		private final Button button;
		
		public ColorBuilder(Button p_button) {
			button = p_button;
		}
		
		public SizeBuilder defaul() {
			return new SizeBuilder();
		}
		
		public SizeBuilder primary() {
			return addClass("btn-primary", new SizeBuilder());
		}

		public SizeBuilder warning() {
			return addClass("btn-warning", new SizeBuilder());
		}

		public SizeBuilder danger() {
			return addClass("btn-danger", new SizeBuilder());
		}

		public SizeBuilder inverse() {
			return addClass("btn-inverse", new SizeBuilder());
		}

		public SizeBuilder link() {
			return addClass("btn-link", new SizeBuilder());
		}
		
		public SizeBuilder info() {
			return addClass("btn-info", new SizeBuilder());
		}

		public SizeBuilder success() {
			return addClass("btn-success", new SizeBuilder());
		}

		private <T> T addClass(String classToAdd, T nextBuilder) {
			Element element = button.getElement();
			String className = element.getClassName();
			if( className == null || className.equals("") ) {
				className = classToAdd;
			} else {
				className = className + " " + classToAdd;
			}
			element.setClassName(className);
			return nextBuilder;
		}

		public class SizeBuilder {
			
			public ClickBuilder defaul() {
				return new ClickBuilder();
			}

			public ClickBuilder large() {
				return addClass("btn-large", new ClickBuilder());
			}

			public ClickBuilder small() {
				return addClass("btn-small", new ClickBuilder());
			}

			public ClickBuilder mini() {
				return addClass("btn-mini", new ClickBuilder());
			}
			
		}

		public class ClickBuilder {
			
			public Button emptyClick() {
				return button;
			}
			
			public Button click(ClickHandler clickHandler) {
				button.addClickHandler(clickHandler);
				return button;
			}
			
		}
		
	}

	
}
