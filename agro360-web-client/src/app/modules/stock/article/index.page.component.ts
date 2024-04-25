
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { ListTabComponent } from './list.tab.component';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports: [
        EditTabComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListTabComponent
    ],
    selector: 'stock-article-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    editingBeans: ArticleBean[] = [];

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Articles & Services')
        }else{
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Articles & Services');
        }
    }
    
    @Input()
    breadcrumb:BreadcrumbItem;
    
    @ViewChild('stock_article')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;
    
    refreshPageTitle():void{
        var selectedIndex = this.tabGroup.selectedIndex
        if( selectedIndex == null ){
            selectedIndex = 0
        }
        this._refreshTabTitle(selectedIndex)
    }

    selectedTabChange($event:any):void{
        this._refreshTabTitle($event.index);
        this.selectedTab.index = $event.index;
    }

    private _refreshTabTitle(index: number){
        if( index == 0 ){
            this.listTab.refreshPageTitle()
        }else{
            let editTab = this.editTabs.get(index-1);
            editTab?.refreshPageTitle()
        }
    }

    private getEditingBeanIndex(bean:ArticleBean):number{
        let filter = (e:ArticleBean) => (ClientOperationEnumVd.CREATE == bean.action && e == bean) 
            || e.articleCode.value == bean.articleCode.value;
        return this.editingBeans.findIndex(filter);
    }

    removeAction(bean:ArticleBean){
        const selectedTabIndex = this.selectedTab.index;
        const editingBeanIndex = this.getEditingBeanIndex(bean);
        this.editingBeans = this.editingBeans.filter((e, i)=> i != editingBeanIndex);
        if( selectedTabIndex == editingBeanIndex + 1 ){
            this.selectedTab.index = editingBeanIndex //On recule
        }
    }

}
