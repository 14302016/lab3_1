package Paper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Anwser.Answer;
import Question.Question;

public class Test extends Page {
	int totalScore;
	
	@Override
	public HashMap<String, String> store() {
		HashMap<String, String> returnVal = super.store();
		
		returnVal.put("score", Integer.toString(this.getTotalScore()));
		
		return returnVal;
	}
	
	public void setTotalScore(int score){
		totalScore = score;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public void computeScore(){
		totalScore = 0;
		for(int i=0; i<questionList.size(); i++){
			Question question = questionList.get(i);
			totalScore += question.getScore();
		}
	}

	@Override
	public String getTypeString() {
		return "test";
	}
	
	@Override
	public void finalize() {
		this.computeScore();
	}
	
	@Override
	public void finalizeAnswer(Record record) {
		this.grade(record);
	}
	
	public void grade(Record record) {
		Iterator<Question> questionIterator = this.iterator();
		Iterator<Answer> answerIterator = record.iterator();
		if(questionIterator.hasNext()){
			Question q = questionIterator.next();
			if(q.getType() != 3){
				if(q.match(answerIterator.next())){
					record.addScore(q.getScore());
				}
			}else{
				answerIterator.next();
			}
		}
	}
}
