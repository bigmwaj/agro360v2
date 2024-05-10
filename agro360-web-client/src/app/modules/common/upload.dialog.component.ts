import { Component, Injectable, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared.module';

@Injectable({
    providedIn: 'root',
})
@Component({
    standalone: true,
    imports: [CommonModule, SharedModule],
    selector: 'upload.dialog',
    templateUrl: './upload.dialog.component.html',
})
export class UploadDialogComponent implements OnInit {
        
    ngOnInit(): void {
       
    }
    
    uploadAction():void{

    }
}
