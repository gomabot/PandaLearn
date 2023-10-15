import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Exercise} from '../models/exercise.model';
import {ExerciseService} from '../services/exercise.service';

@Component({
  selector: 'app-exam-exercises',
  templateUrl: './exam-exercises.component.html',
  styleUrls: ['./exam-exercises.component.css']
})
export class ExamExercisesComponent implements OnInit {
  currentExerciseIndex: number = 0; // Índice del ejercicio actual
  correctAnswers: number = 0; // Contador de respuestas correctas
  exercises: Exercise[] = []; // Lista de ejercicios
  showSummary: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private exerciseService: ExerciseService
  ) {
  }

  ngOnInit() {
    // Obtener el ID del examen desde la URL
    this.route.paramMap.subscribe(params => {
      const examId = +params.get('examId')!;
      this.loadExercises(examId);
    });
  }

  async loadExercises(examId: number): Promise<void> {
    try {
      // Obtener la lista de ejercicios para el examen seleccionado desde el servicio
      this.exercises = await this.exerciseService.getExercisesForExam(examId);
    } catch (error) {
      console.error('Error al obtener los ejercicios', error);
      // Aquí puedes manejar el error según tus necesidades (por ejemplo, mostrar un mensaje de error al usuario)
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
    // Desordenar con el algoritmo de Fisher-Yates
    for (let i = answerOptions.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [answerOptions[i], answerOptions[j]] = [answerOptions[j], answerOptions[i]];
    }
    return answerOptions;
  }

  answerSelected(answer: string) {
    // Verificar si la respuesta seleccionada es correcta
    const currentExercise = this.exercises[this.currentExerciseIndex];
    if (answer === currentExercise.correctAnswer) {
      this.correctAnswers++;
    }

    // Pasar al siguiente ejercicio o finalizar si no hay más ejercicios
    if (this.currentExerciseIndex < this.exercises.length - 1) {
      this.currentExerciseIndex++;
    } else {
      this.showSummary = true;
    }
  }

  returnToHome() {
    this.router.navigate(['exams']);
  }
}
