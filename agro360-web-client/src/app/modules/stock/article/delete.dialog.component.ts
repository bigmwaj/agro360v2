import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { map } from 'rxjs';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-article-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: ArticleBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: any,
        private http: HttpClient,
        private ui: UIService) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('articleCode', this.data.articleCode)
        this.http
            .get(`stock/article/delete-form`, { params: queryParams })    
            .subscribe(data => this.bean = <ArticleBean>data);
    }

    deleteAction() {
        this.http.post(`stock/article`, this.bean)   
            .pipe(map((data: any) => data))
            .subscribe(data => this.ui.displayFlashMessage(data.messages))
    }  
}
