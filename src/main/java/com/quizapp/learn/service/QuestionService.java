package com.quizapp.learn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizapp.learn.dao.QuestionDao;
import com.quizapp.learn.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
		List<Question> questions = questionDao.findAll();
//        System.out.println("Number of questions retrieved: " + questions.size());
//        System.out.println(questions);
        return new ResponseEntity<>(questions  , HttpStatus.OK);
		}
		catch(Exception e){
		      e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>()  , HttpStatus.BAD_REQUEST);
		}

	
	public List<Question> getAllQuestions(String category) {
		return questionDao.findByCategory(category) ;
	}

	public String addQuestion(Question question) {
		try {
			questionDao.save(question);
			return "Successfully added a new question to Database";
		}
		catch(Exception e) {
			  e.printStackTrace(); // This will print the exception details to the console
			    return "Error: " + e.getMessage(); // You can customize the error message as needed
		}
	}


	public ResponseEntity<String> deleteQuestion(int id) {
		try {
			questionDao.deleteById(id);
			return new ResponseEntity<>( "Successfully deleted item with id= " + id + "a new question to Database", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	

}
