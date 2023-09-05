import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleBean  } from 'src/app/backed/bean.stock';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { IndexModalComponent as UniteIndexModalComponent} from '../unite/index.modal.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { SharedModule } from 'src/app/common/shared.module';
import { EditConversionListComponent } from './edit.conversion.list.component';
import { EditVariantListComponent } from './edit.variant.list.component';
import { BeanTools } from 'src/app/common/bean.tools';

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
        EditConversionListComponent,
        EditVariantListComponent,
        SharedModule
    ],
    selector: 'stock-article-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: ArticleBean;

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
                    .get(BASE_URL + "/stock/article/create-form", { params: queryParams })
                    .subscribe(data => { this.bean = <ArticleBean>data; });

                this.pageTitle = "Création d'un Article"
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const articleCode = params.get('articleCode');
                if (!!articleCode) {
                    queryParams = queryParams.append("articleCode", articleCode);
                }
                this.http
                    .get<any>(BASE_URL + `/stock/article/update-form`, { params: queryParams })
                    .subscribe(data => { this.bean = data; });

                this.pageTitle = "Édition du Article " + articleCode
            });
        }
    }

    addAction() {
        this.router.navigate(['edit'], { relativeTo: this.route })
    }

    copyAction() {
        this.router.navigate(['edit', this.bean.articleCode.value], { relativeTo: this.route.parent })
    }

    saveAction() {
        this.http.post(BASE_URL + `/stock/article`, BeanTools.reviewBeanAction(this.bean)).subscribe(data => console.log(data))
    }
    
    uniteAction() {
        this.dialog.open(UniteIndexModalComponent);
    }

}
