import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommandeBean } from 'src/app/backed/bean.vente';

const BASE_URL = "http://localhost:8080/vente/commande";

interface CommandeModel extends CommandeBean {
    
}

@Component({
    selector: 'vente-commande-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: CommandeModel;

    commandeCode: string | null;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

    ngOnInit(): void {

        this.route.paramMap.subscribe(params => {
            this.commandeCode = params.get('commandeCode');

            if( this.commandeCode != null ){
                this.http
                    .get<any>(BASE_URL + `/${this.commandeCode}`)
                    .subscribe(data => this.bean = data);
            }else{
                this.bean = <CommandeModel>{};
            }   
        });
    }

    addAction() {
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

   
}
