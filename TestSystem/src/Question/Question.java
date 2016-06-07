package Question;

import java.util.HashMap;

import Anwser.Answer;


public abstract class Question {
	String prompt;
	int score;
	int type;
	
	public HashMap<String, String> store() {
		HashMap<String, String> returnVal = new HashMap<>();
		
		returnVal.put("prompt", prompt);
		returnVal.put("score", Integer.toString(score));
		
		return returnVal;
	}
	
	public Question(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	public String getQuestion(){
		return null;
	}
	
	public String getPrompt(){
		return prompt;
	}
	
	public void setPrompt(String prompt){
		this.prompt = prompt;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public abstract void setAnswer(String answer);
	
	public abstract Answer getAnswer();
	public abstract boolean match(Answer answer);
	public abstract Answer constructAnswer(String answerString);
	public abstract boolean remove(int index);
}
