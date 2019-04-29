import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.*;


public class GraphTicketsByHour extends ApplicationFrame {
	
	/**
	 * Piechart generation class for Tickets by Hour
	 */
	private static final long serialVersionUID = 1L;
	private HashMap <String, Integer> ticketByTime;
		
	public GraphTicketsByHour (String title, HashMap<String, Integer> curTicketByTime) {
		super(title);
		this.ticketByTime = curTicketByTime;
		setContentPane(createGraphPanel());
		//RefineryUtilities.centerFrameOnScreen(createGraphPanel());		

		setVisible(true);
	}
	
	public PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key : this.ticketByTime.keySet()) {
			if (this.ticketByTime.get(key) >= 100000) {
				dataset.setValue(key, this.ticketByTime.get(key) );
			}
		}
		return dataset;
	}
	
	public JFreeChart createChart (PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(
				"Ticket by Hour Intervals", 
				dataset, 
				true, //legend
				true, //tool tip
				false // use to generate URL
				);		
		return chart;		
	}
	
	public JPanel createGraphPanel() {
		JFreeChart chart = createChart(createDataset());
		setSize(560, 367);
		return new ChartPanel(chart);
	}
}
