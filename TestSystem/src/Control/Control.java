package Control;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import Anwser.Answer;
import Anwser.ChoiceAnswer;
import Anwser.DecideAnswer;
import Anwser.MapAnswer;
import Anwser.RankAnswer;
import Anwser.TextAnswer;
import Paper.Page;
import Paper.Record;
import Paper.Survey;
import Paper.Test;
import Question.ChoiceQuestion;
import Question.DecideQuestion;
import Question.EssayQuestion;
import Question.ItemQuestion;
import Question.MapQuestion;
import Question.Question;
import Question.RankQuestion;
import Question.ShortEssayQuestion;

public class Control {
	List<String>[] pageNameList;
	Page page;
	Question question;
	int index;
	Record record;
	IO io = new IO();
	List<String> recordName;
	Iterator<Question> iterator;
	
	public Control(){
		pageNameList = io.readInfo();
	}
	
	public void createPage(int type){
		if(type == 0){
			page = new Survey();
			page.setType("survey");
		}else{
			page = new Test();
			page.setType("test");
		}
	}
		
	public void setPageName(String name){
		page.setPageName(name);
		if(page.getType().equals("test")){
			pageNameList[1].add(name);
		}else{
			pageNameList[0].add(name);
		}
	}
	
	public void setItem(String item){
		((ItemQuestion)question).setItem(item);
	}
	
	public void setItem(int side, String item){
		MapQuestion map = (MapQuestion)question;
		map.setSide(side);
		map.setItem(item);
	}
	
	public void setSide(int side){
		MapQuestion map = (MapQuestion)question;
		map.setSide(side);
	}
	
	public void setAnswer(String answer){
		question.setAnswer(answer);
	}
	
	public void setPrompt(String prompt){
		question.setPrompt(prompt);

	}
	
	public void setScore(int score){
		question.setScore(score);
	}
	
	public List<String> getPageName(int type){
		return pageNameList[type];
	}
	
	public List<String> displayPage(int index, int type){
		List<String> ret = new LinkedList<String>();
		if(pageNameList[type].size() <= index){
			return ret;
		}else{
			page = io.readPage(pageNameList[type].get(index));
			Iterator<Question> questions = page.iterator();
			while(questions.hasNext()){
				Question q = questions.next();
				int ty = q.getType();
				String answer = "";
				if(type == 1 && ty != 3){
					answer = "\nThe correct answer is " + q.getAnswer().writeAnswer();
				}
				ret.add(q.getQuestion()+answer+"\n");
			}
			return ret;
		}
	}
	
	public void save(){
		if(page.getType().equals("test")){
			Test test = (Test)page;
			test.computeScore();
		}
		io.writeInfo(pageNameList);
		io.writePage(page);
	}
	
	public int modify(int index){
		if(index >= page.getQuestionList().size()){
			return -1;
		}else{
			question = page.getQuestion(index);
			return question.getType();
		}
	}
	
	public void addQuestion(Question question)
	{
		page.addQuestion(question);
	}
	
	public boolean remove(int index){
		if(question.getType() == 5){
			return ((MapQuestion)question).remove(index);
		}
		return ((ItemQuestion)question).remove(index);
	}
	
	public boolean changeItem(int index, String item){
		if(question.getType() == 5){
			return ((MapQuestion)question).changeItem(index, item);		
		}
		return ((ItemQuestion)question).changeItem(index, item);
	}
	
	public boolean changeItemNumber(int num){
		if(question.getType() == 5){
			return ((MapQuestion)question).changeItemNumber(num);		
		}
		return ((ItemQuestion)question).changeItemNumber(num);
	}
	
	public void loadPage(int index, int type){
		page = io.readPage(pageNameList[type].get(index));
		record = new Record();
	}
	
	public void setRecordName(String name){
		record.setPersonName(name);
		iterator = page.iterator();
	}
	
	public String nextQuestion(){
		question = iterator.next();
		return question.getQuestion();
	}
	
	public boolean hasNextQuestion(){
		return iterator.hasNext();
	}
	
	public void answerQuestion(String answer){
		Answer ans = question.constructAnswer(answer);
		record.addAnwser(ans);
	}
	
	public void saveAnswer(){
		recordName = io.readRecordInfo(page.getPageName());
		recordName.add(page.getPageName()+"-"+record.getPersonName());
		if(page.getType().equals("test")){
			this.grade();
		}
		io.writeReordInfo(page.getPageName(), recordName);
		io.writeRecord(page.getPageName()+"-"+record.getPersonName(), record);
	}
	
	public void grade(){
		Iterator<Question> questionIterator = page.iterator();
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
	
	public String getOutcome(int index, int type){
		this.loadPage(index, type);
		recordName = io.readRecordInfo(page.getPageName());
		List<Iterator<Answer>> recordList = new LinkedList<Iterator<Answer>>();
		for(int i=0; i<recordName.size(); i++){
			recordList.add(io.readRecord(recordName.get(i)).iterator());
		}
		Iterator<Question> questionIterator = page.iterator();
		List<String> outcome = new LinkedList<String>();
		while(questionIterator.hasNext()){
			Question question = questionIterator.next();
			Map<String, Integer> map = new HashMap<String, Integer>();
			for(int i=0; i<recordList.size(); i++){
				Answer answer = recordList.get(i).next();
				if(map.containsKey(answer.writeAnswer())){
					int value = map.get(answer.writeAnswer());
					map.put(answer.writeAnswer(), value+1);
				}else{
					map.put(answer.writeAnswer(), 1);
				}
			}
			String oneOutcome = question.getQuestion();
			for(String key: map.keySet()){
				oneOutcome +="Answer: " + key+"\t"+map.get(key)+"\n";
			}
			outcome.add(oneOutcome);
		}
		String ret = "";
		for(int i=0; i<outcome.size(); i++){
			ret += outcome.get(i)+"\n";
		}
		return ret;
	}
}
