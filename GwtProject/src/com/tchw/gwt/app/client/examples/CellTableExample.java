package com.tchw.gwt.app.client.examples;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CellTableExample {

	private final static List<Person> persons = Arrays.asList(
			new Person("Jola", "Samowola"),
			new Person("Tolek", "Swawolek"),
			new Person("Jas", "Samowola")
			);
	
	private static class Person {
		
		private final String firstName;
		private final String lastName;
		
		public Person(String p_firstName, String p_lastName) {
			firstName = p_firstName;
			lastName = p_lastName;
		}
	}
	
	public static CellTable<Person> cellTable() {
		CellTable<Person> cellTable = new CellTable<Person>();
		prepareColumns(cellTable);
		setData(cellTable);

		final SingleSelectionModel<Person> selectionModel = new SingleSelectionModel<Person>();
		

	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	        public void onSelectionChange(SelectionChangeEvent event) {
	          Person selected = selectionModel.getSelectedObject();
	          System.out.println(selected.firstName);
	        }
	      });		
		
		cellTable.setSelectionModel(selectionModel);
		
		return cellTable;
	}

	private static void setData(CellTable<Person> cellTable) {
		cellTable.setRowData(0, persons);
	}

	private static void prepareColumns(CellTable<Person> cellTable) {
		cellTable.addColumn(new TextColumn<CellTableExample.Person>() {
				@Override
				public String getValue(Person person) {
					return person.firstName;
				}
			}, "First name");
		cellTable.addColumn(new TextColumn<CellTableExample.Person>() {
				@Override
				public String getValue(Person person) {
					return person.lastName;
				}
			}, "Last name");
	}
	
}
