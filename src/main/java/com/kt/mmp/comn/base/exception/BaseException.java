package com.kt.mmp.comn.base.exception;

public class BaseException extends RuntimeException {

  private final String code;

  private final Object[] args;

  public BaseException(String code, Object... args) {
    super(code);  // TODO should modify message
    this.code = code;
    this.args = args;
  }

  public BaseException(ErrorCode code, Object... args) {
    this(code.name(), args);
  }

}
