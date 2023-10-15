import {Component} from '@angular/core';
import {Exam} from '../models/exam.model';
import {ExamService} from '../services/exams.service';

@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.css']
})
export class ExamsComponent {
  exams: Exam[] = [];

  constructor(private examService: ExamService) {
    this.loadExams();
  }

  async loadExams(): Promise<void> {
    try {
      this.exams = await this.examService.getExams();
    } catch (error) {
      console.error('Error al obtener los exámenes', error);
    }
  }
}
