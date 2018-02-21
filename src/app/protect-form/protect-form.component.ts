import { Component, OnInit } from '@angular/core';
import { ProtectService } from '../service/protect.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-protect-form',
  templateUrl: './protect-form.component.html',
  styleUrls: ['./protect-form.component.css']
})
export class ProtectFormComponent implements OnInit {
  formKey: string = ""
  formText: string = ""
  formResult: string = ""

  constructor(private protectService: ProtectService, 
              private snackBar: MatSnackBar) { }

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
      if (copySuccess) {
        this.snackBar.open("Contents copied to clipboard", "OK", { duration: 2000 });
      } else {
        throw new Error()
      }
    } catch (err) {
      this.snackBar.open("Contents not copied to clipboard", "OK", { duration: 2000, panelClass: "snack-bar-color" });
    }
  }

}
