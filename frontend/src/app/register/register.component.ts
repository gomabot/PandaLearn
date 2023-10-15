import {Component} from '@angular/core';
import {RegisteredUserService} from "../services/registered-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = "";
  password: string = "";
  errorMessage: string = "";

  constructor(
    private registeredUserService: RegisteredUserService,
    private router: Router
  ) {
  }

  async onSubmit(username: string, password: string) {
    if (await this.registeredUserService.registerUser(username, password)) {
      await this.router.navigate(['home'])
    } else {
      this.errorMessage = "No se puede crear el usuario";
    }
  }
}
