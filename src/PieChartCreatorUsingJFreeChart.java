import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.*;

/**
 * Pie Chart Create Class using JFreeChart API. Save two Pie Chart plots in png format.
 * @author lukeshin
 *
 */

public class PieChartCreatorUsingJFreeChart extends ApplicationFrame {
	
	public PieChartCreatorUsingJFreeChart(String title) {
		super(title);
	}

	private static final long serialVersionUID = 1L;
	
	/**
	 * Piechart generation class for Tickets by Hour
	 * @param curTicketByHour
	 */
	public void PieChartForTicketsByHour (HashMap<String, Integer> curTicketByHour) {
		
		// Dataset Generation		
		HashMap <String, Integer> ticketByTime = curTicketByHour;
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key : ticketByTime.keySet()) {
			if (ticketByTime.get(key) >= 123000) {
				dataset.setValue(key, ticketByTime.get(key) );
			}
		}
		
		// JFreeChart Call for PieChart and passing formats
		JFreeChart pieChart1 = ChartFactory.createPieChart(
				"Tickets per Hour Interval", 
				dataset, 
				true, //legend
				true, //tool tip
				false // use to generate URL
				);
		
	    //Formatting label  
	    PieSectionLabelGenerator labelGenerator1 = new StandardPieSectionLabelGenerator(  
	        "{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));  
	    ((PiePlot) pieChart1.getPlot()).setLabelGenerator(labelGenerator1);
	      
	    // Chart panel generation 
	    ChartPanel panel1 = new ChartPanel(pieChart1);  
	    setContentPane(panel1);
		setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//RefineryUtilities.centerFrameOnScreen(createGraphPanel());		
		setVisible(true);
		try {
        	final ChartRenderingInfo chartInfo1 = new ChartRenderingInfo(new StandardEntityCollection());
        	final File file1 = new File("PieChartForViolationByHour.png");
            ChartUtilities.saveChartAsPNG(file1, pieChart1, 800, 800, chartInfo1);
        }
        catch (Exception e) {
        }
	}
	
	/**
	 * Pie chart generation class for Tickets by Day
	 * @param curTicketByDay
	 */
	public void PieChartForTicketsByDay (HashMap<String, Integer> curTicketByDay) {
		
		// Dataset Generation		
		HashMap <String, Integer> ticketByDay = curTicketByDay;
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key : ticketByDay.keySet()) {
			dataset.setValue(key, ticketByDay.get(key) );
		}
		
		// JFreeChart Call for PieChart and passing formats
		JFreeChart pieChart2 = ChartFactory.createPieChart(
				"Tickets per Day", 
				dataset, 
				true, //legend
				true, //tool tip
				false // use to generate URL
				);
		
	    //Formatting label  
	    PieSectionLabelGenerator labelGenerator2 = new StandardPieSectionLabelGenerator(  
	        "{0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));  
	    ((PiePlot) pieChart2.getPlot()).setLabelGenerator(labelGenerator2);  
	      
	    // Chart panel generation 
	    ChartPanel panel2 = new ChartPanel(pieChart2);  
	    setContentPane(panel2);
		setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//RefineryUtilities.centerFrameOnScreen(createGraphPanel());		
		setVisible(true);
		try {
        	final ChartRenderingInfo chartInfo2 = new ChartRenderingInfo(new StandardEntityCollection());
        	final File file2 = new File("PieChartForViolationByDay.png");
            ChartUtilities.saveChartAsPNG(file2, pieChart2, 800, 800, chartInfo2);
        }
        catch (Exception e) {
        }
		
	}

}
