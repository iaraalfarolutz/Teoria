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

static Imagen img;

public Histograma(String title, Imagen img) {
super(title);
JPanel chartPanel = crearPanel();
chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
setContentPane(chartPanel);
Histograma.img = img;
}

private static IntervalXYDataset crearDataset(double[] dist) {
HistogramDataset dataset = new HistogramDataset();
for (int i = 0; i< dist.length ; i++)
	if (dist[i] !=0)
	System.out.println("-distribucion del numero " + i+ ": "+ dist[i]);

dataset.addSeries("Frecuencias", dist , 15);
return dataset;
}

private static JFreeChart crearChart(IntervalXYDataset dataset) {
	JFreeChart chart = ChartFactory.createHistogram(
		"Histograma",
		null,
		null,
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
		ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Iara\\Desktop\\histograma.jpg"), chart, 500, 475);
		}
		catch(IOException e){
		System.out.println("Error al abrir el archivo");
		}
	return chart;
}

public static JPanel crearPanel() {
	System.out.println("+++++++++++++++++++++");
	double[] dist = Histograma.img.getDistribucion();
	//System.out.println("+++++++++++++++++++++");
	JFreeChart chart = crearChart(crearDataset(dist));
		return new ChartPanel(chart);
}
}