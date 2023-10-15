export class Vocabulary {
  id: number;
  lessonId: number;
  word: string;
  pinyin: string;
  translation: string;

  constructor(id: number, lessonId: number, word: string, pinyin: string, translation: string) {
    this.id = id;
    this.lessonId = lessonId;
    this.word = word;
    this.pinyin = pinyin;
    this.translation = translation;
  }
}
