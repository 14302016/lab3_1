package Paper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import Question.Question;

public abstract class Page {
	
	String pageName;
	
	List<Question> questionList = new LinkedList<Question>();
	
	public HashMap<String, String> store() {
		HashMap<String, String> returnVal = new HashMap<String, String>();
		returnVal.put("type", this.getTypeString());
		returnVal.put("pagename", this.getPageName());
		
		return returnVal;
	}
	
	public void setPageName(String pageName){
		this.pageName = pageName;
	}
	
	public String getPageName(){
		return this.pageName;
	}
	
	public void addQuestion(Question question){
		questionList.add(question);
	}
	
	public Question getQuestion(int index){
		if(index >= questionList.size()){
			return null;
		}else{
			return questionList.get(index);
		}
	}
	
	public List<Question> getQuestionList(){
		return questionList;
	}
	
	public int getQuestionSize(){
		return questionList.size();
	}
	
	public Iterator<Question> iterator(){
		return questionList.iterator();
	}
	
	public void finalize(){
		
	}
	
	public void finalizeAnswer(Record record) {
		
	}

	public abstract String getTypeString();
}
