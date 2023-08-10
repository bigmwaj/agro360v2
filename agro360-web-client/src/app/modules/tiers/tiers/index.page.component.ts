import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { TiersBean, TiersSearchBean } from 'src/app/backed/bean.tiers';

interface TiersModel extends TiersBean{
    selected:boolean
}

@Component({
    selector: 'app-tiers-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    searchForm ?: TiersSearchBean;
    
    beans : Array<TiersModel>;

    constructor(private router: Router,
        private route:ActivatedRoute, 
        private http:HttpClient) { }

    ngOnInit(): void {
        this.http
        .get("http://localhost:8080/tiers/tiers")
        .pipe(map((data:any) => data))
        .subscribe(data => this.beans = <Array<TiersModel>>data.records);
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
