import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { SharedModule } from './shared/shared.module';
import { ServiceWorkerModule } from '@angular/service-worker';
import { AppComponent } from './app.component';
import { ProtectFormComponent } from './protect-form/protect-form.component';
import { ProtectService } from './service/protect.service';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    ProtectFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    SharedModule,
    ServiceWorkerModule.register('ngsw-worker.js',{enabled: environment.production}),
  ],
  providers: [ProtectService],
  bootstrap: [AppComponent]
})
export class AppModule { }
