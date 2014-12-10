package com.ontime.resource;

public class IdShortener {

  public static final String ALPHABET = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
  public static final int BASE = ALPHABET.length();

  public static String encode(long id) {
      StringBuilder str = new StringBuilder();
      while (id > 0) {
          str.insert(0, ALPHABET.charAt((int)(id % BASE)));
          id = id / BASE;
      }
      return str.toString();
  }

  public static long decode(String strId) {
      long id = 0;
      for (int i = 0; i < strId.length(); i++) {
          id = id * BASE + ALPHABET.indexOf(strId.charAt(i));
      }
      return id;
  }
  
}
