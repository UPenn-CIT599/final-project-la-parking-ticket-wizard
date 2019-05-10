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
 * Generating PieChart for Tickets by Day data using JFree chart. Each pie chart generation has a corresponding class to avoid JFreeChart random
 * exception throw issue which is known as JFreechart is not thread safe and has issues with large load Graph generation.
 * 
 * @author lukeshin
 *
 */

public class PieChartForTicketsByDayUsingJFreeChart extends ApplicationFrame {

	private HashMap<String, Integer> ticketByDay = new HashMap<String, Integer>();

	public PieChartForTicketsByDayUsingJFreeChart(String title, HashMap<String, Integer> curTicketByDay) {
		super(title);
		this.ticketByDay = curTicketByDay;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Pie chart generation class for Tickets by Day
	 * 
	 * @param curTicketByDay
	 */

	public void PieChartForTicketsByDay() {

		// Dataset Generation
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key : this.ticketByDay.keySet()) {
			dataset.setValue(key, this.ticketByDay.get(key));
		}

		// JFreeChart Call for PieChart and passing formats
		JFreeChart pieChart2 = ChartFactory.createPieChart("Tickets per Day", dataset, true, // legend
				true, // tool tip
				false // use to generate URL
		);

		// Formatting label
		PieSectionLabelGenerator labelGenerator2 = new StandardPieSectionLabelGenerator("{0} : ({2})",
				new DecimalFormat("0"), new DecimalFormat("0%"));
		((PiePlot) pieChart2.getPlot()).setLabelGenerator(labelGenerator2);

		// Chart panel generation
		ChartPanel panel2 = new ChartPanel(pieChart2);
		setContentPane(panel2);
		setSize(800, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		try {
			final ChartRenderingInfo chartInfo2 = new ChartRenderingInfo(new StandardEntityCollection());
			final File file2 = new File("PieChartForViolationByDay.png");
			ChartUtilities.saveChartAsPNG(file2, pieChart2, 800, 800, chartInfo2);
		} catch (Exception e) {
		}
	}
}
