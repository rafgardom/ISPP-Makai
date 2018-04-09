
package domain;

public enum NotificationType {
	REQUEST("REQUEST", "SOLICITUD"), OFFER("OFFER", "OFERTA"), TRAVEL("TRAVEL", "VIAJE"), TRAINING("TRAINING", "ADIESTRAMIENTO"), RATING("RATING", "VALORACIONES"), GENERAL("GENERAL", "GENERAL"), BANNER("BANNER", "BANNER");

	private final String	name;
	private final String	spanishName;


	private NotificationType(final String name, final String spanishName) {
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
