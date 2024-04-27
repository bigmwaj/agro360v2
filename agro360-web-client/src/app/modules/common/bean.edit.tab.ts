import { AfterViewInit, Component, EventEmitter, Input, Output } from "@angular/core";
import { AbstractBean } from "src/app/backed/bean.common";
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
    
    abstract saveAction():void;

    protected afterSaveAction(bean:B):void{
        this.bean = bean;
        this.onBeanSave.emit(bean);
    }

    constructor(public ui: UIService){

    }

    ngAfterViewInit():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }
}
