package app.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.data.RNADataHandler;

@RestController
@RequestMapping()
class RNASequenceController {

	private final String RNA_SEQUENCES_PATH = "polymers/RNA/tags/{tag}/sequences";

	private RNADataHandler RNADataHandler;

	RNASequenceController(RNADataHandler RNADataHandler, ControllerHelper controllerHelper) {
		super();
		this.RNADataHandler = RNADataHandler;
	}

	@PostMapping(RNA_SEQUENCES_PATH)
	void saveRNAs(@PathVariable String tag, @RequestBody List<String> sequences) {
		RNADataHandler.savePolymers(tag, sequences);
	}

}
