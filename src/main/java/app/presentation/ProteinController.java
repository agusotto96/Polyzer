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
class ProteinController {

	private final String Protein_SEQUENCES_PATH = "polymers/Protein/tags/{tag}/sequences";

	private ProteinDataHandler proteinDataHandler;

	ProteinController(ProteinDataHandler proteinDataHandler, ControllerHelper controllerHelper) {
		super();
		this.proteinDataHandler = proteinDataHandler;
	}

	@PostMapping(Protein_SEQUENCES_PATH)
	void saveProteins(@PathVariable String tag, @RequestBody List<String> sequences) {
		proteinDataHandler.savePolymers(tag, sequences);
	}

}
