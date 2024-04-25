
import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { IBeanListTab } from './bean.list.tab';
import { IBeanEditTab } from './bean.edit.tab';
import { MatTabGroup } from '@angular/material/tabs';
import { AbstractBean } from 'src/app/backed/bean.common';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    template: ''
})
export abstract class BeanIndexPage<B extends AbstractBean, L extends IBeanListTab, E extends IBeanEditTab> implements OnInit {

    editingBeans: B[] = [];
    
    currentEditingBean: B | null;
    
    currentEditTab: E | null;

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {

    }

    protected abstract areBeansEqual(b1: B, b2: B):boolean;
    
    protected abstract getListTab(): L;

    protected abstract getEditTabs(): QueryList<E>;

    protected abstract getTabGroup(): MatTabGroup;

    private getEditingBeanIndex(bean:B):number{
        const filter = (e:B) => (ClientOperationEnumVd.CREATE == bean.action && e == bean) || this.areBeansEqual(e, bean);
        return this.editingBeans.findIndex(filter);
    }
    
    private getCurrentEditingBean():B | null{  
        const tabGroup = this.getTabGroup();
        if( tabGroup == null ){
            return null;
        }  

        const selectedIndex = tabGroup.selectedIndex;

        if( selectedIndex != null && selectedIndex > 0 ){
            return this.editingBeans.filter((e, i)=> i == (selectedIndex - 1))[0];
        }   
        return null;
    }

    removeAction(bean:B){
        const selectedTabIndex = this.selectedTab.index;
        const editingBeanIndex = this.getEditingBeanIndex(bean);
        this.editingBeans = this.editingBeans.filter((e, i)=> i != editingBeanIndex);
        if( selectedTabIndex == editingBeanIndex + 1 ){
            this.selectedTab.index = editingBeanIndex //On recule
        }
    }

    addAction(option?:any){
        const listTab = this.getListTab()
        listTab.addAction(option);
    }

    private setCurrentEditingBeanSavable(){
        this.currentEditingBean = this.getCurrentEditingBean();
    }
    
    saveAction(){
        this.currentEditTab?.saveAction();
    }

    refreshPageTitle():void{
        const tabGroup = this.getTabGroup();
        var selectedIndex = tabGroup.selectedIndex
        if( selectedIndex == null ){
            selectedIndex = 0
        }
        this._refreshTabTitle(selectedIndex)
    }

    selectedTabChange($event:any):void{
        this._refreshTabTitle($event.index);
        this.selectedTab.index = $event.index;
        this.setCurrentEditingBeanSavable()
    }

    private _refreshTabTitle(index: number){
        if( index == 0 ){
            const listTab = this.getListTab();
            listTab.refreshPageTitle()
        }else{
            const editTabs = this.getEditTabs();
            let editTab = editTabs.get(index-1);
            editTab?.refreshPageTitle()
        }
    }
}
