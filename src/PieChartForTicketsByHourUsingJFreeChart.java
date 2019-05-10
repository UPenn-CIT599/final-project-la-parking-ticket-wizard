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
 * Generating PieChart for Tickets by Hour data using JFree chart. Each pie chart generation has a corresponding class to avoid JFreeChart random
 * exception throw issue which is known as JFreechart is not thread safe and has issues with large load Graph generation.
 * @author lukeshin
 *
 */

public class PieChartForTicketsByHourUsingJFreeChart extends ApplicationFrame {
	
		private HashMap<Integer, ParkingTickets> parkingTicketsRaw;
		private String[] timeByHour = new String[24];
		
		/**
		 * Constructor to receive chart title and HashMap data. It also defines 24hour time keys.
		 * @param title
		 * @param curParkingTicketsData
		 */
		public PieChartForTicketsByHourUsingJFreeChart(String title, HashMap<Integer, ParkingTickets> curParkingTicketsData) {
			super(title);
			this.parkingTicketsRaw = curParkingTicketsData;
			for (int i = 0; i < 24; i++) {
				String time = i + ":00-" + (i + 1) + ":00";
				timeByHour[i] = time;
			}
		}

		private static final long serialVersionUID = 1L;
		
		/**
		 * Piechart generation class for Tickets by Hour
		 * @param curTicketByHour
		 */
		public void PieChartForTicketsByHour () {
			
			//Dataset Generation		
			
			HashMap<String, Integer> ticketCountsByTime = new HashMap<String, Integer>();
			DefaultPieDataset dataset = new DefaultPieDataset();

			for (Integer currentTicket : parkingTicketsRaw.keySet()) {
				int ticketTime = this.parkingTicketsRaw.get(currentTicket).getIssueTime();
				for (int i = 0; i < 24; i++) {
					if ((ticketTime >= i * 100) && (ticketTime < (i + 1) * 100)) {
						int ticketCount = ticketCountsByTime.containsKey(timeByHour[i])
								? ticketCountsByTime.get(timeByHour[i]) : 0;
						ticketCount = ticketCount + 1;
						ticketCountsByTime.put(timeByHour[i], ticketCount);
						if(ticketCount >= 123000) {
							dataset.setValue(timeByHour[i], ticketCount );
						}
					}
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
}
