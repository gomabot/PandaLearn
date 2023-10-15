import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {AppRoutingModule} from './app-routing.module';
import {VocabularyComponent} from './vocabulary/vocabulary.component';
import {LearnComponent} from './learn/learn.component';
import {ExamsComponent} from './exams/exams.component';
import {FormsModule} from "@angular/forms";
import {LessonService} from "./services/lesson.service";
import {RegisteredUserService} from "./services/registered-user.service";
import {VocabularyService} from "./services/vocabulary.service";
import {ExerciseComponent} from './exercise/exercise.component';
import {ExerciseService} from "./services/exercise.service";
import {ExamService} from "./services/exams.service";
import {ExamExercisesComponent} from './exam-exercises/exam-exercises.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {CryptoService} from './services/crypto.service';
import {AuthGuard} from "./auth.guard";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    VocabularyComponent,
    LearnComponent,
    ExamsComponent,
    ExerciseComponent,
    ExamExercisesComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [LessonService, RegisteredUserService, VocabularyService, ExerciseService, ExamService, AuthGuard, CryptoService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
