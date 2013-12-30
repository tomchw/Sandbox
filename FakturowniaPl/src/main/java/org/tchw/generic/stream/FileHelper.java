package org.tchw.generic.stream;

import com.google.common.base.Joiner;

public class FileHelper {

    public static Joiner joiner() {
        return Joiner.on("/");
    }

}
