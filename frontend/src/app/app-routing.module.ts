import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ExamsComponent} from "./exams/exams.component";
import {VocabularyComponent} from "./vocabulary/vocabulary.component";
import {LearnComponent} from "./learn/learn.component";
import {ExerciseComponent} from "./exercise/exercise.component";
import {ExamExercisesComponent} from "./exam-exercises/exam-exercises.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./auth.guard";

const routes: Routes = [
  {path: 'home', component: LearnComponent, canActivate: [AuthGuard]},
  {path: 'exercises/:lessonId', component: ExerciseComponent, canActivate: [AuthGuard]},
  {path: 'examExercises/:examId', component: ExamExercisesComponent, canActivate: [AuthGuard]},
  {path: 'vocabulary', component: VocabularyComponent, canActivate: [AuthGuard]},
  {path: 'exams', component: ExamsComponent, canActivate: [AuthGuard]},
  {path: '', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

