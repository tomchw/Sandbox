package com.tchw.gwt.app.client.panels;

import java.util.Arrays;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

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
		final VerticalPanel panel = new VerticalPanel();
		return new MainPanel(panel);
	}
	
	private static void initWidgets(Panel mainPanel) {
		mainPanel.add(cellListPanel());
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

		CellList<String> list = new CellList<String>(new TextCell());
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
