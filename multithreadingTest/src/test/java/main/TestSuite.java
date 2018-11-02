package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import service.ServiceTypesTest;
import service.WorkTypesTest;
import types.TypeATest;
import types.TypeBTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceTypesTest.class, WorkTypesTest.class, TypeATest.class, TypeBTest.class} )
public class TestSuite {
}
