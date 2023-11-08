import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { MatDialog } from '@angular/material/dialog';
import { IndexModalComponent as UniteIndexModalComponent } from '../unite/index.modal.component';
import { SharedModule } from 'src/app/common/shared.module';
import { EditConversionListComponent } from './edit.conversion.list.component';
import { EditVariantListComponent } from './edit.variant.list.component';
import { BeanTools } from 'src/app/common/bean.tools';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';
import { map } from 'rxjs';

@Component({
    standalone: true,
    imports: [
        EditConversionListComponent,
        EditVariantListComponent,
        SharedModule
    ],
    selector: 'stock-article-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: ArticleBean;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        private dialog: MatDialog,
        private ui: UIService) { }

    isCreation(): boolean {
        let path = this.route.routeConfig?.path;
        return !!path && path.endsWith("create");
    }

    ngOnInit(): void {
        this.ui.setTitle(`Création d'un Article`)
        let queryParams = new HttpParams();
        if (this.isCreation()) {
            this.route.queryParamMap.subscribe(params => {
                const copyFrom = params.get('copyFrom');
                if (copyFrom) {
                    queryParams = queryParams.append("copyFrom", copyFrom);
                }
                this.http
                    .get(`stock/article/create-form`, { params: queryParams })  
                    .subscribe(data => { this.bean = <ArticleBean>data; });

                    this.ui.setTitle(`Création d'un Article`)
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const articleCode = params.get('articleCode');
                if (!!articleCode) {
                    queryParams = queryParams.append("articleCode", articleCode);
                }
                this.http
                    .get<any>(`stock/article/edit-form`, { params: queryParams })         
                    .subscribe(data => { this.bean = data; });

                this.ui.setTitle(`Édition du Article ${articleCode}`)
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
        console.log(this.bean)
        this.http.post(`stock/article`, BeanTools.reviewBeanAction(this.bean))   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.redirectToEditPage(data.id);
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }
    
    uniteAction() {
        this.dialog.open(UniteIndexModalComponent);
    }

    private redirectToEditPage(id:string):void{
        this.router.navigate(
            [
                '/stock/article',
                'edit', 
                id
            ]
        )
    }

}
