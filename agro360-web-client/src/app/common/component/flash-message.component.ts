import { Component, Injectable, OnInit, inject } from '@angular/core';
import { MatSnackBarRef, MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { Message } from 'src/app/backed/message';

@Injectable({
    providedIn: 'root',
})
@Component({
    standalone: true,
    imports: [MatSnackBarModule, CommonModule],
    selector: 'flash-message',
    templateUrl: './flash-message.component.html'
})
export class FlashMessageComponent implements OnInit {
    snackBarRef = inject(MatSnackBarRef);

    messages: Array<Message>;
    
    ngOnInit(): void {
        this.messages =this.snackBarRef.containerInstance.snackBarConfig.data;
    }
    
}
