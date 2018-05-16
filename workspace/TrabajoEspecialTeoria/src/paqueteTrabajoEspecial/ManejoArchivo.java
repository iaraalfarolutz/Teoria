package paqueteTrabajoEspecial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ManejoArchivo {
	private String name;
	private String dimensiones;
	private String distribucion;
	

public ManejoArchivo(String name){
	this.name = name;
	this.dimensiones =new String();
	this.setDistribucion(new String());
}

public String getDimensiones(){
	return this.dimensiones;
}
	
private void setDimensiones(String str){
	this.dimensiones = str;
}
public int EscribirArchivo(String encabezado, String codificacion) throws IOException{
	//try {
		
		FileOutputStream fw = new FileOutputStream(name);
		ObjectOutputStream bw = new ObjectOutputStream(fw);
		bw.writeBytes(encabezado);
		for(char c : codificacion.toCharArray())
			try {
				bw.writeChar(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		bw.close();
		return codificacion.length();
}

@SuppressWarnings("deprecation")
public String LeerArchivo(int cant_chars) throws IOException{
	FileInputStream fis = new FileInputStream(name);
	ObjectInputStream ois = new ObjectInputStream(fis);
	int i = 0;
	char caracter;
	String salida = new String();
	this.setDimensiones(ois.readLine());
	this.setDistribucion(ois.readLine());
	while(i < cant_chars){
		caracter = ois.readChar();
		salida+=caracter;
		i++;
		}
	ois.close();
	return salida;
}

public String getDistribucion() {
	return distribucion;
}

private void setDistribucion(String distribucion) {
	this.distribucion = distribucion;
}

}