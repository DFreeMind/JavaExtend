package com.weduoo.javaExtend.reflect;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	
	public Person() {
		super();
		this.id = 100L;
		this.name = "莱昂纳多";
	}
	
	public Person(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Person(String name) {
		super();
		this.name = name+"=======";
		System.out.println(name+"=======");
	}
	
	public Person(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
