package Question;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Anwser.Answer;
import Anwser.RankAnswer;

public class RankQuestion extends ItemQuestion {
	
	RankAnswer answer;
	List<String> items = new LinkedList<String>();
	
	public RankQuestion(String prompt, String[] items){
		super(4);
		
		this.setPrompt(prompt);
		for(int i=0; i<items.length; i++){
			this.setItem(items[i]);
		}
	}
	
	public RankQuestion(String prompt, String[] items, int score, String answer){
		this(prompt,items);
		
		this.setScore(score);
		this.setAnswer(answer);
	}
	
	@Override
	public void setItem(String item) {
		items.add(item);		
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = constructAnswer(answer);
	}

	@Override
	public Answer getAnswer() {
		return this.answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.getAnswer().equals(answer);
	}
	
	@Override
	public String getQuestion(){
		String ret = "Rank: "+prompt + "\n";
		for(int i=0; i<items.size(); i++){
			ret += items.get(i)+"\n";
		}
		return ret;
	}

	@Override
	public List<String> getItem() {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public boolean remove(int index) {
		// TODO Auto-generated method stub
		if(items.size() > index){
			items.remove(index);
			return true;
		}
		return false;
	}

	@Override
	public boolean changeItem(int index, String item) {
		// TODO Auto-generated method stub
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
			for(int i=num; i < items.size(); i++){
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
	public RankAnswer constructAnswer(String answerString) {
		RankAnswer ansObj = new RankAnswer();
		ansObj.setQuestion(items);
		ansObj.setAnswer(answerString);
		
		return ansObj;
	}

	@Override
	public Map<String, FieldType> getFields() {
		// TODO Auto-generated method stub
		return null;
	}
}
