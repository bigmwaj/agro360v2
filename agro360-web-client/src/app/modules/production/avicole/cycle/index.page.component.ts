import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { CycleBean, CycleSearchBean } from 'src/app/backed/bean.production.avicole';
import { BeanList } from 'src/app/modules/common/bean.list';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { ProductionAvicoleService } from '../production.avicole.service';

@Component({
    standalone: true,
    imports: [
        SharedModule,
    ],
    selector: 'production-avicole-cycle-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<CycleBean> implements OnInit {
    
    searchForm: CycleSearchBean;

    displayedColumns: string[] = [
        'select',
        'cycleCode',
        'magasin',
        'status',
        'racePlanifiee',
        'quantitePlanifiee',
        'datePlanifiee',
        'raceEffective',
        'quantiteEffective',
        'dateEffective',
        'description',
        'actions'
    ];

    @ViewChild(MatTable)
    table: MatTable<CycleBean>;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog,
        private service: ProductionAvicoleService) {
        super()
    }

    override getViewChild(): MatTable<CycleBean> {
        return this.table;
    }

    getKeyLabel(bean: CycleBean): string {
        return bean.cycleCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(`production/avicole/cycle/search-form`)
            .subscribe(data => {
                this.searchForm = <CycleSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`production/avicole/cycle`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: CycleBean) {
        this.router.navigate(['edit', bean.cycleCode.value], { relativeTo: this.route })
    }

    copyAction(bean: CycleBean) {
        this.router.navigate(['create'], { relativeTo: this.route, queryParams: { 'copyFrom': bean.cycleCode.value } })
    }

    deleteAction(bean: CycleBean) {

    }

    redirectJourneePage(bean: CycleBean):void{
        this.router.navigate(
            [
                '/production/avicole/journee',
                bean.cycleCode.value
            ]
        )
    }

    redirectPrevisionPage(bean: CycleBean):void{
        this.router.navigate(
            [
                '/production/avicole/prevision',
                bean.cycleCode.value
            ]
        )
    }
}
