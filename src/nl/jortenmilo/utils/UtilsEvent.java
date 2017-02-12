package nl.jortenmilo.utils;

import nl.jortenmilo.event.Event;

public abstract class UtilsEvent extends Event {
	
	private String utilName = "";

	public String getUtilName() {
		return utilName;
	}

	public void setUtilName(String utilName) {
		this.utilName = utilName;
	}
	
	public class UtilsEventListener {
		public void onUtilsCreated(UtilsCreatedEvent event) {}
		public void onUtilsCloned(UtilsClonedEvent event) {}
	}
	
}
