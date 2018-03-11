package domain;

public enum Sex {
	MALE("MALE", "HOMBRE"),
	FEMALE("FEMALE", "MUJER");
	
	private final String  name;
	private final String  spanishName;
	
	private Sex(final String name, final String spanishName) {
	    this.name = name;
	    this.spanishName = spanishName;
	  }

	public String getName() {
		return name;
	}

	public String getSpanishName() {
		return spanishName;
	}
	
	

}
