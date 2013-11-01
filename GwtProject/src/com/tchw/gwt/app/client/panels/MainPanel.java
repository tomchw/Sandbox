package com.tchw.gwt.app.client.panels;

import java.util.Arrays;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Resources;
import com.google.gwt.user.cellview.client.CellList.Style;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.tchw.gwt.app.client.common.button.Buttons;
import com.tchw.gwt.app.client.widgets.GroupOfButtons;

public class MainPanel {

	public final Panel panel;
	
	private MainPanel(Panel p_panel) {
		panel = p_panel;
		initWidgets(panel);
	}
	
	public void clear() {
		panel.clear();
	}
	
	public void rebuild() {
		panel.clear();
	}
	
	public static MainPanel create() {
		final Panel panel = new HTMLPanel("");
		return new MainPanel(panel);
	}
	
	private static void initWidgets(Panel mainPanel) {
		mainPanel.add(setOfLinksPanel());
	}

	private static Widget setOfLinksPanel() {
		return GroupOfButtons.builder()
				.addClickable("Home")
				.addClickable("Action")
				.addButton(Buttons.builder("About me").success().defaul().emptyClick())
				.addClickable("About you")
				.vertical()
				.build();
	}

	private static Panel cellListPanel() {
		final Panel panel = new VerticalPanel();
		final Label label = infoLabel();
		
		CellList<String> list = cellList();	
		setSelectionModel(label, list);
		
		panel.add(list);
		panel.add(label);
		return panel;
	}

	private static void setSelectionModel(final Label label, CellList<String> list) {
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		selectionModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				String selectedObject = selectionModel.getSelectedObject();
				label.setText(selectedObject);
			}
		});

		list.setSelectionModel(selectionModel);
	}

	private static CellList<String> cellList() {

		Resources resources = new CellList.Resources() {

			@Override
			public ImageResource cellListSelectedBackground() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Style cellListStyle() {
				return new Style() {

					@Override
					public boolean ensureInjected() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public String getText() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String cellListEvenItem() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String cellListKeyboardSelectedItem() {
						return "label label-warning";
					}

					@Override
					public String cellListOddItem() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String cellListSelectedItem() {
						return "label label-success";
					}

					@Override
					public String cellListWidget() {
						return "well";
					}
				};
			}
		};
		
		CellList<String> list = new CellList<String>(new TextCell(), resources);
		list.setRowCount(3);
		list.setRowData(0, Arrays.asList("Monday", "Tuesday", "Wednesday"));
		
		return list;
	}

	private static Label infoLabel() {
		final Label label = new Label();
		label.setStyleName("label label-success");
		return label;
	}
	
}
