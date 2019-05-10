import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Bar Chart Creator Class using JFreeChart API. Saves two Bar Chart plots in png format.
 * @author lukeshin
 *
 */

public class BarChartCreatorUsingJFreeChart extends JFrame {
	
	/**
	 * Barchart generation class for violation description. 
	 */
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Method to create Bar Chart for Top 10 Parking Violation Ticket Descriptions in LA
	 * @param curViolationDescription
	 */
	public void BarChartForViolationDescription (HashMap<String, Integer> curViolationDescription) {
		
		// Dataset Generation		
		HashMap<String, Integer> ticketByVioDescription = curViolationDescription;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		ArrayList<String> sortedKeys = new ArrayList<String>(ticketByVioDescription.keySet());
		for (int i = 0; i < 10; i++) {
			String key = sortedKeys.get(i);
			dataset.addValue(ticketByVioDescription.get(key), "Violation Description", key);
		}
		CategoryDataset plotDataset = dataset;
		
		// JFreeChart Call for BarChart and passing formats
		JFreeChart barChart1 = ChartFactory.createBarChart(
				"Parking Tickets by Violation Description", 
				"Ticket Description", 
				"Violation Counts", 
				plotDataset, 
				PlotOrientation.VERTICAL, 
				false, //legend
				true, //tool tip
				false // use to generate URL
				);	
		
		// Turing label by 45 degree to show full label
		CategoryPlot cplot1 = barChart1.getCategoryPlot();
		CategoryAxis xAxis = (CategoryAxis)cplot1.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		
		//Chart panel generation
        ChartPanel chartPanel1 = new ChartPanel(barChart1);
        chartPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel1.setBackground(Color.white);
        add(chartPanel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        try {
        	final ChartRenderingInfo chartInfo1 = new ChartRenderingInfo(new StandardEntityCollection());
        	final File file3 = new File("BarChartForViolationDesc.png");
            ChartUtilities.saveChartAsPNG(file3, barChart1, 800, 800, chartInfo1);
        }
        catch (Exception e) {
        }
	}

	/**
	 * Method to create Bar Chart for Top 10 Parking Violation Ticket's Fines in LA
	 * @param curTicketByVioFine
	 * @param curSortedKeysByVioDesc
	 */
	public void BarChartForViolationFines (HashMap<String, Integer> curTicketByVioFine, ArrayList<String> curSortedKeysByVioDesc) {
		
		// Dataset Generation		
		HashMap<String, Integer> ticketByVioFine = curTicketByVioFine;
		ArrayList<String> sortedKeysByVioDesc = curSortedKeysByVioDesc;	
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();			
		for (int i = 0; i < 10; i++) {
			String key = sortedKeysByVioDesc.get(i);
			dataset.addValue(ticketByVioFine.get(key), "Violation Fine", key);
		}			
		CategoryDataset plotDataset = dataset;

		// JFreeChart Call for BarChart and passing formats
		JFreeChart barChart2 = ChartFactory.createBarChart(
				"Parking Tickets by Violation Fee", 
				"Ticket Description", 
				"Ticket Fee ($)", 
				plotDataset, 
				PlotOrientation.VERTICAL, 
				false, //legend
				true, //tool tip
				false // use to generate URL
				);	

		// Turing label by 45 degree to show full label
		CategoryPlot cplot2 = barChart2.getCategoryPlot();
		CategoryAxis xAxis = (CategoryAxis)cplot2.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		
		//Chart panel generation
        ChartPanel chartPanel2 = new ChartPanel(barChart2);
        chartPanel2.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel2.setBackground(Color.white);
        add(chartPanel2);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        try {
        	final ChartRenderingInfo chartInfo2 = new ChartRenderingInfo(new StandardEntityCollection());
        	final File file4 = new File("BarChartForViolationFee.png");
            ChartUtilities.saveChartAsPNG(file4, barChart2, 800, 800, chartInfo2);
        }
        catch (Exception e) {
        }
	}
}
