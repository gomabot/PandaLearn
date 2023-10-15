export class RegisteredUser {
  id: number;
  username: string;
  passwordHash: string;
  lesson: number;

  constructor(id: number, username: string, passwordHash: string, isPremium: boolean, lesson: number) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.lesson = lesson;
  }
}
