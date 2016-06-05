package Question;

import Anwser.Answer;
import Anwser.TextAnswer;

public class ShortEssayQuestion extends PromptQuestion {
	
	TextAnswer answer;
	
	public ShortEssayQuestion(String prompt) {
		super(2);
		
		this.setPrompt(prompt);
	}	
	
	public ShortEssayQuestion(String prompt, int score, String answer)
	{
		this(prompt);
		
		this.setScore(score);
		this.setAnswer(answer);
	}
	
	@Override
	public String getQuestion(){
		return "Text: "+prompt;
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


	@Override
	public boolean match(Answer answer) {
		return this.answer.match(answer);
	}

	@Override
	public TextAnswer constructAnswer(String answerString) {
		TextAnswer ansObj = new TextAnswer();
		ansObj.setAnswer(answerString);
		
		return ansObj;
	}
}
