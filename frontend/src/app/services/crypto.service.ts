import { Injectable } from '@angular/core';
import { SHA256 } from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {
  async hash(message: string | null) {
    if (message == null) {
      return null
    }
    return SHA256(message).toString();
  }
}

