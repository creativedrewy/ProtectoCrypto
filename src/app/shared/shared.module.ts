import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatToolbarModule,
    MatSelectModule,
    MatTabsModule,
    MatInputModule,
    MatSnackBarModule
  ],
  declarations: [ ],
  exports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatTabsModule,
    MatInputModule,
    MatSelectModule,
    MatToolbarModule,
    MatSnackBarModule
  ]
})
export class SharedModule {}