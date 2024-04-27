
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanIndexPage } from '../../common/bean.index.page';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';

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
export class IndexPageComponent extends BeanIndexPage<ArticleBean, ListTabComponent, EditTabComponent> implements OnInit {

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

    protected areBeansEqual(b1: ArticleBean, b2: ArticleBean):boolean{
        return b1 == b2 || b1.articleCode.value == b2.articleCode.value;
    }

    protected override getEditTabs(): QueryList<EditTabComponent> {
        return this.editTabs;
    }

    protected override getTabGroup(): MatTabGroup {
        return this.tabGroup;
    }

    protected override getListTab(): ListTabComponent {
        return this.listTab;
    }
}
