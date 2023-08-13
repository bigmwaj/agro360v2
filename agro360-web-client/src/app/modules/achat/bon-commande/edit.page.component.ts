import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BonCommandeBean } from 'src/app/backed/bean.achat';

const BASE_URL = "http://localhost:8080/achat/bon-commande";

interface BonCommandeModel extends BonCommandeBean {
    
}

@Component({
    selector: 'achat-bonCommande-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: BonCommandeModel;

    bonCommandeCode: string | null;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

    ngOnInit(): void {

        this.route.paramMap.subscribe(params => {
            this.bonCommandeCode = params.get('bonCommandeCode');

            if( this.bonCommandeCode != null ){
                this.http
                    .get<any>(BASE_URL + `/${this.bonCommandeCode}`)
                    .subscribe(data => this.bean = data);
            }else{
                this.bean = <BonCommandeModel>{};
            }   
        });
    }

    addAction() {
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

   
}
