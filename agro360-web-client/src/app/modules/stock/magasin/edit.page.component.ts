import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { SharedModule } from 'src/app/common/shared.module';
import { EditCasierListComponent } from './edit.casier.list.component';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule,
        EditCasierListComponent
    ],
    selector: 'stock-magasin-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: MagasinBean;

    pageTitle: string = "Edition";

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

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
                    .get(BASE_URL + "/stock/magasin/create-form", { params: queryParams })
                    .subscribe(data => { this.bean = <MagasinBean>data; });

                this.pageTitle = "Création d'un Magasin"
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const magasinCode = params.get('magasinCode');
                if (!!magasinCode) {
                    queryParams = queryParams.append("magasinCode", magasinCode);
                }
                this.http
                    .get<any>(BASE_URL + `/stock/magasin/update-form`, { params: queryParams })
                    .subscribe(data => { this.bean = data; });

                this.pageTitle = "Édition du Magasin " + magasinCode
            });
        }
    }

    addAction() {
        this.router.navigate(['edit'], { relativeTo: this.route })
    }

    copyAction() {
        this.router.navigate(['edit', this.bean.magasinCode.value], { relativeTo: this.route.parent })
    }

    saveAction() {
        this.http.post(BASE_URL + `/stock/magasin`, this.bean).subscribe(data => console.log(data))
    }


}
