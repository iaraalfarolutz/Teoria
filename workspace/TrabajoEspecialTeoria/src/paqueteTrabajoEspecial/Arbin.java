package paqueteTrabajoEspecial;

public class Arbin implements Comparable<Arbin>{
private int simbolo;
private double prob;
private Arbin derecho;
private Arbin izquierdo;


public Arbin(int simbolo,double prob){
	this.setSimbolo(simbolo);
	this.setProb(prob);
	this.derecho=null;
	this.izquierdo=null;
}

public void insertarDer(Arbin i){
	this.derecho=i;
}

public void insertarIzq(Arbin i){
	this.izquierdo=i;
}

public Arbin getDer(){
	if (this.derecho!=null)
		return this.derecho;
	else
		return null;
}

public Arbin getIzq(){
	if (this.izquierdo!=null)
		return this.izquierdo;
	else
		return null;
}

public double getProb() {
	return prob;
}

public void setProb(double prob) {
	this.prob = prob;
}

public int getSimbolo() {
	return simbolo;
}

public void setSimbolo(int simbolo) {
	this.simbolo = simbolo;
}

public int compareTo(Arbin a1){
	if ( this.getProb() < a1.getProb())
		return 1;
	else
		return -1;
}

public boolean esHoja(){
	if(this.getDer()==null && this.getIzq() ==null)
		return true;
	return false;
}

}