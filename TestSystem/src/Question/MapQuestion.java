package Question;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Anwser.Answer;
import Anwser.MapAnswer;

public class MapQuestion extends Question {
	
	MapAnswer answer;
	List<String> side1 = new LinkedList<String>();
	List<String> side2 = new LinkedList<String>();
	int side;
	
	public MapQuestion(String prompt, String[] side1, String[] side2){
		super(5);
		
		this.setPrompt(prompt);
		this.setSide(1);
		for(int i=0; i<side1.length; i++){
			this.setItem(side1[i]);
		}
		this.setSide(2);
		for(int i=0; i<side2.length; i++){
			this.setItem(side2[i]);
		}
	}
	
	public MapQuestion(String prompt, String[] side1, String[] side2, int score, String answer) {
		this(prompt, side1, side2);
		
		this.setScore(score);
		this.setAnswer(answer);
	}
	
	public void setItem(String item) {
		// TODO Auto-generated method stub
		if(side == 1){
			side1.add(item);
		}else{
			side2.add(item);
		}
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = constructAnswer(answer);
	}

	@Override
	public Answer getAnswer() {
		// TODO Auto-generated method stub
		return answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.getAnswer().equals(answer);
	}
	
	public void setSide(int side){
		this.side = side;
	}
	
	@Override
	public String getQuestion(){
		String ret = "Map: "+prompt+"\n";
		for(int i=0; i<side1.size(); i++){
			ret += side1.get(i)+"\t"+side2.get(i)+"\n";
		}
		return ret;
	}

	public List<String> getItem() {
		// TODO Auto-generated method stub
		if(side == 1){
			return side1;
		}
		return side2;
	}

	public boolean remove(int index) {
		if(side == 1){
			if(side1.size() > index){
				side1.remove(index);
				return true;
			}
		}else{
			if(side2.size() > index){
				side2.remove(index);
				return true;
			}
		}
		return false;
	}

	public boolean changeItem(int index, String item) {
		if(side == 1){
			if(side1.size() > index){
				side1.remove(index);
				side1.add(index, item);
				return true;
			}
		}else{
			if(side2.size() > index){
				side2.remove(index);
				side2.add(index, item);
				return true;
			}
		}
		return false;
	}

	public boolean changeItemNumber(int num) {
		if(side == 1){
			if(side1.size() > num){
				for(int i = num; i<side1.size(); i++){
					side1.remove(i);
				}
				return true;
			}
		}else{
			if(side2.size() > num){
				for(int i = num; i<side2.size(); i++){
					side2.remove(i);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}

	@Override
	public MapAnswer constructAnswer(String answerString) {
		MapAnswer ansObj = new MapAnswer();
		ansObj.setQuestion(side1, side2);
		ansObj.setAnswer(answerString);
		
		return ansObj;
	}

	@Override
	public Map<String, FieldType> getFields() {
		// TODO Auto-generated method stub
		return null;
	}
}
