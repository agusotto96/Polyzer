package app.data;

import app.domain.NucleicAcid;

public class NucleicAcidDTO {

	private long id;
	private NucleicAcid nucleicAcid;

	NucleicAcidDTO(long id, NucleicAcid nucleicAcid) {
		super();
		this.id = id;
		this.nucleicAcid = nucleicAcid;
	}

	public long getId() {
		return id;
	}

	public NucleicAcid getNucleicAcid() {
		return nucleicAcid;
	}

}
