package me.monitorex;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("me.monitorex.tests")
//@IncludeClassNamePatterns(".*Tests")
public class TestAllSuite {

}
