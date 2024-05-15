import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared.module';
import { FieldMetadata } from 'src/app/backed/metadata';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Injectable({
    providedIn: 'root',
})
@Component({
    standalone: true,
    imports: [CommonModule, SharedModule],
    selector: 'editor.dialog',
    templateUrl: './editor.dialog.component.html',
})
export class EditorDialogComponent implements OnInit {

    field: FieldMetadata<string>;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: FieldMetadata<string>
    ){}
        
    ngOnInit(): void {
       this.field = this.data
    }
}
