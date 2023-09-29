import { MatSnackBar } from '@angular/material/snack-bar';
import { Message } from 'src/app/backed/message';
import { FlashMessageComponent } from '../component/flash-message.component';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class UIService {
    title: string = 'To do';
    constructor(public _snackBar: MatSnackBar) {

    }

    displayFlashMessage(messages: Array<Message>) {
        this._snackBar.openFromComponent(FlashMessageComponent, {
            duration: 5 * 1000,
            data: messages
        });
    }

    setTitle(title: string) {
        this.title = title
    }
}
