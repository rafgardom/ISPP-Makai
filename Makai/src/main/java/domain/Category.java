package domain;

public enum Category {
	DISABILITY("DISABILITY", "DISCAPACIDAD"),
	PSYCHOLOGICAL("PSYCHOLOGICAL", "PSICOLOGICO"),
	GENERAL("GENERAL", "GENERAL"),
	ABUSE("ABUSE", "ABUSO"),
	ADDICTION("ADDICTION", "ADICCION"),
	GERENTOLOGY("GERENTOLOGY", "GERONTOLOGIA"),
	DISEASES("DISEASES", "DOLENCIA");
	
	private final String  name;
	private final String  spanishName;
	
	private Category(final String name, final String spanishName){
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
