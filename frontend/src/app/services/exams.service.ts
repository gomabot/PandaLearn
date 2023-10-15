import {Injectable} from '@angular/core';
import {Exam} from '../models/exam.model';

@Injectable({
  providedIn: 'root'
})
export class ExamService {
  async getExams(): Promise<Exam[]> {
    const response = await fetch("/api/exams");
    return await response.json();
  }
}
