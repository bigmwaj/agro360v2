//https://angular.io/guide/router#setting-the-page-title
import { MatSnackBar } from '@angular/material/snack-bar';
import { Message } from 'src/app/backed/message';
import { FlashMessageComponent } from '../component/flash-message.component';
import { Injectable } from '@angular/core';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { MessageTypeEnumVd } from 'src/app/backed/vd.common';
import { RouterStateSnapshot, TitleStrategy } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Injectable({
    providedIn: 'root'
})
export class UIService  extends TitleStrategy{
    title: string = 'Change from ui.service'
    
    constructor(public _snackBar: MatSnackBar, private readonly pageTitle: Title) {
        super();
    }

    override updateTitle(routerState: RouterStateSnapshot) {
        const title = this.buildTitle(routerState);
        if (title !== undefined) {
          this.pageTitle.setTitle(`My Application | ${title}`);
        }
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
        //this.updateTitle(`The title is set to ${title}`)
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
