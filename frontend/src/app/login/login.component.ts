import {Component, OnInit} from '@angular/core';
import {RegisteredUserService} from "../services/registered-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = "";
  password: string = "";
  errorMessage: string = "";

  constructor(
    private registeredUserService: RegisteredUserService,
    private router: Router
  ) {
  }

  async ngOnInit() {
    let userId: string | null = localStorage.getItem("userId")
    let passwordHash: string | null = localStorage.getItem("passwordHash")
    if (!userId || !passwordHash) {
      return;
    }
    if (await this.registeredUserService.checkCredentials(userId, passwordHash)) {
      await this.router.navigate(['home']);
    }
  }

  async onSubmit(username: string, password: string) {
    if (await this.registeredUserService.checkLogin(username, password)) {
      await this.router.navigate(['home'])
    } else {
      this.errorMessage = "credenciales incorrectas";
    }

  }


}
