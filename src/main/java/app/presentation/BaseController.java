package app.presentation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

public class BaseController {

	Map<String, Object> formatPage(String content, Page<?> page) {

		Map<String, Object> formattedPage = new HashMap<>(3);

		formattedPage.put(content, page.getContent());
		formattedPage.put("current-page", page.getNumber());
		formattedPage.put("total-pages", page.getTotalPages());

		return formattedPage;

	}

}
