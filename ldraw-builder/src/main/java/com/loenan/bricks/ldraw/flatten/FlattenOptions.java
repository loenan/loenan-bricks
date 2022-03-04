package com.loenan.bricks.ldraw.flatten;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public class FlattenOptions {

	private final Set<String> keepUnflattenModelNames;

	public FlattenOptions() {
		this(emptySet());
	}

	public FlattenOptions(Collection<String> unflattenedModelNames) {
		this.keepUnflattenModelNames = unmodifiableSet(new HashSet<>(unflattenedModelNames));
	}

	public boolean isModelToFlatten(String modelName) {
		return !keepUnflattenModelNames.contains(modelName);
	}
}
