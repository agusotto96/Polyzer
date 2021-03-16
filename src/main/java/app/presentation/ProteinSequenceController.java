package app.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.data.ProteinDataHandler;

@RestController
@RequestMapping()
class ProteinSequenceController {

	private final String PROTEIN_SEQUENCES_PATH = "polymers/protein/tags/{tag}/sequences";

	private ProteinDataHandler proteinDataHandler;

	ProteinSequenceController(ProteinDataHandler proteinDataHandler, ControllerHelper controllerHelper) {
		super();
		this.proteinDataHandler = proteinDataHandler;
	}

	@PostMapping(PROTEIN_SEQUENCES_PATH)
	void saveProteins(@PathVariable String tag, @RequestBody List<String> sequences) {
		proteinDataHandler.savePolymers(tag, sequences);
	}

}
