package paqueteTrabajoEspecial;

import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import org.jfree.ui.RefineryUtilities;
import java.util.HashMap;
import java.util.Collections;

public class TPE {

	public static void main(String[] args) throws IOException {
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
		double[] dist = vimg.elementAt(3).getDistribucion();
		double [] distImagenO = imgO.getPixeles();
		double [] distImagen1 = vimg.elementAt(0).getPixeles();
		double [] distImagen6 = vimg.elementAt(6).getPixeles();
		double [] distImagen4 = vimg.elementAt(3).getPixeles();//prueba
		
		Histograma histo = new Histograma("Histograma", distImagenO, "Histograma_Original.jpg", "Imagen Original");
		//histo.pack();
		//RefineryUtilities.centerFrameOnScreen(histo);
		//histo.setVisible(true);
		Histograma histParecido = new Histograma("Histograma", distImagen1, "Histograma_Parecido.jpg", "Imagen mas similar");
		Histograma histMenosParecido = new Histograma("Histograma", distImagen6, "Histograma_Menos_Parecido.jpg", "Imagen menos similar");		
		Huffman prueba = new Huffman(dist);
		HashMap<Integer, Vector<String>> cod= prueba.getCodificacion();		
		ManejoArchivo archivo = new ManejoArchivo("codificacionwill4.txt");
		String codificacion = prueba.getMensajeCodificado(distImagen4, cod);
		String encabezado = vimg.elementAt(3).generarEncabezado(dist);
		int cantidad = archivo.EscribirArchivo(encabezado, codificacion);
		System.out.println("TERMINO DE ESCRIBIR EN ARCHIVO"+System.currentTimeMillis());   
        String leido = archivo.LeerArchivo(cantidad);
        String dimensiones=archivo.getDimensiones();
        String distribu=archivo.getDistribucion();
        System.out.println(dimensiones);
        System.out.println(distribu);
        
        int alto = prueba.getAlto(dimensiones);
        int ancho = prueba.getAncho(dimensiones); 
        double[] distDecod=prueba.getDistDecodificado(distribu);
        prueba.setVechuff(distDecod);
        
        String mRecuperado = prueba.recuperarMensaje(leido);
        System.out.println("termino de decodificar"+System.currentTimeMillis());
        //System.out.println(mRecuperado);
       
        int[] imagen = prueba.obtenerImagen(mRecuperado,ancho,alto, prueba.getHuffmanTree());
        BufferedImage img = map(ancho, alto, imagen);
        saveBMP( img, "imagenes/ImagenDecodificada4.bmp" );
        
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
	 
	 private static void saveBMP( final BufferedImage bi, final String path ){
	        try {
	            RenderedImage rendImage = bi;
	            ImageIO.write(rendImage, "bmp", new File(path));
	        } catch ( IOException e) {
	            e.printStackTrace();
	        }
	    }
	 }
