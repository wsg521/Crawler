package com.open.crawler.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Questions {
	/**
	 * 将html的Document对象解析成Question
	 * @param document
	 * @return
	 */
	public static List<Question> StringToQuestion(Document document) {
		List<Question> questions = null;
		try {
			questions = new ArrayList<Question>();
			for (int i = 0;i < 5;i++) {
				Question question = new Question();
				questions.add(question);
			}
			Elements elements = document.getElementsByAttributeValue("type", "hidden");		
			Element element = null;
			for (int i = 0;i < elements.size();i++) {
				element = elements.get(i);
				if (element.attr("name").contains("kbaList[0]")) {
					setQuestion(questions.get(0),element); 
				} else if (element.attr("name").contains("kbaList[1]")) {
					setQuestion(questions.get(1),element); 
				} else if (element.attr("name").contains("kbaList[2]")) {
					setQuestion(questions.get(2),element); 
				} else if (element.attr("name").contains("kbaList[3]")) {
					setQuestion(questions.get(3),element); 
				} else if (element.attr("name").contains("kbaList[4]")) {
					setQuestion(questions.get(4),element); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return questions;
		}
	}	
	/**
	 * 为对应索引Question赋值
	 * @param question
	 * @param element
	 */
	public static void setQuestion(Question question, Element element) {
		if (element.attr("name").contains("derivativecode")) 
			question.setDerivativecode(element.val());
		else if (element.attr("name").contains("businesstype"))
			question.setBusinesstype(element.val());
		else if (element.attr("name").contains("questionno"))
			question.setQuestionno(element.val());
		else if (element.attr("name").contains("kbanum"))
			question.setKbanum(element.val());
		else if (element.attr("name").contains("question"))
			question.setQuestion(element.val());
		else if (element.attr("name").contains("options1"))
			question.setOptions1(element.val());
		else if (element.attr("name").contains("options2"))
			question.setOptions2(element.val());
		else if (element.attr("name").contains("options3"))
			question.setOptions3(element.val());
		else if (element.attr("name").contains("options4"))
			question.setOptions4(element.val());
		else if (element.attr("name").contains("options5"))
			question.setOptions5(element.val());
	}
	/**
	 * 将json字符串转换成Question List<Question>
	 * @param json
	 * @return
	 */
	public static List<Question> JsonToQuestions(String json) {
		 List<Question> questions = null;
		 try {
			 questions = new ArrayList<Question>();
			 JSONArray array = JSONArray.parseArray(json);
			 for(int i = 0; i < array.size();i++){
				 JSONObject jobj = (JSONObject)array.get(i);
				 Question question = JSON.toJavaObject(jobj, Question.class);
				 questions.add(question);
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		} finally {
			return questions;
		}
	}
}
