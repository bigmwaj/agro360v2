import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Message } from 'src/app/backed/message';
import { FlashMessageComponent } from '../flash-message.component';
import { ElementRef, Injectable, Optional } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { MessageTypeEnumVd } from 'src/app/backed/vd.common';

export class UIConfig {
    title:string = 'Welcome';
}

export class BreadcrumbItem {
    label:string
    parent?:BreadcrumbItem

    constructor(label:string, parent ?:BreadcrumbItem){
        this.label = label;
        this.parent = parent
    }

    addAndReturnChildItem(label:string):BreadcrumbItem{
        let bc =  new BreadcrumbItem(label, this)
        bc.parent = this
        return bc;
    }
}

export interface BreadcrumbItemRegister{
    refreshPageTitle():void
}

@Injectable({
    providedIn: 'root'
})
export class UIService {

    private title: string = "Bienvenue!";

    pageTitle:ElementRef
    
    constructor(public _snackBar: MatSnackBar, @Optional() config?: UIConfig) {
        if (config) { this.title = config.title; }
    }

    setPageTitle(pageTitle:ElementRef){
        this.pageTitle = pageTitle        
        this._setBreadcrumb([this.title])
    }

    setTitle(title: string) {
        this._setBreadcrumb([title])
    }

    setBreadcrumb(item: BreadcrumbItem) {
        let curr : undefined|BreadcrumbItem = item
        let itemsLabel = []

        while(curr !== null && curr !== undefined){
            itemsLabel.unshift(curr.label)
            curr = curr.parent
        }

        this._setBreadcrumb(itemsLabel)
    }

    private _setBreadcrumb(items: string[]) {
        /*

        <nav aria-label="breadcrumb" 
            style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='currentColor'/%3E%3C/svg%3E&#34;);">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Ventes</li>
                <li class="breadcrumb-item active" aria-current="page">Commandes</li>
            </ol>
        </nav>

        */
    const lis = items.map( e => `<li class="breadcrumb-item">${e}</li>`)
        .reduce((e, f) => e+f);
    const nav = `
            <nav aria-label="breadcrumb" class="fs-6"
                style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='currentColor'/%3E%3C/svg%3E&#34;);">
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
            duration: 100 * 1000,
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
