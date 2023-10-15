export class Exercise {
  id: number;
  lessonId: number;
  examId: number;
  question: string;
  correctAnswer: string;
  incorrectAnswer1: string;
  incorrectAnswer2: string;


  constructor(id: number, lessonId: number, examId: number, question: string, correctAnswer: string, incorrect1: string, incorrect2: string) {
    this.id = id;
    this.lessonId = lessonId;
    this.examId = examId;
    this.question = question;
    this.correctAnswer = correctAnswer;
    this.incorrectAnswer1 = incorrect1;
    this.incorrectAnswer2 = incorrect2;
  }
}
