package main.DTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "POLYMERS")
public class PolymerDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	private String tag;
	private String type;
	private String sequence;

	PolymerDTO() {
		super();
	}

	public PolymerDTO(String tag, String type, String sequence) {
		super();
		this.tag = tag;
		this.type = type;
		this.sequence = sequence;
	}

	public Long getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public String getType() {
		return type;
	}

	public String getSequence() {
		return sequence;
	}

}
