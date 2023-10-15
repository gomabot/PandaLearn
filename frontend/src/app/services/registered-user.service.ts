import {Injectable} from '@angular/core';
import {RegisteredUser} from "../models/registered-user.model";
import {CryptoService} from "./crypto.service";

@Injectable({
  providedIn: 'root'
})
export class RegisteredUserService {

  registeredUser: RegisteredUser | null = null;

  constructor(private cryptoService: CryptoService) {
  }

  async checkCredentials(userId: string | null, passwordHash: string | null) {
    try {
      const headers = new Headers();
      headers.append("User-id", userId ?? "");
      headers.append("Password-hash", passwordHash ?? "");

      const response = await fetch("/api/user", {
        method: "GET",
        headers: headers
      });

      if (!response.ok) {
        return false;
      }

      const user = await response.json();
      localStorage.setItem("userId", user.id);
      localStorage.setItem("passwordHash", user.passwordHash);
      this.registeredUser = (user)

      return true;
    } catch (error) {
      console.log(error)
      return false;
    }

  }

  async checkLogin(username: string | null, password: string | null) {
    try {
      const passwordHash = await this.cryptoService.hash(password);
      const headers = new Headers();
      headers.append("Username", username ?? "");
      headers.append("Password-hash", passwordHash ?? "");

      const response = await fetch("/api/user/login", {
        method: "GET",
        headers: headers
      });

      if (!response.ok) {
        return false;
      }

      const user = await response.json();
      localStorage.setItem("userId", user.id);
      localStorage.setItem("passwordHash", user.passwordHash);
      this.registeredUser = (user)

      return true;
    } catch (error) {
      console.log(error)
      return false;
    }

  }

  async registerUser(username: string | null, password: string | null) {
    try {
      const passwordHash = await this.cryptoService.hash(password);
      const headers = new Headers();
      headers.append("Username", username ?? "");
      headers.append("Password-hash", passwordHash ?? "");

      const response = await fetch("/api/user", {
        method: "PUT",
        headers: headers
      });

      if (!response.ok) {
        return false;
      }

      const user = await response.json();
      localStorage.setItem("userId", user.id);
      localStorage.setItem("passwordHash", user.passwordHash);
      this.registeredUser = (user)

      return true;
    } catch (error) {
      console.log(error)
      return false;
    }

  }

  async increaseUserLesson(lessonId: number) {
    const headers = new Headers();
    headers.append("User-id", String(this.registeredUser?.id));
    headers.append("Password-hash", <string>this.registeredUser?.passwordHash);
    headers.append("Lesson-id", String(lessonId));
    await fetch("/api/user/increase_lesson_level", {method: "POST", headers});
  }

  logOut() {
    localStorage.removeItem("userId");
    localStorage.removeItem("passwordHash");
    this.registeredUser = null;
  }

  getLesson(): number {
    if (this.registeredUser == null) {
      return -1;
    }
    return this.registeredUser.lesson;
  }

}
