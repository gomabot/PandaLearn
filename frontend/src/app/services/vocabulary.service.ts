import {Injectable} from '@angular/core';
import {Vocabulary} from "../models/vocabulary.model";

@Injectable({
  providedIn: 'root'
})
export class VocabularyService {
  async getVocabulary(): Promise<Vocabulary[]> {
    const response = await fetch("/api/vocabulary");
    return await response.json();
  }
}
