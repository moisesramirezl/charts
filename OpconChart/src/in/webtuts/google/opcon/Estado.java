package in.webtuts.google.opcon;

public final class Estado {

	private int tiempo;
	private String nombre;
	
	public int getTiempo() {
		return tiempo;
	}
	public String getNombre(){
		return nombre;
	}
	public void setTiempo(int tiempo) {
		//En el caso que pase mas de una vez por estado
		System.out.println("Tiempo = " + tiempo + " Estado = " + this.nombre + " Tiempo obj = " + this.tiempo);
		if(this.tiempo > 0){
			System.out.println("Tiempo  > 0 = " + tiempo);
			this.tiempo = this.tiempo + tiempo;
			System.out.println("Tiempo  after = " + this.tiempo);
		}else{
			this.tiempo = tiempo;
		}
	}

	public void setNombre(String nombre){		
		this.nombre = nombre;
	}
	
}