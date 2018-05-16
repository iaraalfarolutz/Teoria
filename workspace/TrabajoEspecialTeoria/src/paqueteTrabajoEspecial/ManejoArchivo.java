package paqueteTrabajoEspecial;

import java.io.BufferedReader;
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

public class ManejoArchivo {
	private String name;
	

public ManejoArchivo(String name){
	this.name = name;
}
	
public void EscribirArchivo(String codificacion){
	try {
	     FileOutputStream fos = new FileOutputStream(name);
	     Writer out = new OutputStreamWriter(fos, "UTF8");
    	 out.write(codificacion);
	     out.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
		  }
}

public byte[] LeerArchivo() throws IOException{
	Path fileLocation = Paths.get(name);
	byte[] data = Files.readAllBytes(fileLocation);
	//String str = new String(data, StandardCharsets.UTF_8);
	return data;
}

}