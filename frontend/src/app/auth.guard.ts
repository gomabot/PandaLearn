import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {RegisteredUserService} from "./services/registered-user.service";

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private registeredUserService: RegisteredUserService) {
  }

  canActivate(): boolean {
    const userIsLoaded = this.registeredUserService.registeredUser != null;
    console.log("authguard")
    if (userIsLoaded) {
      return true;
    } else {
      this.router.navigate(['']);
      return false;
    }
  }
}
