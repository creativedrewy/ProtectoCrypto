import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatButtonModule, MatToolbarModule } from '@angular/material'

import { AppComponent } from './app.component';
import { ProtectFormComponent } from './protect-form/protect-form.component';


@NgModule({
  declarations: [
    AppComponent,
    ProtectFormComponent
  ],
  imports: [
    BrowserModule,
    MatButtonModule,
    MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
