package app.data;

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
	private String polymer;
	private String value;

	public Sequence() {
		super();
	}

	public Sequence(String tag, String polymer, String sequence) {
		super();
		this.tag = tag;
		this.polymer = polymer;
		this.value = sequence;
		vaidateSequence();
	}

	private void vaidateSequence() {

		if (this.tag == null || this.tag.isBlank()) {
			throw new IllegalArgumentException("tag cannot be empty");
		}

		if (this.polymer == null || this.polymer.isBlank()) {
			throw new IllegalArgumentException("polymer cannot be empty");
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

	public String getPolymer() {
		return polymer;
	}

	public String getValue() {
		return value;
	}

}
