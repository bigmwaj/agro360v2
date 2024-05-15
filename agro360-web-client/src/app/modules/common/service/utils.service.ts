import { Injectable } from '@angular/core';
import { DomSanitizer, Title } from '@angular/platform-browser';

@Injectable({
    providedIn: 'root'
})
export class UtilsService {

    constructor(
        private titleService:Title, 
        private sanitizer:DomSanitizer){
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
}