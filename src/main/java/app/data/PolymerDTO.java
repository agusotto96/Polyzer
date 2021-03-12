package app.data;

import app.domain.Polymer;

public class PolymerDTO {

	private long id;
	private Polymer polymer;

	PolymerDTO(long id, Polymer polymer) {
		super();
		this.id = id;
		this.polymer = polymer;
	}

	public long getId() {
		return id;
	}

	public Polymer getPolymer() {
		return polymer;
	}

}
