package org.beanfuse.db.sequence;

import static org.testng.Assert.assertEquals;

import org.beanfuse.db.sequence.impl.DefaultSequenceNamePattern;
import org.testng.annotations.Test;

@Test
public class SequenceNamePatternTest {

	public void testGetTableName() {
		DefaultSequenceNamePattern pattern = new DefaultSequenceNamePattern();
		assertEquals("SYS_USERS_T", pattern.getTableName("SEQ_SYS_USERS_T"));
	}
}
