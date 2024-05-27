import { Component, Injectable } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared.module';
import { MatDialogRef } from '@angular/material/dialog';

@Injectable({
    providedIn: 'root',
})
@Component({
    standalone: true,
    imports: [CommonModule, SharedModule],
    selector: 'upload.dialog',
    templateUrl: './upload.dialog.component.html',
})
export class UploadDialogComponent {

    files: Array<File> = [];

    constructor(private dialogRef: MatDialogRef<UploadDialogComponent>) { }
    
    uploadAction():void{
        this.dialogRef.close(this.files);
    }

    onFileSelected(event: any) {
        for (let i = 0; i < event.target.files.length; i++) {
            const f = event.target.files[i];
            this.files.push(f);
        }
    }

    remove(file:File){
        this.files = this.files.filter( f => f != file)
    }
}
