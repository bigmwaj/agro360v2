import { Component, Injectable, OnInit, inject } from '@angular/core';
import { MatSnackBarRef, MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { Message } from 'src/app/backed/message';
import { MessageTypeEnumVd } from 'src/app/backed/vd.common';

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

    getBootrapType(type:MessageTypeEnumVd):string{
        switch(type){
            case MessageTypeEnumVd.ERROR: return 'danger';
            case MessageTypeEnumVd.INFO: return 'info';
            case MessageTypeEnumVd.SUCCESS: return 'success';
            case MessageTypeEnumVd.WARNING: return 'warning';
            default: return 'primary';
        }
    }
    
    ngOnInit(): void {
        this.messages = this.snackBarRef.containerInstance.snackBarConfig.data;
    }
    
}
