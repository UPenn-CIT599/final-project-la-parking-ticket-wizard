import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

public class BarChartForViolationDescription extends JFrame {
	
	/**
	 * Barchart generation class for violation description. TODO: needs to make it generic class 
	 * for plotting other data.
	 */
	private static final long serialVersionUID = 1L;
	private HashMap <String, Integer> ticketByVioDescription;
		
	public BarChartForViolationDescription (HashMap <String, Integer> curTicketByVioDescription) {
		// TODO Auto-generated constructor stub
		this.ticketByVioDescription = curTicketByVioDescription;
	
		CategoryDataset dataset = createDatasetForBarChart();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	} 
	
	public  CategoryDataset createDatasetForBarChart() {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		ArrayList<String> sortedKeys = new ArrayList<String>(this.ticketByVioDescription.keySet());

		for (int i = 0; i < 10; i++) {
			String key = sortedKeys.get(i);
			dataset.addValue(this.ticketByVioDescription.get(key), "Violation Description", key);
		}
				
		/*for (String key : this.ticketByVioDescription.keySet()) {
			if ((!this.ticketByVioDescription.containsValue(null)) && (this.ticketByVioDescription.get(key) > 1)) {
				//System.out.println("this is probing point");
				dataset.addValue(this.ticketByVioDescription.get(key), "Violation Description", key);
			}
		}*/
		return dataset;
	}
	
	public JFreeChart createChart (CategoryDataset dataset) {
		JFreeChart barChart = ChartFactory.createBarChart(
				"Ticket by Violation Description", 
				"Description", 
				"Violation Counts", 
				dataset, 
				PlotOrientation.VERTICAL, 
				false, //legend
				true, //tooltip
				false // use to generate URL
				);	
		
		return barChart;		
	}
	

}
