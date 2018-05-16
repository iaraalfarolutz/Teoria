package paqueteTrabajoEspecial;
import java.util.Vector;
import java.util.Collections;
import java.util.HashMap;

public class Huffman {
	private Vector<Arbin> vechuff;
	
public Huffman(double[] dist){
	this.setVechuff(dist); //una vez creado un objeto huffman ya tiene el vector de distribucion ordenado
}

public void setVechuff(double[] dist){ //crea el vector de distribución
	this.vechuff = new Vector<Arbin>();
	for (int i = 0; i< dist.length ; i++ )
		if(dist[i] > 0){
			Arbin a = new Arbin(i, dist[i]);
			this.vechuff.add(a);
		}
Collections.sort(vechuff);
}

public Vector<Arbin> getVechuff(){
	return this.vechuff;
}


public HashMap<Integer, Vector<String>> getCodificacion(){ // devuele el hash de simbolo/codigo binario
	Arbin aux = this.getHuffmanTree();
	HashMap<Integer, Vector<String>> cod = new HashMap<Integer, Vector<String>>();
	Vector<String> s = new Vector<String>();
	return this.getCode(cod, aux, s);
}

private HashMap<Integer, Vector<String>> getCode (HashMap<Integer, Vector<String>> cod, Arbin a, Vector<String> s){ //recursion s es el string auxiliar , cod es la solución y arbin es el arbol de huffman
	if(a!= null){
		if(a.esHoja()){
			Vector<String> aux = new Vector<String>();
			aux.addAll(s);
			cod.put(a.getSimbolo(), aux);	
		}
		else{
			//0 derecha 1 izquierda
			s.add("0");
			getCode(cod, a.getDer(), s );
			s.removeElementAt(s.size()-1);
			s.add("1");
			getCode(cod, a.getIzq(), s);
			s.removeElementAt(s.size()-1);
		}
	}	
	return cod;
}

public Arbin getHuffmanTree(){ // devuele el arbol de huffman , utiliza el vector el vector de la clase "arbhuff"
	while ( this.vechuff.size() > 1 ){
		Arbin a1 = vechuff.elementAt(vechuff.size()-1);
		Arbin a2 = vechuff.elementAt(vechuff.size()-2);
		Arbin aux= new Arbin (-1,a1.getProb()+a2.getProb());
		aux.insertarDer(a1);
		aux.insertarIzq(a2);
		vechuff.removeElementAt(vechuff.size()-1);
		vechuff.removeElementAt(vechuff.size()-1);
		vechuff.add(aux);
		Collections.sort(vechuff);
	}
	return vechuff.elementAt(0);
}
//cambiar nombre a mensaje codificado

public String getMensajeCodificado(double[] mensaje, HashMap<Integer, Vector<String>> cod){ //dado el mensaje(imagen) y el hash de codificación devuelve el mensaje en binario
char buffer = '0';
int digitos=0;
String resultado="";
for (int i = 0; i < mensaje.length ; i++){
	Vector<String> codigo = cod.get((int)mensaje[i]);
	for(String bit : codigo){
		buffer = (char) (buffer << 1) ;
		if(bit =="1")
			buffer= (char) (buffer | 1);
		digitos++;
		if(digitos ==16){
			//System.out.println(buffer);
			resultado+=buffer;
			buffer = '0';
			digitos=0;
		}
	}
}
if((digitos<16) && (digitos!=0)){
	buffer = (char) (buffer << (16-digitos));
	resultado+=buffer;
}
return resultado;//guardarlo en una variable y comprobar con el decodificacion
}



public double[] getDistDecodificado(String encabezado){ //devuelve el arreglo de distribuciones a partir de los datos del txt.
	double[] aux = new double[256];
	String[] s = encabezado.split(";");
	for (int i=0 ; i< s.length; i++){
		  String[] celda = s[i].split(",");
		  int indice = Integer.parseInt(celda[0]);
		  double frec = Double.parseDouble(celda[1]);
		  aux[indice]=frec;
	}
	return aux;
	
}

private String generarBits(char a){
	String res="";
	char mask = 1<<15;
	for(int i=0 ; i<16 ; i++){
		if((a&mask) == 32768)
			res+="1";
		else
			res+="0";
	a = (char)(a<<1);
	}	
	return res;
}

public String recuperarMensaje(String mensaje){ //devuelve un string con el mensaje original en binario
	String aux="";
	for(char a : mensaje.toCharArray())
		aux+=this.generarBits(a);
	return aux;
}

public int[] obtenerImagen(String mensaje, int ancho, int alto, Arbin a){ //a partir del mensaje en binario obtiene un arreglo con los rgb de la imagen original
	int[] img = new int[ancho*alto];
	int i=0;
	Vector<Character> vmsj = new Vector<Character>();
	for(char c : mensaje.toCharArray())
		vmsj.add(c);
	while(i<img.length-1){
		obtenerImagenRecur(img, vmsj, a, i);
		i++;
		System.out.println(i);
	}
	
	return img;
}

private void obtenerImagenRecur(int[] img, Vector<Character> mensaje, Arbin a, int i){
	if(!a.esHoja()){
		if(mensaje.elementAt(0)=='0'){
			mensaje.remove(0);
			obtenerImagenRecur(img,mensaje, a.getDer(), i);
		}
		else{
			mensaje.remove(0);
			obtenerImagenRecur(img, mensaje, a.getIzq(), i);
		}
	}
	else{
		img[i]=a.getSimbolo();
	}
		
}

public int getAlto(String dimensiones){
	String[] aux = dimensiones.split(",");
	return Integer.parseInt(aux[0]);	
}

public int getAncho(String dimensiones){
	String[] aux = dimensiones.split(",");
	return Integer.parseInt(aux[1]);	
}
}


/*public Vector<Byte> getMensajeCodificado(double[] mensaje, HashMap<Integer, Vector<String>> cod){ //imrpimimos en el archivo aca
	byte buffer =0;
	int digitos=0;
	Vector<Byte >resultado= new Vector<Byte>();
	for (int i = 0; i < mensaje.length ; i++){
		Vector<String> codigo = cod.get((int)mensaje[i]);
		for(String bit : codigo){
			buffer = (byte) (buffer << 1) ;
			if(bit =="1")
				buffer= (byte) (buffer | 1);
			digitos++;
			if(digitos ==8){
				//System.out.println(buffer);
				resultado.addElement(buffer);
				buffer = 0;
				digitos=0;
			}
		}
	}
	if((digitos<8) && (digitos!=0)){
		buffer = (byte) (buffer << (8-digitos));
		resultado.addElement(buffer);
	}
	return resultado;//guardarlo en una variable y comprobar con el decodificacion
}
*/


