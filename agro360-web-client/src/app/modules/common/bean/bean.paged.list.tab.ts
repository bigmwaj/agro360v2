import { AfterViewInit, Component, OnInit } from "@angular/core";
import { AbstractBean, AbstractSearchBean } from "src/app/backed/bean.common";
import { UIService } from "../service/ui.service";
import { BeanListTab } from "./bean.list.tab";
import { HttpClient, HttpParams } from "@angular/common/http";
import { map } from "rxjs";
import { PageEvent } from "@angular/material/paginator";

@Component({
    standalone: true,
    template: ''
})
export abstract class BeanPagedListTab<B extends AbstractBean, SF extends AbstractSearchBean> extends BeanListTab<B> implements OnInit, AfterViewInit{
   
    searchForm: SF;

    constructor(
        public http: HttpClient,    
        public override ui: UIService) {
        super(ui)
    }

    protected abstract getSearchFormUrl(): string

    protected abstract getSearchUrl(): string;  

    protected abstract getEditFormUrl(): string;

    protected abstract getCreateFormUrl(): string;
    
    protected abstract getEditQueryParam(bean: B): HttpParams;
    
    protected abstract getCreateQueryParam(option?:any): HttpParams

    ngOnInit(): void {
        this.resetSearchFormAction();
    }

    resetSearchFormAction() {
        let currentPageSize: number | null;

        if( this.searchForm ){
            currentPageSize = this.searchForm.pageSize;
        }
        const queryParams = this.initSearchQuery(new HttpParams())
        this.http        
            .get(this.getSearchFormUrl(),   { params: queryParams })  
            .subscribe(data => {
                this.searchForm = this.initSearchForm(<SF>data);
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

    addAction(option?:any) {  
        let queryParams = this.getCreateQueryParam(option);
        this.http.get(this.getCreateFormUrl(), { params: queryParams })
        .subscribe(data => this.appendTab(<B>data));
    }
    
    editAction(bean: B) {
        if( this.isAlreadLoaded(bean) ){
            this.displayTab(bean);
            return;
        }
        let queryParams = this.getEditQueryParam(bean);

        this.http.get(this.getEditFormUrl(), { params: queryParams })
        .subscribe(data => this.appendTab(<B>data));
    }

    protected initSearchForm(sf: SF):SF{
        return sf;
    }

    protected initSearchQuery(queryParams: HttpParams):HttpParams{
        return queryParams;
    }

    handlePageEvent(e: PageEvent) {
        this.searchForm.pageSize = e.pageSize;
        this.searchForm.pageIndex = e.pageIndex;
        this.searchAction(true);
    }

}
