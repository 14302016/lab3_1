package Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import Anwser.Answer;
import Paper.Page;
import Paper.Record;
import Paper.Survey;
import Paper.Test;
import Question.ItemQuestion;
import Question.MapQuestion;
import Question.Question;
import utils.GenericObservable;

public class Control {
	Map<String, String> pageNameList;
	GenericObservable<Page> page;
	Question question;
	int index;
	Record record;
	IO io = new IO();
	List<String> recordName;
	Iterator<Question> iterator;
	
	public Control(){
		pageNameList = io.getPages();
		page = new GenericObservable<Page>(null);
	}
	
	public void observePage(Observer o)
	{
		page.addObserver(o);
	}
	
	/**
	 * @deprecated
	 * requires knowledge of subtype.
	 * Use createPage(Page) instead.
	 */
	@Deprecated
	public void createPage(int type){
		if(type == 0){
			createPage(new Survey());
		}else{
			createPage(new Test());
		}
	}
	
	public void createPage(Page newPage) {
		page.set(newPage);
		pageNameList.put(page.get().getPageName(), page.get().getTypeString());
	}
	
	public void setPageName(String name){
		page.get().setPageName(name);
		pageNameList.put(name, page.get().getTypeString());
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
	
	public Map<String, String> getPageName() {
		return io.getPages();
	}
	
	/**
	 * @deprecated
	 * This method exist for compatibility.
	 */
	@Deprecated
	private List<String>[] getPageNameList() {
		Map<String, String> pageList = getPageName();
		List<String> pageName[] = new ArrayList[2];
		
		pageName[0] = new ArrayList<String>();
		pageName[1] = new ArrayList<String>();
		
		for (String page : pageList.keySet()) {
			if(pageList.get(page).equals("survey")) {
				pageName[0].add(page);
			} else {
				pageName[1].add(page);
			}
		}
		
		return pageName;
	}
	
	/**
	 * @deprecated
	 * Deprecated because it requires knowledge of subtype.
	 * Use getPageName() instead
	 */
	@Deprecated
	public List<String> getPageName(int type){
		return getPageNameList()[type];
	}
	
	public List<String> displayPage(int index, int type){
		List<String>[] pageNameList = getPageNameList();
		
		List<String> ret = new LinkedList<String>();
		if(pageNameList[type].size() <= index){
			return ret;
		}else{
			page.set(io.readPage(pageNameList[type].get(index)));
			Iterator<Question> questions = page.get().iterator();
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
		page.get().finalize();
		io.writeInfo(getPageNameList());
		io.writePage(page.get());
	}
	
	public int modify(int index){
		if(index >= page.get().getQuestionList().size()){
			return -1;
		}else{
			question = page.get().getQuestion(index);
			return question.getType();
		}
	}
	
	public void addQuestion(Question question)
	{
		page.get().addQuestion(question);
	}
	
	public boolean remove(int index){
		return question.remove(index);
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
		page.set(io.readPage(getPageNameList()[type].get(index)));
		record = new Record();
	}
	
	public void setRecordName(String name){
		record.setPersonName(name);
		iterator = page.get().iterator();
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
		recordName = io.readRecordInfo(page.get().getPageName());
		recordName.add(page.get().getPageName()+"-"+record.getPersonName());
		
		page.get().finalizeAnswer(record);
		
		io.writeReordInfo(page.get().getPageName(), recordName);
		io.writeRecord(page.get().getPageName()+"-"+record.getPersonName(), record);
	}
	
	public void grade(){
		page.get().finalizeAnswer(record);
	}
	
	public String getOutcome(int index, int type){
		this.loadPage(index, type);
		recordName = io.readRecordInfo(page.get().getPageName());
		List<Iterator<Answer>> recordList = new LinkedList<Iterator<Answer>>();
		for(int i=0; i<recordName.size(); i++){
			recordList.add(io.readRecord(recordName.get(i)).iterator());
		}
		Iterator<Question> questionIterator = page.get().iterator();
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
