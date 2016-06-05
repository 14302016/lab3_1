package Paper;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import Anwser.Answer;

public class Record {
	
	List<Answer> answerList = new LinkedList<Answer>();
	int score;
	String personName;
	int index;
	
	public int getScore() {
		return score;
	}
	public void addScore(int score) {
		this.score += score;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public void addAnwser(Answer answer){
		answerList.add(answer);
	}
	
	public Answer getAnswer(int index){
		if(index < answerList.size()){
			return answerList.get(index); 
		}
		return null;
	}
	
	public Answer getAnswer(){
		return answerList.get(index++);
	}
	
	public boolean hasNext(){
		if(index >= answerList.size()){
			index = 0;
			return false;
		}
		return true;
	}	
	
	public Iterator<Answer> iterator(){
		return answerList.iterator();
	}
}
