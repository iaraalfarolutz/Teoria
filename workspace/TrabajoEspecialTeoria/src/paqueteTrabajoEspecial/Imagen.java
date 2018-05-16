package paqueteTrabajoEspecial;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Imagen implements Comparable<Imagen> {

	private BufferedImage img=null;
	private int alto;
	private int ancho;
	private double factor;
	private String name;
	
public Imagen(String name){	
	try {
			this.img = ImageIO.read(new File(name));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
this.alto=img.getHeight();
this.ancho=img.getWidth();
this.factor= 1;
this.name = name;

}

public Imagen(String name, Imagen imgO){
	try {
		this.img = ImageIO.read(new File(name));
	} catch (IOException e) {
		System.out.println(e.getMessage());
	}
this.alto=img.getHeight();
this.ancho=img.getWidth();
this.factor= this.getFactorCorrelacion(imgO);
this.name =name;
}

public String getName(){
	return this.name;
}
	
public int getAlto(){
	return this.alto;
}

public int getAncho(){
	return this.ancho;
}

public double getFactor(){
	return this.factor;
}

public int getColor(int x, int y){
	int rgb = img.getRGB(x, y);
	Color color = new Color(rgb, true);
	return color.getRed();//como son tonos de gris los 3 valores rgb son iguales
}

public double getMedia(){
	double suma=0;
	for(int i=0; i< this.getAncho();i++)
		for(int j=0;j< this.getAlto();j++)
			 suma+= this.getColor(i, j);
	double resultado = suma / (this.getAlto()*this.getAncho());
	return resultado;
}

public double getDesvio(double media)
{
	double suma=0;
	for(int i=0; i< this.getAncho();i++)
		for(int j=0;j< this.getAlto();j++)
			{
			 suma+= Math.pow((this.getColor(i,j)-media), 2);
			}
	suma= suma/(this.getAlto()*this.getAncho()-1); /// la formula esta bien ?
	double resultado = (float) Math.sqrt(suma);
	return resultado;
}

private double getFactorCorrelacion(Imagen img1){
	double media1 = this.getMedia();
	double media2 = img1.getMedia();
	double covarianza = this.getMediaCruz(img1) - (media1 * media2);
	double desvios = this.getDesvio(media1) * img1.getDesvio(media2);
	return covarianza / desvios;
}

public double getMediaCruz(Imagen img1){
	double suma=0;
	for(int i=0; i< this.getAncho();i++)
		for(int j=0;j< this.getAlto();j++)
			 suma+= this.getColor(i, j) * img1.getColor(i, j);
	double resultado = suma / (this.getAlto()*this.getAncho());
	return resultado;
}

public int compareTo(Imagen img1){
	if ( this.getFactor() < img1.getFactor())
		return 1;
	else
		return -1;
}

public double[] getDistribucion(){
	double[] vec = new double[256];
	for ( int i=0 ; i< this.getAncho() ; i++ )
		for ( int j =0; j< this.getAlto() ; j++)
			vec[this.getColor(i, j)]++;
	return vec;
	
}


public double[] getPixeles(){ //el arreglo gigante que le pasas al histograma (pide un arreglo el metodo)
	double[] vec = new double[(this.getAlto())*this.getAncho()];
	for ( int i=0 ; i< this.getAncho() ; i++ )
		for ( int j =0; j< this.getAlto() ; j++)
			vec[i*this.getAlto()+j]= this.getColor(i, j);
	return vec;
}

public String generarEncabezado(double[] dist){
	int alto = this.getAlto();
	int ancho = this.getAncho();
	String aux1 = String.valueOf(alto)+","+String.valueOf(ancho)+'\n';
	String aux2="";
	for(int i = 0; i < dist.length; i++)
		aux2+=String.valueOf(i)+","+String.valueOf(dist[i])+";";
	return aux1+aux2+'\n';
}

}