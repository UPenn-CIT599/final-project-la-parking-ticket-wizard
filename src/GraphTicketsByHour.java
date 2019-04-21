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
	}
	
	public PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key : this.ticketByTime.keySet()) {
			dataset.setValue(key, this.ticketByTime.get(key) );
		}
		return dataset;
	}
	
	public JFreeChart createChart (PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(
				"Ticket by 3 Hour Intervals", 
				dataset, 
				true, //legend
				true, //tooltip
				false // use to generate URL
				);		
		return chart;		
	}
	
	public JPanel createGraphPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel( chart );
	}
	
	/* public static void main(String[] args) {		
		HashMapCreator hmc = new HashMapCreator("parking-citations_small.csv");
		ParkingTicketDataProcessor ptp = new ParkingTicketDataProcessor(hmc.parkingTicketRaw);
		HashMap<String, Integer> tcbh = ptp.ticketCountsByHour();
		ptp.ticketPrint();
		GraphTicketsByHour gtbh = new GraphTicketsByHour("Ticket Counts by 3 Hour Intervals", tcbh);
		gtbh.setSize(560, 367);
		RefineryUtilities.centerFrameOnScreen(gtbh);
		gtbh.setVisible(true);
	} */
	
	/*public GraphTicketsByHour (HashMap<String, Integer> curTicketByVioDescription) {
		//super(title);
		this.ticketByVioDescription = curTicketByVioDescription;
		setContentPane(createGraphPanel());
	}*/
	
}
