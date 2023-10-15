import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Exercise} from '../models/exercise.model';
import {ExerciseService} from '../services/exercise.service';
import {RegisteredUserService} from "../services/registered-user.service";

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.css']
})
export class ExerciseComponent implements OnInit {
  currentExerciseIndex: number = 0; // Índice del ejercicio actual
  correctAnswers: number = 0; // Contador de respuestas correctas
  exercises: Exercise[] = []; // Lista de ejercicios
  showSummary: boolean = false;
  lessonId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private exerciseService: ExerciseService,
    private registeredUserService: RegisteredUserService
  ) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const lessonId = +params.get('lessonId')!;
      this.loadExercises(lessonId);
    });
  }

  async loadExercises(lessonId: number): Promise<void> {
    this.lessonId = lessonId;
    try {
      this.exercises = await this.exerciseService.getExercisesForLesson(lessonId);
    } catch (error) {
      console.error('Error al obtener los ejercicios', error);
    }
  }

  getAnswerOptions() {
    const currentExercise = this.exercises[this.currentExerciseIndex];
    return this.shuffleAnswerOptions([
      currentExercise.correctAnswer,
      currentExercise.incorrectAnswer1,
      currentExercise.incorrectAnswer2
    ]);
  }

  shuffleAnswerOptions(answerOptions: string[]) {
    for (let i = answerOptions.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [answerOptions[i], answerOptions[j]] = [answerOptions[j], answerOptions[i]];
    }
    return answerOptions;
  }

  answerSelected(answer: string) {
    const currentExercise = this.exercises[this.currentExerciseIndex];
    if (answer === currentExercise.correctAnswer) {
      this.correctAnswers++;
    }

    if (this.currentExerciseIndex < this.exercises.length - 1) {
      this.currentExerciseIndex++;
    } else {
      if (this.correctAnswers >= 7 && this.lessonId === this.registeredUserService.registeredUser?.lesson) {
        this.registeredUserService.increaseUserLesson(this.lessonId);
      }
      this.showSummary = true;
    }
  }


  returnToHome() {
    this.router.navigate(['']);
  }

}
