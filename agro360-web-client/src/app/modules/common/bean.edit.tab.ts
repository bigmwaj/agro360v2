import { HttpClient, HttpParams } from "@angular/common/http";
import { AfterViewInit, Component, EventEmitter, Input, Output } from "@angular/core";
import { map } from "rxjs";
import { AbstractBean } from "src/app/backed/bean.common";
import { Message } from "src/app/backed/message";
import { ClientOperationEnumVd } from "src/app/backed/vd.common";
import { BeanTools } from "./bean.tools";
import { BreadcrumbItem, UIService } from "./service/ui.service";

@Component({
    standalone: true,
    template: ''
})
export abstract class BeanEditTab<B extends AbstractBean> implements AfterViewInit{

    @Input({required:true})
    breadcrumb: BreadcrumbItem;

    @Input({required:true})
    bean: B;

    @Output()
    onBeanSave = new EventEmitter();

    abstract saveUrl():string;

    protected afterSaveAction(bean:B, option?:any):void{
        this.bean = bean;
        this.onBeanSave.emit(bean);
    }

    constructor(public ui: UIService, public http: HttpClient){

    }

    ngAfterViewInit():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    saveAction(option?:any) {
        const queryParams = this.getSaveQueryParam(option);
        this.http.post(this.saveUrl(), BeanTools.reviewBeanAction(this.bean), { params: queryParams })   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                let _option:any
                if( option ){
                    _option = option;
                }
                _option.data = data;
                this.afterSaveAction(data.record, _option)
            });
    }

    protected getSaveQueryParam(option?:any):HttpParams{
        return new HttpParams();
    }

    protected isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }
}
