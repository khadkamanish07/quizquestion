package com.quizapp.learn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizapp.learn.dao.QuestionDao;
import com.quizapp.learn.dao.QuizDao;
import com.quizapp.learn.model.Question;
import com.quizapp.learn.model.QuestionWrapper;
import com.quizapp.learn.model.Quiz;
import com.quizapp.learn.model.Response;

@Service
public class QuizService {
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> questions = questionDao.findQuestionByCategory(category, numQ);
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		
		quizDao.save(quiz);
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
		
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(int id) {
		
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionFromDB = quiz.get().getQuestions();
		
		List<QuestionWrapper> questionsForUser  = new ArrayList<>();
		
		for(Question q: questionFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestion() , q.getOption1() , q.getOption2() , q.getOption3() , q.getOption4());
			questionsForUser.add(qw);
		}
 		
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
		
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		
		/*  Request Body provided in postman for id= 1
		 * [ { "id": 8, "response":"Stack" }, { "id":3, "response":"Object" }, { "id":4,
		 * "response":"Abstract Factory" }, { "id":2, "response":"To declare a constant"
		 * }, { "id":5, "response":"Encapsulation" } ]
		 */
		
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		
		int right=0;
		int count=0;
		for(Response response: responses) {
			if(response.getResponse().equals(questions.get(count).getAnswer())) {
				right++;
				
			}
			count++;
		}
		
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
	
	

}
