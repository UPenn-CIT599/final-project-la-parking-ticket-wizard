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
 * Class to create a Barchart showing the most common top10 violation ticket types.
 * @author lukeshin
 *
 */

public class BarChartForTicketsByTop10Violations extends JFrame {
	

	private static final long serialVersionUID = 1L;	
	HashMap<String, Integer> ticketByVioDescription;
	
	/**
	 * Constructor taking HashMap of most common violation types from ParkingTicketDataProcessor class.
	 * @param curViolationDescription
	 */
	
	public BarChartForTicketsByTop10Violations (HashMap<String, Integer> curViolationDescription) {
		this.ticketByVioDescription = curViolationDescription;	
	}
	
	/**
	 * Method to create Bar Chart for Top 10 Parking Violation Ticket Descriptions in LA
	 * @param curViolationDescription
	 */
	public void BarChartForViolationDescription() {
		
		// Dataset Generation		
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

}
