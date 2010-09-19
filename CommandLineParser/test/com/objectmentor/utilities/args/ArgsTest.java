package com.objectmentor.utilities.args; 
 
import junit.framework.TestCase;
 
public class ArgsTest extends TestCase { 
  public void testCreateWithNoSchemaOrArguments() throws Exception { 
    new Args("", new String[0]); 
  } 
 
  public void testWithNoSchemaButWithOneArgument() throws Exception { 
    try { 
      new Args("", new String[]{"-x"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT,  
                   e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
    } 
  } 
 
  public void testWithNoSchemaButWithMultipleArguments() throws Exception { 
    try { 
      new Args("", new String[]{"-x", "-y"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT,  
                   e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
    } 
 
  } 
 
  public void testNonLetterSchema() throws Exception { 
    try { 
      new Args("*", new String[]{}); 
      fail("Args constructor should have thrown exception"); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME,  
                   e.getErrorCode()); 
      assertEquals('*', e.getErrorArgumentId()); 
    } 
  } 
 
  public void testInvalidArgumentFormat() throws Exception { 
    try { 
      new Args("f~", new String[]{}); 
      fail("Args constructor should have throws exception"); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.INVALID_ARGUMENT_FORMAT, e.getErrorCode()); 
      assertEquals('f', e.getErrorArgumentId()); 
    } 
  } 
 
  public void testSimpleBooleanPresent() throws Exception { 
    Args args = new Args("x", new String[]{"-x"}); 
    assertEquals(true, args.getBoolean('x')); 
  } 
 
  public void testSimpleStringPresent() throws Exception { 
    Args args = new Args("x*", new String[]{"-x", "param"}); 
    assertTrue(args.has('x')); 
    assertEquals("param", args.getString('x')); 
  } 
 
  public void testMissingStringArgument() throws Exception { 
    try { 
      new Args("x*", new String[]{"-x"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.MISSING_STRING, e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
    } 
  } 
 
  public void testSpacesInFormat() throws Exception { 
    Args args = new Args("x, y", new String[]{"-xy"}); 
    assertTrue(args.has('x')); 
    assertTrue(args.has('y')); 
  } 
 
  public void testSimpleIntPresent() throws Exception { 
    Args args = new Args("x#", new String[]{"-x", "42"}); 
    assertTrue(args.has('x')); 
    assertEquals(42, args.getInt('x')); 
  } 
 
  public void testInvalidInteger() throws Exception { 
    try { 
      new Args("x#", new String[]{"-x", "Forty two"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.INVALID_INTEGER, e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
      assertEquals("Forty two", e.getErrorParameter()); 
    } 
 
  } 
 
  public void testMissingInteger() throws Exception { 
    try { 
      new Args("x#", new String[]{"-x"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.MISSING_INTEGER, e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
    } 
  } 
 
  public void testSimpleDoublePresent() throws Exception { 
    Args args = new Args("x##", new String[]{"-x", "42.3"}); 
    assertTrue(args.has('x')); 
    assertEquals(42.3, args.getDouble('x'), .001); 
  } 
 
  public void testInvalidDouble() throws Exception { 
    try { 
      new Args("x##", new String[]{"-x", "Forty two"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.INVALID_DOUBLE, e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
      assertEquals("Forty two", e.getErrorParameter()); 
    } 
  } 
 
  public void testMissingDouble() throws Exception { 
    try { 
      new Args("x##", new String[]{"-x"}); 
      fail(); 
    } catch (ArgsException e) { 
      assertEquals(ArgsException.ErrorCode.MISSING_DOUBLE, e.getErrorCode()); 
      assertEquals('x', e.getErrorArgumentId()); 
    } 
  } 
} 
