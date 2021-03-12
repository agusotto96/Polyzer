package app.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POLYMERS")
public class Polymer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tag;
	private String type;
	private String sequence;

	Polymer() {
		super();
	}

	Polymer(String tag, String type, String sequence) {
		super();
		this.tag = tag;
		this.type = type;
		this.sequence = sequence;
		vaidateSequence();
	}

	private void vaidateSequence() {

		if (this.tag == null || this.tag.isBlank()) {
			throw new IllegalArgumentException("tag cannot be empty");
		}

		if (this.type == null || this.type.isBlank()) {
			throw new IllegalArgumentException("type cannot be empty");
		}

		if (this.sequence == null || this.sequence.isBlank()) {
			throw new IllegalArgumentException("sequence cannot be empty");
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

	public String getSequence() {
		return sequence;
	}

}
