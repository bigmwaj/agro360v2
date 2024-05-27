import { Component, Injectable, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared.module';
import { FieldMetadata } from 'src/app/backed/metadata';
import { MatDialog } from '@angular/material/dialog';
import { EditorDialogComponent } from './editor.dialog.component';

@Injectable({
    providedIn: 'root',
})
@Component({
    standalone: true,
    imports: [CommonModule, SharedModule],
    selector: 'editor',
    templateUrl: './editor.component.html',
})
export class EditorComponent implements OnInit {

    @Input({required: true})
    field: FieldMetadata<string>;

    constructor(public dialog: MatDialog){}
        
    ngOnInit(): void {
        
    }
    
    editDescriptionAction() {
        this.dialog.open(EditorDialogComponent, { data: this.field });
    }
}
