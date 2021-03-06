package com.objectmentor.utilities.args;

import static com.objectmentor.utilities.args.ArgsException.ErrorCode.*;

import java.util.*;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
  private List<String> strings = new ArrayList<String>();

  public void set(Iterator<String> currentArgument) throws ArgsException {
    try {
      strings.add(currentArgument.next());
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_STRING);
    }
  }

  public static String[] getValue(ArgumentMarshaler am) {
    if (am != null && am instanceof StringArrayArgumentMarshaler)
      return ((StringArrayArgumentMarshaler) am).strings.toArray(new String[0]);
	return new String[0];
  }
}
