package app.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.data.DNADataHandler;

@RestController
@RequestMapping()
class DNAController {

	private final String DNA_SEQUENCES_PATH = "polymers/DNA/tags/{tag}/sequences";

	private DNADataHandler DNADataHandler;

	DNAController(DNADataHandler DNADataHandler, ControllerHelper controllerHelper) {
		super();
		this.DNADataHandler = DNADataHandler;
	}

	@PostMapping(DNA_SEQUENCES_PATH)
	void saveDNAs(@PathVariable String tag, @RequestBody List<String> sequences) {
		DNADataHandler.savePolymers(tag, sequences);
	}

}
