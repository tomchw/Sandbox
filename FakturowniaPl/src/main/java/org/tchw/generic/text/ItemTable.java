package org.tchw.generic.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTable {

    private final List<TableItem> items = new ArrayList<TableItem>();

    private final Map<Integer, Integer> fieldSizes = new HashMap<Integer, Integer>();

    private final String columnSeparator;

    public ItemTable() {
        this(" ");
    }

    public ItemTable(String p_columnSeparator, Integer...p_minFieldSizes) {
        for (int i = 0; i < p_minFieldSizes.length; i++) {
            fieldSizes.put(i, p_minFieldSizes[i]);
        }
        columnSeparator = p_columnSeparator;
    }

    public ItemTable addSeparator() {
        items.add(new TableItem());
        return this;
    }

    public TextTableRow addRow() {
        TextTableRow row = new TextTableRow();
        items.add(new TableItem(row));
        return row;
    }

    public ItemTable addSingleLine(String line) {
        items.add(new TableItem(line));
        return this;
    }

    @Override
    public String toString() {

        calculateFieldSizes();

        String separator = createSeparator();

        ItemBuilder tableBuilder = new ItemBuilder();
        for (TableItem item : items) {
            if( item.isSeparator() ) {
                tableBuilder.append(separator);
            } else if( item.isSingleLine() ) {
                tableBuilder.append(item.singleLine);
            } else {
                TextTableRow row = item.getRow();

                ItemBuilder fieldBuilder = new ItemBuilder("");
                List<String> fields = row.getFields();
                for (int i = 0; i < fields.size(); i++) {
                    fieldBuilder.appendWithLength(fields.get(i), fieldSizes.get(i));
                    if( i < fields.size() - 1 ) {
                        fieldBuilder.append(columnSeparator);
                    }
                }
                tableBuilder.append(fieldBuilder.toString());
            }
        }
        return tableBuilder.toString();
    }

    private String createSeparator() {
        int tableWidth = 0;

        Collection<Integer> values = fieldSizes.values();
        if(!values.isEmpty()) {
            for (Integer size : values) {
                tableWidth = tableWidth + size + columnSeparator.length();
            }
            tableWidth -= columnSeparator.length();
        }

        char[] dashes = new char[tableWidth];
        Arrays.fill(dashes, 0, tableWidth, '-');
        return new String(dashes);
    }

    private void calculateFieldSizes() {
        for (TableItem item : items) {
            if( item.isTableRow() ) {
                TextTableRow row = item.getRow();
                List<String> fields = row.getFields();
                for (int i = 0; i < fields.size(); i++) {
                    String field = fields.get(i);
                    if(fieldSizes.get(i) == null) {
                        fieldSizes.put(i, 0);
                    }
                    Integer size = fieldSizes.get(i);
                    int fieldSize = (field == null) ? 4 : field.length();
                    if (size < fieldSize) {
                        fieldSizes.put(i, fieldSize);
                    }
                }
            }
        }
    }

    public static class TextTableRow {

        private final List<String> fields = new ArrayList<String>();

        public TextTableRow addHeader(Object...p_fields) {
            for (Object string : p_fields) {
                fields.add("-" + string + "-");
            }
            return this;
        }

        public TextTableRow addFields(Object...p_fields) {
            for (Object object : p_fields) {
                fields.add(String.valueOf(object));
            }
            return this;
        }

        public List<String> getFields() {
            return fields;
        }

    }

    public int getRowsNumber() {
        return items.size();
    }

    public ItemTable clear() {
        items.clear();
        fieldSizes.clear();
        return this;
    }

    private class TableItem {

        private final String singleLine;

        private final TextTableRow row;

        public TableItem() {
            row = null;
            singleLine = null;
        }

        public TableItem(String p_singleLine) {
            singleLine = p_singleLine;
            row = null;
        }

        public TableItem(TextTableRow p_row) {
            row = p_row;
            singleLine = null;
        }

        public boolean isTableRow() {
            return row != null;
        }

        public boolean isSeparator() {
            return row == null && singleLine == null;
        }

        public boolean isSingleLine() {
            return singleLine != null;
        }

        public TextTableRow getRow() {
            return row;
        }

    }

}
