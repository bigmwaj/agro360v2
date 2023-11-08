//https://angular.io/guide/component-interaction#parent-and-children-communicate-via-a-service 
import { MatSnackBar } from '@angular/material/snack-bar';
import { Message } from 'src/app/backed/message';
import { FlashMessageComponent } from '../component/flash-message.component';
import { Injectable } from '@angular/core';
import { Observable, throwError, Subject, BehaviorSubject, never } from 'rxjs';
import { MessageTypeEnumVd } from 'src/app/backed/vd.common';

@Injectable({
    providedIn: 'root'
})
export class UIService {
    title: string = 'Change from ui.service';

    //private node: Subject<Node> = new BehaviorSubject<Node>([]);
    
    constructor(public _snackBar: MatSnackBar) {

    }

    displayFlashMessage(messages: Array<Message>) {
        this._snackBar.openFromComponent(FlashMessageComponent, {
            duration: 50 * 1000,
            data: messages
        });
    }

    setTitle(title: string) {
        this.title = title
        console.log(`The title is set to ${title}`)
    }

    handleError(err:any, o:Observable<any>){
        const status = err.status;
        switch (status) {
            case 500:
                const msg:Message = {
                    type: MessageTypeEnumVd.ERROR,
                    message:err.message
                }
                this.displayFlashMessage([msg]);
                break;
        
            default:
                break;
        }
        
        return throwError(() => `Une erreur est survenue!`);
    }
}
