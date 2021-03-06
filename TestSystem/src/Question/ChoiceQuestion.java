package Question;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Anwser.Answer;
import Anwser.ChoiceAnswer;

public class ChoiceQuestion extends ItemQuestion {
	List<String> items = new LinkedList<String>();
	ChoiceAnswer answer;
	
	@Override
	public java.util.HashMap<String,String> store() {
		HashMap<String, String> returnVal = super.store();
		
		returnVal.put("type", "choice");
		returnVal.put("isscore", "1");
		returnVal.put("answer", this.getAnswer() == null? "0" : "1");
		
		for (String item : items) {
			returnVal.put("items", item);
		}
		
		return returnVal;
	}
	
	public ChoiceQuestion(String prompt, String[] items){
		super(1);

		this.setPrompt(prompt);
		for(int i=0; i<items.length; i++){
			this.setItem(items[i]);
		}
	}
	
	public ChoiceQuestion(String prompt, String[] items, int score, String answer){
		this(prompt, items);
		
		this.setScore(score);
		this.setAnswer(answer);
	}
	
	@Override
	public void setItem(String item) {
		this.items.add(item);
	}

	@Override
	public void setAnswer(String answerString) {
		this.answer = constructAnswer(answerString);
	}

	@Override
	public Answer getAnswer() {
		return answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.equals(answer);
	}
	
	@Override
	public String getQuestion(){
		String ret = "ChoiceQuestion: "+prompt+"\n";
		for(int i=0; i<items.size(); i++){
			ret += items.get(i)+"\n";
		}
		return ret;
	}

	@Override
	public List<String> getItem() {
		return items;
	}

	@Override
	public boolean remove(int index) {
		if(items.size() > index){
			items.remove(index);
			return true;
		}
		return false;
	}

	@Override
	public boolean changeItem(int index, String item) {
		if(items.size() > index){
			items.remove(index);
			items.add(index, item);
			return true;
		}
		return false;
	}

	@Override
	public boolean changeItemNumber(int num) {
		if(items.size() > num){
			for(int i=num; i<items.size(); i++){
				items.remove(i);
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
	public ChoiceAnswer constructAnswer(String answerString) {
		ChoiceAnswer ansObj = new ChoiceAnswer();
		ansObj.setItem(items);
		ansObj.setAnswer(answerString);
		
		return ansObj;
	}

	@Override
	public Map<String, FieldType> getFields() {
		HashMap<String, FieldType> returnVal = new HashMap<>();
		
		returnVal.put("prompt", FieldType.STRING);
		//TODO: Fill in fields
		
		return returnVal;
	}
}
