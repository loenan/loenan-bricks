package com.loenan.bricks.api.controller;

import com.loenan.bricks.sphere.generator.SphereGenerator;
import com.loenan.bricks.sphere.generator.SphereGeneratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SphereController {

	private final SphereGeneratorFactory generatorFactory;

	@Autowired
	public SphereController(SphereGeneratorFactory generatorFactory) {
		this.generatorFactory = generatorFactory;
	}

	@GetMapping("/{colorScheme}/{diameter}")
	public void generateSphere(
			@PathVariable String colorScheme,
			@PathVariable double diameter,
			HttpServletResponse response) throws IOException {

		SphereGenerator generator = generatorFactory.createSphereGenerator();
		generator.setColorSchemeName(colorScheme);
		generator.setDiameter(diameter);

		response.setContentType(generator.getContentType());
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + generator.getFileName() + "\"");

		generator.generateSphere(response.getOutputStream());
	}
}
