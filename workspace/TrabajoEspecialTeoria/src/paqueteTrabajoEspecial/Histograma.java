package paqueteTrabajoEspecial;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;

public class Histograma extends ApplicationFrame {

private static final long serialVersionUID = 1L;

private static double[] dist;

public Histograma(String title, double[] dist, String ruta, String titulo) {
super(title);
Histograma.dist = dist;
JPanel chartPanel = crearPanel(Histograma.dist, ruta, titulo);
chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
setContentPane(chartPanel);
}

private static IntervalXYDataset crearDataset(double[] dist) {
HistogramDataset dataset = new HistogramDataset();
/*for (int i = 0; i< dist.length ; i++)
	if (dist[i] !=0)
	System.out.println("-distribucion del numero " + i+ ": "+ dist[i]);
*/
dataset.addSeries("Frecuencias", dist , 16);
return dataset;
}

private static JFreeChart crearChart(IntervalXYDataset dataset, String ruta, String titulo) {
	JFreeChart chart = ChartFactory.createHistogram(
		titulo,
		"Tono de gris",
		"Frecuencia",
		dataset,
		PlotOrientation.VERTICAL,
		true,
		true,
		false
		);
	XYPlot plot = (XYPlot) chart.getPlot();
	XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
	renderer.setDrawBarOutline(false);
		try{
		ChartUtilities.saveChartAsJPEG(new File(ruta), chart, 500, 475);//SACAR
		}
		catch(IOException e){
		System.out.println("Error al abrir el archivo");
		}
	return chart;
}

public static JPanel crearPanel(double[] dist, String ruta, String titulo) {
	//System.out.println("+++++++++++++++++++++");
	JFreeChart chart = crearChart(crearDataset(dist), ruta, titulo);
		return new ChartPanel(chart);
}
}