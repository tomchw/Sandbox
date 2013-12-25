package org.tchw.text;

import java.text.MessageFormat;

public class ItemBuilder {

    /** String with 2000 spaces */
    private final static String LONG_EMPTY_STRING;

    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append("                    ");
        }
        LONG_EMPTY_STRING = builder.toString();
    }

    private static final String NL = System.getProperty("line.separator");

    /** Item separator */
    private final String separator;

    /** String builder */
    private final StringBuilder builder;

    /** String builder */
    private int itemsCount;

    /**
     * Constructs item builder with \r\n separator
     */
    public ItemBuilder() {
        this(NL);
    }

    /**
     * Constructs item builder with given separator
     *
     * @param p_separator TODO separator description missing
     */
    public ItemBuilder(String p_separator) {
        builder = new StringBuilder();
        separator = p_separator;
        itemsCount = 0;
    }

    public ItemBuilder copy() {
        if (itemsCount == 0) {
            return new ItemBuilder(separator);
        }
        ItemBuilder copy = new ItemBuilder(separator).append(toString());
        copy.itemsCount = itemsCount;
        return copy;
    }

    /**
     * Gets reference to associated string builder
     * @return
     */
    public StringBuilder getStringBuilder() {
        return builder;
    }

    /**
     * Appends item. You can add item in the same way like you are using Mf.f().
     * Example:
     * append("Login: {0}, Password: {1}", login, password);
     *
     * @param line
     * @param objects
     */
    public ItemBuilder append(String line, Object... objects) {
        preAppending();

        if (objects.length != 0) {
            builder.append(MessageFormat.format(line, objects));
        } else {
            builder.append(line);
        }
        return this;
    }

    public ItemBuilder appendWithLength(Object s, int length) {
        preAppending();

        String string = String.valueOf(s);
        builder.append(string);
        int diff = length - string.length();
        if (diff > 0) {
            // add extra spaces
            int remainder = diff;
            while (remainder > LONG_EMPTY_STRING.length()) {
                builder.append(LONG_EMPTY_STRING);
                remainder -= LONG_EMPTY_STRING.length();
            }
            builder.append(LONG_EMPTY_STRING.substring(0, remainder));
        }
        return this;
    }

    private void preAppending() {
        if (itemsCount > 0) {
            builder.append(separator);
        }
        itemsCount++;
    }

    /**
     * Appends empty item. The method simply adds separator only
     */
    public ItemBuilder appendEmpty() {
        preAppending();
        return this;
    }

    /**
     * Appends each object's toString() description in new line.
     */
    public ItemBuilder appendObjects(Iterable<?> appendedObjects) {
        for (Object appendedObject : appendedObjects) {
            append(String.valueOf(appendedObject));
        }
        return this;
    }

    /**
     * Appends each object's toString() description in new line.
     */
    public ItemBuilder appendObjectsWithIndent(Iterable<?> appendedObjects, String indent) {
        for (Object appendedObject : appendedObjects) {
            preAppending();
            builder.append(indent);
            builder.append(String.valueOf(appendedObject));
        }
        return this;
    }

    /**
     * Returns string.
     */
    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * Returns number of added items.
     */
    public int getItemsCount() {
        return itemsCount;
    }

    public static void main(String[] args) {
        ItemBuilder b = new ItemBuilder(":");
        b.append("AAA").appendWithLength("BBB",3).append("CCC");
        System.out.println(b);
    }

}
