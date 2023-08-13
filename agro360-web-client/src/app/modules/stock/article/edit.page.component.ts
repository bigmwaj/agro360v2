import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleBean } from 'src/app/backed/bean.stock';

const BASE_URL = "http://localhost:8080/stock/article";

interface ArticleModel extends ArticleBean {
    
}

@Component({
    selector: 'stock-article-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: ArticleModel;

    articleCode: string | null;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

    ngOnInit(): void {

        this.route.paramMap.subscribe(params => {
            this.articleCode = params.get('articleCode');

            if( this.articleCode != null ){
                this.http
                    .get<any>(BASE_URL +`/${this.articleCode}`)
                    .subscribe(data => this.bean = data);
            }else{
                this.bean = <ArticleModel>{};
            }   
        });
    }

    addAction() {
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: ArticleModel) {
        this.router.navigate(['edit', bean.articleCode.value], { relativeTo: this.route })
    }

    copyAction(bean: ArticleModel) {
        this.router.navigate(['edit', bean.articleCode.value], { relativeTo: this.route })
    }

    changeStatusAction(bean: ArticleModel) {
        // Open Dialog
    }

    deleteAction(bean: ArticleModel) {
        // Open Dialog
    }

    editCategoryAction() {
        // Open Dialog
    }
}
