package in.webtuts.google.opcon;

import java.util.ArrayList;
import java.util.List;

public class Opcon {
	
	public Opcon(int id, int issueNum) {
		super();
		System.out.println("Creando opcon con ID = " + id + " issuenum = " + issueNum);
		this.id = id;
		this.issueNum = issueNum;
	}
	private int id;
	private int issueNum;
	
	public int getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(int issueNum) {
		this.issueNum = issueNum;
	}
	private List<Estado> estado = new ArrayList<Estado>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Estado> getEstado(){
		return estado;
	}
	
	public void setEstado(String nombre, int tiempo){
		//System.out.println("INDEX " + this.getIndexFromEstado(nombre));
		if(!this.checkIfEstadoExist(nombre)){
			Estado e = new Estado();
			e.setNombre(nombre);
			e.setTiempo(tiempo);			
			this.estado.add(e);
		}else{
			//System.out.println("INDEX " + this.getIndexFromEstado(nombre));
			this.estado.get(this.getIndexFromEstado(nombre)).setTiempo(tiempo);
		}
		
	}
	
	private boolean checkIfEstadoExist(String estado){		
		int i = 0;
		while(i < this.estado.size()){
			//System.out.println("Check estado " + estado + " vs " + this.estado.get(i).getNombre());
			if(this.estado.get(i).getNombre().equals(estado) ){
				//System.out.println("EQUAL");
				return true;
			}
			i++;
		}
		return false;
	}
	
	private int getIndexFromEstado(String estado){
		int i = 0;
		while(i < this.estado.size()){
			//System.out.println("Estado = " + this.estado.get(i).getNombre());
			if(this.estado.get(i).getNombre().equals(estado) ){
				return i;
			}
			i++;
		}
		return -1;
	}
}