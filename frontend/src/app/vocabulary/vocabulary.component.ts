import {Component} from '@angular/core';
import {VocabularyService} from "../services/vocabulary.service";
import {RegisteredUserService} from "../services/registered-user.service";
import {Vocabulary} from "../models/vocabulary.model";
import {LessonService} from "../services/lesson.service";
import {Lesson} from "../models/lesson.model";

@Component({
  selector: 'app-vocabulary',
  templateUrl: './vocabulary.component.html',
  styleUrls: ['./vocabulary.component.css']
})
export class VocabularyComponent {
  vocabularyList: Vocabulary[] = [];
  lessons: Lesson[] = [];

  constructor(private vocabularyService: VocabularyService, public registeredUserService: RegisteredUserService, private lessonService: LessonService) {
    this.loadLessons();
    this.loadVocabulary();
  }

  async loadLessons() {
    this.lessons = await this.lessonService.getLessons();
  }

  async loadVocabulary() {
    this.vocabularyList = await this.vocabularyService.getVocabulary();
    this.vocabularyList.sort((a, b) => a.lessonId - b.lessonId);
  }
}
