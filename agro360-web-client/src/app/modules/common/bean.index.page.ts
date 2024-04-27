
import { QueryList } from '@angular/core';
import { BeanEditTab } from './bean.edit.tab';
import { MatTabGroup } from '@angular/material/tabs';
import { AbstractBean } from 'src/app/backed/bean.common';
import { BeanListTab } from './bean.list.tab';

export abstract class BeanIndexPage<B extends AbstractBean, L extends BeanListTab<B>, E extends BeanEditTab<B>> {

    editingBeans: B[] = [];
    
    currentEditingBean: B | null;
    
    currentEditTab: E | null;

    selectedTab: {index:number} = {index:0}

    protected abstract areBeansEqual(b1: B, b2: B):boolean;
    
    protected abstract getListTab(): L;

    protected abstract getEditTabs(): QueryList<E>;

    protected abstract getTabGroup(): MatTabGroup;

    /*
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
*/
        
    removeTabAction(index:number){
        this.editingBeans = this.editingBeans.filter((e, i)=> i != index);
        if( index == index + 1 ){
            this.selectedTab.index = index //On recule
        }
    }

    addAction(option?:any){
        const listTab = this.getListTab()
        listTab.addAction(option);
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
        this.currentEditTab = null;
        this.currentEditingBean = null;

        if( $event.index != 0 ){
            this.initEditTabContext($event.index);
        }

        this._refreshTabTitle($event.index);
        this.selectedTab.index = $event.index;
    }

    private _refreshTabTitle(index: number){
        if( index == 0 ){
            this.getListTab().ngAfterViewInit()
        }else{
            const editTabs = this.getEditTabs();
            let editTab = editTabs.get(index - 1);
            editTab?.ngAfterViewInit()
        }
    }

    private initEditTabContext(index: number){
        const editTabs = this.getEditTabs();
        let editTab = editTabs.get(index - 1);
        if( editTab ){
            this.currentEditTab = editTab;             
            this.currentEditingBean = this.currentEditTab.bean
        }
    }

    onBeanSaveFronEditTab($event:any){
        this.getListTab().prependItemIfNotInList(<B>$event);
    }
}
