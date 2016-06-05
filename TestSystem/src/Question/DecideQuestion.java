package Question;

import Anwser.Answer;
import Anwser.DecideAnswer;

public class DecideQuestion extends PromptQuestion {
	DecideAnswer answer;
	
	public DecideQuestion(){
		super(0);
	}
	
	@Override
	public String getQuestion(){
		String ret = "T/F: "+prompt+"\n"+"1. right\n2. false";
		return ret;
	}
	
	@Override
	public void setAnswer(String answerString){
		answer = constructAnswer(answerString);
	}
	
	@Override
	public Answer getAnswer(){
		return answer;
	}

	public boolean match(String answer) {
		// TODO Auto-generated method stub
		return this.answer.getAnswer().equals(answer);
	}


	@Override
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}

	@Override
	public DecideAnswer constructAnswer(String answerString) {
		DecideAnswer ansObj = new DecideAnswer();
		ansObj.setAnswer(answerString);
		
		return ansObj;
	}
}
