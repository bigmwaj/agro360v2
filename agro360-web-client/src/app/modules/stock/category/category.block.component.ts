import { Component, Input, OnInit } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ArticleCategoryBean } from 'src/app/backed/bean.stock';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule,
        MatTooltipModule
    ],
    selector: 'stock-article-category-block',
    templateUrl: './category.block.component.html'
})
export class CategoryBlockComponent implements OnInit {

    @Input()
    bean: ArticleCategoryBean;

    ngOnInit(): void {

    }

}
