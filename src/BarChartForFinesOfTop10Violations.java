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
 * Class to create a Barchart showing the fee for most common top10 violation ticket types.
 * @author lukeshin
 *
 */

public class BarChartForFinesOfTop10Violations extends JFrame {
	
	private static final long serialVersionUID = 1L;
	HashMap<String, Integer> ticketByVioFine;
	ArrayList<String> sortedKeysByVioDesc;	
	
	/**
	 * Constructor receiving fines for 10 most common violation types.
	 * @param curTicketByVioFine
	 * @param curSortedKeysByVioDesc
	 */
	public BarChartForFinesOfTop10Violations(HashMap<String, Integer> curTicketByVioFine, ArrayList<String> curSortedKeysByVioDesc) {
		this.ticketByVioFine = curTicketByVioFine;
		this.sortedKeysByVioDesc = curSortedKeysByVioDesc;
	}

	/**
	 * Method to create Bar Chart for Top 10 Parking Violation Ticket's Fines in LA
	 * @param curTicketByVioFine
	 * @param curSortedKeysByVioDesc
	 */
	public void BarChartForViolationFines() {
		
		// Dataset Generation		

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
