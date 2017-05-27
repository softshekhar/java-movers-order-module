package com.packers.movers.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class UnitTest extends AbstractTestNGSpringContextTests {
}
