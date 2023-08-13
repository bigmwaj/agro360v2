import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { ArticleBean, ArticleSearchBean } from 'src/app/backed/bean.stock';

interface ArticleModel extends ArticleBean {
    selected: boolean
}

@Component({
    selector: 'stock-article-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    searchForm?: ArticleSearchBean;

    beans: Array<ArticleModel>;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

    ngOnInit(): void {
        this.http
            .get("http://localhost:8080/stock/article")
            .pipe(map((data: any) => data))
            .subscribe(data => this.beans = <Array<ArticleModel>>data.records);
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
