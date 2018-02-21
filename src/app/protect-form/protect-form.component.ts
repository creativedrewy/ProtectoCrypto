import { Component, OnInit } from '@angular/core';
import { ProtectService } from '../service/protect.service';

@Component({
  selector: 'app-protect-form',
  templateUrl: './protect-form.component.html',
  styleUrls: ['./protect-form.component.css']
})
export class ProtectFormComponent implements OnInit {
  formKey: string = ""
  formText: string = ""
  formResult: string = ""

  constructor(private protectService: ProtectService) { }

  ngOnInit() { }

  encryptData() {
    this.formResult = this.protectService.encryptText(this.formKey, this.formText);
  }

  decryptData() {
    this.formResult = this.protectService.decryptText(this.formKey, this.formText);
  }

  copyText(inputElement: HTMLElement) {
    (document.getElementById(inputElement.id) as any).select()

    try {
      var copySuccess = document.execCommand('copy');
      //TODO: Success snackbar/toast
    } catch (err) {
      //TODO: Display error snackbar/toast here
    }
  }

}
