package utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;

public class Utilities {
	private static Calendar calendar;
	private static HashMap<String,Double> conversions;
	private static String currencies[] = {"EUR", "USD", "GBP", "CAD", "CNY", "AUD"};
	
	public static String getTime(){
		calendar = Calendar.getInstance();
		return String.format("%tH:%tM:%tS", calendar,calendar,calendar);
	}
	
	public static JLabel drawLineChart(TreeMap<String,Double> data){
		String[] dates = new String[data.size()];
		double[] values = new double[data.size()];
		int i = 0;
		for (String str : data.keySet()) {
		    dates[i]=str;
		    values[i]=data.get(str);
		    i++;
		}
		Plot plot = Plots.newPlot(Data.newData(values));
        LineChart chart = GCharts.newLineChart(plot);
		try {
			return new JLabel(new ImageIcon(ImageIO.read(new URL(chart.toURLString()))));
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static JLabel drawCoolLineChart(TreeMap<String,Double> data){
		String[] dates = new String[data.size()];
		double[] values = new double[data.size()];
		String[] literalValues = new String[data.size()];
		int i = 0;
		double greatest = 0;
		for (String str : data.keySet()) {
		    dates[i]=str;
		    values[i]=data.get(str);
		    if(values[i]>greatest) greatest=values[i];
		    i++;
		}
		greatest += greatest*1/6;
		//for(int j = values.length; j>0;j++){
		//	literalValues[j-1] = ""+greatest/j;
		//}
		for (int j = 0; j<values.length;j++){
			values[j] = (values[j]*100)/greatest; 
		}
		//Plot plot = Plots.newPlot(Data.newData(values));
        //LineChart chart = GCharts.newLineChart(plot);
        Line line1 = Plots.newLine(Data.newData(values), Color.newColor("CA3D05"));
        line1.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line1.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
        line1.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
        
     // Defining chart.
        LineChart chart = GCharts.newLineChart(line1);
        chart.setSize(600, 450);
        chart.setTitle("Value of the title in time", Color.WHITE, 14);
        //chart.addHorizontalRangeMarker(40, 60, Color.newColor(Color.RED, 30));
        //chart.addVerticalRangeMarker(70, 90, Color.newColor(Color.GREEN, 30));
        chart.setGrid(25, 25, 3, 2);
        
        
        // Axis style
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.WHITE, 12, AxisTextAlignment.CENTER);
        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(dates);
        xAxis.setAxisStyle(axisStyle);
        chart.addXAxisLabels(xAxis);
        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels("Time", 50.0);
        xAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.WHITE, 14, AxisTextAlignment.CENTER));
        chart.addXAxisLabels(xAxis2);
        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, greatest);
        yAxis.setAxisStyle(AxisStyle.newAxisStyle(Color.WHITE, 14, AxisTextAlignment.CENTER));
        yAxis.setAxisStyle(axisStyle);
        chart.addYAxisLabels(yAxis);
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels("Valu\nin\nUSD", 50.0);
        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.WHITE, 14, AxisTextAlignment.CENTER));
        yAxis2.setAxisStyle(axisStyle);
        chart.addYAxisLabels(yAxis2);
        
        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("363433"), 100);
        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
        chart.setAreaFill(fill);
		try {
			return new JLabel(new ImageIcon(ImageIO.read(new URL(chart.toURLString()))));
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static JLabel drawCompanyRankBarChart(TreeMap<String,LinkedList<String>> data){
		// EXAMPLE CODE START
        // Defining data plots.
		double[] plots = new double[data.size()];
		String[] labels = new String[data.size()];
		int i = 0;
		for(String key : data.keySet()){
			LinkedList<String> l = data.get(key);
			plots[i]=Integer.parseInt(l.get(0));
			labels[i]=l.get(2);
			//System.out.println(Integer.parseInt(l.get(0))+", "+l.get(2));
			i++;
		}

        // Instantiating chart.
        BarChart chart = GCharts.newBarChart(Plots.newBarChartPlot(DataUtil.scaleWithinRange(0, 10,plots), Color.BLUEVIOLET));

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels score = AxisLabelsFactory.newAxisLabels("Ranking", 1);
        score.setAxisStyle(axisStyle);
        AxisLabels year = AxisLabelsFactory.newAxisLabels("Company code", 1);
        year.setAxisStyle(axisStyle);

        // Adding axis info to chart.
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(labels));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 10));
        chart.addYAxisLabels(score);
        chart.addXAxisLabels(year);

        chart.setSize(600, 450);
        chart.setBarWidth(250/data.size());
        chart.setSpaceWithinGroupsOfBars(20);
        chart.setDataStacked(true);
        chart.setTitle("Company Ranking", Color.BLACK, 16);
        chart.setGrid(100, 10, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(Color.ALICEBLUE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.LAVENDER, 100);
        fill.addColorAndOffset(Color.WHITE, 0);
        chart.setAreaFill(fill);
		try {
			return new JLabel(new ImageIcon(ImageIO.read(new URL(chart.toURLString()))));
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static double convert(double value, String currencyFrom, String currencyTo) {
        return value*conversions.get(currencyFrom+currencyTo);
    }
	public static void updateConversions(JFrame frame) {
		System.out.print("Updating conversions....");
		HttpClient httpclient = new DefaultHttpClient();
		conversions = new HashMap<String,Double>();
		for(String currencyFrom:currencies){
		     HttpGet httpGet = new HttpGet("http://quote.yahoo.com/d/quotes.csv?s=" + currencyFrom + "USD" + "=X&f=l1&e=.csv");
		     ResponseHandler<String> responseHandler = new BasicResponseHandler();
		     try{
		     String responseBody = httpclient.execute(httpGet, responseHandler);
		     conversions.put(currencyFrom+"USD", Double.parseDouble(responseBody));
		     System.out.print(currencyFrom+"USD: " + Double.parseDouble(responseBody));
		     conversions.put("USD"+currencyFrom, 1.0/Double.parseDouble(responseBody));
		     System.out.print("..");
		     } catch (Exception e){
		    	 System.out.println("Connection lost! Exiting...");
		    	 JOptionPane.showMessageDialog(frame, "Connection to Yahoo not available.\nPlease check your internet connection!");
		     }
		}
		httpclient.getConnectionManager().shutdown();
		System.out.println("Done.");
    }
}
