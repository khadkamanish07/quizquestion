package com.quizapp.learn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizapp.learn.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer>{
	

}
