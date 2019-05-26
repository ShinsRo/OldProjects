package com.midas2018.root.model;

public interface CodeEnum<T extends Enum<T> & CodeEnum<T>> {


	String getCode();
}
