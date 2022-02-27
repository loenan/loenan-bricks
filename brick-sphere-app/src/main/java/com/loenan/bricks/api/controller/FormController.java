package com.loenan.bricks.api.controller;

import com.loenan.bricks.sphere.generator.color.ColorSchemeRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FormController {

	private final ColorSchemeRegistry colorSchemeRegistry;

	@Autowired
	public FormController(ColorSchemeRegistry colorSchemeRegistry) {
		this.colorSchemeRegistry = colorSchemeRegistry;
	}

	@GetMapping
	public ModelAndView displayForm() {
		return new ModelAndView("sphere-form")
			.addObject("colorSchemeNames", colorSchemeRegistry.getSchemeNames());
	}
}
