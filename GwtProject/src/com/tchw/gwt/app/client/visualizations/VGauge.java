package com.tchw.gwt.app.client.visualizations;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Gauge;

public class VGauge {

    public static Builder builder() {
    	return new Builder();
    }
    	
    private final Builder builder;

    private VGauge(Builder p_builder) {
    	builder = p_builder;
    }
    
    public VGauge addValue(int toAdd) {
    	setValue(getValue() + toAdd);
    	return this;
    }

    public int getValue() {
    	return builder.data.getValueInt(0, 1);
    }
    
    public VGauge setValue(int newValue) {
    	builder.data.setValue(0, 1, newValue);
    	builder.gauge.draw(builder.data, builder.options);
    	return this;
    }

    public Gauge asGauge() {
    	return builder.gauge;
    }
    
    public static class Builder {
    
    	private DataTable data;
    	
    	private Gauge.Options options;

    	private Gauge gauge;
    	
		public VGauge buildAndAddTo(final Panel panel) {
		    Runnable onLoadCallback = new Runnable() {
	            public void run() {
	            	data = dataTable(5);
	            	options = options();
	            	gauge = new Gauge(data, options);
		            panel.add(gauge);
		        }
		    };
		    VisualizationUtils.loadVisualizationApi(onLoadCallback, Gauge.PACKAGE);
		    return new VGauge(this);
		}
	
		private static Gauge.Options options() {
			Gauge.Options option = Gauge.Options.create();
		    option.setHeight(200);
		    option.setWidth(200);
		    option.setGreenRange(71, 80);
		    option.setMinorTicks(10);
		    option.setRedRange(81, 100);
		    option.setYellowRange(61, 70);
			return option;
		}
	
		private static DataTable dataTable(int value) {
			DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "Label");
		    data.addColumn(ColumnType.NUMBER, "Value");
		    data.addRows(1);
		    data.setValue(0, 0, "CPU(%)");
		    data.setValue(0, 1, value);
			return data;
		}
		
	}
	
}
