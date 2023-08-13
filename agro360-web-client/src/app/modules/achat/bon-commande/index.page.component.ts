import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { BonCommandeBean, BonCommandeSearchBean } from 'src/app/backed/bean.achat';

interface BonCommandeModel extends BonCommandeBean{
    selected:boolean
}

@Component({
    selector: 'achat-bonCommande-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    searchForm ?: BonCommandeSearchBean;
    
    beans : Array<BonCommandeModel>;

    constructor(private router: Router,
        private route:ActivatedRoute, 
        private http:HttpClient) { }

    ngOnInit(): void {
        this.http
        .get("http://localhost:8080/achat/bon-commande")
        .pipe(map((data:any) => data))
        .subscribe(data => this.beans = <Array<BonCommandeModel>>data.records);
    }

    addAction(){
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean:BonCommandeModel){
        this.router.navigate(['edit', bean.bonCommandeCode.value], { relativeTo: this.route })
    }

    copyAction(bean:BonCommandeModel){
        this.router.navigate(['edit', bean.bonCommandeCode.value], { relativeTo: this.route })
    }

    changeStatusAction(bean:BonCommandeModel){
        // Open Dialog
    }

    deleteAction(bean:BonCommandeModel){
        // Open Dialog
    }

    editCategoryAction(){
        // Open Dialog
    }
}
