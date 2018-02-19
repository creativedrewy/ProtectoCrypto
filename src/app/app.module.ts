import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { SharedModule } from './shared/shared.module';

import { AppComponent } from './app.component';
import { ProtectFormComponent } from './protect-form/protect-form.component';
import { ProtectService } from './service/protect.service';

@NgModule({
  declarations: [
    AppComponent,
    ProtectFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    SharedModule
  ],
  providers: [ProtectService],
  bootstrap: [AppComponent]
})
export class AppModule { }
