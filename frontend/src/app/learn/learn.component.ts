import {Component} from '@angular/core';
import {Lesson} from "../models/lesson.model";
import {LessonService} from "../services/lesson.service";
import {RegisteredUserService} from "../services/registered-user.service";

@Component({
  selector: 'app-learn',
  templateUrl: './learn.component.html',
  styleUrls: ['./learn.component.css']
})
export class LearnComponent {
  lessons: Lesson[] = [];
  protected readonly RegisteredUserService = RegisteredUserService;

  constructor(private lessonService: LessonService, public registeredUserService: RegisteredUserService) {
    this.loadLessons();
  }

  async loadLessons(): Promise<void> {
    try {
      this.lessons = await this.lessonService.getLessons();
    } catch (error) {
      console.error('Error al obtener los lecciones', error);
    }
  }
}
