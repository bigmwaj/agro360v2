import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TiersBean } from 'src/app/backed/bean.tiers';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { MatIconModule } from '@angular/material/icon';
import { CategoryBlockComponent } from './category.block.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports:[
        CommonModule,
        CategoryBlockComponent,
        MatButtonModule,
        MatIconModule,
        
        SharedModule
    ],
    selector: 'tiers-tiers-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: TiersBean;

    pageTitle: string = "Edition";

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog) { }

    isCreation(): boolean {
        let path = this.route.routeConfig?.path;
        return !!path && path.endsWith("create");
    }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        if (this.isCreation()) {
            this.route.queryParamMap.subscribe(params => {
                const copyFrom = params.get('copyFrom');
                if (copyFrom) {
                    queryParams = queryParams.append("copyFrom", copyFrom);
                }
                this.http
                    .get(BASE_URL + "/tiers/tiers/create-form", { params: queryParams })
                    .subscribe(data => this.bean = <TiersBean>data);
                
                this.pageTitle = "Création d'un Tiers"
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const tiersCode = params.get('tiersCode');
                if( !! tiersCode ){
                    queryParams = queryParams.append("tiersCode", tiersCode);
                }
                this.http
                    .get<any>(BASE_URL + `/tiers/tiers/update-form`, {params: queryParams})
                    .subscribe(data => this.bean = data);
                
                this.pageTitle = "Édition du Tiers " + tiersCode
            });
        }
    }

    addAction() {
        this.router.navigate(['edit'], { relativeTo: this.route })
    }

    copyAction() {
        this.router.navigate(['edit', this.bean.tiersCode.value], { relativeTo: this.route.parent })
    }

    saveAction() {
        this.http.post(BASE_URL + `/tiers/tiers`, this.bean).subscribe(data => console.log(data))
    }

    categoryAction() {
        this.dialog.open(CategoryIndexModalComponent);
    }
}
