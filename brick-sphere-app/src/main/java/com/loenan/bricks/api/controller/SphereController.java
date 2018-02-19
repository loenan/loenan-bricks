package com.loenan.bricks.api.controller;

import com.loenan.bricks.sphere.generator.SphereGenerator;
import com.loenan.bricks.sphere.generator.SphereParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SphereController {

	private final SphereGenerator generator;

	@Autowired
	public SphereController(SphereGenerator generator) {
		this.generator = generator;
	}

	@GetMapping("/sphere/{diameter}")
	public void generateSphere(@PathVariable double diameter, HttpServletResponse response) throws IOException {
		response.setContentType("application/x-ldraw");
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"LoenanSphere-" + diameter + ".mpd\"");

		SphereParameters parameters = new SphereParameters(diameter);
		generator.generateSphere(parameters, response.getOutputStream());
	}
}
