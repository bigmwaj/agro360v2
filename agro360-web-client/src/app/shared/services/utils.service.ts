import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DomSanitizer, Title } from '@angular/platform-browser';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, throwError } from 'rxjs';
import { AppConstants } from 'src/app/core/app-constants';
import { AlertModalComponent } from '../components/modal/alert-modal.component';

@Injectable({
    providedIn: 'root'
})
export class UtilsService {

    private static ngbModal:NgbModal;

    constructor(
        private titleService:Title, 
        private modalService:NgbModal, 
        private sanitizer:DomSanitizer){
        UtilsService.ngbModal = this.modalService;
    }

    private appTitle = 'ProFarmerSoft';

    /**
     * Permet d'indiquer le titre de la page via la balise <title>
     * @param title Titre de la page
     */
    setPageTitle(title:string){
        this.titleService.setTitle(`${this.appTitle} ${title}`);
    }
    
    /**
     * 
     * @param url l'url à accéder
     * @returns L'url sans préfixe impropre à l'affichage
     */
     sanitize(url:string){
        return this.sanitizer.bypassSecurityTrustUrl(url);
    }

    /**
     * 
     * @param error 
     * @returns 
     */
    handleError(error: HttpErrorResponse):Observable<never> {
        const inst = UtilsService.ngbModal.open(AlertModalComponent).componentInstance;
        inst.title = "Une erreur s'est produite";
        if ( error.status === 0 ) {
            // A client-side or network error occurred. Handle it accordingly.
            inst.message = error.error;
        } else if(error.status == 401){
            inst.title = `Erreur ${error.status}`;
            inst.message = `Paramètres d'authentification non valides. <a href="${AppConstants.LOGIN_PAGE_URL}">Merci de vous connecter</a>`;
        } else {
            inst.title = `Erreur ${error.status}`;
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            inst.message = error.error;
        }
        // Return an observable with a user-facing error message.
        return throwError(() => `Une erreur s'est produite`);
    }

    /**
     * 
     * @param title 
     * @param message 
     */
    notify(title:string, message:string){
        const inst = UtilsService.ngbModal.open(AlertModalComponent).componentInstance;
        inst.title = title
        inst.message = message;
    }
}