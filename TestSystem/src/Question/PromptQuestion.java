package Question;

import Anwser.Answer;

public abstract class PromptQuestion extends Question{
	
	public PromptQuestion(int type) {
		super(type);
	}

	@Override
	public abstract Answer getAnswer();
	@Override
	public abstract void setAnswer(String answer);
	
	
	@Override
	public boolean remove(int index) {
		return false;
	}
}
