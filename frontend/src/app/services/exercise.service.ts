import {Injectable} from '@angular/core';
import {Exercise} from '../models/exercise.model';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  async getExercisesForLesson(lessonId: number): Promise<Exercise[]> {
    const headers = new Headers();
    headers.append("Lesson-id", String(lessonId));
    const response = await fetch("/api/lesson/exercises", {headers});
    return await response.json();
  }

  async getExercisesForExam(examId: number): Promise<Exercise[]> {
    const headers = new Headers();
    headers.append("Exam-id", String(examId));
    const response = await fetch("/api/exam/exercises", {headers});
    return await response.json();
  }
}
