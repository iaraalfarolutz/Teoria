package paqueteTrabajoEspecial;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
			this.img = ImageIO.read(getClass().getResource(name));
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
		this.img = ImageIO.read(getClass().getResource(name));
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

private double getMediaCruz(Imagen img1){
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

public int[] getDistribucion(){
	int[] vec = new int[256];
	for ( int i=0 ; i< this.getAncho() ; i++ )
		for ( int j =0; j< this.getAlto() ; j++)
			vec[this.getColor(i, j)]++;
	return vec;
}


}
//Clase
//histograma cuento cuantos 50 tengo, cuantos 45... y armo.
//no guardar el codificado a nivel string, guardar a nivel bit.
//eliminar bits de un operando. And con una mascara(1 lo que uiqero que se quede, 0 lo que quiero que se vaya) o con or al reves. Sirve para averiguar el valor de un bit
//en una posicion tambien(mask en 0 y 1s)
//desplazamento 0011 <<2 -> 1100
//1100 >>1 -> 011
//PARA COMPRIMIR 
// proceso cada simbolo
//recupero codificacion del simbolo(como secuencia de bits)
//empaquetAR bits en un char. haciendo corrimientos y usando operaciones lógicas-CHAR:8 bits (en java capaz 16)
//en un char puedo guardar simbolos a nivel bits
//cuando termino de guardar todo guardo en un archivo .txt o lo que sea. Si lo abro va a haber simbolos raros

//En un vestor de string llamado mensajes meto los simbolos(String) en nuestro caso el valor del r
//Tengo hash con string, valor del simbolo, y String[] con codificacion. Cargo hash
//como resultado de generar toda la codificacion tengo UN string (secuencia de char)
//GENERAR CODIF
//resultado en vacio
//char en 0 (buffer)
//int cant digitos en 0
//for string en mensaje
//en String[] = hash.get.simbolo
//for string en codigo ->bit
//buffer = (char)(buffer << 1)
//if bit = 1
//	buffer = (char) (buffer |1)
//cantdigitos++
//if cantdigitos = 16 
//rsultado +=uffer
//cantdigitos=0
//buffer=0;

//PARA HISTOGRAMA JFREECHART

//termino de analizar todos los simbolos
//Para que no quede basura al final
//if cant_digitos<16 && cantdigitos!=0
//buffer = (char) (buffer <<(16 - cant_digitos))
//resultado+=buffer;
//return resultado (afuera). Lo guardo en un archivo. Y despues tengo qu levantarlo y decod
//CUANDO LEA TENGO QUE LEER HASTA CANTIDAD DE SIMBOLOS

//DECODIFICAR
// for(char c : resultado.tocharArry())
//Codificacion.generarbits


//GENERAR BITS
//CHAR MASCARA = 1<<15
/*for(int i=0; i<16;i++)
		if(num&mascara==32768)
			sysout 1
		else
				sysout 0
		 num = (char) (num <<1 )*/

//Puedo usar char o int o lo que quiera. ES mejor char

//Despues tengo que generar imagen. Recupero decodificacion y recorro imagen y e este pixel pone este color
//ENTREGAR archivo codificado.txt y la imagen decodificada(la imagen hay que levatarla del disco)