package main.persistance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEQUENCES")
public class Sequence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tag;
	private String type;
	private String value;

	public Sequence() {
		super();
	}

	public Sequence(String tag, String type, String sequence) {
		super();
		this.tag = tag;
		this.type = type;
		this.value = sequence;
		vaidateSequence();
	}

	private void vaidateSequence() {

		if (this.tag == null || this.tag.isBlank()) {
			throw new IllegalArgumentException("tag cannot be empty");
		}

		if (this.type == null || this.type.isBlank()) {
			throw new IllegalArgumentException("type cannot be empty");
		}

		if (this.value == null || this.value.isBlank()) {
			throw new IllegalArgumentException("value cannot be empty");
		}

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

	public String getValue() {
		return value;
	}

}
