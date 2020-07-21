package shit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementoConjunto implements Comparable<ElementoConjunto> {
	public ElementoConjunto getObjSiguiente() {
		return objSiguiente;
	}
	public void setObjSiguiente(ElementoConjunto objSiguiente) {
		this.objSiguiente = objSiguiente;
	}
	public ElementoConjunto getObjAnterior() {
		return objAnterior;
	}
	public void setObjAnterior(ElementoConjunto objAnterior) {
		this.objAnterior = objAnterior;
	}
	public int getDelta() {
		return delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
	}
	public int getDistSig() {
		return distSig;
	}
	public void setDistSig(int distSig) {
		this.distSig = distSig;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fin;
		result = prime * result + inicio;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementoConjunto other = (ElementoConjunto) obj;
		if (fin != other.fin)
			return false;
		if (inicio != other.inicio)
			return false;
		return true;
	}
	public boolean isSubconjunto() {
		return subconjunto;
	}
	public void setSubconjunto(boolean subconjunto) {
		this.subconjunto = subconjunto;
	}
	public ElementoConjunto(int inicio, int fin) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.subconjunto = false;
	}
	public int getInicio() {
		return inicio;
	}
	public void setInicio(int inicio) {
		this.inicio = inicio;
	}
	public int getFin() {
		return fin;
	}
	public void setFin(int fin) {
		this.fin = fin;
	}
	
	public boolean cercano (ElementoConjunto e) {
		return ((Math.abs(this.inicio - e.fin)) <= 1 || (Math.abs(this.fin - e.inicio) <=1));
	}
	
	public String toString () {
		return "["+inicio+" "+fin+" "+subconjunto+"]";
	}
	
	int 	inicio;
	int		fin;
	boolean subconjunto;
	int		distSig;
	int 	delta;
	ElementoConjunto objSiguiente;
	ElementoConjunto objAnterior;
	
	
	@Override
	public int compareTo(ElementoConjunto o) {
	
		return this.inicio-o.inicio;

	}
	

	public ArrayList<ElementoConjunto> separarConjunto(List<ElementoConjunto> lis2) {
		ArrayList<ElementoConjunto> conjSalida = new ArrayList<>();
		
		lis2.stream().sorted().forEach(System.out::println);
		conjSalida = (ArrayList<ElementoConjunto>) lis2.stream().sorted().collect(Collectors.toList());
		int idx = conjSalida.indexOf(this);
		conjSalida.get(idx).setSubconjunto(true);
		System.out.println("Indice donde esta el selector: "+idx);
		
		int x = 0;
		boolean goDown = true;
		boolean goUp   = true;
		while (goDown || goUp) {
			x++;
			int down = idx - x;
			int up   = idx + x;
			
			if (down < 0) {
				down = 0;
				goDown = false;
			}
			if (up > conjSalida.size()-1) {
				up=conjSalida.size()-1;
				goUp = false;
			}
			
			if (conjSalida.get(down+1).isSubconjunto()) 
				conjSalida.get(down).setSubconjunto(conjSalida.get(down+1).cercano(conjSalida.get(down)));

			if (conjSalida.get(up-1).isSubconjunto()) 
				conjSalida.get(up  ).setSubconjunto(conjSalida.get(up  -1).cercano(conjSalida.get(up  )));
		}
		System.out.println("===========");
//		conjSalida.stream().sorted().forEach(System.out::println);
		
		conjSalida = (ArrayList<ElementoConjunto>) conjSalida.stream().filter(elemento -> elemento.subconjunto == true).collect(Collectors.toList());

		conjSalida.stream().sorted().forEach(System.out::println);
		
		return conjSalida;
	}

	public ArrayList<ElementoConjunto> separarConjunto2(List<ElementoConjunto> lis2) {
		ArrayList<ElementoConjunto> conjSalida = new ArrayList<>();
		
		lis2.stream().sorted().forEach(System.out::println);
		conjSalida = (ArrayList<ElementoConjunto>) lis2.stream().sorted().collect(Collectors.toList());
		int idx = conjSalida.indexOf(this);
		conjSalida.get(idx).setSubconjunto(true);
		int size = 1;
		
		boolean iterar = true;
		while (iterar && conjSalida.size()>1) {
			for (int a=0; a<conjSalida.size(); a++) {
				boolean cercanoArriba;
				boolean cercanoAbajo;
				if (a==0) {
					cercanoArriba = false;
					cercanoAbajo  = conjSalida.get(0).cercano(conjSalida.get(1));
				} else if (a==conjSalida.size()-1) {
					cercanoArriba = conjSalida.get(a).cercano(conjSalida.get(a-1));
					cercanoAbajo  = false;
				} else {
					cercanoArriba = conjSalida.get(a).cercano(conjSalida.get(a-1));
					cercanoAbajo  = conjSalida.get(a).cercano(conjSalida.get(a+1));
				}
				if ((cercanoArriba) && conjSalida.get(a).isSubconjunto())   conjSalida.get(a-1).setSubconjunto(true);
				if ((cercanoAbajo)  && conjSalida.get(a).isSubconjunto())   conjSalida.get(a+1).setSubconjunto(true);
			}
			int sizeNow = (int) conjSalida.stream().filter(elem -> elem.isSubconjunto()).count();
			if (sizeNow > size) {
				size = sizeNow;
			} else
				iterar =false;
		}
		System.out.println("===========");
		conjSalida.stream().sorted().forEach(System.out::println);
		return conjSalida;
	}

	public ArrayList<ElementoConjunto> separarConjunto3(List<ElementoConjunto> lis2) {
		ArrayList<ElementoConjunto> conjSalida = new ArrayList<>();
		Stream<ElementoConjunto> sal = null;
		List<ElementoConjunto> sal1 = null;
		
		conjSalida = (ArrayList<ElementoConjunto>) lis2.stream().sorted().collect(Collectors.toList());
		int idx = conjSalida.indexOf(this);
		conjSalida.get(idx).setSubconjunto(true);
		int size = 1;
		
		lis2.stream().sorted().forEach(System.out::println);
		
		if (conjSalida.size()>1) {
			
			// armar la lista encadenada
			for (int a=0; a<conjSalida.size(); a++) {
				if (a==0) {
					conjSalida.get(0).objAnterior = null;
					conjSalida.get(0).objSiguiente = conjSalida.get(1);
				} else if (a==conjSalida.size()-1) {
					conjSalida.get(a).objAnterior  = conjSalida.get(a-1);
					conjSalida.get(a).objSiguiente = null;
				} else {
					conjSalida.get(a).objAnterior  = conjSalida.get(a-1);
					conjSalida.get(a).objSiguiente = conjSalida.get(a+1);
				}
			
			}
		
			// gota de aceite
			boolean iterar = true;
			while (iterar) {
				sal1=conjSalida.stream().map( elem -> determinar2(elem)).collect(Collectors.toList());
				int sizeNow = (int) sal1.size();
				if (sizeNow > size) {
					size = sizeNow;
				} else {
					iterar =false;
				}
			}
			System.out.println("===========");
			sal1.forEach(System.out::println);
		}
		return conjSalida;
	}
	private boolean determinar(ElementoConjunto elem) {
		
		boolean respuesta = false;
		
		if (elem.getObjSiguiente() != null) {
			if (elem.cercano(elem.getObjSiguiente()) && elem.getObjSiguiente().isSubconjunto()) {
				respuesta = true;
			}
		
		}
		if (elem.getObjAnterior()  != null) {
			if (elem.cercano(elem.getObjAnterior())  && elem.getObjAnterior().isSubconjunto()) {
				respuesta = true;
			}

		}
		if (respuesta) elem.setSubconjunto(true);
		System.out.println("Analize elemento "+elem+" veredicto "+respuesta);
		return respuesta;
	}
	private ElementoConjunto determinar2 (ElementoConjunto elem) {
		if (elem.isSubconjunto()) return elem;
		boolean respuesta = false;
		if (elem.getObjSiguiente() != null) {
			if (elem.cercano(elem.getObjSiguiente()) && elem.getObjSiguiente().isSubconjunto()) {
				respuesta = true;
			}
		
		}
		if (elem.getObjAnterior()  != null) {
			if (elem.cercano(elem.getObjAnterior())  && elem.getObjAnterior().isSubconjunto()) {
				respuesta = true;
			}

		}
		if (respuesta) {
			elem.setSubconjunto(true);
			return elem;
		}
		else return elem;
	}
}

