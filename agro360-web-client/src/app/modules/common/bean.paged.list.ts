import { AfterViewInit, Component } from "@angular/core";
import { AbstractBean, AbstractSearchBean } from "src/app/backed/bean.common";
import { UIService } from "./service/ui.service";
import { BeanListTab } from "./bean.list.tab";
import { HttpClient, HttpParams } from "@angular/common/http";
import { map } from "rxjs";
import { PageEvent } from "@angular/material/paginator";
import { BeanList } from "./bean.list";

@Component({
    standalone: true,
    template: ''
})
export abstract class BeanPagedList<B extends AbstractBean, SF extends AbstractSearchBean> extends BeanList<B>{
   
    searchForm: SF;

    constructor(
        public http: HttpClient) {
        super()
    }

    protected abstract getSearchFormUrl(): string

    protected abstract getSearchUrl(): string

    resetSearchFormAction() {
        let currentPageSize: number | null;

        if( this.searchForm ){
            currentPageSize = this.searchForm.pageSize;
        }
        this.http        
            .get(this.getSearchFormUrl())  
            .subscribe(data => {
                this.searchForm = <SF>data;

                if( currentPageSize ){
                    this.searchForm.pageSize = currentPageSize;
                }

                this.searchAction();
            });
    }

    searchAction(preservePage?: boolean) {
        if( !preservePage ){
            this.searchForm.pageIndex = 0;
        }

        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(this.getSearchUrl(), { params: queryParams })  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
                this.searchForm.length = data.total;
            });
    }

    handlePageEvent(e: PageEvent) {
        this.searchForm.pageSize = e.pageSize;
        this.searchForm.pageIndex = e.pageIndex;
        this.searchAction(true);
    }

}
