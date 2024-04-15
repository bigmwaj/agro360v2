import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Message } from 'src/app/backed/message';
import { FlashMessageComponent } from '../component/flash-message.component';
import { ElementRef, Injectable, Optional } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { MessageTypeEnumVd } from 'src/app/backed/vd.common';

export class UIConfig {
    title = 'Welcome';
}

@Injectable({
    providedIn: 'root'
})
export class UIService {

    private title: string = "Bienvenue";

    pageTitle:ElementRef
    
    constructor(public _snackBar: MatSnackBar, @Optional() config?: UIConfig) {
        console.log('Instanciation ... ')
        if (config) { this.title = config.title; }
    }

    setPageTitle(pageTitle:ElementRef){
        this.pageTitle = pageTitle
        this.pageTitle.nativeElement.innerHTML = this.title
    }

    setTitle(title: string) {
        this.pageTitle.nativeElement.innerHTML = title
    }

    setBreadcrumb(items: string[]) {
        console.log('Set breadcrumb ...')

        /*

<nav aria-label="breadcrumb" style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='currentColor'/%3E%3C/svg%3E&#34;);">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item">Ventes</li>
                                    <li class="breadcrumb-item active" aria-current="page">Commandes</li>
                                </ol>
                            </nav>




        */
    const lis = items.map( e => `<li class="breadcrumb-item">${e}</li>`)
        .reduce((e, f) => e+f);
    const nav = `
            <nav aria-label="breadcrumb" style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='currentColor'/%3E%3C/svg%3E&#34;);">
                <ol class="breadcrumb">
                    ${lis}
                </ol>
            </nav>`;

        this.pageTitle.nativeElement.innerHTML = nav
    }
    
    horizontalPosition: MatSnackBarHorizontalPosition = 'center';

    verticalPosition: MatSnackBarVerticalPosition = 'top';

    displayFlashMessage(messages: Array<Message>) {
        this._snackBar.openFromComponent(FlashMessageComponent, {
            duration: 5 * 1000,
            data: messages,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

    handleError(err:any, o:Observable<any>){
        console.log(err)
        const status = err.status;
        let msg:Message = {            
            type: MessageTypeEnumVd.ERROR,
            message:`Une erreur est survenue pendant le traitement de votre requête.`
        }
        switch (status) {
            case 500:
                msg = {
                    type: MessageTypeEnumVd.ERROR,
                    message:err.error.message
                }
                break;

            case 404:
                 msg = {
                    type: MessageTypeEnumVd.ERROR,
                    message:`La ressource demandée n'a pas été trouvée!`
                }
                break;
                
                default:
                    break;
            }
            
            this.displayFlashMessage([msg]);
        return throwError(() => msg.message);
    }
}
