package paqueteTrabajoEspecial;

import java.util.Vector;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import org.jfree.ui.RefineryUtilities;
import java.util.HashMap;
import java.util.Collections;

public class TPE {

	public static void main(String[] args) throws IOException {
		//preguntar que onda lo de path relativo y resources
		//C:\\Users\\Iara\\workspace\\TrabajoEspecialTeoria\\src\\paqueteTrabajoEspecial\\Will_7.bmp
		Imagen imgO = new Imagen("imagenes/Will(Original).bmp");
		Vector<Imagen> vimg = new Vector<Imagen>();
		vimg.add(new Imagen("imagenes/Will_1.bmp", imgO));
		vimg.add(new Imagen("imagenes/Will_2.bmp", imgO));
		vimg.add(new Imagen("imagenes/Will_3.bmp", imgO));
		vimg.add(new Imagen("imagenes/Will_4.bmp", imgO));
		vimg.add(new Imagen("imagenes/Will_5.bmp", imgO));
		vimg.add(new Imagen("imagenes/Will_6.bmp", imgO));
		vimg.add(new Imagen("imagenes/Will_7.bmp", imgO));
		Collections.sort(vimg);
		for(Imagen i: vimg){
			String[] s = i.getName().split("/");
			System.out.println("La imagen "+ s[1] +" tiene un factor de correlacion  = "+ i.getFactor());
		}
		double[] dist = imgO.getDistribucion();
		double [] distImagenO = imgO.getPixeles();
		double [] distImagen1 = vimg.elementAt(0).getPixeles();
		double [] distImagen6 = vimg.elementAt(6).getPixeles();
		
		Histograma histo = new Histograma("Histograma", distImagenO, "Histograma_Original.jpg", "Imagen Original");
		//histo.pack();
		//RefineryUtilities.centerFrameOnScreen(histo);
		//histo.setVisible(true);
		
		Histograma histParecido = new Histograma("Histograma", distImagen1, "Histograma_Parecido.jpg", "Imagen mas similar");
		Histograma histMenosParecido = new Histograma("Histograma", distImagen6, "Histograma_Menos_Parecido.jpg", "Imagen menos similar");		
		Huffman prueba = new Huffman(dist);
		HashMap<Integer, Vector<String>> cod= prueba.getCodificacion();		
		ManejoArchivo archivo = new ManejoArchivo("holis.txt");
		String codificacion = prueba.getMensajeCodificado(distImagenO, cod);
		archivo.EscribirArchivo(codificacion);
		System.out.println("TERMINO DE ESCRIBIR EN ARCHIVO"+System.currentTimeMillis());
		String encabezado = imgO.generarEncabezado(dist);
		
		//usar OutputStreamWriter usar el mismo, o buffered output stream
		
		//ESCRIBO en el archivo de los caracteres de la codificación
		
        //File archivo = new File(ruta);
        
       /* OutputStream out = new OutputStream(new File(ruta));
        out.write(encabezado.getBytes(StandardCharsets.UTF_8));
        out.write(codificacion.getBytes(StandardCharsets.UTF_8));
        out.close();*/
        //saveFile(archivo, str);
        
       /* BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(encabezado);
            bw.write(codificacion);
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(encabezado);
            bw.write(codificacion);
        }
        bw.close();*/
              
        //LEO DEL DISCO LA DECODIFICACION
        char LF = (char) 00001010;
        /* char CR = (char) 0x000D;
        
        String entrada = str.replaceAll(String.valueOf(CR),"CACA");
        String in = entrada.replaceAll(String.valueOf(LF), "CACA2");*/
        
        //en vez de str me fijo que da si imprimo salto de linea
        
             
		//writeOutput(encabezado, a);
        /*
        String a = String.valueOf(CR);
        writeOutput(a);*/
        
        String dimensiones="";
        String distribu="";
        
        //String pruebaRecupera = readInput();
        
        /*String out = pruebaRecupera.replaceAll("CACA", String.valueOf(CR));
        String line = out.replaceAll("CACA2", String.valueOf(LF));*/

       /* BufferedReader reader = null;
        try {
            //File file = new File(ruta);
            //reader = new BufferedReader(new FileReader(file));
            reader = new BufferedReader( new InputStreamReader( new FileInputStream("test.txt"), "UTF8"));
            int cont=0;
            String aux;
            while ((aux =reader.readLine()) != null) {
            	if (cont==0)
            		dimensiones = aux;
            	else {if (cont == 1)
            			distribu = aux;
            		else{
            			line+=aux;//+'\n';//+String.valueOf(LF);
            			//System.out.println(aux);
            		}
            	}
            	
            	cont++;
            }
            System.out.println("++++++++++"+ line.length());
            System.out.println("++++++++++"+ codificacion.length());
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        int alto = prueba.getAlto(dimensiones);
        int ancho = prueba.getAncho(dimensiones);   */   
        //double[] distDecod=prueba.getDistDecodificado(distribu);
        //prueba.setVechuff(distDecod);
       // byte[] line = archivo.LeerArchivo();
        
        String mRecuperado = prueba.recuperarMensaje(codificacion);
        System.out.println("termino de decodificar"+System.currentTimeMillis());
        //System.out.println(mRecuperado);
       
        int[] imagen = prueba.obtenerImagen(mRecuperado, imgO.getAncho(), imgO.getAlto(), prueba.getHuffmanTree());
        BufferedImage img = map( imgO.getAncho(), imgO.getAlto(), imagen);
        savePNG( img, "imagenes/ImagenDecodificada.bmp" );
        
       //GUARDAR IMAGEN DECODIFICADA   
        System.out.println("FIN prog"+ System.currentTimeMillis()); 
       // System.exit(0);
}
	
	 private static BufferedImage map( int sizeX, int sizeY , int[] imagen){
         final BufferedImage res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
         for (int x = 0; x < sizeX; x++){
             for (int y = 0; y < sizeY; y++){
            	 int p = (imagen[x*sizeY+y]<<16)|(imagen[x*sizeY+y]<<8)|imagen[x*sizeY+y];
                 res.setRGB(x, y, p);
             }
         }
         return res;
     }
	 
	 private static void savePNG( final BufferedImage bi, final String path ){
	        try {
	            RenderedImage rendImage = bi;
	            ImageIO.write(rendImage, "bmp", new File(path));
	            //ImageIO.write(rendImage, "PNG", new File(path));
	            //ImageIO.write(rendImage, "jpeg", new File(path));
	        } catch ( IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("++++++++++++++++++++++");
	    }
	 
	 public static void saveFile(File f, final String str) {
		    try {
		      PrintStream p = new PrintStream(new FileOutputStream(f, false));
		      p.println(str);
		      p.close();

		    } catch (Exception e) {
		      e.printStackTrace();
		      System.err.println(f);
		    }
		  }
	 
public static void writeOutput(String str, byte[] body) {
	try {
	     FileOutputStream fos = new FileOutputStream("test.txt");
	     Writer out = new OutputStreamWriter(fos, "UTF8");
	     out.write(str);
	     for(byte a : body)
	    	 out.write(a);
	     out.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
		  }
}

 public static String readInput() {
 StringBuffer buffer = new StringBuffer();
    try {
      FileInputStream fis = new FileInputStream("test.txt");
      InputStreamReader isr = new InputStreamReader(fis, "UTF8");
      Reader in = new BufferedReader(isr);
      int ch;
      while ((ch = in.read()) > -1) {
        buffer.append((char) ch);
      }
      in.close();
      return buffer.toString();
    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    }
	  }
	
}
