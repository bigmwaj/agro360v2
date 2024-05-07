import { AfterViewInit, Component, Input } from "@angular/core";
import { AbstractBean } from "src/app/backed/bean.common";
import { BeanList } from "./bean.list";
import { BreadcrumbItem, UIService } from "./service/ui.service";

@Component({
    standalone: true,
    template: ''
})
export abstract class BeanListTab<B extends AbstractBean> extends BeanList<B> implements AfterViewInit{
    
    @Input({required:true})
    breadcrumb:BreadcrumbItem;

    @Input({required:true})
    editingBeans: B[] = [];

    @Input({required:true})
    selectedTab: {index:number};

    constructor( public ui: UIService){
        super();
    }

    abstract addAction(option?:any): void;
    
    protected abstract areBeansEqual(b1: B, b2: B): boolean;

    ngAfterViewInit():void{
        this.ui.setBreadcrumb(this.breadcrumb);
    }

    private getEditingIndex(bean:B){
        return this.editingBeans.findIndex(e => this.areBeansEqual(bean, e))
    }

    protected isAlreadLoaded(bean:B){
        const index = this.getEditingIndex(bean);
        return index >= 0;
    }
    
    protected displayTab(bean:B){
        const index = this.getEditingIndex(bean);
        this.selectedTab.index = index + 1;
    }

    protected appendTab(bean:B){
        this.editingBeans.push(bean); 
        this.selectedTab.index = this.editingBeans.length;
    }

    removeFromEditingBeansList(bean: B) {        
        const tmp = this.editingBeans.filter(b => !this.areBeansEqual(bean, b));
        this.editingBeans.length = 0;
        this.editingBeans.push(...tmp);
    }

    override removeItem(bean: B) {
        super.removeItem(bean);
        if( this.isAlreadLoaded(bean) ){
            this.removeFromEditingBeansList(bean);
        }
    }

    private findRefFromDataSource(bean:B):B | undefined{
        return this.getData().filter(b => this.areBeansEqual(bean, b)).pop();
    }

    prependItemIfNotInList(bean: B){
        const ref = this.findRefFromDataSource(bean);
        if( !ref ){
            this.prependItem(bean);
        }else{
            this.replaceItemWith(ref, bean)
        }
    }
}
