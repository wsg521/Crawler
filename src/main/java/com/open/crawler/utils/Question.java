package com.open.crawler.utils;

public class Question {
	
	private String derivativecode;
	private String businesstype;
	private String questionno;
	private String kbanum;
	private String question;
	private String options1;
	private String options2;
	private String options3;
	private String options4;
	private String options5;
	
	public Question() {
		
	}

	public String getDerivativecode() {
		return derivativecode;
	}

	public void setDerivativecode(String derivativecode) {
		this.derivativecode = derivativecode;
	}

	public String getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getQuestionno() {
		return questionno;
	}

	public void setQuestionno(String questionno) {
		this.questionno = questionno;
	}

	public String getKbanum() {
		return kbanum;
	}

	public void setKbanum(String kbanum) {
		this.kbanum = kbanum;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptions1() {
		return options1;
	}

	public void setOptions1(String options1) {
		this.options1 = options1;
	}

	public String getOptions2() {
		return options2;
	}

	public void setOptions2(String options2) {
		this.options2 = options2;
	}

	public String getOptions3() {
		return options3;
	}

	public void setOptions3(String options3) {
		this.options3 = options3;
	}

	public String getOptions4() {
		return options4;
	}

	public void setOptions4(String options4) {
		this.options4 = options4;
	}

	public String getOptions5() {
		return options5;
	}

	public void setOptions5(String options5) {
		this.options5 = options5;
	}

	@Override
	public String toString() {
		return "Question: [derivativecode=" + derivativecode + ", businesstype="
				+ businesstype + ", questionno=" + questionno + ", kbanum="
				+ kbanum + ", question=" + question + ", options1=" + options1
				+ ", options2=" + options2 + ", options3=" + options3
				+ ", options4=" + options4 + ", options5=" + options5 + "]";
	}
	
}
