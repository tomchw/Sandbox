package org.tchw.generic.data.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class Streams {

    public static From from(File directory, final Pattern fileNamePattern) {
        Preconditions.checkArgument(directory.isDirectory(), directory + " is not a directory");
        ImmutableList<InputStream> inputStreams = inputStreamsWithFilesInDirectory(directory, fileNamePattern);
        return new From(inputStreams);
    }

    public static From fromResources(Class<?> clazz, String...resourceNames) {
        ImmutableList.Builder<InputStream> builder = ImmutableList.builder();
        for (String resourceName : resourceNames) {
            builder.add( clazz.getResourceAsStream(resourceName) );
        }
        return new From(builder.build());
    }

    private static ImmutableList<InputStream> inputStreamsWithFilesInDirectory(File directory, final Pattern fileNamePattern) {
        File[] listFiles = listFiles(directory, fileNamePattern);
        return filesToInputStreams(listFiles);
    }

    private static ImmutableList<InputStream> filesToInputStreams(File[] listFiles) {
        ImmutableList.Builder<InputStream> builder = ImmutableList.builder();
        for (File file : listFiles) {
            try {
                builder.add( new FileInputStream(file) );
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        ImmutableList<InputStream> inputStreams = builder.build();
        return inputStreams;
    }

    private static File[] listFiles(File directory,
            final Pattern fileNamePattern) {
        File[] listFiles = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return fileNamePattern.matcher(name).matches();
            }
        });
        return listFiles;
    }

    public static class From {

        private final ImmutableList<InputStream> inputStreams;

        private From(ImmutableList<InputStream> inputStreams) {
            this.inputStreams = inputStreams;
        }

        public interface ReadersPasser<T> {
            T pass(ImmutableList<? extends Reader> tokener);
        }

        public ImmutableList<BufferedReader> asBufferedReaders() {
            ImmutableList.Builder<BufferedReader> builder = ImmutableList.builder();
            for (InputStream inputStream : inputStreams) {
                builder.add(new BufferedReader(new InputStreamReader(inputStream)));
            }
            return builder.build();
        }

        public <T> T passTo(ReadersPasser<T> passer) {
            return passer.pass(asBufferedReaders());
        }

    }
}
