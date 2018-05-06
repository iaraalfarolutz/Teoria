package paqueteTrabajoEspecial;

import java.util.Vector;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import java.util.Collections;
//import org.jfree.chart.*;
//import org.jfree.data.xy.IntervalXYDataset;

public class TPE {

	public static void main(String[] args) throws IOException {
		//preguntar que onda lo de path relativo y resources
		//C:\\Users\\Iara\\workspace\\TrabajoEspecialTeoria\\src\\paqueteTrabajoEspecial\\Will_7.bmp
		Imagen imgO = new Imagen("Will(Original).bmp");
		Vector<Imagen> vimg = new Vector<Imagen>();
		vimg.add(new Imagen("Will_1.bmp", imgO));
		vimg.add(new Imagen("Will_2.bmp", imgO));
		vimg.add(new Imagen("Will_3.bmp", imgO));
		vimg.add(new Imagen("Will_4.bmp", imgO));
		vimg.add(new Imagen("Will_5.bmp", imgO));
		vimg.add(new Imagen("Will_6.bmp", imgO));
		vimg.add(new Imagen("Will_7.bmp", imgO));
		Collections.sort(vimg);
		//double[] dist = imgO.getDistribucion();
		/*for (int i=0; i < vimg.size() ; i++)
			System.out.println("Imagen " + vimg.elementAt(i).getName() + " factor de correlación respecto de la imagen original " + vimg.elementAt(i).getFactor());
		
		for (int i = 0; i< dist.length ; i++)
			if (dist[i] !=0)
			System.out.println("-distribucion del numero " + i+ ": "+ dist[i]);*/
		//comprobado ta bien la distribucion
	
		Histograma histo = new Histograma("Histograma", imgO);
		histo.pack();
		RefineryUtilities.centerFrameOnScreen(histo);
		histo.setVisible(true);
		
	}
	
}
