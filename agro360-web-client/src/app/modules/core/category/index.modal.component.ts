import { NestedTreeControl } from '@angular/cdk/tree';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatTreeModule, MatTreeNestedDataSource } from '@angular/material/tree';
import { map } from 'rxjs';
import { CategoryBean } from 'src/app/backed/bean.core';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule,
        MatTreeModule
    ],
    selector: 'core-category-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent implements OnInit {

    bean: CategoryBean;

    treeControl = new NestedTreeControl<CategoryBean>(node => node.children);

    dataSource = new MatTreeNestedDataSource<CategoryBean>();

    hasChild = (_: number, node: CategoryBean) => !!node.children && node.children.length > 0;

    constructor(private http: HttpClient) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('deep', 4)
        this.http
            .get("core/category", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.bean = <CategoryBean>data;
                this.dataSource.data = [this.bean]
            });
    }

}
