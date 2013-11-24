package org.tchw.csvtojson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

public class CsvToJsonConverter {

	private final Reader reader;
	private final CsvPreference csvPreference;

	private CsvToJsonConverter(Reader reader, CsvPreference csvPreference) {
		this.reader = reader;
		this.csvPreference = csvPreference;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public List<JsonObject> resultAsListOfJsons() {
		ImmutableList.Builder<JsonObject> builder = ImmutableList.<JsonObject>builder();
		CsvListReader csvReader = new CsvListReader(reader, csvPreference);
		try {
			List<String> header = header(csvReader);
			List<String> csvLine;
			while( (csvLine=csvReader.read()) != null ) {
				builder.add( csvLineToJson(header, csvLine) );
			}
			return builder.build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {		
			closeQuietly(csvReader);
		}
	}

	private JsonObject csvLineToJson(List<String> header, List<String> csvLine) {
		Preconditions.checkArgument(header.size() == csvLine.size(), "Not equal number of header columns and csv line columnes. CsvLine: " + csvLine);
		JsonObject json = new JsonObject();
		for (int i = 0; i < header.size(); i++) {
			String name = header.get(i);
			String value = csvLine.get(i);
			json.addProperty(name, value);
		}
		return json;
	}

	private List<String> header(CsvListReader csvReader) throws IOException {
		List<String> header = csvReader.read();
		Preconditions.checkNotNull(header, "It seems that the CSV input is empty" );
		return header;
	}

	private void closeQuietly(CsvListReader csvReader) {
		try {
			csvReader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class Builder {

		private CsvPreference csvPreference = CsvPreference.STANDARD_PREFERENCE;
		
		public Builder CsvPreference(CsvPreference csvPreference) {
			this.csvPreference = csvPreference;
			return this;
		}
		
		public CsvToJsonConverter buildForFile(File file) {
			try {
				return new CsvToJsonConverter(new FileReader(file), csvPreference);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		public CsvToJsonConverter buildForClasResource(Class<?> clazz, String resourceName) {
			return new CsvToJsonConverter(new InputStreamReader(clazz.getResourceAsStream(resourceName)), csvPreference);
		}
		
		public CsvToJsonConverter buildForInputStream(InputStream inputStream) {
			return new CsvToJsonConverter(new InputStreamReader(inputStream), csvPreference);
		}
		
	}

}
