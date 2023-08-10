import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { CommandeBean, CommandeSearchBean } from 'src/app/backed/bean.vente';

interface CommandeModel extends CommandeBean{
    selected:boolean
}

@Component({
    selector: 'app-commande-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    searchForm ?: CommandeSearchBean;
    
    beans : Array<CommandeModel>;

    constructor(private router: Router,
        private route:ActivatedRoute, 
        private http:HttpClient) { }

    ngOnInit(): void {
        this.http
        .get("http://localhost:8080/vente/commande")
        .pipe(map((data:any) => data))
        .subscribe(data => this.beans = <Array<CommandeModel>>data.records);
    }

    addAction(){
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean:CommandeModel){
        this.router.navigate(['edit', bean.commandeCode.value], { relativeTo: this.route })
    }

    copyAction(bean:CommandeModel){
        this.router.navigate(['edit', bean.commandeCode.value], { relativeTo: this.route })
    }

    changeStatusAction(bean:CommandeModel){
        // Open Dialog
    }

    deleteAction(bean:CommandeModel){
        // Open Dialog
    }

    editCategoryAction(){
        // Open Dialog
    }
}
