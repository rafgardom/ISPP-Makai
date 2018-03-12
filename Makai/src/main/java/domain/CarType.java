package domain;

public enum CarType {
	UTILITY_CAR("UTILITY_CAR", "VEHICULO_UTILITARIO"),
	SEDAN("SEDAN", "SEDAN"),
	VAN("VAN", "FURGONETA"),
	TRUCK("TRUCK", "CAMION"),
	MINI_VAN("MINI_VAN", "MINI_FURGONETA"),
	NORMAL_CAR("NORMAL_CAR", "COCHE_NORMAL");
	
	private final String  name;
	private final String  spanishName;
	
	private CarType(final String name, final String spanishName){
		 this.name = name;
		 this.spanishName = spanishName;
		  }

	public String getName() {
		return this.name;
	}

	public String getSpanishName() {
		return this.spanishName;
	}
	
}
