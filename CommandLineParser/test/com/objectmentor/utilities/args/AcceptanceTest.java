package com.objectmentor.utilities.args;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AcceptanceTest {
	
	@Test
	public void schemaWithAllPossibleArgumentTypes() throws ArgsException {
		String schema = "p,q*,r#,s##";
		String[] commandLineArguments = new String[] { 
				"-p", "-q", "some-string", "-r", "-101", "-s", "5623.125" 
		};
		Args args = new Args(schema, commandLineArguments);
		assertThat(args.has('p'), is(true));
		assertThat(args.getBoolean('p'), is(true));
		assertThat(args.has('q'), is(true));
		assertThat(args.getString('q'), is("some-string"));
		assertThat(args.has('r'), is(true));
		assertThat(args.getInt('r'), is(-101));
		assertThat(args.has('s'), is(true));
		assertThat(args.getDouble('s'), is(5623.125));
	}

	@Test
	public void argumentsNeedNotBePresent() throws ArgsException {
		Args args = new Args("a#,b#", new String[0]);
		assertThat(args.has('a'), is(false));
		assertThat(args.has('b'), is(false));
	}
	
	@Test
	public void argumentsCanBeInDifferentOrderToSchema() throws ArgsException {
		String schema = "a*,b*,c*";
		Args args = new Args(schema, new String[] { "-c", "charlie", "-b", "bob", "-a", "adam" });
		assertThat(args.has('a'), is(true));
		assertThat(args.getString('a'), is("adam"));
		assertThat(args.has('b'), is(true));
		assertThat(args.getString('b'), is("bob"));
		assertThat(args.has('c'), is(true));
		assertThat(args.getString('c'), is("charlie"));
	}
}
