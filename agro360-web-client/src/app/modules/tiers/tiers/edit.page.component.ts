import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TiersBean } from 'src/app/backed/bean.tiers';

const BASE_URL = "http://localhost:8080/tiers/tiers";

interface TiersModel extends TiersBean{
    selected:boolean
}

@Component({
    selector: 'app-tiers-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    tiersCode: string|null;
    
    bean : TiersModel;

    constructor(private router: Router,
        private route:ActivatedRoute, 
        private http:HttpClient) { }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.tiersCode = params.get('tiersCode');

            if( this.tiersCode != null ){
                this.http
                    .get<any>(BASE_URL +`/${this.tiersCode}`)
                    .subscribe(data => this.bean = data);
            }else{
                this.bean = <TiersModel>{};
            }   
        });
    }

    addAction(){
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean:TiersModel){
        this.router.navigate(['edit', bean.tiersCode.value], { relativeTo: this.route })
    }

    copyAction(bean:TiersModel){
        this.router.navigate(['edit', bean.tiersCode.value], { relativeTo: this.route })
    }

    changeStatusAction(bean:TiersModel){
        // Open Dialog
    }

    deleteAction(bean:TiersModel){
        // Open Dialog
    }

    editCategoryAction(){
        // Open Dialog
    }
}
