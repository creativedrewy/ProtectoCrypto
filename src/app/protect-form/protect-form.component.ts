import { Component, OnInit } from '@angular/core';
import { ProtectService } from '../service/protect.service';

@Component({
  selector: 'app-protect-form',
  templateUrl: './protect-form.component.html',
  styleUrls: ['./protect-form.component.css']
})
export class ProtectFormComponent implements OnInit {
  formKey: String = ""
  formText: String = ""
  formResult: String = ""

  constructor(private protectService: ProtectService) { }

  ngOnInit() {
    this.formResult = this.protectService.testValue;
  }

  encryptData() {
    
  }

}
