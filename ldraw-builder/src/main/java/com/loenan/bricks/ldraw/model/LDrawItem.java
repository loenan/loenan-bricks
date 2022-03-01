package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.reader.LineReader;
import org.apache.commons.lang3.StringUtils;

public interface LDrawItem {

	String getName();

	static LDrawItem read(LineReader reader) {
		String reference = reader.readEndOfLine().trim();
		if (reference.endsWith(Extensions.DAT)) {
			String designId = StringUtils.removeEnd(reference, Extensions.DAT);
			return new Part(designId);
		} else {
			return new LDrawFileReference(reference);
		}
	}
}
