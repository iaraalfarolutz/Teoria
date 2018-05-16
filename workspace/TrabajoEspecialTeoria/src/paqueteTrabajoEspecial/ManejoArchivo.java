package paqueteTrabajoEspecial;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ManejoArchivo {
	private String name;
	

public ManejoArchivo(String name){
	this.name = name;
}
	
public int EscribirArchivo(String codificacion) throws IOException{
	//try {
		
		FileOutputStream fw = new FileOutputStream(name);
		ObjectOutputStream bw = new ObjectOutputStream(fw);
		for(char c : codificacion.toCharArray())
			try {
				bw.writeChar(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     /*FileOutputStream fos = new FileOutputStream(name);
	     Writer out = new OutputStreamWriter(fos, "UTF8");
    	 out.write(codificacion);
	     out.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
		  }*/
		bw.close();
		return codificacion.length();
}

public String LeerArchivo(int cant_chars) throws IOException{
	/*Path fileLocation = Paths.get(name);
	byte[] data = Files.readAllBytes(fileLocation);
	//String str = new String(data, StandardCharsets.UTF_8);
	return data;*/
	
	FileInputStream fis = new FileInputStream(name);
	ObjectInputStream ois = new ObjectInputStream(fis);
	int i = 0;
	char caracter;
	String salida = new String();
	while(i < cant_chars){
		caracter = ois.readChar();
		salida+=caracter;
		i++;
		}
	ois.close();
	return salida;
}

}