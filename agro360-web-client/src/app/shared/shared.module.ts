import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditableListRendererComponent } from './components/editable-list/editable-list-renderer.component';
import { AlertModalComponent } from './components/modal/alert-modal.component';
import { ChangeStatusModalComponent } from './components/modal/change-status-modal.component';
import { ConfirmModalComponent } from './components/modal/confirm-modal.component';
import { ImportModalComponent } from './components/modal/import-modal.component';
import { AppNumberPipe } from './pipes/app-number.pipe';
import { EditableListPipe } from './pipes/editable-list.pipe';
import { YesNoPipe } from './pipes/yes-no.pipe';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppNumberPipe, 
    YesNoPipe,
    EditableListPipe,

    EditableListRendererComponent,

    AlertModalComponent,
    ConfirmModalComponent,
    ImportModalComponent,
    ChangeStatusModalComponent,
    BreadcrumbComponent,
  ],

  imports: [
    CommonModule,
    NgbModule,    
    FormsModule, 
    ReactiveFormsModule,  
    RouterModule  
  ],

  exports:[
    AppNumberPipe, 
    YesNoPipe,
    EditableListRendererComponent,
    EditableListPipe,
    BreadcrumbComponent
  ]
})
export class SharedModule { }
