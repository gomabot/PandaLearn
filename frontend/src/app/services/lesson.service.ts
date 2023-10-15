import {Injectable} from '@angular/core';
import {Lesson} from "../models/lesson.model";

@Injectable({
  providedIn: 'root'
})
export class LessonService {
  async getLessons(): Promise<Lesson[]> {
    const response = await fetch("/api/lessons");
    return await response.json();
  }
}
