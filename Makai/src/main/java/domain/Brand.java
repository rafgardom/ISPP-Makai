package domain;

public enum Brand {
	SEAT("SEAT", "SEAT"),
	CITROEN("CITROEN", "CITROEN"),
	GENERAL("GENERAL", "GENERAL"),
	VOLKSWAGEN("VOLKSWAGEN", "VOLKSWAGEN"),
	FIAT("FIAT", "FIAT"),
	DODGE("DODGE", "DODGE"),
	FORD("FORD", "FORD");
	
	private final String  name;
	private final String  spanishName;
	
	private Brand(final String name, final String spanishName){
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
