export class Lesson {
  constructor(id: number, name: string) {
    this._id = id;
    this._name = name;
  }

  private _id: number;

  get id(): number {
    return this._id;
  }

  private _name: string;

  get name(): string {
    return this._name;
  }
}
