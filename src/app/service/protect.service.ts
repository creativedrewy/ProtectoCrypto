import { Injectable } from '@angular/core';

@Injectable()
export class ProtectService {

  constructor() { }

  public doSomething(): String {
    return "The first field is for the key"
  }
}
